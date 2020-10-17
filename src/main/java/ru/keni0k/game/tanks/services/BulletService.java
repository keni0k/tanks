package ru.keni0k.game.tanks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.keni0k.game.tanks.models.*;
import ru.keni0k.game.tanks.repositories.BulletRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BulletService implements BaseService<Bullet> {

    private BulletRepository repository;
    private EntityInTheWorldService entityInTheWorldService;
    private TankService tankService;
    private final WorldService worldService;

    @Autowired
    public BulletService(BulletRepository bulletRepository, EntityInTheWorldService entityInTheWorldService,
                         TankService tankService, WorldService worldService) {
        repository = bulletRepository;
        this.entityInTheWorldService = entityInTheWorldService;
        this.tankService = tankService;
        this.worldService = worldService;
    }

    @Override
    public Bullet getById(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<Bullet> findAll() {
        return repository.findAll();
    }

    @Override
    public Bullet add(Bullet model) {
        return repository.saveAndFlush(model);
    }

    @Override
    public void update(Bullet model) {
        repository.save(model);
    }

    @Override
    public void delete(Bullet model) {
        repository.delete(model);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Scheduled(fixedRate = 75)
    @Transactional
    public void reportCurrentTime() {
        List<Bullet> bullets = findAll();
        if (bullets != null) {
            for (Bullet bullet : bullets) {
                EntityInTheWorld entity = entityInTheWorldService.getByTargetEntity(bullet);
                World world = entity.getWorld();
                List<EntityInTheWorld> otherEntities = world.checkBulletCollide(entity);
                if (world.isEntityOutOfTheField(entity)) {
                    world.removeEntity(entity);
                } else if (otherEntities.size() > 0) {
                    for (EntityInTheWorld otherEntity : otherEntities) {
                        switch (otherEntity.getTargetEntity().getDType()) {
                            case "Tank":
                                Tank tank = (Tank) otherEntity.getTargetEntity();
                                tank.decLives();
                                tankService.update(tank);
                                if (!tank.isAlive()) {
                                    world.removeEntity(otherEntity);
                                }
                                break;
                            case "Brick":
                                Brick brick = (Brick) otherEntity.getTargetEntity();
                                if (!brick.isHard()) {
                                    world.removeEntity(otherEntity);
                                }
                                break;
                            case "Bullet":
                                world.removeEntity(otherEntity);
                                break;
                        }
                    }
                    world.removeEntity(entity);
                } else {
                    entity.goStraight();
                    entityInTheWorldService.update(entity);
                }
                worldService.update(world);
            }
        }
    }
}