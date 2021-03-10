.text
.globl main
main:
addi $t0,$zero,0x1001
sll $t0,$t0,16
lw $s0,0($t0)
slt $t1,$s0,$zero
bne $t1,$zero,negativo
sw $s0,0($t0)

negativo:
sub $s1,$zero,$s0
sw $s1,0($t0)


.data
a: .word -2