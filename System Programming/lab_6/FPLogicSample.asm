; (2*c - b/2 +1)/(a*a-7); a=sqrt(7) is bad number

                                                                ;Макрос (аля функция) с двумя параметрами print(str, format0)
%macro PRINT 2      
    pushad
                                                        ;во время вызова printf следующие регистры сбросятся. Что бы избежать этого, временно уберем их в стек
    push %1             
    push %2
    call printf
    
    pop dword [temp]                                            ;что бы достать из стека переданные параметры, будем убирать их в temp
    pop dword [temp]

    popad
%endmacro

%macro DOUBLE_PRINT 2
    pushad

    push %1
    push %2
    push format_float
    call printf

    pop dword [temp]
    pop dword [temp]
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
    a_num resd 1
    b_num resd 1
    c_num resd 1
    result resq 1

section .data                                                   ;секция объявления инициализированных данных
    inf_message dd "Some number doesn't fit in integer. Exiting...."
    zero_message dd "Arithmetic exception. Divide by zero. Exiting...."
    bigger dd "A bigger than B"
    equal dd "A = B"
    less dd "A less than B"
    a_message dd "a = "
    b_message dd "b = "
    a_params dd 7.0, 16.0, 3.0
    b_params dd 12.0, 10.0, 3.0
    const3 dd 3.0
    const2 dd 2.0
    const8 dd 8.0
    const26 dd 26.0
    count dd 3                                                  ;количество циклов (наборов)
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
            fstp qword [result]
            DOUBLE_PRINT dword [result + 4], dword [result]
            pop ecx
            loop .cycle 
        call exit

    function: 
        push ebp                                                ;созраняем старое значение
        mov ebp, esp                
        
        fld dword [b_params + (ecx-1)*4]
        fld dword [a_params + (ecx-1)*4]

        fcomip
        fstp st0       

        jp broken_numbers
        jz a_equals_b
        jc a_less_then_b
        jmp a_bigger_then_b

        continue:
        mov esp, ebp                                            ;восстанавливаем значение esp 
        pop ebp                
        ret

    a_bigger_then_b:
        PRINT bigger, format_textln
        fld dword [const3]
        fld dword [a_params + (ecx-1)*4]
        fmulp

        fld dword [const2]
        fld dword [b_params + (ecx-1)*4]
        fmulp

        fsubp

        fld dword [const8]
        fld dword [const2]
        fld dword [a_params + (ecx-1)*4]
        fmulp
        fsubp
        fdivp

        jmp continue

    a_less_then_b:
        PRINT less, format_textln

        fld dword [const2]
        fld dword [a_params + (ecx-1)*4]
        fmulp

        fld1
        fsubp

        fld dword [b_params + (ecx-1)*4]
        fdivp

        jmp continue

    a_equals_b:
        PRINT equal, format_textln
        fld dword [const26]
        jmp continue


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

    broken_numbers:
        push ebp
        mov ebp, esp
        PRINT zero_message, format_textln
        mov esp, ebp
        pop ebp
        call exit

