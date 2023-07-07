package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameListener implements KeyListener {

    private ElementManager em = ElementManager.getManager(); // 元素管理器

    private Set<Integer> set = new HashSet<>(); // 记录所有按下的按键

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // 长按的话会直接执行该方法
    // 上38 下40 左37 右39
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (set.contains(key)) {
        /*
            长按的时候就会不断执行keyPressed()
         */
            return;
        }
        set.add(key);

        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY); // 玩家集合
        for (ElementObj obj : play) {
            obj.keyClick(true, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (!set.contains(key)) {
            return;
        }

        set.remove(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY); // 玩家集合
        for (ElementObj obj : play) {
            obj.keyClick(false, e.getKeyCode());
        }
    }
}
