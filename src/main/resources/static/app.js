var container = document.getElementById("containerr");
var searchMatchButton = document.querySelector("#searchMatch");
var quitMatchButton = document.querySelector("#quitMatch");
var matchColor = document.querySelector("#matchColor");


searchMatchButton.addEventListener('click', searchMatch);
quitMatchButton.addEventListener('click', quitMatch);

var chessBoard = [];
var aux = 1;
var mouseDown = 0;

var userColor;

var currentColor = "white";  // construire matrice

var pressed1 = 0;
var start;
var finish = { x_val: 50, y_val: 50 };
var pressed2 = 0;
var released2 = 0;

var colorToMove;
chessBoardColor = [];

var userId;

var stompClient = null;

getUserInfo();


function getUserInfo() {
    const userInfo = async () => {
        const response = await fetch('http://localhost:8080/chess/getUserLogged', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        const myJson = await response.json();
        userId = myJson.userId;
        userColor = myJson.color;
        isInMatch = myJson.inMatch;

        createChessBoardForColors();
        showChessBoardOnScreen();
        connect();
        if (isInMatch == true) {
            showChessBoardFromCache();
            getColorToMoveNext();
            if (userColor == "B") {
                flipChessBoardPieces();
            }
        }
    }
    userInfo();
}


function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/searchMatchResponse', function(greeting) {
            myJson = JSON.parse(greeting.body);
            if (myJson.whiteUserId != "noUserId") {
                if (userId == myJson.blackUserId || userId == myJson.whiteUserId) {
                    showChessBoardFromCache();
                    getColorToMoveNext();
                    if (userId == myJson.blackUserId) {
                        flipChessBoardPieces();
                    }
                }
            }
        });
        stompClient.subscribe('/topic/chessPiecesInfoResponse', function(greeting) {
            myJson = JSON.parse(greeting.body);
            if (myJson != null) {
                if (userId == myJson.users[0] || userId == myJson.users[1]) {
                    for (var i = 0; i < myJson.pieces.length; i++) {
                        chessBoard[myJson.pieces[i].position.x + 1][myJson.pieces[i].position.y + 1].innerHTML = getChessPieceCode(myJson.pieces[i].chessPieceName.concat(myJson.pieces[i].chessPieceColor));
                    }
                }
                getColorToMoveNext();
            }
        });
        stompClient.subscribe('/topic/isCheckMateResponse', function(greeting) {
            myJson = JSON.parse(greeting.body);

            if (myJson.checkMate == true) {
             if(userId == myJson.winnerId){
               alert("Well done, you won!");
             }else if(userId == myJson.looserId){
               alert("You lost");
             }
            }
        });

        stompClient.subscribe('/topic/quitMatchResponse', function(greeting) {
            myJson = JSON.parse(greeting.body);
            console.log(myJson);
            userColor = "no_color";
            if(myJson.length != 0 ){
              if(myJson[0] == userId || myJson[1] == userId){
               clearChessBoard();
              }
            }
        });
    });
}

function sendChessPiecesInfo() {
    stompClient.send("/app/chessPiecesInfo", {}, userId);
}

function sendSearchMatch(chessMatchRequest) {
    stompClient.send("/app/searchMatch", {}, JSON.stringify(chessMatchRequest));
}

function isCheckMate(userId) {
    stompClient.send("/app/isCheckMate", {}, userId);
}

function quitMatch() {
  stompClient.send("/app/quitMatch", {}, userId);
}

function searchMatch() {
    var chessMatchRequest = {
        userId: userId,
        desiredColor: matchColor.value.toUpperCase()
    };
    sendSearchMatch(chessMatchRequest);
}

function showChessBoardFromCache() {
    const userAction = async () => {
        const response = await fetch('http://localhost:8080/chess/getChessBoard', {
            method: 'POST',
            body: userId,
            headers: {
                'Content-Type': 'application/json'
            }
        });
        const myJson = await response.json();
        for (var i = 0; i < myJson.length; i++) {
            chessBoard[myJson[i].position.x + 1][myJson[i].position.y + 1].innerText = getChessPieceCode(myJson[i].chessPieceName.concat(myJson[i].chessPieceColor));
        }
    }
    userAction();

}

function getColorToMoveNext() {
    const userAction = async () => {

        const response = await fetch('http://localhost:8080/chess/getColorToMove', {
            method: 'POST',
            body: userId,
            headers: {
                'Content-Type': 'application/json'
            }
        });
        const myJson = await response.json();
        colorToMove = myJson.charAt(0);
        console.log("color to move next is ", colorToMove);
    }

    userAction();
}

function showChessBoardOnScreen() {

    for (let i = 1; i < 9; i++) {
        chessBoard[i] = new Array(8);
        for (let j = 1; j < 9; j++) {
            chessBoard[i][j] = document.createElement("div");
            chessBoard[i][j].className = "cub";
            chessBoard[i][j].id = aux;
            //chessBoard[i][j].innerText = getPieceText(i,j);
            chessBoard[i][j].style.backgroundColor = currentColor;
            chessBoard[i][j].addEventListener('mouseup', function(e) {
                var index = e.srcElement.id;
                var x = parseInt((index - 1) / 8) + 1;
                var y = index % 8;
                if (y == 0) {
                    y = 8;
                }
                if (pressed1 == 1) {
                    if ((x != start.x_val || y != start.y_val) && chessBoard[x][y].style.backgroundColor == "rgb(175, 238, 238)") { //"green"){ //"#E0FFFF" // DACA NU APAS TOT PE POZ DE START SI DACA NU AM APASAT PE ALTA POZITIE DECAT VERDE
                        chessBoard[x][y].innerText = chessBoard[start.x_val][start.y_val].innerText;
                        chessBoard[start.x_val][start.y_val].innerText = "";
                        chessBoard[start.x_val][start.y_val].style.backgroundColor = lastColor;

                        chessBoard[x][y].style.backgroundColor = chessBoardColor[x][y].style.backgroundColor;
                        var body = {
                            userId: userId,
                            move: {
                                currentPosition: {
                                    x: start.x_val - 1,
                                    y: start.y_val - 1
                                },
                                desiredPosition: {
                                    x: x - 1,
                                    y: y - 1
                                }
                            }
                        };
                        const userAction = async () => {
                            const response = await fetch('http://localhost:8080/chess/makeMove', {
                                method: 'POST',
                                body: JSON.stringify(body),
                                headers: {
                                    'Content-Type': 'application/json'
                                }
                            });
                        }
                        userAction();
                        isCheckMate(userId);

                        chessBoard[start.x_val][start.y_val].style.backgroundColor = lastColor;
                        colorToMove = switchColorToMove(colorToMove);
                        console.log("color to move next", colorToMove);
                    }
                    chessBoard[start.x_val][start.y_val].style.backgroundColor = lastColor;
                    pressed1 = 0;
                    var requestAvailablePositionsCommand = {
                        userId: userId,
                        currentPosition: {
                            x: start.x_val - 1,
                            y: start.y_val - 1
                        }
                    };
                    const userAction = async () => {
                        const response = await fetch('http://localhost:8080/chess/availablePositions', {
                            method: 'POST',
                            body: JSON.stringify(requestAvailablePositionsCommand),
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        });
                        const myJson = await response.json();
                        for (let i = 0; i < myJson.length; i++) {
                            chessBoard[myJson[i].x + 1][myJson[i].y + 1].style.backgroundColor = chessBoardColor[myJson[i].x + 1][myJson[i].y + 1].style.backgroundColor;
                        }
                        return myJson;
                    }
                    userAction();
                    sendChessPiecesInfo();
                    start = finish;
                } else {
                    if (colorToMove == getPieceColor(chessBoard[i][j].innerText)) { //&& userColor == getPieceColor(chessBoard[i][j].innerText)){
                        pressed1 = 1;
                        start = {
                            x_val: x,
                            y_val: y
                        };
                        lastColor = chessBoard[x][y].style.backgroundColor;
                        chessBoard[x][y].style.backgroundColor = "#fd5c63"; //"red";
                        console.log(start);

                        var body = {
                            userId: userId,
                            currentPosition: {
                                x: 6,
                                y: 0
                            }
                        };

                        var requestAvailablePositionsCommand = {
                            userId: userId,
                            currentPosition: {
                                x: start.x_val - 1,
                                y: start.y_val - 1
                            }
                        };
                        const userAction = async () => {
                            const response = await fetch('http://localhost:8080/chess/availablePositions', {
                                method: 'POST',
                                body: JSON.stringify(requestAvailablePositionsCommand),
                                headers: {
                                    'Content-Type': 'application/json'
                                }
                            });
                            const myJson = await response.json();
                            for (let i = 0; i < myJson.length; i++) {
                                chessBoard[myJson[i].x + 1][myJson[i].y + 1].style.backgroundColor = "#AFEEEE"; //"green"; //"#E0FFFF"
                            }
                            return myJson;
                        }
                        userAction();

                    }
                }
            });
            currentColor = switchColor(currentColor);
            container.appendChild(chessBoard[i][j]);
            aux = aux + 1;
        }
        currentColor = switchColor(currentColor);
    }
}

function switchColor(color) {
    if (color == "white") return "#FFE4C4";
    if (color == "#FFE4C4") return "white";
}

function switchColorToMove(color) {
    if (color == "B") return "W";
    return "B";
}

function createChessBoardForColors() {
    var aux = 1;
    var currentColor1 = "white";
    for (let i = 1; i < 9; i++) {
        chessBoardColor[i] = new Array(8);
        for (let j = 1; j < 9; j++) {
            chessBoardColor[i][j] = document.createElement("div");
            chessBoardColor[i][j].id = aux;
            chessBoardColor[i][j].style.backgroundColor = currentColor1;
            aux = aux + 1;
            currentColor1 = switchColor(currentColor1);
        }
        currentColor1 = switchColor(currentColor1);
    }
}
function clearChessBoard() {
    for (let i = 1; i < 9; i++) {
        for (let j = 1; j < 9; j++) {;
            chessBoard[i][j].innerText = "";
        }
    }
}

function flipChessBoardPieces() {
    container.style.transform = 'rotate(180deg)';
    for (let i = 1; i < 9; i++) {
        for (let j = 1; j < 9; j++) {
            chessBoard[i][j].style.transform = 'rotate(180deg';
        }
    }

}

function getChessPieceCode(i) {
    if (i == "PawnB") return "♟";
    if (i == "PawnW") return "♙";

    if (i == "RookB") return "♜";
    if (i == "KnightB") return "♞";
    if (i == "BishopB") return "♝";

    if (i == "RookW") return "♖";
    if (i == "KnightW") return "♘";
    if (i == "BishopW") return "♗";

    if (i == "QueenB") return "♛";
    if (i == "KingB") return "♚";

    if (i == "QueenW") return "♕";
    if (i == "KingW") return "♔";

    return "";
}

function getPieceColor(code) {
    if (code == "♟" || code == "♜" || code == "♞" || code == "♛" || code == "♚" || code == "♝")
        return "B";
    return "W";
}

function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}