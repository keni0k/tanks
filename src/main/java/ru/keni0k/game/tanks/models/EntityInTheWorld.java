package ru.keni0k.game.tanks.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static ru.keni0k.game.tanks.models.EntityInTheWorld.Direction.*;

@Getter
@Setter
@Entity(name = "entity_in_the_world")
public class EntityInTheWorld {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private GameEntity targetEntity;

    private int x, y;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    private World world;

    public enum Direction {RIGHT, LEFT, UP, DOWN, NONE}

    private Direction direction;

    public EntityInTheWorld(int x, int y, Direction direction, World world) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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

    public void setCoordsReverseDuration(int direction) {
        switch (direction) {
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
        setDirection(UP);
        setY(getY() - 1);
    }

    public void goDown() {
        setDirection(DOWN);
        setY(getY() + 1);
    }

    public void goRight() {
        setDirection(RIGHT);
        setX(getX() + 1);
    }

    public void goLeft() {
        setDirection(LEFT);
        setX(getX() - 1);
    }

    public void goStraight() {
        switch (direction) {
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

    public static int getDirection(Direction direction) {
        switch (direction) {
            case UP:
                return 1;
            case LEFT:
                return 2;
            case DOWN:
                return 3;
            case RIGHT:
                return 4;
            default:
                return 5;
        }
    }

    public long getNumberToView() {
        if (getTargetEntity() != null && getTargetEntity().getDType() != null) {
            switch (getTargetEntity().getDType()) {
                case "Brick":
                    Brick brick = (Brick) getTargetEntity();
                    return brick.isHard() ? 2 : 1;
                case "Bullet":
                    return 3;
                default:
                    return getTargetEntity().getId() + 3;
            }
        }
        return -1;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public EntityInTheWorld() { }
}
