function drawBrick(x, y) {
    // Отрисовка основного цвета кирпича
    ctx.fillStyle = '#FFA500';
    ctx.fillRect(x, y, cellSize / 2, cellSize / 2);
    // Отрисовка теней
    ctx.fillStyle = '#CD8500';
    ctx.fillRect(x, y, cellSize / 2, cellSize / 16);
    ctx.fillRect(x, y + cellSize / 4, cellSize / 2, cellSize / 16);
    ctx.fillRect(x + cellSize / 4, y, cellSize / 16, cellSize / 4);
    ctx.fillRect(x + cellSize / 16, y + cellSize / 4, cellSize / 16, cellSize / 4);
    // Отрисовка раствора между кирпичами
    ctx.fillStyle = '#D3D3D3';
    ctx.fillRect(x, y + cellSize / 4 - cellSize / 16, cellSize / 2, cellSize / 16);
    ctx.fillRect(x, y + cellSize / 2 - cellSize / 16, cellSize / 2, cellSize / 16);
    ctx.fillRect(x + cellSize / 4 - cellSize / 16, y, cellSize / 16, cellSize / 4);
    ctx.fillRect(x, y + cellSize / 4 - cellSize / 16, cellSize / 16, cellSize / 4);
}

function drawHardBrick(x, y) {
    // Отрисовка основного фона
    ctx.fillStyle = '#ccc';
    ctx.fillRect(x, y, cellSize / 2, cellSize / 2);
    // Отрисовка Тени
    ctx.fillStyle = '#909090';
    ctx.beginPath();
    ctx.moveTo(x, y + cellSize / 2);
    ctx.lineTo(x + cellSize / 2, y + cellSize / 2);
    ctx.lineTo(x + cellSize / 2, y);
    ctx.fill();
    // Отрисовка белого прямоугольника сверху
    ctx.fillStyle = '#eeeeee';
    ctx.fillRect(x + cellSize / 8, y + cellSize / 8, cellSize / 4, cellSize / 4);
}

function drawBullet(x, y) {
    ctx.fillStyle = '#ccc';
    ctx.beginPath();
    ctx.arc(x + cellSize / 2, y + cellSize / 2, cellSize / 4, 0, 2 * Math.PI, false);
    ctx.fillStyle = 'green';
    ctx.lineWidth = 5;
    ctx.strokeStyle = '#003300';
    ctx.stroke();
    ctx.fill();
}

function drawMap() {
    ctx.fillStyle = '#ccc';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = '#000';
    ctx.fillRect(cellSize, cellSize, 13 * cellSize, 13 * cellSize);
    for (let j = 0; j < 26; j++) {
        for (let i = 0; i < 26; i++) {
            let x = i * cellSize / 2 + cellSize;
            let y = j * cellSize / 2 + cellSize;
            switch (map[j][i]['e']) {
                case 1:
                    drawBrick(x, y);
                    break;
                case 2:
                    drawHardBrick(x, y);
                    break;
                case 3:
                    drawBullet(x, y);
                    break;
                default:
                    switch (map[j][i]['d']) {
                        case 1:
                            ctx.drawImage(TANK_SPRITE_IMG, 0, 0, 45, 45, x, y, cellSize, cellSize);
                            break;
                        case 2:
                            ctx.drawImage(TANK_SPRITE_IMG, 48.5, 0, 45, 45, x, y, cellSize, cellSize);
                            break;
                        case 3:
                            ctx.drawImage(TANK_SPRITE_IMG, 97, 0, 45, 45, x, y, cellSize, cellSize);
                            break;
                        case 4:
                            ctx.drawImage(TANK_SPRITE_IMG, 145.5, 0, 45, 45, x, y, cellSize, cellSize);
                            break;
                    }
            }
        }
    }
}