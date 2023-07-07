package com.tedu.element;

import javax.swing.*;
import java.awt.*;

// 基本元素 POJO
public abstract class ElementObj {

    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;

    private boolean live = true; // 生存状态,可以使用枚举类型表示"隐身,无敌..."

    public ElementObj() {
    }

    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    public ElementObj createElement(String str) {
        return null;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, w, h);
    }

    // 检查当前对象与ElementObj是否发生碰撞 true:碰撞 false:没碰撞
    public boolean checkCrash(ElementObj obj) {
        return this.getRectangle().intersects(obj.getRectangle());
    }

    public abstract void showElement(Graphics g);

    public void die() {

    }

    // true:按下 false:松开
    public void keyClick(boolean b1, int key) {
    }

    /*
        模板模式，里面方法执行的顺序不能被改变
        但是子类可以对3个方法进行重写
     */
    public final void model(long currTime) {
        // 换装
        updateImage();
        // 移动
        move();
        // 发射子弹
        add(currTime);
    }

    protected void move() {
    }

    protected void updateImage() {
    }

    protected void add(long currTime) {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
