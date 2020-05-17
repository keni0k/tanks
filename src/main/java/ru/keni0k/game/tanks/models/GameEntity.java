package ru.keni0k.game.tanks.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorColumn(name = "dType", discriminatorType = DiscriminatorType.STRING)
public abstract class GameEntity {

    public GameEntity(World world) {
        this.lives = -1;
        this.world = world;
    }

    public GameEntity() {
        this.lives = -1;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int lives;

    @Column(insertable = false, updatable = false)
    private String dType;

    @JsonIgnore
    @ManyToOne
    private World world;

    public boolean isAlive() {
        return lives != 0;
    }

    public void die() {
        lives = 0;
    }

    public void decLives(){
        lives -= 1;
    }

}
