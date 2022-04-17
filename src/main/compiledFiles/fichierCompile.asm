DATA SEGMENT 
DATA ENDS 
CODE SEGMENT 
	mov eax, 1
	push eax
	mov eax, 2
	pop ebx
	sub eax, ebx
	jle etiq_sinon_1
	mov eax, 3
	push eax
	mov eax, 4
	pop ebx
	sub eax, ebx
	jle etiq_sinon_1
	mov eax, 3
	push eax
	mov eax, 6
	pop ebx
	sub eax, ebx
	jle etiq_sinon_1
	mov eax, 1
	jmp etiq_fin_1
etiq_sinon_1 :
	mov eax, 0
etiq_fin_1 :
	mov eax, eax
	out eax
CODE ENDS 
