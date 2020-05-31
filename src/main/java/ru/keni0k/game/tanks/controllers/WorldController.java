package ru.keni0k.game.tanks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.keni0k.game.tanks.services.WorldService;
import ru.keni0k.game.tanks.utils.MapAndIds;
import ru.keni0k.game.tanks.utils.MapItem;
import ru.keni0k.game.tanks.utils.PATH;

@Controller
@RequestMapping(PATH.WORLD)
public class WorldController {

    private WorldService service;

    @Autowired
    public WorldController(WorldService tankService){
        service = tankService;
    }

    @MessageMapping("/world/init")
    @SendTo("/topic/init")
    public MapAndIds getInitialWorld(Long worldId){
        if (worldId == -1) {
            return service.initWorld();
        } else {
            return service.addNewTank(worldId);
        }
    }

    @MessageMapping("/world/map")
    @SendTo("/topic/map")
    public MapItem[][] getWorldMap(Long worldId){
        return service.getWorldMap(worldId);
    }

}
