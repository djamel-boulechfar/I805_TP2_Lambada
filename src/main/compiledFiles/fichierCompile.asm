DATA SEGMENT 
	 x DD
DATA ENDS 
CODE SEGMENT 
	mov eax, 5
	mov x, eax
	mov eax, x
	out eax
CODE ENDS 
