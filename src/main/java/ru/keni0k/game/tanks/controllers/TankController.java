package ru.keni0k.game.tanks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.keni0k.game.tanks.utils.KeyAction;
import ru.keni0k.game.tanks.services.TankService;
import ru.keni0k.game.tanks.utils.MapItem;
import ru.keni0k.game.tanks.utils.PATH;

@Controller
@RequestMapping(PATH.TANKS)
public class TankController {

    private final TankService service;

    @Autowired
    public TankController(TankService tankService) {
        service = tankService;
    }

    @MessageMapping("/tanks/key")
    @SendTo("/topic/map")
    public MapItem[][] keyPressed(KeyAction keyAction) {
        return service.keyPressed(keyAction);
    }

}
