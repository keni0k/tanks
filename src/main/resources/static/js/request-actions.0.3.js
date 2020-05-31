function mapRequest(worldId) {
    const request = ajax().get(WORLD_URL, {worldId: worldId});
    request.then(function (response) {
        map = response;
        drawMap(canvas, ctx);
    });
}