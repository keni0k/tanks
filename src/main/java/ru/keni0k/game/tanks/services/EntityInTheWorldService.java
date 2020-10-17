package ru.keni0k.game.tanks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.keni0k.game.tanks.models.EntityInTheWorld;
import ru.keni0k.game.tanks.models.GameEntity;
import ru.keni0k.game.tanks.repositories.EntityInTheWorldRepository;

import java.util.List;

@Service
public class EntityInTheWorldService implements BaseService<EntityInTheWorld> {

    private EntityInTheWorldRepository repository;

    @Autowired
    public EntityInTheWorldService(EntityInTheWorldRepository entityInTheWorldRepository) {
        repository = entityInTheWorldRepository;
    }

    @Override
    public EntityInTheWorld getById(Long id) {
        return repository.getOne(id);
    }

    public EntityInTheWorld getByTargetEntity(GameEntity entity) {
        return repository.getByTargetEntity(entity);
    }

    public EntityInTheWorld getByTargetEntityId(Long id) {
        return repository.getByTargetEntityId(id);
    }

    @Override
    public List<EntityInTheWorld> findAll() {
        return repository.findAll();
    }

    @Override
    public EntityInTheWorld add(EntityInTheWorld model) {
        return repository.saveAndFlush(model);
    }

    @Override
    public void update(EntityInTheWorld model) {
        repository.save(model);
    }

    @Override
    public void delete(EntityInTheWorld model) {
        repository.delete(model);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
