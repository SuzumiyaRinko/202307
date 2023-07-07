package com.tedu.element;

import com.tedu.show.GameJFrame;

import java.awt.*;

public class Maps extends ElementObj {

    public static int SPEED = 2;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public void move() {
        int currY = this.getY();
        if (currY < GameJFrame.GameY) {
            this.setY(currY + SPEED);
        } else {
            this.setY(-GameJFrame.GameY);
        }
    }
}
