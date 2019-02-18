%include "scripts.inc"

global _start           ;глобальная метка

section .text
    _start:
        mov eax, 0      ;eax = 0

    again:
        PRINT "Hello"   ;"hello"
        PUTCHAR 10      ;\n
        inc eax         ;increment
        cmp eax, 5      ;Compare
        jl again        ;jumpIfLower
        FINISH