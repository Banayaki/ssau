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

%macro SCAN 2
    pushad
    push %2
    push %1
    call scanf
        

    pop dword [temp]
    pop dword [temp]
    popad
%endmacro

                                                                ;Проверка на равенство с нулем, применяется для предотвращения деления на ноль
%macro ZERO_CHECK 1
    cmp %1, 0
    je divide_by_zero_ex
%endmacro

%macro THORW_EXCEPTION 1
    push ebp
    mov ebp, esp
    PRINT %1, format_textln
    mov esp, ebp
    pop ebp
    call exit
%endmacro

%macro POW 2
    pushad
    mov ecx, %2

    cmp ecx, 0
    je .equals

    mov temporal_val, qword %1
    mov returned_val, qword %1
    fld qword [temporal_val]
    .cycle:
        fld qword [temporal_val]
        fmulp
        loop .cycle
    jmp .return:

    .equals:
        mov qword returned_val, 1

    .return:
%endmacro

global main                                                     ;глобальная метка
extern printf                                                   ;импорт СИшного printf и exit (что бы не мучаться с прерываниями)
extern exit
extern scanf

section .bss                                                    ;секция в которой объявляются не инициализированные участки памяти (переменные)
    temp resd 1                                                 ;resd - reserve dword
    x resq 1
    eps resq 1
    degree resd 1
    result resq 1
    accurate_val resq 1
    returned_val resq 1

section .data                                                   ;секция объявления инициализированных данных
    enter_x dd "Enter the x (x < |1|): "
    enter_eps dd "Enter the eps: "
    accurate_result dd "Accurate result: "

    overflow_ex dd "EXCEPTION: Some number doesn't fit. Exiting...."
    divide_by_zero_ex dd "EXCEPTION: Arithmetic exception. Divide by zero. Exiting...."
    incorrect_number_format_ex dd "EXCEPTION: Bad number"
    out_of_bounds_ex dd "EXCEPTION: Out of bounds"

    const_neg_and_pos_one dq 1.0
    const_neg dq -1.0

    const_2 dq 2.0
                                                 
    format_number dd ' %2d', 0x0a                               ;0x0a = \n, 0
    format_float dd ' %g ', 0x0a
    format_textln dd ' %s ', 0x0a
    format_text dd ' %s '
    scan_float dd '%lf', 0

section .text                                                   ;директива кода
    main:  
        PRINT format_text, enter_x
        SCAN scan_float, x

        call x_bounds_check

        PRINT format_text, enter_eps
        SCAN scan_float, eps

        call accurate

        PRINT format_text, accurate_result
        DOUBLE_PRINT dword [accurate_val + 4], dword [accurate_val]

        call function
        call exit

    x_bounds_check:
        push ebp                                           
        mov ebp, esp   

        fld1
        fld qword [x]
        fcomip
        fstp st0

        ja out_of_bounds

        fld qword [const_neg_one]
        fld qword [x]
        fcomip
        fstp st0

        jb out_of_bounds

        mov esp, ebp                                           
        pop ebp                
        ret

    accurate:
        fld1
        fld qword [x]
        fpatan

        fstp qword [accurate_val]
        ret

    function: 
        push ebp                                                ;созраняем старое значение
        mov ebp, esp
        mov ecx, 1   

        .cycle:
            push dword [const_neg_one + 4]
            push dword [const_neg_one]
            push ecx
            call pow

            fld qword [returned_val]

            mov eax, ecx
            mov ebx, 2
            mul ebx
            add eax, 1
            push dword [x + 4]
            push dword [x]
            push ebx
            call pow

            fld qword [returned_val]
            fmulp

            mov dword [temp], ebx
            fild dword [temp]
            fdivp

            fstp qword [result]
            DOUBLE_PRINT dword [result + 4], dword [result]

        continue:
        mov esp, ebp                                            ;восстанавливаем значение esp 
        pop ebp                
        ret

    pow:
        push ebp                                               
        mov ebp, esp 
        pushad

        mov ecx, [ebp + 8]
        mov dword [returned_val], [ebp + 12]
        mov dword [returned_val + 4], [ebp + 16]
        
        popad
        mov esp, ebp                                            
        pop ebp                
        ret

    check_with_eps:
        push ebp                                                
        mov ebp, esp 

        mov esp, ebp                                            
        pop ebp                
        ret


    overflow:
        THORW_EXCEPTION overflow_ex

    divide_by_zero:
        THORW_EXCEPTION divide_by_zero_ex

    broken_numbers:
        THORW_EXCEPTION incorrect_number_format_ex

    out_of_bounds:
        THORW_EXCEPTION out_of_bounds_ex