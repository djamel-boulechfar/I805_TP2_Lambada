DATA SEGMENT 
	 c DD
	 b DD
	 aux DD
DATA ENDS 
CODE SEGMENT 
	in eax
	mov c, eax
	in eax
	mov b, eax
debut_while_1 : 
	mov eax, 0
	push eax
	mov eax, b
	pop ebx
	sub eax, ebx
	jle etiq_cond_not_ok_1
	mov eax, 1
	jmp etiq_action_while_1
etiq_cond_not_ok_1 : 
	mov eax, 0
etiq_action_while_1 : 
	jz fin_while_1
	mov eax, c
	push eax
	mov eax, b
	pop ebx
	mov ecx, ebx
	div ebx, eax
	mul eax, ebx
	sub ecx, eax
	mov eax, ecx
	mov aux, eax
	mov eax, b
	mov c, eax
	mov eax, aux
	mov b, eax
	jmp debut_while_1
fin_while_1 : 
	mov eax, c
	out eax
CODE ENDS 
