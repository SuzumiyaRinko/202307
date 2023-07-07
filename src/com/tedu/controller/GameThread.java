package com.tedu.controller;

import com.tedu.element.*;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class GameThread extends Thread {

    public static long FPS = 60; // 游戏帧数
    public static long GAME_TIME = 0L; // 游戏时间
    public static long LAST_REFRESH_ENEMY = 0L; // 上次敌人刷新时间
    public static long REFRESH_ENEMY_DEALT = FPS * 2L; // 敌人2s刷新一次;
    public static long LAST_REFRESH_ITEM = 0L; // 上次道具刷新时间
    public static long REFRESH_ITEM_DEALT = FPS * 5L; // 道具5s刷新一次;
    public static int KILLS = 0; // 击杀数
    public static int OBTAINS = 0; // 拾取道具数
    public static int LEFT_ITEM = 0; // 剩余道具数
    // 元素管理器
    private ElementManager em;

    public GameThread() {
        em = ElementManager.getManager();
    }

    @Override
    public void run() {
        while (true) {
            // 游戏开始前 读进度条
            gameLoad();
            // 游戏开始时 游戏过程中
            gameRun();
            // 游戏场景结束 更换游戏场景
            gameOver();

            try {
                Thread.sleep(1000 / GameThread.FPS); // fps60
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void gameLoad() {
        // 加载资源
        load();
    }

    private void gameRun() {
        // 可以替换为bool，通过bool判断是否继续gameRun()
        while (true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            moveAndUpdate(all); // 移动
            checkLive(); // 判断生存
            refreshEnemy(); // 刷新敌人
            refreshItem(); // 刷新道具

            GAME_TIME++; // 游戏时间++（1单位时间表示1帧）

            try {
                Thread.sleep(1000 / GameThread.FPS); // fps60
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void gameOver() {
    }

    // 在元素管理器中添加元素
    private void load() {
        // PLAY
        ImageIcon icon = new ImageIcon("image/up.png");
        ElementObj obj = new Play(100, 100, Play.W, Play.H, icon);
        em.addElement(obj, GameElement.PLAY);

        // MAPS
        ImageIcon mapIcon = GameLoad.imgMap.get("map");
        // map1
        Maps maps1 = new Maps();
        maps1.setX(0);
        maps1.setY(-GameJFrame.GameY);
        maps1.setW(GameJFrame.GameX);
        maps1.setH(GameJFrame.GameY);
        maps1.setIcon(mapIcon);
        // map2
        Maps maps2 = new Maps();
        maps2.setX(0);
        maps2.setY(0);
        maps2.setW(GameJFrame.GameX);
        maps2.setH(GameJFrame.GameY);
        maps2.setIcon(mapIcon);
        em.addElement(maps1, GameElement.MAPS);
        em.addElement(maps2, GameElement.MAPS);
    }

    // 游戏元素自动化
    private void moveAndUpdate(Map<GameElement, List<ElementObj>> all) {
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = list.size() - 1; i >= 0; i--) {
                ElementObj obj = list.get(i);
                if (!obj.isLive()) {
                    obj.die();
                    list.remove(i);
                }
                obj.model(GAME_TIME); // 移动每个元素
            }
        }
    }

    private void refreshEnemy() {
        if (GAME_TIME - LAST_REFRESH_ENEMY < REFRESH_ENEMY_DEALT) {
            return;
        }

        // ENEMY
        em.addElement(new Enemy().createElement(""), GameElement.ENEMY);
        LAST_REFRESH_ENEMY = GAME_TIME;
    }

    private void refreshItem() {
        if (GAME_TIME - LAST_REFRESH_ITEM < REFRESH_ITEM_DEALT) {
            return;
        }

        // ITEM
        em.addElement(new Item().createElement("type:1"), GameElement.ITEM);
        LAST_REFRESH_ITEM = GAME_TIME;
    }

    private void checkLive() {
        Play play = (Play) em.getElementsByKey(GameElement.PLAY).get(0);
        List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
        List<ElementObj> playfiles = em.getElementsByKey(GameElement.PLAYFILE);
        List<ElementObj> dies = em.getElementsByKey(GameElement.DIE);
        List<ElementObj> items = em.getElementsByKey(GameElement.ITEM);

        // 判断敌人和子弹碰撞
        for (int i = 0; i <= enemys.size() - 1; i++) {
            ElementObj enemy = enemys.get(i);
            for (int j = 0; j <= playfiles.size() - 1; j++) {
                PlayFile playfile = (PlayFile) playfiles.get(j);
                if (playfile.getType() == PlayFile.TYPE_PLAYER && enemys.get(i).checkCrash(playfiles.get(j))) {
                    enemy.setLive(false);
                    playfile.setLive(false);
                    KILLS++;
                }
            }
        }

        // 判断玩家道具和 敌人+敌人子弹 碰撞
        for (int i = 0; i <= items.size() - 1; i++) {
            Item item = (Item) items.get(i);
            if (item.getType() == Item.TYPE_DROP) {
                continue; // 只判断射出去的
            }

            // 判断敌人子弹
            for (int j = 0; j <= playfiles.size() - 1; j++) {
                PlayFile playfile = (PlayFile) playfiles.get(j);
                if (playfile.getType() == PlayFile.TYPE_ENEMY && playfile.checkCrash(item)) {
                    playfile.setLive(false);
                }
            }

            // 判断敌人
            for (int j = 0; j <= enemys.size() - 1; j++) {
                Enemy enemy = (Enemy) enemys.get(j);
                if (enemy.checkCrash(item)) {
                    enemy.setLive(false);
                    KILLS++;
                }
            }
        }

        // 判断玩家子弹和敌人子弹碰撞
        for (int i = 0; i <= playfiles.size() - 1; i++) {
            for (int j = i + 1; j <= playfiles.size() - 1; j++) {
                PlayFile playfile1 = (PlayFile) playfiles.get(i);
                PlayFile playfile2 = (PlayFile) playfiles.get(j);

                if (((playfile1.getType() == PlayFile.TYPE_PLAYER && playfile2.getType() == PlayFile.TYPE_ENEMY)
                        || (playfile1.getType() == PlayFile.TYPE_ENEMY && playfile2.getType() == PlayFile.TYPE_PLAYER))
                        && playfile1.checkCrash(playfile2)) {
                    playfile1.setLive(false);
                    playfile2.setLive(false);
                }
            }
        }

        // 判断Die对象是否已经失效
        for (int i = 0; i <= dies.size() - 1; i++) {
            Die die = (Die) dies.get(i);
            if (GameThread.GAME_TIME - die.getStartTime() > Die.LAST_TIME) {
                die.setLive(false);
            }
        }

        // 判断玩家是否碰到ITEM
        for (int i = 0; i <= items.size() - 1; i++) {
            Item item = (Item) items.get(i);
            if (item.getType() == Item.TYPE_DROP && item.checkCrash(play)) {
                item.setLive(false);
                OBTAINS++;
                LEFT_ITEM++;
            }
        }

        // 判断是否已经飞出屏幕
        // 敌人
        for (int i = 0; i <= enemys.size() - 1; i++) {
            ElementObj enemy = enemys.get(i);
            if (enemy.getY() >= GameJFrame.GameY - GameJFrame.DY) {
                enemy.setLive(false);
            }
        }
        // 掉落物/使用道具
        for (int i = 0; i <= items.size() - 1; i++) {
            ElementObj item = items.get(i);
            if (item.getY() >= GameJFrame.GameY + 200 || item.getY() <= -200) {
                item.setLive(false);
            }
        }
        // 子弹
        for (int i = 0; i <= playfiles.size() - 1; i++) {
            ElementObj playfile = playfiles.get(i);
            if (playfile.getY() >= GameJFrame.GameY - GameJFrame.DY || playfile.getY() <= 0) {
                playfile.setLive(false);
            }
        }
    }
}
