DATA SEGMENT 
	 x DD
	 z DD
DATA ENDS 
CODE SEGMENT 
	 mov eax, 3
	 mov x, eax
	 mov eax, 57
	 mov z, eax
	 mov eax, x
	 add eax, z
CODE ENDS 
