package ru.keni0k.game.tanks.models;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Tank")
public class Tank extends MovableGameEntity {

    public Tank(World world, int lives, int damage, int speed) {
        super(world, damage, speed);
        setLives(lives);
    }

    public Tank() {
        super();
    }

}
