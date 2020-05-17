package ru.keni0k.game.tanks.utils;

import lombok.Getter;
import lombok.Setter;
import ru.keni0k.game.tanks.models.EntityInTheWorld;

@Getter
@Setter
public class MapItem {
    long e;
    int d;

    public MapItem(long entity) {
        this.e = entity;
        this.d = EntityInTheWorld.getDuration(EntityInTheWorld.Duration.NONE);
    }

    public MapItem(long entity, int duration) {
        this.e = entity;
        this.d = duration;
    }
}
