DATA SEGMENT 
DATA ENDS 
CODE SEGMENT 
	 mov eax, 1
	 push eax
	 mov eax, 2
	 pop ebx
	 sub ebx, eax
	 jg etiq_sinon_1
	 mov eax, 1
	 jmp etiq_fin_1
	 etiq_sinon_1 :
	 mov eax, 2
	 etiq_fin_1 :
	 out eax
	 mov eax, 4
	 push eax
	 mov eax, 3
	 pop ebx
	 sub ebx, eax
	 jg etiq_sinon_2
	 mov eax, 3
	 jmp etiq_fin_2
	 etiq_sinon_2 :
	 mov eax, 4
	 etiq_fin_2 :
	 out eax
CODE ENDS 
