DATA SEGMENT 
	 x DD
DATA ENDS 
CODE SEGMENT 
	 mov eax, 5
	 mov x, eax
	 mov eax,5
	 push eax
	 mov eax,5
	 push eax
	 mov eax,5
	 push eax
	 mov eax,8
	 push eax
	 mov eax,5
	 push eax
	 pop ebx
	 add eax,ebx
	 pop ebx
	 add eax,ebx
	 pop ebx
	 add eax,ebx
	 pop ebx
	 add eax,ebx
	 pop ebx
	 add eax,ebx
	 out eax
CODE ENDS 
