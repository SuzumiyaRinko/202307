package com.tedu.show;

import com.tedu.controller.GameThread;
import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GameMainJPanel extends JPanel implements Runnable {
    private ElementManager em;

    public GameMainJPanel() {
        init();
    }

    // 获取元素管理器
    public void init() {
        em = ElementManager.getManager();
    }

    // 该方法会在GameMainJPanel被加载时就会执行，用于绘画
    // 先画的会在底层，所以要把地图背景放在0，这样会在最底层
    // 本方法之会被执行一次，所以需要用另一个线程不断执行该方法
    @Override
    public void paint(Graphics g) {
        super.paint(g); // 如果注释了，那么旧的图像不会被删除

        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).showElement(g); // 显示每个元素
            }
        }

        g.setFont(new Font("微软雅黑", Font.BOLD, 22));
        // 时间
        g.drawImage(new ImageIcon("image/gametime.png").getImage(), 20, 20, 50, 40, null);
        g.drawString(": " + GameThread.GAME_TIME / 60 + "s", 80, 50);
        // 击杀数
        g.drawImage(new ImageIcon("image/kills.png").getImage(), 20, 70, 50, 50, null);
        g.drawString(": " + GameThread.KILLS, 80, 100);
        // 普通攻击
        g.drawImage(new ImageIcon("image/playfile.png").getImage(), 20, GameJFrame.GameY - 180, 40, 40, null);
        g.drawString(": ∞", 80, GameJFrame.GameY - 150);
        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g.drawString("SPACE 键", 20, GameJFrame.GameY - 120);
        // 道具剩余数
        g.drawImage(new ImageIcon("image/item-1.png").getImage(), 20, GameJFrame.GameY - 110, 40, 40, null);
        g.setFont(new Font("微软雅黑", Font.BOLD, 22));
        g.drawString(": " + GameThread.LEFT_ITEM, 80, GameJFrame.GameY - 80);
        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g.drawString(" X 键", 20, GameJFrame.GameY - 50);
    }

    @Override
    public void run() {
        while (true) {
            this.repaint();
            try {
                Thread.sleep(16); // fps60
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
