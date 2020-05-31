package ru.keni0k.game.tanks.models;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class MovableGameEntity extends GameEntity {

    protected int speed;
    protected int damage;

    public MovableGameEntity(EntityInTheWorld entityInTheWorld, int damage, int speed) {
        super(entityInTheWorld);
        this.damage = damage;
        this.speed = speed;
    }

    public MovableGameEntity() {
        super();
    }

}
