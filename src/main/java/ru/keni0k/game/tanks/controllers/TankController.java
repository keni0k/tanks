package ru.keni0k.game.tanks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.keni0k.game.tanks.utils.KeyAction;
import ru.keni0k.game.tanks.services.TankService;
import ru.keni0k.game.tanks.utils.PATH;

import static ru.keni0k.game.tanks.utils.PATH.KEY;

@RestController
@RequestMapping(PATH.TANKS)
public class TankController {

    private TankService service;

    @Autowired
    public TankController(TankService tankService) {
        service = tankService;
    }

    @PostMapping(KEY)
    ResponseEntity<?> keyPressed(@RequestBody KeyAction keyAction) {
        return service.keyPressed(keyAction);
    }

}
