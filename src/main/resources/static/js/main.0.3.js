const cellSize = 32;
const TANK_SPRITE_IMG = new Image();
TANK_SPRITE_IMG.src = 'img/tank_sprite.png';

const BASE_URL = '/app';
const TANKS_URL = BASE_URL + '/tanks';
const KEY_EVENT = TANKS_URL + '/key';
const WORLD_URL = BASE_URL + "/world";
const INIT_WORLD = WORLD_URL + '/init';
const BASE_SUBSCRIBE = '/topic';
const INIT_SUBSCRIBE = BASE_SUBSCRIBE + '/init';
const MAP_SUBSCRIBE = BASE_SUBSCRIBE + '/map';
let stompClient = null;
let map = null;
let canvas;
let ctx;

window.addEventListener("DOMContentLoaded", function() {
    canvas = document.getElementById('field');
    canvas.width = 15 * cellSize;
    canvas.height = 15 * cellSize;
    ctx = canvas.getContext('2d');
}, false);

function initGame(worldId) {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.send(INIT_WORLD, {}, worldId);
        stompClient.subscribe(MAP_SUBSCRIBE, function (response) {
            map = JSON.parse(response.body);
            drawMap();
        });
        stompClient.subscribe(INIT_SUBSCRIBE, function (response) {
            response = JSON.parse(response.body);
            worldId = response['worldId'];
            const tankId = response['tankId'];
            map = response['map'];
            drawMap();
            document.onkeypress = function (e) {
                stompClient.send(KEY_EVENT, {}, JSON.stringify({key: e.key, worldId: worldId, tankId: tankId}));
            };
        });
    });
}
