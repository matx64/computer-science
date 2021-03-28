import math

f = open("matrix.txt", 'r')

mat = []
qnt_tons = 0

for row in f:
    r = []
    for char in row:
        if char != '\n':
            r.append(int(char))
            if int(char) > qnt_tons:
                qnt_tons = int(char)
    mat.append(r)

qnt_tons += 1

co_matrix = [[0 for i in range(qnt_tons)] for j in range(qnt_tons)]

somatorio = 0.0

for i in range(len(mat) - 1):
    for j in range(len(mat[0])):
        co_matrix[mat[i][j]][mat[i+1][j]] += 1
        somatorio += 1.0

entropia = 0.0
homogeneidade = 0.0

for i in range(len(co_matrix)):
    for j in range(len(co_matrix)):
        
        prob = co_matrix[i][j]/somatorio

        homogeneidade += prob/(1 + abs(i - j))
        
        # entropia += prob * math.log(float(prob))

entropia *= -1
print(f"homogeneidade: {homogeneidade}")
print(f"entropia: {entropia}")

for row in co_matrix:
    print(row)