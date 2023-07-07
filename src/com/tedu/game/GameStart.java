package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

// 程序入口
public class GameStart {
    public static void main(String[] args) {
        // 窗口
        GameJFrame gj = new GameJFrame();
        GameMainJPanel jp = new GameMainJPanel();

        // 监听
        GameListener gameListener = new GameListener();

        // 实例化主线程
        GameThread gameThread = new GameThread();

        // 注入
        gj.setjPanel(jp); // 在JFrame里面可以嵌入JPanel
        gj.setKeyListener(gameListener);
        gj.setThread(gameThread);

        // 开始
        gj.start(); // JFrame.start()
    }
}
