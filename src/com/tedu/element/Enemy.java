package com.tedu.element;

import com.tedu.controller.GameThread;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj {

    public static int W = 40;
    public static int H = 40;

    public static int SPEED = 2;
    public static int FIRE_DELTA = (int) (GameThread.FPS * 1.5); // 1.5s发射一次
    public static int MOVE_DELTA = (int) (GameThread.FPS * 2); // 2s更换一次方向
    private long lastFireTime = 0L;
    private long lastMoveTime = 0L;
    private int fx = 0; // 0:左 1:右

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        Random r = new Random();
        int x = r.nextInt(GameJFrame.GameX - 2 * GameJFrame.DX) + 20;
//        int y = r.nextInt(GameJFrame.GameY - 3*GameJFrame.DY);
        int y = -H;

        this.setX(x);
        this.setY(y);
        this.setW(W);
        this.setH(H);
        this.setIcon(GameLoad.imgMap.get("enemy"));
        return this;
    }

    @Override
    public void move() {
        if (GameThread.GAME_TIME - this.lastMoveTime >= Enemy.MOVE_DELTA) {
            fx = new Random().nextInt(2); // [0, 1]
            this.lastMoveTime = GameThread.GAME_TIME;
        }

        this.setY(this.getY() + Enemy.SPEED); // 一直往下走

        if (fx == 0 && this.getX() - Enemy.SPEED >= 0) {
            this.setX(this.getX() - Enemy.SPEED);
        } else if (fx == 1 && this.getX() + Enemy.SPEED + 5 * GameJFrame.DX <= GameJFrame.GameX) {
            this.setX(this.getX() + Enemy.SPEED);
        }
    }

    // 添加子弹
    @Override
    protected void add(long gameTime) {
        if (GameThread.GAME_TIME - this.lastFireTime < Enemy.FIRE_DELTA) {
            return;
        }

        ElementObj element = new PlayFile().createElement(this.toString());
        ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
        this.lastFireTime = GameThread.GAME_TIME;
    }

    @Override
    public void die() {
        ElementObj element = new Die().createElement(this.toString());
        ElementManager.getManager().addElement(element, GameElement.DIE);
    }

    @Override
    public String toString() {
        // 调整子弹坐标
        int newX = this.getX() + (Play.W - PlayFile.W) / 2;
        int newY = this.getY() + PlayFile.H;

        // x:1,y:2,type:1
        return "x:" + newX + ",y:" + newY + ",type:2";
    }
}
