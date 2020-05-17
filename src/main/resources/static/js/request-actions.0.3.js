function keyRequest(eventUrl, worldId, tankId, keyAction) {
    const request = ajax().post(eventUrl, {key: keyAction, worldId: worldId, tankId: tankId});
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