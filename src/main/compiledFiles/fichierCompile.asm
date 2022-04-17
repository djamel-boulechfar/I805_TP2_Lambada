DATA SEGMENT 
	 age DD
	 taille DD
DATA ENDS 
CODE SEGMENT 
	in eax
	mov age, eax
	in eax
	mov taille, eax
	mov eax, taille
	push eax
	mov eax, 150
	pop ebx
	sub eax, ebx
	jle etiq_sinon_1
	mov eax, age
	push eax
	mov eax, 16
	pop ebx
	sub eax, ebx
	jle etiq_sinon_1
	mov eax, 16
	jmp etiq_fin_1
etiq_cond_ok_1 : 
	mov eax, 16
	jmp etiq_fin_1
etiq_sinon_1 :
	mov eax, 20
etiq_fin_1 : 
	mov eax, eax
	out eax
CODE ENDS 
