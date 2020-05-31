package ru.keni0k.game.tanks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.keni0k.game.tanks.models.*;
import ru.keni0k.game.tanks.repositories.BulletRepository;
import ru.keni0k.game.tanks.repositories.TankRepository;
import ru.keni0k.game.tanks.utils.KeyAction;
import ru.keni0k.game.tanks.utils.MapItem;

import java.util.List;

@Service
public class TankService implements BaseService<Tank> {

    private TankRepository repository;
    private WorldService worldService;
    private EntityInTheWorldService entityInTheWorldService;
    private BulletRepository bulletRepository;

    @Autowired
    TankService(TankRepository tankRepository, WorldService worldService,
                EntityInTheWorldService entityInTheWorldService, BulletRepository bulletRepository) {
        repository = tankRepository;
        this.worldService = worldService;
        this.entityInTheWorldService = entityInTheWorldService;
        this.bulletRepository = bulletRepository;
    }

    @Override
    public Tank getById(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<Tank> findAll() {
        return repository.findAll();
    }

    @Override
    public Tank add(Tank model) {
        return repository.saveAndFlush(model);
    }

    @Override
    public void update(Tank model) {
        repository.save(model);
    }

    @Override
    public void delete(Tank model) {
        repository.delete(model);
    }

    private void addBullet(World world, KeyAction keyAction) {
        long tankId = keyAction.getTankId();
        EntityInTheWorld tankEntity = entityInTheWorldService.getByTargetEntity(getById(tankId)); //TODO getByTargetEntityId
        int x = tankEntity.getX();
        int y = tankEntity.getY();
        switch (tankEntity.getDirection()) {
            case UP:
                y -= 2;
                break;
            case DOWN:
                y += 2;
                break;
            case LEFT:
                x -= 2;
                break;
            case RIGHT:
                x += 2;
                break;
        }
        EntityInTheWorld entityInTheWorld = new EntityInTheWorld(x, y, tankEntity.getDirection(), world);
        entityInTheWorld = entityInTheWorldService.add(entityInTheWorld);
        world.addGameEntity(entityInTheWorld);
        Bullet bullet = new Bullet(entityInTheWorld, 1, 1, tankId);
        bullet = bulletRepository.saveAndFlush(bullet);
        entityInTheWorld.setTargetEntity(bullet);
        entityInTheWorldService.update(entityInTheWorld);
        worldService.update(world);
    }

    private void moveTank(World world, KeyAction keyAction) {
        String tankKeys = "wasdцфыв";
        int dur = (tankKeys.indexOf(keyAction.getKey()) % 4) + 1;
        Tank tank = getById(keyAction.getTankId());
        EntityInTheWorld entityInTheWorld = entityInTheWorldService.getByTargetEntity(tank);
        entityInTheWorld.go(dur);
        boolean mayChangeCoords = world.checkTankCollideAndChangeCoords(entityInTheWorld);
        if (!mayChangeCoords) {
            entityInTheWorld.setCoordsReverseDuration(dur);
        }
        entityInTheWorldService.update(entityInTheWorld);
    }

    public MapItem[][] keyPressed(KeyAction keyAction) {
        String bullets = "eу";
        String tank = "wasdцфыв";
        World world = worldService.getById(keyAction.getWorldId());
        if (bullets.indexOf(keyAction.getKey()) != -1)
            addBullet(world, keyAction);
        else if (tank.indexOf(keyAction.getKey()) != -1) {
            moveTank(world, keyAction);
        }
        return worldService.getWorldMap(keyAction.getWorldId());
    }

    @Override
    public void deleteAll() {
        List<Tank> tanks = findAll();
        for (Tank tank : tanks) {
            delete(tank);
        }
    }
}