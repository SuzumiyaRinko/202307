package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 元素管理器
public class ElementManager {

    // 懒汉单例模式，创建元素管理器
    private static ElementManager EM = null;
    private List<Object> listMap;
    private List<Object> listPlay;
    /*
        游戏元素集合
        一共有 PLAY,MAPS,ENEMY,BOSS 四种类型的游戏元素(GameElement)，每种类型都有一个元素列表List<ElementObj>
     */
    private Map<GameElement, List<ElementObj>> gameElements;

    // 初始化元素管理器里面的GameElements
    private ElementManager() {
        init();
    }

    public static synchronized ElementManager getManager() {
        if (EM == null) {
            EM = new ElementManager();
        }
        return EM;
    }

    public void init() {
        gameElements = new HashMap<>();
        GameElement[] values = GameElement.values();
        for (GameElement value : values) {
            gameElements.put(value, new ArrayList<>());
        }
    }

    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    // 添加元素
    public void addElement(ElementObj obj, GameElement ge) {
        gameElements.get(ge).add(obj);
    }

    // 根据类型获取元素列表
    public List<ElementObj> getElementsByKey(GameElement ge) {
        return gameElements.get(ge);
    }
}
