; (2*c - b/2 +1)/(a*a-7); a=sqrt(7) is bad number

                                                                ;Макрос (аля функция) с двумя параметрами print(str, format0)
%macro PRINT 2      
    push eax                                                    ;во время вызова printf следующие регистры сбросятся. Что бы избежать этого, временно уберем их в стек
    push ebx
    push ecx

    push %1             
    push %2
    call printf
    
    pop dword [temp]                                            ;что бы достать из стека переданные параметры, будем убирать их в temp
    pop dword [temp]
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
    a_params dd 0, 1, 2, 3, 4, 5, 3
    b_params dd 0, 1, 2, 3, 4, 5, 10
    c_params dd 0, 1, 2, 3, 4, 5, 10
    count dd 7                                                  ;количество циклов (наборов)
    format_number dd ' %2d', 0x0a                               ;0x0a = \n, 0
    format_text dd ' %s ', 0x0a

section .text                                                   ;директива кода
    main:  
        mov ecx, [count]                                        ;counter - кол-во итераций
        .cycle:                                                 ;локальняа метка
            push dword [a_params + (ecx-1)*4]                   ;передача параметров и сохранение знчения счетчика
            push dword [b_params + (ecx-1)*4]
            push dword [c_params + (ecx-1)*4]
            push ecx
            call function
            pop ecx
            pop dword [temp]                                    ;держим стек чистым
            pop dword [temp]
            pop dword [temp]
            loop .cycle 
        call exit

    function: 
        push ebp                                                ;созраняем старое значение
        mov ebp, esp                                            ;будем использовать для доступа к аргументам и локальным переменным
        sub esp, 12                                             ;выделяем место в стеке под локальные переменные (нам хватит трех)
                                                                ;ebp + 8 = ecx, ebp + 4 = адресс возврата
        mov eax, [ebp + 20]
        mov ebx, [ebp + 16]
        mov ecx, [ebp + 12]

                                                                ;eax = 3*3 = 9
        imul eax                                                ;возведение в квадрат (умножение на само себя), т.к. работаем только с 32битнмыи цифрами, лишь проверим на переполнение
        jo overflow_ex                                          ;условный переход. Если флаг CO (overflow) = 1

        sub eax, 7                                              ;eax = 9 - 7 = 2
        jo overflow_ex

        mov [ebp - 4], eax                                      ;сохранили в стек полученный знаменатель уравнения

        mov eax, ecx                                            ;eax = ecx = 10
        mov ecx, 2                                              ;ecx = 2
        imul ecx                                                ;eax = eax * 2 = 20
        jo overflow_ex

        mov [ebp - 8], eax                                      ;сохранили промежуточный результат в стек

        mov eax, ebx                                            ;eax = ebx = 10
        xor edx, edx                                            ;обнулили edx (иначе ассемблер будет считать EDX - старшей частью делителя)
        idiv ecx                                                ;eax = eax / ecx = 10 / 2 = 5

        mov [ebp - 12], eax                                     ;сохраили промежуточный результат 

        mov ecx, [ebp - 8]                                      ;ecx = 20

        sub ecx, eax                                            ;ecx = ecx - eax = 20 - 5 = 15
        jo overflow_ex

        add ecx, 1                                              ;ecx += 1 = 16
        jo overflow_ex

        mov eax, ecx                                            ;eax = 16
        mov ecx, [ebp - 4]                                      ;ecx = 2

        xor edx, edx                                            ;edx = 0
        ZERO_CHECK ecx                                          ;проверка что бы не разделить на ноль. (в нашем случае мы никогда не сможем этого добиться)
        idiv ecx                                                ;eax = eax / ecx = 16 / 2 = 8

        PRINT eax, format_number

        mov esp, ebp                                            ;восстанавливаем значение esp 
        pop ebp                
        ret

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

