package ru.keni0k.game.tanks.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static ru.keni0k.game.tanks.models.EntityInTheWorld.Duration.*;

@Getter
@Setter
@Entity
public class EntityInTheWorld {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GameEntity targetEntity;
    private int x, y;

    @JsonIgnore
    @ManyToOne
    private World world;

    public enum Duration {RIGHT, LEFT, UP, DOWN, NONE}

    private Duration duration;

    public EntityInTheWorld(GameEntity entity, int x, int y, Duration duration, World world) {
        targetEntity = entity;
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.world = world;
    }

    public void go(int duration) {
        switch (duration) {
            case 1:
                goUp();
                break;
            case 2:
                goLeft();
                break;
            case 3:
                goDown();
                break;
            case 4:
                goRight();
                break;
        }
    }

    public void setCoordsReverseDuration(int duration) {
        switch (duration) {
            case 1:
                setY(getY() + 1);
                break;
            case 2:
                setX(getX() + 1);
                break;
            case 3:
                setY(getY() - 1);
                break;
            case 4:
                setX(getX() - 1);
                break;
        }
    }

    public void goUp() {
        setDuration(UP);
        setY(getY() - 1);
    }

    public void goDown() {
        setDuration(DOWN);
        setY(getY() + 1);
    }

    public void goRight() {
        setDuration(RIGHT);
        setX(getX() + 1);
    }

    public void goLeft() {
        setDuration(LEFT);
        setX(getX() - 1);
    }

    public void goStraight() {
        switch (duration) {
            case RIGHT:
                goRight();
                break;
            case LEFT:
                goLeft();
                break;
            case DOWN:
                goDown();
                break;
            case UP:
                goUp();
                break;
        }
    }

    public static Duration getDuration(int duration) {
        switch (duration) {
            case 1:
                return UP;
            case 2:
                return LEFT;
            case 3:
                return DOWN;
            case 4:
                return RIGHT;
            case 5:
                return NONE;
        }
        throw new IllegalArgumentException("Duration must be in {1, 2, 3, 4, 5}");
    }

    public static int getDuration(Duration duration) {
        switch (duration) {
            case UP:
                return 1;
            case LEFT:
                return 2;
            case DOWN:
                return 3;
            case RIGHT:
                return 4;
            case NONE:
                return 5;
        }
        throw new IllegalArgumentException("Duration must be in {1, 2, 3, 4, 5}");
    }

    public long getNumberToView() {
        if (getTargetEntity() != null && getTargetEntity().getDType() != null)
            switch (getTargetEntity().getDType()) {
                case "Brick":
                    Brick brick = (Brick) getTargetEntity();
                    return brick.isHard() ? 2 : 1;
                case "Bullet":
                    return 3;
                default:
                    return getTargetEntity().getId() + 3;
            }
        return -1;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public EntityInTheWorld() {
    }
}
