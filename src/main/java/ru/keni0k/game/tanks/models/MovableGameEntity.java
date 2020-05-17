package ru.keni0k.game.tanks.models;

import lombok.Getter;

import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class MovableGameEntity extends GameEntity {

    protected int speed;
    protected int damage;

    public MovableGameEntity(World world, int damage, int speed) {
        super(world);
        this.damage = damage;
        this.speed = speed;
    }

    public MovableGameEntity() {
        super();
    }

}
