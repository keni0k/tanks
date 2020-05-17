package ru.keni0k.game.tanks.models;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Bullet")
@Getter
public class Bullet extends MovableGameEntity {

    private long tankId;

    public Bullet(World world, int damage, int speed, long tankId) {
        super(world, damage, speed);
        this.tankId = tankId;
        this.setLives(1);
    }

    public Bullet() { }

}
