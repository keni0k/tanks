var map = null;
const cellSize = 32;
const TANK_SPRITE_IMG = new Image();
TANK_SPRITE_IMG.src = 'img/tank_sprite.png';

const BASE_URL = '/api';
const TANKS_URL  = BASE_URL + '/tanks';
const KEY_EVENT = TANKS_URL + '/key';
const TANK2WAIT = TANKS_URL + '/get2tank';
const WORLD_URL = BASE_URL + "/world";
const INIT_WORLD = WORLD_URL + '/init';
let canvas;
let ctx;

window.addEventListener("DOMContentLoaded", function() {
    canvas = document.getElementById('field');
    canvas.width = 15 * cellSize;
    canvas.height = 15 * cellSize;
    ctx = canvas.getContext('2d');
}, false);

function startGame(worldId) {
    let tankId = null;
    const request = ajax().get(INIT_WORLD, {worldId: worldId});
    request.then(function (response) {
        worldId = response['worldId'];
        tankId = response['tankId'];
        map = response['map'];
        drawMap();
        setInterval(() => mapRequest(worldId, map), 100);
        document.onkeypress = function (e) {
            keyRequest(KEY_EVENT, worldId, tankId, e.key, map)
        };
    });

}
