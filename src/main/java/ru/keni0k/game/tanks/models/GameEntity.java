package ru.keni0k.game.tanks.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorColumn(name = "dType", discriminatorType = DiscriminatorType.STRING)
@Table(indexes = {
        @Index(columnList = "dType", name = "d_type_hidx")})
public abstract class GameEntity {

    public GameEntity(EntityInTheWorld entityInTheWorld) {
        targetEntityInTheWorld = entityInTheWorld;
        this.lives = -1;
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

    @OneToOne
    @JoinColumn(name = "targetEntityInTheWorld")
    private EntityInTheWorld targetEntityInTheWorld;

    public boolean isAlive() {
        return lives != 0;
    }

    public void decLives() {
        lives -= 1;
    }

}
