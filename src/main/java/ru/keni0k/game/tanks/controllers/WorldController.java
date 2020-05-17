package ru.keni0k.game.tanks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.keni0k.game.tanks.services.WorldService;
import ru.keni0k.game.tanks.utils.PATH;

@RestController
@RequestMapping(PATH.WORLD)
public class WorldController {

    private WorldService service;

    @Autowired
    public WorldController(WorldService tankService){
        service = tankService;
    }

    @GetMapping(PATH.INIT)
    ResponseEntity<?> getInitialWorld(@RequestParam Long worldId){
        if (worldId == -1) {
            return service.initWorld();
        } else {
            return service.addNewTank(worldId);
        }
    }

    @GetMapping
    ResponseEntity<?> getWorldMap(@RequestParam Long worldId){
        return service.getWorldMap(worldId);
    }

}
