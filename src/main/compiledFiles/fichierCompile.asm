DATA SEGMENT 
DATA ENDS 
CODE SEGMENT 
	mov eax, 1
	push eax
	mov eax, 2
	pop ebx
	sub eax, ebx
	jg etiq_cond_ok_1
	mov eax, 3
	push eax
	mov eax, 4
	pop ebx
	sub eax, ebx
	jg etiq_cond_ok_1
	jmp etiq_sinon_1
	mov eax, 1
	push eax
	mov eax, 3
	pop ebx
	sub eax, ebx
	jg etiq_cond_ok_1
	mov eax, 4
	push eax
	mov eax, 4
	pop ebx
	sub eax, ebx
	jz etiq_cond_ok_1
	jmp etiq_sinon_1
	jmp etiq_sinon_1
	mov eax, 1
	jmp etiq_fin_1
etiq_cond_ok_1 : 
	mov eax, 1
	jmp etiq_fin_1
etiq_sinon_1 :
	mov eax, 0
etiq_fin_1 : 
	mov eax, eax
	out eax
CODE ENDS 
