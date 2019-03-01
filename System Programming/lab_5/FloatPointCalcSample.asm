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
    a_num resd 1
    b_num resd 1
    c_num resd 1
    result resd 1

section .data                                                   ;секция объявления инициализированных данных
    inf_message dd "Some number doesn't fit in integer. Exiting...."
    zero_message dd "Arithmetic exception. Divide by zero. Exiting...."
    a_message dd "a = "
    b_message dd "b = "
    c_message dd "c = "
    a_params dd 7.0, 16.0
    b_params dd 2.0, 10.0
    c_params dd 10.0, 1.0
    const2 dd 2.0
    const7 dd 7.0
    count dd 2                                                  ;количество циклов (наборов)
    format_number dd ' %2d', 0x0a                               ;0x0a = \n, 0
    format_float dd ' %g ', 0x0a
    format_textln dd ' %s ', 0x0a
    format_text dd ' %s '

section .text                                                   ;директива кода
    main:  
        mov ecx, [count]                                        ;counter - кол-во итераций
        .cycle:                                                 ;локальняа метка
            push ecx
            call function
            pop ecx
            loop .cycle 
        call exit

    function: 
        push ebp                                                ;созраняем старое значение
        mov ebp, esp                                            ;будем использовать для доступа к аргументам и локальным переменным
        
        fld dword [c_params + (ecx-1)*4]
        fld dword [const2]
        fmulp

        fst dword [result]
        PRINT dword [result], format_float

        fld dword [b_params + (ecx-1)*4]
        fld dword [const2]
        fdivp

        fsubp
        fld1
        faddp

        fld dword [a_params + (ecx-1)*4]
        fld dword [a_params + (ecx-1)*4]
        fmulp
        
        fld dword [const7]
        fsubp
        fdivp

        fstp dword [result]
        PRINT dword [result], format_float

        mov esp, ebp                                            ;восстанавливаем значение esp 
        pop ebp                
        ret

    overflow_ex:
        push ebp
        mov ebp, esp
        PRINT inf_message, format_textln
        mov esp, ebp
        pop ebp
        call exit

    divide_by_zero_ex:
        push ebp
        mov ebp, esp
        PRINT zero_message, format_textln
        mov esp, ebp
        pop ebp
        call exit

