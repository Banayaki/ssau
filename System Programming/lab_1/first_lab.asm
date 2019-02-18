; (2*c - b/2 +1)/(a*a-7); a=sqrt(7) is bad number


%macro PRINT 1
	mov dword [msg], %1
    push dword [msg]
    %define format_1	db	 ' %2d', 0x0a
    push format 
    call printf
%endmacro


global main
extern printf
extern exit

section .bss
    msg resd 1
    temp resd 1

section .data                       ;директива объявления данных
    a_params dd 0, 1, 2, 3, 4, 5
    b_params dd 0, 1, 2, 3, 4, 5
    c_params dd 0, 1, 2, 3, 4, 5
    format db ' %2d', 0x0a              ;0x0a = \n

section .text                       ;директива кода

    function: 
        push ebp
        mov ebp, esp
        sub esp, 16     ;объем под локальные переменные

        mov eax, [ebp + 8]
        mov ebx, [ebp + 12]
        mov ecx, [ebp + 16]

        PRINT eax

        ; imul eax
        ; sub eax, 7

        ; mov [ebp - 4], edx ;старшая часть правой части
        ; mov [ebp - 8], eax ;младшая часть правой части

        ; mov eax, ecx
        ; mov ecx, 2
        ; imul ecx

        ; mov [ebp - 12], eax ;младшая часть а*2
        ; mov [ebp - 16], edx ;старшая часть а*2

        ; xor edx, edx
        ; mov eax, ebx
        ; idiv ecx

        ; mov ecx, [ebp - 12]
        ; mov edx, [ebp - 16]
        ; sub ebx, eax ;small
        ; sbb edx, ebx ;big

        ; add ebx, 2      ;не думаем о переполнении пока

        ; mov eax, ebx

        ; idiv dword [ebp - 12]    ;не думаем о переполнении

        ; mov dword [msg], eax
        ; ; PRINT dword [msg]
        ; ; push 'bor'
        ; ; push 'sud'
        ; push dword [msg]
        ; push format
        ; call printf

        mov esp, ebp
        pop ebp
        ret

    main:  
        mov ecx, 5
        loop_1:
            push dword [a_params + ecx*4]
            push dword [b_params + ecx*4]
            push dword [c_params + ecx*4]
            push ecx
            call function
            pop ecx
            loop loop_1 
        call exit
