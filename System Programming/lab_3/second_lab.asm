                                                                ;Макрос (аля функция) с двумя параметрами print(str, format0)
%macro PRINT 2      
    push eax                                                    ;во время вызова printf следующие регистры сбросятся. Что бы избежать этого, временно уберем их в стек
    push ebx
    push ecx
    push edx

    push %1             
    push %2
    call printf
    
    pop dword [temp]                                            ;что бы достать из стека переданные параметры, будем убирать их в temp
    pop dword [temp]
    pop edx
    pop ecx
    pop ebx
    pop eax
%endmacro

                                                                ;Проверка на равенство с нулем, применяется для предотвращения деления на ноль
%macro ZERO_CHECK 1
    cmp %1, 0
    je divide_by_zero_ex
%endmacro

global main                                                     ;глобальная метка
extern printf                                                   ;импорт СИшного printf и exit (что бы не мучаться с прерываниями)
extern exit

section .bss                                                    ;секция в которой объявляются не инициализированные участки памяти (переменные)
    temp resd 1                                                 ;resd - reserve dword

section .data                                                   ;секция объявления инициализированных данных
    inf_message dd "Some number doesn't fit in integer. Exiting...."
    zero_message dd "Arithmetic exception. Divide by zero. Exiting...."
    bigger dd "A bigger than B"
    equal dd "A = B"
    less dd "A less than B"
    a_params dd 0, 1, 2, 30, 4, 5, 0
    b_params dd 9, 10, 2, 3, 2, 5, 0
    count dd 7                                                  ;количество циклов (наборов)
    format_number dd ' %2d', 0x0a                               ;0x0a = \n, 0
    format_text dd ' %s ', 0x0a

section .text                                                   ;директива кода
    main:  
        mov ecx, [count]                                        ;counter - кол-во итераций
        .cycle:                                                 ;локальняа метка
            push dword [a_params + (ecx-1)*4]                   ;передача параметров и сохранение знчения счетчика
            push dword [b_params + (ecx-1)*4]
            push ecx
            call function
            PRINT eax, format_number
            pop ecx
            pop dword [temp]                                    ;держим стек чистым
            pop dword [temp]
            loop .cycle 
        call exit

    function: 
        push ebp                                                ;созраняем старое значение
        mov ebp, esp                                            ;будем использовать для доступа к аргументам и локальным переменным
                                                                ;выделяем место в стеке под локальные переменные (нам хватит трех)
                                                                ;ebp + 8 = ecx, ebp + 4 = адресс возврата
        mov ebx, [ebp + 16]
        mov eax, [ebp + 12]

        cmp ebx, eax                                            ;сравниваем числа и идем в соответствующую ветку
        je equals
        jg a_bigger_then
        jl a_less_then

        continue:
        mov esp, ebp                                            ;восстанавливаем значение esp 
        pop ebp                
        ret

    equals:
        PRINT equal, format_text
        mov eax, 10
        jmp continue

    a_bigger_then:
        PRINT bigger, format_text
        cmp ebx, 0                                              ;проверяем деление на ноль
        je divide_by_zero_ex

        imul eax                                                ;b*b
        jo overflow_ex

        cdq
        idiv ebx                                                ;b*b/a

        mov ebx, 1                                              ;ebx = 1
        sub ebx, eax                                            ;1 - b*b/a
        jo overflow_ex

        mov eax, ebx
        jmp continue

    a_less_then:
        PRINT less, format_text
        sub eax, 1                                              ;eax = b -  1
        jz divide_by_zero_ex
        jo overflow_ex

        mov edi, eax                                            ;edi = eax

        mov eax, ebx                                            ;eax = a
        mov ebx, 3                                              ;ebx = 3
        imul ebx                                                ;eax = a * 3
        jo overflow_ex

        sub eax, 5                                              ;eax = a * 3 - 5
        jo overflow_ex
        
        cdq
        idiv edi                                                ;eax = (a * 3 - 5) / b - 1

        jmp continue

    overflow_ex:
        push ebp
        mov ebp, esp
        PRINT inf_message, format_text
        mov esp, ebp
        pop ebp
        call exit

    divide_by_zero_ex:
        push ebp
        mov ebp, esp
        PRINT inf_message, format_text
        mov esp, ebp
        pop ebp
        call exit

