.text
.globl main
main:
	addi $t0,$zero,0x1001
	sll $t0,$t0,16
	addi $s0,$zero,0 # soma
	addi $s1,$zero,0 # i
	addi $s2,$zero,0 # atual
	addi $s3,$zero,100 # TAM VETOR
	LOOP:
		sll $t1,$s1,1 # i * 2
		addi $s2,$t1,1 # s2 = i*2 + 1
		sw $s2,0($t0) # armazena s2 na memoria
		addi $t0,$t0,4 # pula pro proximo endereço
		add $s0,$s0,$s2 # soma = soma + s2
		addi $s1,$s1,1 # i++
		slt $t2,$s1,$s3
		beq $t2,$zero,fim
		j LOOP

fim:
	addi $t0,$zero,0x1001
	sll $t0,$t0,16
	addi $t3,$t0,4 # segundo endereço
	addi $t6,$zero,1 # condição de parada
	addi $s1,$zero,0 # i = 0
	LOOP2:
		slt $t4,$s3,$t6 # se s3 < 1: parar
		bne $t4,$zero,fim2
		LOOP3:
			slt $t2,$s1,$s3
			beq $t2,$zero,fim2
			lw $s4,0($t0)
			lw $s5,0($t3)
			slt $t5,$s5,$s4
			addi $s1,$s1,1 # i++
			bne $t5,$zero,swap
			addi $t0,$t0,4
			addi $t3,$t3,4
			j LOOP3
		
		sub $s3,$s3,1
		j LOOP2
		
swap:
	sw $s5,0($t0) # s4 = s5
	sw $s4,0($t3) # s5 = s4
	addi $t0,$t0,4
	addi $t3,$t3,4
	j LOOP3

fim2:

			
			