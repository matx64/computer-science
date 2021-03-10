.text
.globl main
main:
	addi $t0,$zero,0x1001
	sll $t0,$t0,16
	lw $s0,0($t0)
	addi $t1,$zero,51
	slt $t2,$s0,$t1
	bne $t2,$zero,ehMenor
	
ehMenor:
	addi $t3,$zero,29
	slt $t4,$s0,$t3
	beq $t4,$zero,ehMaior

ehMaior:
	lw $s1,4($t0)
	add $s1,$s1,1
	sw $s1,4($t0)

.data
TEMP: .word 40
FLAG: .word 0
