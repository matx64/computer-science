.text
.globl main
main:
	addi $t0,$zero,0x1001
	sll $t0,$t0,16
	lw $s0,0($t0) # s0 = x
	addi $t1,$zero,1
	slt $t2,$s0,$t1 # if x < 1
	bne $t2,$zero,ehMenor
	mult $s0,$s0
	mflo $t3
	mult $s0,$t3
	mflo $t3
	add $s1,$t3,1
	sw $s1,4($t0)
	
	
ehMenor:
	mult $s0,$s0
	mflo $t3
	mult $s0,$t3
	mflo $t3
	mult $s0,$s0
	mflo $t3
	sub $s1,$t3,1
	sw $s1,4($t0)

.data
x: .word 0