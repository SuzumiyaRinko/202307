package com.tedu.element;

import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;

// 玩家子弹类
public class PlayFile extends ElementObj {

    // 1表示玩家的子弹 2表示敌人的子弹
    public static int TYPE_PLAYER = 1;
    public static int TYPE_ENEMY = 2;

    public static int W = 20;
    public static int H = 20;

    private int attack = 1; // 攻击力
    private int moveNum = 10; // 移动速度
    //    private String fx; // 子弹方向
    private int type;

    public PlayFile() {
    }

    // 工厂方法
    @Override
    public ElementObj createElement(String str) {
        // x, y, type
        String[] split = str.split(",");
        for (String str1 : split) {
            String[] split2 = str1.split(":");
            switch (split2[0]) {
                case "x":
                    this.setX(Integer.parseInt(split2[1]));
                    break;
                case "y":
                    this.setY(Integer.parseInt(split2[1]));
                    break;
                case "type":
                    this.type = Integer.parseInt(split2[1]);
                    break;
            }
        }
        // w, h
        this.setW(PlayFile.W);
        this.setH(PlayFile.H);

        return this;
    }

    @Override
    public void showElement(Graphics g) {
        ImageIcon imageIcon;
        if (this.type == PlayFile.TYPE_PLAYER) {
            imageIcon = GameLoad.imgMap.get("player-playfile");
        } else {
            imageIcon = GameLoad.imgMap.get("enemy-playfile");
        }
        g.drawImage(imageIcon.getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    protected void move() {
        if (this.getX() <= 0 || this.getY() <= 0 ||
                this.getX() >= GameJFrame.GameX || this.getY() >= GameJFrame.GameY) {
            this.setLive(false);
            return;
        }

        if (this.type == PlayFile.TYPE_PLAYER) {
            this.setY(this.getY() - this.moveNum);
        } else {
            this.setY(this.getY() + this.moveNum);
        }

//        switch (this.fx) {
//            case "left":
//                this.setX(this.getX() - this.moveNum);
//                break;
//            case "up":
//                this.setY(this.getY() - this.moveNum);
//                break;
//            case "right":
//                this.setX(this.getX() + this.moveNum);
//                break;
//            case "down":
//                this.setY(this.getY() + this.moveNum);
//                break;
//        }
    }

    @Override
    public void die() {

    }

    public int getType() {
        return type;
    }
}
