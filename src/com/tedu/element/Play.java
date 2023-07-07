package com.tedu.element;

import com.tedu.controller.GameThread;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;

public class Play extends ElementObj /* implements Comparable<Play> */ {

    public static int W = 50;
    public static int H = 50;
    public static int SPEED = 6;
    public static int FIRE_DELTA = (int) (GameThread.FPS * 0.2); // 0.2s发射一次
    public static long LAST_FIRE_TIME = 0L;

    public static int ITEM_DELTA = (int) (GameThread.FPS * 1); // 1s发射一次
    public static long LAST_ITEM_TIME = 0L;

    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;

    private String fx = "up"; // 默认方向为上
    private boolean pkType = false; // true:攻击 false:不攻击
    private boolean itemType = false; // true:使用道具 false:不使用道具

    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    @Override
    public void showElement(Graphics g) {
        // 把image绘画到以(x,y)为左上角坐标的位置，w是宽度，h是高度
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public void keyClick(boolean b1, int key) {
        if (b1) {
            switch (key) {
                case 37: // 左
                    this.right = false;
                    this.left = true;
                    this.fx = "left";
                    break;
                case 38: // 上
                    this.down = false;
                    this.up = true;
                    this.fx = "up";
                    break;
                case 39: // 右
                    this.left = false;
                    this.right = true;
                    this.fx = "right";
                    break;
                case 40: // 下
                    this.up = false;
                    this.down = true;
                    this.fx = "down";
                    break;
                case 32: // 空格
                    this.pkType = true;
                    break;
                case 88: // x
                    this.itemType = true;
                    break;
            }
        } else {
            switch (key) {
                case 37: // 左
                    this.left = false;
                    break;
                case 38: // 上
                    this.up = false;
                    break;
                case 39: // 右
                    this.right = false;
                    break;
                case 40: // 下
                    this.down = false;
                    break;
                case 32: // 空格
                    this.pkType = false;
                    break;
                case 88: // x
                    this.itemType = false;
                    break;
            }
        }
    }

    @Override
    protected void updateImage() {
//        ImageIcon imageIcon = GameLoad.imgMap.get(fx);
        ImageIcon imageIcon = GameLoad.imgMap.get("up");
        if (imageIcon.getIconHeight() <= 0 || imageIcon.getIconWidth() <= 0) {
            // 说明图片路径错误
        }
        this.setIcon(imageIcon);
    }

    @Override
    public void move() {
        if (this.left && this.getX() - Play.SPEED >= 0) {
            this.setX(this.getX() - Play.SPEED);
        }
        if (this.up && this.getY() - Play.SPEED >= 0) {
            this.setY(this.getY() - Play.SPEED);
        }
        if (this.right && this.getX() + Play.SPEED + Play.W + GameJFrame.DX <= GameJFrame.GameX) {
            this.setX(this.getX() + Play.SPEED);
        }
        if (this.down && this.getY() + Play.SPEED + Play.H + GameJFrame.DY <= GameJFrame.GameY) {
            this.setY(this.getY() + Play.SPEED);
        }
    }

    // 攻击
    @Override
    protected void add(long gameTime) {
        // 发射子弹
        if (this.pkType && (GameThread.GAME_TIME - Play.LAST_FIRE_TIME) >= Play.FIRE_DELTA) {
            ElementObj element = new PlayFile().createElement(this.toString());
            ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
            Play.LAST_FIRE_TIME = GameThread.GAME_TIME;
        }

        // 发射道具
        if (this.itemType && GameThread.LEFT_ITEM > 0 && (GameThread.GAME_TIME - Play.LAST_ITEM_TIME) >= Play.ITEM_DELTA) {
            String t = this.toString();
            t = t.substring(0, t.length() - 1) + "2";
            ElementObj element = new Item().createElement(t);
            ElementManager.getManager().addElement(element, GameElement.ITEM);

            GameThread.LEFT_ITEM--;
        }
    }

    @Override
    public String toString() {
        // 调整子弹坐标
        int newX = this.getX();
        int newY = this.getY();

        newX = this.getX() + (Play.W - PlayFile.W) / 2;
        newY = this.getY() - PlayFile.H;

//        switch (this.fx) {
//            case "left":
//                newX = this.getX() - PlayFile.W;
//                newY = this.getY() + (Play.H - PlayFile.H) / 2;
//                break;
//            case "up":
//                newX = this.getX() + (Play.W - PlayFile.W) / 2;
//                newY = this.getY() - PlayFile.H;
//                break;
//            case "right":
//                newX = this.getX() + PlayFile.W;
//                newY = this.getY() + (Play.H - PlayFile.H) / 2;
//                break;
//            case "down":
//                newX = this.getX() + (Play.W - PlayFile.W) / 2;
//                newY = this.getY() + PlayFile.H;
//                break;
//        }

        // x:1,y:2,fx:up
//        return "x:" + newX + ",y:" + newY + ",f:" + this.fx;

        // x:1,y:2,type:1
        return "x:" + newX + ",y:" + newY + ",type:1";
    }
}
