.text
.globl main
main:
	addi $t0,$zero,0x1001
	sll $t0,$t0,16
	addi $s0,$zero,0 # soma
	addi $s1,$zero,0 # i
	addi $s2,$zero,0 # atual
	addi $s3,$zero,100 # parada
	LOOP:
		sll $t1,$s1,1 # i * 2
		addi $s2,$t1,1 # s2 = i*2 + 1
		sw $s2,0($t0) # armazena s2 na memoria
		addi $t0,$t0,4 # pula pro proximo endere�o
		add $s0,$s0,$s2 # soma = soma + s2
		addi $s1,$s1,1 # i++
		slt $t2,$s1,$s3
		beq $t2,$zero,fim
		j LOOP

fim:
	
