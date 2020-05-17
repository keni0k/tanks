function keyRequest(worldId, tankId, keyAction) {
    const request = ajax().post(KEY_EVENT, {key: keyAction, worldId: worldId, tankId: tankId});
    request.then(function (response) {
        const consoleInfoElement = document.getElementById("consoleInfo");
        consoleInfoElement.innerText = response['key'];
    });
}

function mapRequest(worldId) {
    const request = ajax().get(WORLD_URL, {worldId: worldId});
    request.then(function (response) {
        map = response;
        drawMap(canvas, ctx);
    });
}