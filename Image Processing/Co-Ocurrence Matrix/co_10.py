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

co_matrix = [[0 for i in range(qnt_tons)] for j in range(qnt_tons)]


for i in range(len(mat) - 1):
    for j in range(len(mat[0])):
        if(j < len(mat[0]) -  1):
            co_matrix[mat[i][j]][mat[i][j+1]] += 1



for row in co_matrix:
    print(row)