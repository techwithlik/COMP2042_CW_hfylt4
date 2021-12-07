package main.view;

import main.controller.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class HighScore extends JFrame implements ActionListener {

    private static final int FRAME_WIDTH = 450;
    private static final int FRAME_HEIGHT = 300;

    private JButton exitGameButton;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    private final Wall wall;
    private final String highScoreRecord;

    public HighScore(Wall wall){
        this.wall = wall;
        highScoreRecord = wall.getHighScore();
        createExitGameButton();
        displayText();

        ImageIcon gameOver = new ImageIcon("resources/gameOver-bg.jpg");
        JLabel background = new JLabel(gameOver);
        background.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        background.add(label1);
        background.add(label2);
        background.add(label3);
        background.add(label4);
        background.add(exitGameButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLayout(null);
        this.setUndecorated(true);
        this.setVisible(true);
        // Make frame center of the screen
        this.setLocationRelativeTo(null);
        this.add(background);
    }

    private void displayText() {
        label1 = new JLabel();

        String text = "<html><h1 align = 'center'>GAME OVER!</h1>";
        text = text + "<html><h2 align = 'center'>YOUR SCORE IS</h2>";

        label1.setText(text);
        label1.setForeground(Color.WHITE);
        label1.setBounds(150, 0, 300, 150);

        label2 = new JLabel();
        label2.setText(Integer.toString(wall.getPlayerScore()));
        label2.setForeground(Color.WHITE);
        label2.setBounds(200, 105, 100, 50);
        label2.setFont(new Font(null, Font.PLAIN, 20));

        label3 = new JLabel();
        String text2 = "<html><h2 align = 'center'>HIGH SCORE IS HELD BY</h2>";
        label3.setText(text2);
        label3.setForeground(Color.WHITE);
        label3.setBounds(125, 130, 300, 75);

        label4 = new JLabel();
        label4.setText(highScoreRecord);
        label4.setBounds(200, 170, 100, 50);
        label4.setForeground(Color.WHITE);
        label4.setFont(new Font(null, Font.PLAIN, 20));
    }

    private void createExitGameButton() {
        exitGameButton = new JButton("EXIT GAME");
        exitGameButton.setBounds(125, 230, 200, 30);
        exitGameButton.setHorizontalTextPosition(JButton.CENTER);
        exitGameButton.setVerticalTextPosition(JButton.CENTER);
        exitGameButton.setBackground(Color.LIGHT_GRAY);
        exitGameButton.setForeground(Color.WHITE);
        exitGameButton.setFocusable(false);
        exitGameButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitGameButton){
            System.exit(0);
        }
    }
}