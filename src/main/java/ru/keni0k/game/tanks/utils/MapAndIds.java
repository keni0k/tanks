package ru.keni0k.game.tanks.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MapAndIds {

    private MapItem[][] map;
    private long worldId;
    private long tankId;

}
