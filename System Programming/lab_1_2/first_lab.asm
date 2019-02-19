; (2*c - b/2 +1)/(a*a-7); a=sqrt(7) is bad number

%macro PRINT 2
    sub esp, 20
    push eax
    push ebx
    push ecx

    push %1
    push %2
    call printf
    
    pop dword [temp]
    pop dword [temp]
    pop ecx
    pop ebx
    pop eax
    add esp, 20
%endmacro

%macro ZERO_CHECK 1
    cmp %1, 0
    je divide_by_zero_ex
%endmacro

global main
extern printf
extern exit

section .bss
    msg resd 255
    temp resd 1

section .data                       ;директива объявления данных
    inf_message dd "Some number doesn't fit in integer. Exiting...."
    zero_message dd "Arithmetic exception. Divide by zero. Exiting...."
    a_params dd 0, 1, 2, 3, 4, 5
    b_params dd 0, 1, 2, 3, 4, 5
    c_params dd 0, 1, 2, 3, 4, 5
    format_number dd ' %2d', 0x0a, 0              ;0x0a = \n
    format_text dd ' %s ', 0x0a, 0

section .text                       ;директива кода

    main:  
        mov ecx, 6
        .cycle:
            push dword [a_params + (ecx-1)*4]
            push dword [b_params + (ecx-1)*4]
            push dword [c_params + (ecx-1)*4]
            push ecx
            call function
            pop ecx
            loop .cycle 
        call exit

    function: 
        push ebp
        mov ebp, esp
        sub esp, 12     ;объем под локальные переменные

        mov eax, [ebp + 20]
        mov ebx, [ebp + 16]
        mov ecx, [ebp + 12]

        imul eax
        jo overflow_ex

        sub eax, 7
        jo overflow_ex

        mov [ebp - 4], eax ;младшая часть правой части

        mov eax, ecx
        mov ecx, 2
        imul ecx
        jo overflow_ex

        mov [ebp - 8], eax ;младшая часть c*2

        mov eax, ebx
        xor edx, edx
        idiv ecx

        mov [ebp - 12], eax ;младшая часть b/2

        mov ecx, [ebp - 8]

        sub ecx, eax
        jo overflow_ex

        add ecx, 1  
        jo overflow_ex

        mov eax, ecx
        mov ecx, dword [ebp - 4]

        xor edx, edx
        ZERO_CHECK ecx
        idiv ecx

        PRINT eax, format_number

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

    divide_by_zero_ex:
        push ebp
        mov ebp, esp
        PRINT inf_message, format_text
        mov esp, ebp
        pop ebp
        call exit

