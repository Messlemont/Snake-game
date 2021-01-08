package Snake;

import javax.swing.*;

public class Sframe extends JFrame {
    Sframe(){
        Spanel gamePanel = new Spanel();
        this.add(gamePanel);

        this.setTitle("~<:====> Snake! <====:>~");                                                                      //<====:>~ is meant to look like a lil snake
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();                        //Sframe will fit itself around what we add to the frame
        this.setVisible(true);

        this.setLocationRelativeTo(null);   //Means out game when opened will appear in the centre of the screen
    }
}
