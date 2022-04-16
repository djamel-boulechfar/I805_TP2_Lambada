DATA SEGMENT 
	 x DD
	 y DD
	 res DD
	 res DD
DATA ENDS 
CODE SEGMENT 
	mov eax, 6
	mov x, eax
	mov eax, 6
	mov y, eax
	mov eax, x
	push eax
	mov eax, y
	pop ebx
	sub eax, ebx
	jnz etiq_sinon_1
	mov eax, 13
	mov res, eax
	jmp etiq_fin_1
etiq_sinon_1 :
	mov eax, 1
	mov res, eax
etiq_fin_1 :
	mov eax, res
	out eax
CODE ENDS 
