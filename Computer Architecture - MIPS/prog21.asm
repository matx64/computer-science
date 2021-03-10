.text
.globl main
main:
addi $t0,$zero,0x1001
sll $t0,$t0,16
lw $s0,0($t0) # s0 = x
lw $s1,4($t0) # s1 = y
addi $s2,$zero,0
addi $t1,$zero,1 #contador 1
LOOP:
	addi $t3,$zero,0 #contador 2
	slt $t2,$t1,$s1
	beq $t2,$zero,fim
	LOOP2:
		slt $t4,$t3,$s0
		beq $t4,$zero,fim2
		add $s2,$s2,$s0
		addi $t3,$t3,1
		j LOOP2
		
		fim2:
				
	addi $t1,$t1,1
	j LOOP
		
		
fim:
	sw $s2,8($t0)
		

.data
x: .word 3
y: .word 2