package ru.keni0k.game.tanks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.keni0k.game.tanks.models.*;
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
    private EntityInTheWorldService entityInTheWorldService;

    @Autowired
    public WorldService(WorldRepository worldRepository, TankRepository tankRepository, BrickService brickService,
                        EntityInTheWorldService entityInTheWorldService) {
        repository = worldRepository;
        this.tankRepository = tankRepository;
        this.brickService = brickService;
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

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ResponseEntity<?> initWorld() {
        World world = new World();
        world = add(world);

        Tank tank = new Tank(world, 3, 1, 1);
        tank = tankRepository.saveAndFlush(tank);
        EntityInTheWorld entity = new EntityInTheWorld(tank, 8, 24, EntityInTheWorld.Duration.UP, world);
        entity = entityInTheWorldService.add(entity);
        world.addGameEntity(entity);

        MapItem[][] worldInitialState = World.getInitialWorld26x26(tank.getId(), EntityInTheWorld.getDuration(entity.getDuration()));
        for (int i = 0; i < worldInitialState.length; i++) {
            for (int j = 0; j < worldInitialState[0].length; j++) {
                long value = worldInitialState[j][i].getE();
                if (value == 1) {
                    Brick brick = new Brick(world, 1);
                    brick = brickService.add(brick);
                    entity = new EntityInTheWorld(brick, i, j, EntityInTheWorld.Duration.NONE, world);
                    entity = entityInTheWorldService.add(entity);
                    world.addGameEntity(entity);
                } else if (value == 2) {
                    Brick hardBrick = new Brick(world, -1);
                    hardBrick = brickService.add(hardBrick);
                    entity = new EntityInTheWorld(hardBrick, i, j, EntityInTheWorld.Duration.NONE, world);
                    entity = entityInTheWorldService.add(entity);
                    world.addGameEntity(entity);
                }
            }
        }
        update(world);
        return new ResponseEntity<>(new MapAndIds(worldInitialState, world.getId(), tank.getId()), HttpStatus.OK);
    }

    public ResponseEntity<?> addNewTank(Long worldId){
        World world = getById(worldId);
        Tank tank = new Tank(world, 3, 1, 1);
        tank = tankRepository.saveAndFlush(tank);
        EntityInTheWorld entityInTheWorld = new EntityInTheWorld(tank, 0, 0, EntityInTheWorld.Duration.RIGHT, world);
        entityInTheWorld = entityInTheWorldService.add(entityInTheWorld);
        world.addGameEntity(entityInTheWorld);
        update(world);
        return new ResponseEntity<>(new MapAndIds(world.getMap(), worldId, tank.getId()), HttpStatus.OK);
    }

    public ResponseEntity<?> getWorldMap(Long worldId){
        World world = getById(worldId);
        if (world != null)
            return new ResponseEntity<>(world.getMap(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public void deleteAll() {
        List<World> worlds = findAll();
        for (World world : worlds) {
            world.clearGameEntityList();
            update(world);
        }
        entityInTheWorldService.deleteAll();
        tankRepository.deleteAll();
        brickService.deleteAll();
        repository.deleteAll();
    }

}
