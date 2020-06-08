package ru.keni0k.game.tanks.models;

import lombok.Getter;
import org.hibernate.annotations.Proxy;
import ru.keni0k.game.tanks.utils.MapItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

@Getter
@Entity
@Proxy(lazy = false)
public class World {

    private int width, height;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public World() {
        width = 26;
        height = 26;
        entityList = new ArrayList<>();
    }

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        entityList = new ArrayList<>();
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityInTheWorld> entityList;

    public static MapItem[][] getInitialWorld26x26(long tankId, int tankDuration) {
        long[][] map = new long[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 2, 2, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 2, 2, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
                {2, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 2, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, tankId + 3, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        MapItem[][] mapp = new MapItem[26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (map[i][j] < 5)
                    mapp[i][j] = new MapItem(map[i][j]);
                else
                    mapp[i][j] = new MapItem(map[i][j], tankDuration);
            }
        }
        return mapp;
    }

    public MapItem[][] getMap() {
        MapItem[][] map = new MapItem[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = new MapItem(0);
            }
        }
        for (EntityInTheWorld entity : entityList) {
            if (entity.getY() >= 0 && entity.getX() >= 0) {
                map[entity.getY()][entity.getX()].setE(entity.getNumberToView());
                map[entity.getY()][entity.getX()].setD(EntityInTheWorld.getDirection(entity.getDirection()));
            }
        }
        return map;
    }

    public void removeEntity(EntityInTheWorld entity) {
        entityList.remove(entity);
    }

    public void addGameEntity(EntityInTheWorld gameEntity) {
        entityList.add(gameEntity);
    }

    public void clearGameEntityList() {
        entityList.clear();
    }

    public boolean checkTankCollideAndChangeCoords(EntityInTheWorld entity) {
        if (isEntityOutOfTheField(entity))
            return false;
        for (EntityInTheWorld otherEntity : entityList) {
            if (otherEntity.getTargetEntity().getDType().equals("Brick") ||
                    otherEntity.getTargetEntity().getDType().equals("Tank")) {
                if (otherEntity.getId().equals(entity.getId())) {
                    continue;
                }
                float deltaX = entity.getX() - otherEntity.getX();
                float deltaY = entity.getY() - otherEntity.getY();
                if ((abs(deltaX) < 1 && abs(deltaY) < 1) ||
                        (abs(deltaX + 1) < 1 && abs(deltaY) < 1) ||
                        (abs(deltaX + 1) < 1 && abs(deltaY + 1) < 1) ||
                        (abs(deltaX) < 1 && abs(deltaY + 1) < 1)) {
                    return false;
                }
                if (otherEntity.getTargetEntity().getDType().equals("Tank")) {
                    if (abs(deltaX) < 2 && abs(deltaY) < 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<EntityInTheWorld> checkBulletCollide(EntityInTheWorld entity) {
        Bullet bullet = (Bullet) entity.getTargetEntity();
        List<EntityInTheWorld> entities = new ArrayList<>();
        for (EntityInTheWorld otherEntity : entityList) {
            float deltaX = entity.getX() - otherEntity.getX();
            float deltaY = entity.getY() - otherEntity.getY();
            if ((abs(deltaX) < 1 && abs(deltaY) < 1) ||
                    (abs(deltaX + 1) < 1 && abs(deltaY) < 1) ||
                    (abs(deltaX + 1) < 1 && abs(deltaY + 1) < 1) ||
                    (abs(deltaX) < 1 && abs(deltaY + 1) < 1)) {
                if (otherEntity.getTargetEntity().getId() != bullet.getTankId() &&
                        !otherEntity.getTargetEntity().getId().equals(bullet.getId())) {
                    entities.add(otherEntity);
                }
            }
        }
        return entities;
    }

    public boolean isEntityOutOfTheField(EntityInTheWorld entity) {
        return entity.getX() < 0 || entity.getY() < 0 || entity.getX() > 24 || entity.getY() > 24;
    }

}
