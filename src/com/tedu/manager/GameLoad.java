package com.tedu.manager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GameLoad {

    public static Map<String, ImageIcon> imgMap;

    static {
        imgMap = new HashMap<>();
        imgMap.put("up", new ImageIcon("image/up.png"));
        imgMap.put("enemy", new ImageIcon("image/enemy.png"));
        imgMap.put("player-playfile", new ImageIcon("image/player-playfile.png"));
        imgMap.put("enemy-playfile", new ImageIcon("image/enemy-playfile.png"));
        imgMap.put("die", new ImageIcon("image/die.png"));
        imgMap.put("item-1", new ImageIcon("image/item-1.png"));
        imgMap.put("item-2", new ImageIcon("image/item-2.png"));
        imgMap.put("map", new ImageIcon("image/map.png"));
    }
}
