DATA SEGMENT 
	 x DD
	 y DD
	 true DD
	 false DD
DATA ENDS 
CODE SEGMENT 
	mov eax, 2
	mov x,eax
	mov eax, 2
	mov y,eax
	mov eax, 0
	mov true,eax
	mov eax, 1
	mov false,eax
	mov eax, x
	push eax
	mov eax, 1
	pop ebx
	add eax, ebx
	push eax
	mov eax, y
	push eax
	mov eax, 1
	pop ebx
	add eax, ebx
	pop ebx
	sub ebx, eax
	jnz etiq_sinon_1
	mov eax, true
	jmp etiq_fin_1
etiq_sinon_1 :
	mov eax, false
etiq_fin_1 :
CODE ENDS 
