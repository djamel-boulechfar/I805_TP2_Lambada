DATA SEGMENT 
DATA ENDS 
CODE SEGMENT 
	 mov eax, 400
	 push eax
	 mov eax, 4
	 push eax
	 mov eax, 2
	 push eax
	 mov eax, 2
	 pop ebx
	 add eax, ebx
	 pop ebx
	 div ebx, eax
	 mov eax, ebx
	 pop ebx
	 div ebx, eax
	 mov eax, ebx
CODE ENDS 
