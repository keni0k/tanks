package ru.keni0k.game.tanks.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Bullet")
public class Bullet extends MovableGameEntity {

    private long tankId;

    public Bullet(World world, int damage, int speed, long tankId) {
        super(world, damage, speed);
        this.tankId = tankId;
    }

    public Bullet() {
    }

}
