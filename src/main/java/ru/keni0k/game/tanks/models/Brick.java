package ru.keni0k.game.tanks.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Brick")
public class Brick extends GameEntity {

    public Brick(World world, int lives) {
        super(world);
        setLives(lives);
    }

    public Brick() { }

    public boolean isHard() {
        return getLives() == -1;
    }

}
