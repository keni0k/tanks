package ru.keni0k.game.tanks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
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

    @Autowired
    public BulletService(BulletRepository bulletRepository, EntityInTheWorldService entityInTheWorldService,
                         TankService tankService, BrickService brickService) {
        repository = bulletRepository;
        this.entityInTheWorldService = entityInTheWorldService;
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

    @Component
    public class ScheduledTasks {
        @Scheduled(fixedRate = 50)
        public void reportCurrentTime() {
            List<Bullet> bullets = findAll();
            List<EntityInTheWorld> toRemove = new ArrayList<>();
            if (bullets != null) {
                for (Bullet bullet : bullets) {
                    EntityInTheWorld entity = entityInTheWorldService.getByTargetEntity(bullet);
                    World world = bullet.getWorld();
                    List<EntityInTheWorld> otherEntities = world.checkBulletCollide(entity);
                    if (world.isEntityOutOfTheField(entity)) {
                        toRemove.add(entity);
                    } else if (otherEntities.size() > 0) {
                        for (EntityInTheWorld otherEntity : otherEntities) {
                            switch (otherEntity.getTargetEntity().getDType()) {
                                case "Tank":
                                    Tank tank = (Tank) otherEntity.getTargetEntity();
                                    tank.decLives();
                                    tankService.update(tank);
                                    if (!tank.isAlive()) {
                                        tankService.delete(tank);
                                    }
                                    break;
                                case "Brick":
                                    Brick brick = (Brick) otherEntity.getTargetEntity();
                                    if (!brick.isHard()) {
                                        brickService.delete(brick);
                                    }
                                    break;
                                case "Bullet":
                                    toRemove.add(otherEntity);
                                    break;
                            }
                        }
                        toRemove.add(entity);
                    } else {
                        entity.goStraight();
                        entityInTheWorldService.update(entity);
                    }
                }
                for (EntityInTheWorld entity: toRemove){
                    try {
                        delete((Bullet) entity.getTargetEntity());
                    } catch (JpaObjectRetrievalFailureException ignore){}
                }
            }
        }
    }
}
