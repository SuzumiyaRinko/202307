package com.tedu.element;


import com.tedu.controller.GameThread;
import com.tedu.manager.GameLoad;

import java.awt.*;

/*
    声明一个createTime属性，记录Die对象的创建时间
    然后在主线程内，不断把Die对象的createTime与gameTime做对比
    比如一个爆炸的图像要生成两秒，那么在(gameTime-createTime)<2s时，live==true，大于之后，就直接在元素管理器去掉该对象
    图像设置为爆炸的图像
 */
public class Die extends ElementObj {

    public static int LAST_TIME = (int) GameThread.FPS * 2; // 爆炸显示2s
    private long startTime = 0L; // 爆炸开始时间

    // 工厂方法
    @Override
    public ElementObj createElement(String str) {
        // x, y
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
            }
        }
        // w, h
        this.setW(Enemy.W);
        this.setH(Enemy.H);
        this.setIcon(GameLoad.imgMap.get("die"));
        // startTime
        this.setStartTime(GameThread.GAME_TIME);

        return this;
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public String toString() {
        // 调整子弹坐标
        int newX = this.getX() + (Play.W - PlayFile.W) / 2;
        int newY = this.getY() + PlayFile.H;

        // x:1,y:2,type:1
        return "x:" + newX + ",y:" + newY + ",type:2";
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
