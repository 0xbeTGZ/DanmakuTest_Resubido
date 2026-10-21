package com.kaleworks.danmakutest.core;

import javax.swing.JFrame;

public class GameCore extends JFrame {

    public static final int Ancho = 640;
    public static final int Alto = 480;

    private PantallaMenu panelMenu;

    public GameCore() {
        setTitle("Danmaku Test");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelMenu = new PantallaMenu();

        setContentPane(panelMenu);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        panelMenu.requestFocusInWindow();
    }
}