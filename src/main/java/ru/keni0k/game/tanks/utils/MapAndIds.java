package ru.keni0k.game.tanks.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MapAndIds {
    MapItem[][] map;
    long worldId;
    long tankId;
}
