DATA SEGMENT 
DATA ENDS 
CODE SEGMENT 
	mov eax, 12
	push eax
	mov eax, 5
	pop ebx
	add eax, ebx
	mov eax, 10
	push eax
	mov eax, 2
	pop ebx
	div ebx, eax
	mov eax, ebx
	push eax
	mov eax, 3
	pop ebx
	sub ebx, eax
	mov eax, ebx
	mov eax, 99
	mov eax, 30
	push eax
	mov eax, 1
	pop ebx
	mul eax, ebx
	push eax
	mov eax, 4
	pop ebx
	add eax, ebx
	push eax
	mov eax, 2
	pop ebx
	mov ecx, ebx
	div ebx, eax
	mul eax, ebx
	sub ecx, eax
	mov eax, ecx
	mov eax, 3
	push eax
	mov eax, 0
	sub eax, 4
	pop ebx
	mul eax, ebx
CODE ENDS 
