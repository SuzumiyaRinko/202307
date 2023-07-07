package com.tedu.show;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameJFrame extends JFrame {

    // 游戏窗口宽高
    public static int GameX = 600;
    public static int GameY = 800;
    // 画面修正
    public static int DX = 20; // 右侧显示修正
    public static int DY = 40; // 底部显示修正
    private JPanel jPanel = null;
    // 键盘监听器
    private KeyListener keyListener = null;
    // 鼠标监听器
    private MouseMotionListener mouseMotionListener = null;
    private MouseListener mouseListener = null;
    // 游戏主线程
    private Thread thread = null;

    // 初始化游戏窗口
    public GameJFrame() {
        init();
    }

    public void init() {
        this.setSize(GameX, GameY);
        this.setTitle("demo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 右上角关闭程序
        this.setLocationRelativeTo(null); // 游戏窗口居中
    }

    // 添加按钮元素（保存，读取...）
    public void addButton() {
    }

    // 初始化 JPanel, 监听器, 线程
    public void start() {
        if (jPanel != null) {
            this.add(jPanel);
        }
        if (keyListener != null) {
            this.addKeyListener(keyListener);
        }
        if (thread != null) {
            thread.start();
        }
        this.setVisible(true); // 显示JFrame
        if (this.jPanel instanceof Runnable) {
            // 判断类型为Runnable之后，下面就可以强转为Runnable
            new Thread((Runnable) this.jPanel).start();
        }
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public MouseMotionListener getMouseMotionListener() {
        return mouseMotionListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
