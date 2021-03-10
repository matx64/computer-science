.text
.globl main
main:
	addi $s0,$zero,2 #a
	add $s1,$zero,3  #b
	addi $s2,$zero,4 #c
	add $s3,$zero,5  #d
	add $s4,$s0,$s1  #a+b
	add $s5,$s2,$s3  #c+d
	sub $s6,$s4,$s5  #x
	sub $t0,$s0,$s1  #a-b
	add $s7,$t0,$s6  #y
	sub $s1,$s6,$s7  #b
