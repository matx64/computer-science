.text
.globl main
main:
	addi $t0,$zero,0x1001
	sll $t0,$t0,16
	lw $s0,0($t0) # s0 = x
	addi $t1,$zero,2
	div $s0,$t1
	mfhi $t2
	beq $t2,$zero,ehPar
	mult $s0,$s0
	mflo $t3
	mult $s0,$t3
	mflo $t3
	mult $s0,$t3
	mflo $t4
	mult $s0,$t4
	mflo $t4
	sub $s1,$t4,$t3
	addi $s1,$s1,1
	sw $s1,4($t0)
	
ehPar:
	mult $s0,$s0
	mflo $t3
	sll $s1,$t3,1
	mult $s0,$t3
	mflo $t3
	sub $s1,$t3,$s1 # y = x^3 - 2x^2
	mult $s0,$t3
	mflo $t3
	add $s1,$s1,$t3 # y = x^4 + x^3 - 2x^2
	sw $s1,4($t0)

.data
x: .word 2