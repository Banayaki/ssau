; (2*c - b/2 +1)/(a*a-7); a=sqrt(7) is bad number


%macro PRINT_REG_VAL 1
    ; %define format_1 ' %2d', 0x0a
    ; format_1 db ' %2d', 0x0a
    ; %%str db ' %2d'
	mov dword [msg], %1
    push dword [msg]
    push format
    call printf
%endmacro

%macro PRINT_TEXT 1
    mov dword [msg], %1
    push dword [msg]
    push format_text
    call printf
%endmacro

global main
extern printf
extern exit

section .bss
    msg resd 1
    temp resd 1

section .data                       ;директива объявления данных
    inf_message dd "It doesn't calculate numbers bigger than integer"
    a_params dd 0, 1, 2, 3, 4, 5
    b_params dd 0, 1, 2, 3, 4, 5
    c_params dd 0, 1, 2, 3, 4, 5
    format dd ' %2d', 0x0a              ;0x0a = \n
    format_text dd ' %s ', 0x0a

section .text                       ;директива кода

    main:  
        mov ecx, 5
        .cycle:
            push dword [a_params + ecx*4]
            push dword [b_params + ecx*4]
            push dword [c_params + ecx*4]
            push ecx
            call function
            pop ecx
            loop .cycle 
        call exit

    function: 
        push ebp
        mov ebp, esp
        sub esp, 12     ;объем под локальные переменные

        mov eax, [ebp + 8]
        mov ebx, [ebp + 12]
        mov ecx, [ebp + 16]

        PRINT_TEXT inf_message

        imul eax
        ; jz overflow_warning

        sub eax, 7
        ; jz overflow_warning

        ; TODO проверка на выход за 32бита
        ; mov [ebp - 4], edx ;старшая часть правой части


        mov [ebp - 4], eax ;младшая часть правой части

        mov eax, ecx
        mov ecx, 2
        imul ecx
        ; jz overflow_warning

        ; TODO проверка старшей част

        mov [ebp - 8], eax ;младшая часть c*2

        mov eax, ebx
        idiv ecx

        mov [ebp - 12], eax ;младшая часть b/2

        mov ecx, [ebp - 8]

        sub ecx, eax ;big
        ; jz overflow_warning

        add ecx, 1      ;не думаем о переполнении пока
        ; jz overflow_warning

        mov eax, ecx
        mov ecx, dword [ebp - 4]

        xor edx, edx
        idiv ecx    ;не думаем о переполнении

        ; PRINT_REG_VAL eax

        mov esp, ebp
        pop ebp
        ret

    ; overflow_warning:
        ; ret

