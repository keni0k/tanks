package ru.keni0k.game.tanks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.keni0k.game.tanks.models.Bullet;
import ru.keni0k.game.tanks.services.BulletService;
import ru.keni0k.game.tanks.utils.PATH;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(PATH.BULLETS)
public class BulletController {

    private BulletService service;

    @Autowired
    public BulletController(BulletService bulletService){
        service = bulletService;
    }

    @GetMapping("/{worldId}")
    public ResponseEntity<?> getBullets(@PathVariable long worldId) {
        return new ResponseEntity<>(service.getBulletsByWorldId(worldId), HttpStatus.OK);
    }

}
