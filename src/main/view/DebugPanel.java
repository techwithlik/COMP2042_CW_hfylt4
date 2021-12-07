/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.view;

import main.controller.Wall;
import main.model.Level;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;


public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    public DebugPanel(Wall wall, Level level){

        initialize();

        JButton skipLevel = makeButton("Skip Level", e -> level.nextLevel());
        JButton resetBalls = makeButton("Reset Balls", e -> wall.resetBallCount());

        ballXSpeed = makeSlider(e -> wall.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(e -> wall.setBallYSpeed(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * Initialize panel
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * Creates a button
     * @param title Name of the button
     */
    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return out;
    }

    /** Creates a slider */
    private JSlider makeSlider(ChangeListener e){
        JSlider out = new JSlider(-4, 4);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * Sets values that determines the speed of the ball
     * @param x Integer value as speed at the x axis
     * @param y Integer value as speed at the y axis
     */
    public void setValues(int x, int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }
}
