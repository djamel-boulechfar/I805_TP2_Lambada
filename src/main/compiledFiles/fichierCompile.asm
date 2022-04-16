DATA SEGMENT 
	 x DD
DATA ENDS 
CODE SEGMENT 
	 in eax
	mov x, eax
	mov eax, x
	out eax
CODE ENDS 
