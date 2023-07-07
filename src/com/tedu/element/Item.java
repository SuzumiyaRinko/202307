package com.tedu.element;

import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import java.awt.*;
import java.util.Random;

public class Item extends ElementObj {

    public static int W = 40;
    public static int H = 40;

    public static int SPEED = 3;
    public static int TYPE_DROP = 1;
    public static int TYPE_ITEM = 2;
    private int fx = 0; // 0:左 1:右
    private int type = TYPE_DROP; // 1:掉落物 2:使用

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
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

        if (this.type == Item.TYPE_DROP) {
            // 掉落
            Random r = new Random();
            int x = r.nextInt(GameJFrame.GameX - 2 * GameJFrame.DX) + 20;
            int y = -H;

            this.setX(x);
            this.setY(y);

            this.setIcon(GameLoad.imgMap.get("item-1"));
            this.setW(W);
            this.setH(H);
        } else {
            // 使用
            this.setIcon(GameLoad.imgMap.get("item-2"));
            this.setW(2 * W);
            this.setH(2 * H);
        }

        return this;
    }

    @Override
    public void move() {
        if (this.type == Item.TYPE_DROP) {
            // 掉落
            this.setY(this.getY() + Item.SPEED); // 一直往下走
            if (fx == 0) {
                if (this.getX() - Item.SPEED >= 0) {
                    this.setX(this.getX() - Item.SPEED);
                } else {
                    fx = 1;
                }
            } else {
                if (this.getX() + Item.SPEED + GameJFrame.DX <= GameJFrame.GameX) {
                    this.setX(this.getX() + Item.SPEED);
                } else {
                    fx = 0;
                }
            }
        } else {
            // 使用
            this.setY(this.getY() - 2 * Item.SPEED); // 一直往上走
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
