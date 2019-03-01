;Найти сумму 
;кубов всех отрицательных элементов массива A={a[i]}, 
;удовлетворяющих условию: a[i] <=d

                                                                ;Макрос (аля функция) с двумя параметрами print(str, format0)
%macro PRINT 2      
    pushad

    push %1             
    push %2
    call printf
    
    pop dword [temp]                                            ;что бы достать из стека переданные параметры, будем убирать их в temp
    pop dword [temp]
    popad
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
    intermediate_res dd "Intermediate result: "
    finished_res dd "Finished result: "
    array dd 0, 1, 2, -1, -2, 6, -10, 34, -20, -12, -3, 1543
    parameter dd -10
    size dd 12                                                  ;количество циклов (наборов)
    result dd 0
    format_number dd ' %2d', 0x0a                               ;0x0a = \n, 0
    format_text dd ' %s ', 0x0a

section .text                                                   ;директива кода
    main:  
        mov ecx, [size]                                        ;counter - кол-во итераций
        .cycle:                                                 ;локальняа метка
            push dword [array + (ecx-1)*4]                   ;передача параметров и сохранение знчения счетчика
            push ecx

            call function

            PRINT intermediate_res, format_text
            PRINT eax, format_number

            mov edx, [result]
            add edx, eax
            jo overflow_ex
            mov dword [result], edx

            pop ecx
            pop dword [temp]                                    ;держим стек чистым
            loop .cycle 
            
        PRINT finished_res, format_text
        PRINT edx, format_number
        call exit

    function: 
        push ebp                                                ;созраняем старое значение
        mov ebp, esp                                            ;будем использовать для доступа к аргументам и локальным переменным
                                                                ;выделяем место в стеке под локальные переменные (нам хватит трех)
                                                                ;ebp + 8 = ecx, ebp + 4 = адресс возврата
        mov eax, [ebp + 12]                                     ;значение массива

        cmp eax, 0                                            ;сравниваем числа и идем в соответствующую ветку
        jge not_negative
        cmp eax, [parameter]
        jg not_negative

        mov ebx, eax
        imul ebx
        jo overflow_ex

        imul ebx
        jo overflow_ex

        continue:
        mov esp, ebp                                            ;восстанавливаем значение esp 
        pop ebp                
        ret

        not_negative:
        mov eax, 0
        mov esp, ebp
        pop ebp
        ret

    overflow_ex:
        push ebp
        mov ebp, esp
        PRINT inf_message, format_text
        mov esp, ebp
        pop ebp
        call exit