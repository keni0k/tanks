package ru.keni0k.game.tanks.services;

import java.util.List;

public interface BaseService<T> {
    T getById(Long id);

    List<T> findAll();

    T add(T model);

    void update(T model);

    void delete(T model);

    void deleteAll();
}