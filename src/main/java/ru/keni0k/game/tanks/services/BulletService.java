package ru.keni0k.game.tanks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.keni0k.game.tanks.models.*;
import ru.keni0k.game.tanks.repositories.BulletRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BulletService implements BaseService<Bullet> {

    private BulletRepository repository;
    private EntityInTheWorldService entityInTheWorldService;
    private TankService tankService;
    private BrickService brickService;
    private WorldService worldService;

    @Autowired
    public BulletService(BulletRepository bulletRepository, EntityInTheWorldService entityInTheWorldService,
                         WorldService worldService, TankService tankService, BrickService brickService) {
        repository = bulletRepository;
        this.entityInTheWorldService = entityInTheWorldService;
        this.worldService = worldService;
        this.tankService = tankService;
        this.brickService = brickService;
    }

    @Override
    public Bullet getById(Long id) {
        return repository.getOne(id);
    }

    public List<EntityInTheWorld> getBulletsByWorldId(Long worldId) {
        List<Bullet> bullets = repository.getBulletsByWorldId(worldId);
        List<EntityInTheWorld> entities = new ArrayList<>();
        for (Bullet bullet : bullets) {
            entities.add(entityInTheWorldService.getByTargetEntity(bullet));
        }
        return entities;
    }

    @Override
    public List<Bullet> findAll() {
        return repository.findAll();
    }

    @Override
    public Bullet add(Bullet model) {
        model.setDType("Bullet");  // TODO: autoSet
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
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Component
    public class ScheduledTasks {
        private void update(Bullet bullet) {
            EntityInTheWorld entity = entityInTheWorldService.getByTargetEntity(bullet);
            entity.goStraight();
            World world = bullet.getWorld();
            if (world.isEntityOutOfTheField(entity)) {
                System.out.println("OUT! Entity = " + entity.getId());
                world.removeEntity(entity);
                worldService.update(world);
                delete(bullet);
            } else {
                EntityInTheWorld otherEntity = world.checkBulletCollide(entity);
                if (otherEntity != null) {
                    switch (otherEntity.getTargetEntity().getDType()) {
                        case "Tank":
                            Tank tank = (Tank) otherEntity.getTargetEntity();
                            tank.decLives();
                            if (!tank.isAlive()) {
                                world.removeEntity(otherEntity);
                                worldService.update(world);
                                tankService.delete(tank);
                            }
                            break;
                        case "Brick":
                            Brick brick = (Brick) otherEntity.getTargetEntity();
                            if (!brick.isHard()) {
                                world.removeEntity(otherEntity);
                                worldService.update(world);
                                brickService.delete(brick);
                            }
                            break;
                        case "Bullet":
                            Bullet otherBullet = (Bullet) otherEntity.getTargetEntity();
                            world.removeEntity(otherEntity);
                            worldService.update(world);
                            delete(otherBullet);
                            break;
                    }
                } else {
                    entityInTheWorldService.update(entity);
                }
            }
        }

        @Scheduled(fixedRate = 50)
        public void reportCurrentTime() {
            List<Bullet> bullets = findAll();
            if (bullets != null)
                bullets.forEach(this::update);
        }
    }

}
