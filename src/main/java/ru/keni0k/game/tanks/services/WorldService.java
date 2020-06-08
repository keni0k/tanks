package ru.keni0k.game.tanks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.keni0k.game.tanks.models.*;
import ru.keni0k.game.tanks.repositories.BulletRepository;
import ru.keni0k.game.tanks.repositories.TankRepository;
import ru.keni0k.game.tanks.repositories.WorldRepository;
import ru.keni0k.game.tanks.utils.MapAndIds;
import ru.keni0k.game.tanks.utils.MapItem;

import java.util.List;

@Service
public class WorldService implements BaseService<World> {

    private WorldRepository repository;
    private TankRepository tankRepository;
    private BrickService brickService;
    private BulletRepository bulletRepository;
    private EntityInTheWorldService entityInTheWorldService;

    @Autowired
    public WorldService(WorldRepository worldRepository, TankRepository tankRepository, BrickService brickService,
                        EntityInTheWorldService entityInTheWorldService, BulletRepository bulletRepository) {
        repository = worldRepository;
        this.tankRepository = tankRepository;
        this.brickService = brickService;
        this.bulletRepository = bulletRepository;
        this.entityInTheWorldService = entityInTheWorldService;
    }

    @Override
    public World getById(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<World> findAll() {
        return repository.findAll();
    }

    @Override
    public World add(World model) {
        return repository.saveAndFlush(model);
    }

    @Override
    public void update(World model) {
        repository.save(model);
    }

    @Override
    public void delete(World model) {
        repository.delete(model);
    }

    public MapAndIds initWorld() {
        World world = new World();
        world = add(world);
        System.out.println("Initialization of world " + world.getId() + " has started!");
        EntityInTheWorld entity = new EntityInTheWorld(8, 24, EntityInTheWorld.Direction.UP, world);
        entity = entityInTheWorldService.add(entity);
        world.addGameEntity(entity);
        Tank tank = new Tank(entity, 3, 1, 1);
        tank = tankRepository.saveAndFlush(tank);
        tank.setTargetEntityInTheWorld(entity);
        entity.setTargetEntity(tank);
        entityInTheWorldService.update(entity);
        MapItem[][] worldInitialState = World.getInitialWorld26x26(tank.getId(), EntityInTheWorld.getDirection(entity.getDirection()));
        for (int i = 0; i < worldInitialState.length; i++) {
            for (int j = 0; j < worldInitialState[0].length; j++) {
                long value = worldInitialState[j][i].getE();
                if (value == 1 || value == 2) {
                    entity = new EntityInTheWorld(i, j, EntityInTheWorld.Direction.NONE, world);
                    entity = entityInTheWorldService.add(entity);
                    world.addGameEntity(entity);
                    Brick brick = new Brick(entity, value == 1 ? 1 : -1);
                    brick = brickService.add(brick);
                    entity.setTargetEntity(brick);
                    entityInTheWorldService.update(entity);
                }
            }
        }
        update(world);
        System.out.println("Init of world " + world.getId() + " has finished!");
        return new MapAndIds(worldInitialState, world.getId(), tank.getId());
    }

    public MapAndIds addNewTank(Long worldId){
        World world = getById(worldId);
        EntityInTheWorld entityInTheWorld = new EntityInTheWorld( 0, 0, EntityInTheWorld.Direction.RIGHT, world);
        entityInTheWorld = entityInTheWorldService.add(entityInTheWorld);
        world.addGameEntity(entityInTheWorld);
        update(world);
        Tank tank = new Tank(entityInTheWorld, 3, 1, 1);
        tank = tankRepository.saveAndFlush(tank);
        entityInTheWorld.setTargetEntity(tank);
        entityInTheWorldService.update(entityInTheWorld);
        return new MapAndIds(world.getMap(), worldId, tank.getId());
    }

    public MapItem[][] getWorldMap(Long worldId){
        World world = getById(worldId);
        if (world != null)
            return world.getMap();
        else
            throw new NullPointerException();
    }

    @Override
    public void deleteAll() {
        List<World> worlds = findAll();
        for (World world : worlds) {
            world.clearGameEntityList();
            update(world);
        }
        tankRepository.deleteAll();
        brickService.deleteAll();
        bulletRepository.deleteAll();
        entityInTheWorldService.deleteAll();
        repository.deleteAll();
    }

}
