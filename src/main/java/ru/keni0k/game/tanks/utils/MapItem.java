package ru.keni0k.game.tanks.utils;

import lombok.Getter;
import lombok.Setter;
import ru.keni0k.game.tanks.models.EntityInTheWorld;

@Getter
@Setter
public class MapItem {

    private long e;
    private Integer d;

    public MapItem(long entity) {
        this.e = entity;
        this.d = null;
    }

    public MapItem(long entity, int direction) {
        this.e = entity;
        this.d = direction;
    }

}
