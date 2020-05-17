package ru.keni0k.game.tanks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.keni0k.game.tanks.models.Brick;
import ru.keni0k.game.tanks.repositories.BrickRepository;

import java.util.List;

@Service
public class BrickService implements BaseService<Brick> {

    private BrickRepository repository;

    @Autowired
    public BrickService(BrickRepository brickRepository) {
        repository = brickRepository;
    }

    @Override
    public Brick getById(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<Brick> findAll() {
        return repository.findAll();
    }

    @Override
    public Brick add(Brick model) {
        return repository.saveAndFlush(model);
    }

    @Override
    public void update(Brick model) {
        repository.save(model);
    }

    @Override
    public void delete(Brick model) {
        repository.delete(model);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
