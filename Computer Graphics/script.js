/* 
    Algoritmos Unidade 1 - Matheus Teixeira Alves

    A inferface da aplicação se encontra no arquivo .html, no qual foi usado o framework Bootstrap v5.0.0 para facilitar o design utilizando classes prontas.

    Este arquivo .js possui 3 partes:
        Parte 1: Definição de variáveis globais e configurações que serão usadas para eventos de interface ou para execução de algoritmos
        Parte 2: Definição de eventos para os botões da interface
        Parte 3: Funções e Algoritmos da Unidade 1 - Translação, Escala, Rotação, Reflexões, Cohen-Sutherland e Liang-Barsky.

*/


// ############# Parte 1: Definição de Variáveis e Config #############

const btnDDA = document.getElementById("btnDDA");
const btnBres = document.getElementById("btnBres");
const btnBresCirc = document.getElementById("btnBresCirc");
const btnCohen = document.getElementById("btnCohen");
const btnLiang = document.getElementById("btnLiang");
const btnTransl = document.getElementById("btnTransl");
const btnEscala = document.getElementById("btnEscala");
const btnRotacao = document.getElementById("btnRotacao");
const btnReflexao = document.getElementById("btnReflexao");
const btnRefX = document.getElementById("btnRefX");
const btnRefY = document.getElementById("btnRefY");
const btnRefXY = document.getElementById("btnRefXY");
const cfgX = document.getElementById("configX");
const cfgY = document.getElementById("configY");
const btnAtualizar = document.getElementById("btnAtualizar");
const btnRecorte = document.getElementById("btnRecorte");
const infoRecorte = document.getElementById("infoRecorte");
const firstCoord = document.getElementById("firstCoord");
const secCoord = document.getElementById("secCoord");

const canvas = document.getElementById("myCanvas");
const divCoordenadas = document.getElementById("divCoordenadas");
const divBtnDesenho = document.getElementById("divBtnDesenho");
const displayCoordenadas = document.getElementById("displayCoordenadas");
const ctx = canvas.getContext("2d");
ctx.fillStyle = "#eb3496";
canvas.style.cursor = "pointer";

var numPontos = 0,
    ponto1 = [0, 0],
    ponto2 = [0, 0];
var boolTranslacao = false,
    boolEscala = false,
    boolRotacao = false,
    boolCohen = false,
    boolLiang = false;

// ############# Parte 2: Definição de Eventos ################

canvas.addEventListener("mousemove", (e) => {
    displayCoordenadas.innerHTML = `Coordenadas: (${e.offsetX},${e.offsetY})`;
});

canvas.addEventListener("click", (e) => {
    if (numPontos == 0) {
        ctx.clearRect(ponto1[0], ponto1[1], 1, 1);
        ponto1 = [e.offsetX, e.offsetY];
        firstCoord.innerHTML = `Ponto 1: (${ponto1[0]}, ${ponto1[1]})`;
        ctx.fillRect(ponto1[0], ponto1[1], 1, 1);
        numPontos++;
    } else if (numPontos == 1) {
        ctx.clearRect(ponto2[0], ponto2[1], 1, 1);
        ponto2 = [e.offsetX, e.offsetY];
        secCoord.innerHTML = `Ponto 2: (${ponto2[0]}, ${ponto2[1]})`;
        ctx.fillRect(ponto2[0], ponto2[1], 1, 1);
        numPontos--;
    }
});

btnTransl.addEventListener("click", function () {
    esconderBtnsReflexao();
    divBtnDesenho.style.display = "none";
    cfgX.style.display = "inline-block";
    cfgY.style.display = "inline-block";
    cfgX.placeholder = "Distância de X";
    cfgY.placeholder = "Distância de Y";
    btnAtualizar.style.display = "inline-block";
    btnRecorte.style.display = "none";
    infoRecorte.style.display = "none";

    boolTranslacao = true;
    boolEscala = false;
    boolRotacao = false;
    boolCohen = false;
    boolLiang = false;

    (cfgX.value = 140), (cfgY.value = 140);
    calcularTranslacao();
});

btnEscala.addEventListener("click", function () {
    esconderBtnsReflexao();
    divBtnDesenho.style.display = "none";
    cfgX.style.display = "inline-block";
    cfgY.style.display = "inline-block";
    cfgX.placeholder = "Escala de X";
    cfgY.placeholder = "Escala de Y";
    btnAtualizar.style.display = "inline-block";
    btnRecorte.style.display = "none";
    infoRecorte.style.display = "none";

    boolEscala = true;
    boolTranslacao = false;
    boolRotacao = false;
    boolCohen = false;
    boolLiang = false;

    (cfgX.value = 2), (cfgY.value = 2);
    calcularEscala();
});

btnRotacao.addEventListener("click", function () {
    esconderBtnsReflexao();
    divBtnDesenho.style.display = "none";
    cfgX.style.display = "inline-block";
    cfgX.placeholder = "Ângulo de Rotação";
    cfgY.style.display = "none";
    btnAtualizar.style.display = "inline-block";
    btnRecorte.style.display = "none";
    infoRecorte.style.display = "none";

    boolRotacao = true;
    boolTranslacao = false;
    boolEscala = false;
    boolCohen = false;
    boolLiang = false;

    cfgX.value = 30;
    calcularRotacao();
});

btnReflexao.addEventListener("click", function(){
    cfgX.style.display = "none";
    cfgY.style.display = "none";
    divCoordenadas.style.display = "none";
    btnRefX.style.display = "inline-block";
    btnRefY.style.display = "inline-block";
    btnRefXY.style.display = "inline-block";
    btnAtualizar.style.display = "none";
    btnRecorte.style.display = "none";
    infoRecorte.style.display = "none";
    
    boolRotacao = false;
    boolTranslacao = false;
    boolEscala = false;
    boolCohen = false;
    boolLiang = false;

    ctx.clearRect(0, 0, 400, 400);
    ctx.fillStyle = "#000000";
    calcularDDA([150, 0], [150, 300]);
    calcularDDA([0, 150], [300, 150]);
    ctx.fillStyle = "#eb3496";
    calcularDDA([190, 210], [250, 250]);
});

btnRefY.addEventListener("click", calcularReflexaoY);

btnRefX.addEventListener("click", calcularReflexaoX);

btnRefXY.addEventListener("click", calcularReflexaoXY);

btnAtualizar.addEventListener("click", function () {
    if (boolTranslacao) calcularTranslacao();
    else if (boolEscala) calcularEscala();
    else if (boolRotacao) calcularRotacao();
});

btnDDA.addEventListener("click", function () {
    cfgX.style.display = "none";
    cfgY.style.display = "none";
    infoRecorte.style.display = "none";
    btnAtualizar.style.display = "none";

    boolTranslacao = false;
    boolEscala = false;
    boolRotacao = false;

    if (ponto1 != [0, 0] || ponto2 != [0, 0]) {
        calcularDDA(ponto1, ponto2);
    }
});

btnBres.addEventListener("click", function () {
    cfgX.style.display = "none";
    cfgY.style.display = "none";
    infoRecorte.style.display = "none";
    btnAtualizar.style.display = "none";

    boolTranslacao = false;
    boolEscala = false;
    boolRotacao = false;

    if (ponto1 != [0, 0] || ponto2 != [0, 0]) {
        calcularBresenham(ponto1, ponto2);
    }
});

btnBresCirc.addEventListener("click", function () {
    cfgX.style.display = "none";
    cfgY.style.display = "none";
    infoRecorte.style.display = "none";
    btnAtualizar.style.display = "none";
    btnRecorte.style.display = "none";

    boolTranslacao = false;
    boolEscala = false;
    boolRotacao = false;
    boolCohen = false;
    boolLiang = false;

    if (ponto1 != [0, 0] || ponto2 != [0, 0]) {
        calcularBresenhamCirc(ponto1, ponto2);
    }
});

btnCohen.addEventListener("click", function () {
    limparCanvas();
    esconderBtnsReflexao();
    cfgX.style.display = "none";
    cfgY.style.display = "none";
    divCoordenadas.style.display = "inline-block";
    divBtnDesenho.style.display = "inline-block";
    infoRecorte.style.display = "inline-block";
    btnAtualizar.style.display = "none";
    btnRecorte.style.display = "inline-block";
    btnRecorte.innerHTML = "Verificar Recorte de Reta (Cohen)";

    boolCohen = true;
    boolTranslacao = false;
    boolEscala = false;
    boolRotacao = false;
    boolLiang = false;

    ctx.fillStyle = "#ababab";
    ctx.fillRect(100, 100, 100, 100);
    ctx.fillStyle = "#eb3496";
});

btnLiang.addEventListener("click", function () {
    limparCanvas();
    esconderBtnsReflexao();
    cfgX.style.display = "none";
    cfgY.style.display = "none";
    divCoordenadas.style.display = "inline-block";
    divBtnDesenho.style.display = "inline-block";
    infoRecorte.style.display = "inline-block";
    btnAtualizar.style.display = "none";
    btnRecorte.style.display = "inline-block";
    btnRecorte.innerHTML = "Verificar Recorte de Reta (Liang)";

    boolLiang = true;
    boolCohen = false;
    boolTranslacao = false;
    boolEscala = false;
    boolRotacao = false;

    ctx.fillStyle = "#ababab";
    ctx.fillRect(100, 100, 100, 100);
    ctx.fillStyle = "#eb3496";
});

btnRecorte.addEventListener("click", function () {
    infoRecorte.style.display = "none";
    if (ponto1 != [0, 0] || ponto2 != [0, 0]) {
        if (boolCohen) calcularCohen(ponto1, ponto2);
        else if (boolLiang) calcularLiang(ponto1, ponto2);
    }
});

// ########### Parte 3: Definição de Funções & Algoritmos #############

function limparCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

function esconderBtnsReflexao(){
    btnRefX.style.display = "none";
    btnRefY.style.display = "none";
    btnRefXY.style.display = "none";
}

// Algoritmo de Translação
function calcularTranslacao() {
    ctx.clearRect(0, 0, 400, 400);
    ctx.fillRect(20, 20, 100, 50);
    var transX = parseInt(cfgX.value);
    var transY = parseInt(cfgY.value);
    ctx.fillRect(20 + transX, 20 + transY, 100, 50);
    ctx.fillStyle = "#000000";
    calcularBresenham([20, 20], [20 + transX, 20 + transY]);
    calcularBresenham([20, 70], [20 + transX, 70 + transY]);
    calcularBresenham([120, 20], [120 + transX, 20 + transY]);
    calcularBresenham([120, 70], [120 + transX, 70 + transY]);
    ctx.fillStyle = "#eb3496";
}

// Algoritmo para Escala
function calcularEscala() {
    ctx.clearRect(0, 0, 400, 400);
    var escalaX = parseFloat(cfgX.value);
    var escalaY = parseFloat(cfgY.value);
    ctx.fillRect(0, 0, 100 * escalaX, 50 * escalaY);
    ctx.fillStyle = "#b52673";
    ctx.fillRect(0, 0, 100, 50);
    ctx.fillStyle = "#000000";
    calcularBresenham([100, 50], [100 * escalaX, 50 * escalaY]);
    ctx.fillStyle = "#eb3496";
}

// Algoritmo para Rotação
function calcularRotacao() {
    ctx.clearRect(0, 0, 400, 400);
    var x = 170,
        y = 0;
    calcularBresenham([0, 0], [x, y]);

    var angulo = parseInt(cfgX.value);
    var newX =
        x * Math.cos((angulo * Math.PI) / 180) -
        y * Math.sin((angulo * Math.PI) / 180);
    var newY =
        x * Math.sin((angulo * Math.PI) / 180) +
        y * Math.cos((angulo * Math.PI) / 180);

    ctx.clearRect(0, 0, 400, 400);
    calcularBresenham([0, 0], [newX, newY]);
}

// Algoritmo de Reflexão Y
function calcularReflexaoY(){
    var x1 = 190, y1 = 210, x2 = 250, y2 = 250;
    var origemX = 150, origemY = 150;

    var newX1 = origemX - x1 + origemX,
        newX2 = origemX - x2 + origemX,
        newY1 = y1,
        newY2 = y2;

    calcularBresenham([newX1, newY1], [newX2, newY2]);
}

// Algoritmo de Reflexão X
function calcularReflexaoX(){
    var x1 = 190, y1 = 210, x2 = 250, y2 = 250;
    var origemX = 150, origemY = 150;

    var newX1 = x1,
        newX2 = x2,
        newY1 = origemY - y1 + origemY,
        newY2 = origemY - y2 + origemY;

    calcularBresenham([newX1, newY1], [newX2, newY2]);
}

// Algoritmo de Reflexão XY
function calcularReflexaoXY(){
    var x1 = 190, y1 = 210, x2 = 250, y2 = 250;
    var origemX = 150, origemY = 150;

    var newX1 = origemX - x1 + origemX
        newX2 = origemX - x2 + origemX,
        newY1 = origemY - y1 + origemY,
        newY2 = origemY - y2 + origemY;

    calcularBresenham([newX1, newY1], [newX2, newY2]);
}

// Algoritmo de DDA
function calcularDDA(p1, p2) {
    var dx, dy, passos;
    var x_incr, y_incr, x, y;

    dx = p2[0] - p1[0];
    dy = p2[1] - p1[1];

    if (Math.abs(dx) > Math.abs(dy)) {
        passos = Math.abs(dx);
    } else {
        passos = Math.abs(dy);
    }

    x_incr = dx / passos;
    y_incr = dy / passos;
    x = p1[0];
    y = p1[1];

    for (var k = 1; k <= passos; k++) {
        x = x + x_incr;
        y = y + y_incr;
        ctx.fillRect(Math.round(x), Math.round(y), 1, 1);
    }
}

// Algoritmo de Bresenham para Retas
function calcularBresenham(p1, p2) {
    var dx, dy, x, y, const1, const2, p, incrx, incry;

    dx = p2[0] - p1[0];
    dy = p2[1] - p1[1];

    if (dx >= 0) {
        incrx = 1;
    } else {
        incrx = -1;
        dx = -dx;
    }

    if (dy >= 0) {
        incry = 1;
    } else {
        incry = -1;
        dy = -dy;
    }

    x = p1[0];
    y = p1[1];

    if (dy < dx) {
        p = 2 * dy - dx;
        const1 = 2 * dy;
        const2 = 2 * (dy - dx);

        for (var i = 0; i < dx; i++) {
            x += incrx;
            if (p < 0) {
                p += const1;
            } else {
                y += incry;
                p += const2;
            }
            ctx.fillRect(x, y, 1, 1);
        }
    } else {
        p = 2 * dx - dy;
        const1 = 2 * dx;
        const2 = 2 * (dx - dy);
        for (var i = 0; i < dy; i++) {
            y += incry;
            if (p < 0) {
                p += const1;
            } else {
                x += incrx;
                p += const2;
            }
            ctx.fillRect(x, y, 1, 1);
        }
    }
}

// Algoritmo de Bresenham para Circunferência
function calcularBresenhamCirc(p1, p2) {
    const raio = parseInt(
        Math.sqrt(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2))
    );
    var xc = p1[0],
        yc = p1[1],
        x = 0,
        y = raio,
        p = 3 - 2 * raio;

    function desenharPontos() {
        ctx.fillRect(xc + x, yc + y, 1, 1);
        ctx.fillRect(xc - x, yc + y, 1, 1);
        ctx.fillRect(xc + x, yc - y, 1, 1);
        ctx.fillRect(xc - x, yc - y, 1, 1);
        ctx.fillRect(xc + y, yc + x, 1, 1);
        ctx.fillRect(xc - y, yc + x, 1, 1);
        ctx.fillRect(xc + y, yc - x, 1, 1);
        ctx.fillRect(xc - y, yc - x, 1, 1);
    }

    desenharPontos();

    while (x < y) {
        if (p < 0) {
            p += 4 * x + 6;
        } else {
            p += 4 * (x - y) + 10;
            y--;
        }
        x++;
        desenharPontos();
    }
}

// Algoritmo de Cohen-Sutherland
function calcularCohen(p1, p2) {
    var xmin = 100,
        ymin = 100,
        xmax = 200,
        ymax = 200;
    var aceite = false,
        fim = false;
    var x1 = p1[0],
        y1 = p1[1],
        x2 = p2[0],
        y2 = p2[1];

    function bit(num) {
        var result = Number(num).toString(2);
        while (result.length < 4) {
            result = "0" + result;
        }
        return result;
    }

    function region_code(x, y) {
        var codigo = 0;

        if (x < xmin) codigo += 1;
        if (x > xmax) codigo += 2;
        if (y < ymin) codigo += 4;
        if (y > ymax) codigo += 8;

        return bit(codigo);
    }

    while (!fim) {
        var c1 = region_code(x1, y1);
        var c2 = region_code(x2, y2);

        console.log(c1 + " " + c2);

        if (c1 == "0000" && c2 == "0000") {
            (aceite = true), (fim = true), (totalmenteDentro = true);
        } else if ((c1 & c2) != 0) {
            fim = true;
        } else {
            var cfora, xint, yint;

            if (c1 != "0000") {
                cfora = c1;
            } else {
                cfora = c2;
            }

            if (cfora[3] == "1") {
                xint = xmin;
                yint = y1 + ((y2 - y1) * (xmin - x1)) / (x2 - x1);
            } else if (cfora[2] == "1") {
                xint = xmax;
                yint = y1 + ((y2 - y1) * (xmax - x1)) / (x2 - x1);
            } else if (cfora[1] == "1") {
                yint = ymin;
                xint = x1 + ((x2 - x1) * (ymin - y1)) / (y2 - y1);
            } else if (cfora[0] == "1") {
                yint = ymax;
                xint = x1 + ((x2 - x1) * (ymax - y1)) / (y2 - y1);
            }

            if (c1 == cfora) {
                x1 = xint;
                y1 = yint;
            } else {
                x2 = xint;
                y2 = yint;
            }
        }
    }

    if (aceite) {
        limparCanvas();
        ctx.fillStyle = "#ababab";
        ctx.fillRect(100, 100, 100, 100);
        ctx.fillStyle = "#34eb8f";
        calcularBresenham(
            [Math.round(x1), Math.round(y1)],
            [Math.round(x2), Math.round(y2)]
        );
        ctx.fillStyle = "#eb3496";
    } else {
        limparCanvas();
        ctx.fillStyle = "#ababab";
        ctx.fillRect(100, 100, 100, 100);
        ctx.fillStyle = "#eb3496";
        infoRecorte.classList = "";
        infoRecorte.classList.add("badge");
        infoRecorte.classList.add("bg-danger");
        infoRecorte.innerHTML = "Reta Totalmente fora da Janela!";
    }
}

// Algoritmo de Liang-Barsky
function calcularLiang(p1, p2) {
    var xmin = 100,
        ymin = 100,
        xmax = 200,
        ymax = 200;
    var x1 = p1[0],
        y1 = p1[1],
        x2 = p2[0],
        y2 = p2[1];

    function cliptest(p, q, ref) {
        var r,
            result = true;

        if (p < 0.0) {
            r = q / p;

            if (r > ref.u2) {
                result = false;
            } else if (r > ref.u1) {
                ref.u1 = r;
            }
        } else if (p > 0.0) {
            r = q / p;

            if (r < ref.u1) {
                result = false;
            } else if (r < ref.u2) {
                ref.u2 = r;
            }
        } else if (q < 0.0) {
            result = false;
        }

        return result;
    }

    var dx = x2 - x1,
        dy = y2 - y1;

    var ref = {
        u1: 0.0,
        u2: 1.0,
    };

    if (cliptest(-dx, x1 - xmin, ref)) {
        if (cliptest(dx, xmax - x1, ref)) {
            if (cliptest(-dy, y1 - ymin, ref)) {
                if (cliptest(dy, ymax - y1, ref)) {
                    if (ref.u2 < 1.0) {
                        x2 = x1 + ref.u2 * dx;
                        y2 = y1 + ref.u2 * dy;
                    }
                    if (ref.u1 > 0.0) {
                        x1 = x1 + ref.u1 * dx;
                        y1 = y1 + ref.u1 * dy;
                    }
                    limparCanvas();
                    ctx.fillStyle = "#ababab";
                    ctx.fillRect(100, 100, 100, 100);
                    ctx.fillStyle = "#34eb8f";
                    calcularBresenham(
                        [Math.round(x1), Math.round(y1)],
                        [Math.round(x2), Math.round(y2)]
                    );
                    ctx.fillStyle = "#eb3496";
                }
            }
        }
    }
}
