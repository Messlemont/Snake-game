package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Spanel extends JPanel implements ActionListener {

    Random random;
    Timer timer;
    Boolean running = false;
    Boolean paused = false;

    final static int WIDTH = 600;
    final static int HEIGHT = 600;

    final static int spaceSize = 25;
    final static int TotalSpaces = (WIDTH * HEIGHT) / spaceSize;

    //This will determine how fast the game is. bigger delay = slower game
    final static int DELAY = 100;

    //These arrays are going to hold the co-ords of the different parts of the snake
    final int x[] = new int[TotalSpaces];
    final int y[] = new int[TotalSpaces];

    //How long our snake is and how many apples its eaten
    int snakeBody = 6;
    int totApples;

    //Co-ords for where the apple is, will be randomized
    int appleX;
    int appleY;

    //Initial direction the snake will be moving in
    char direction = 'R';

    Spanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        //True by default
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdaptor());
        start();
    }



    public void start(){
        //Spawns an apple, sets up and starts timer, changes running to true
        running = true;
        newApple();
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void stop(Graphics g){
        //When the player gets a game over this method will make the game stop

        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman",Font.BOLD,50));
        FontMetrics metrics = getFontMetrics(g.getFont());

        //This should place the 'Game Over!' message at the center of the game
        g.drawString("Game Over!",(WIDTH - metrics.stringWidth("Game Over!"))/2,HEIGHT/2);

        g.setColor(Color.green);
        g.setFont(new Font("TimesRoman",Font.BOLD,50));
        FontMetrics metrics2 = getFontMetrics(g.getFont());

        //The score should now be displayed centered on the top of the panel
        g.drawString("Final score: " + totApples,(WIDTH - metrics2.stringWidth("Finsl score: " + totApples))/2,g.getFont().getSize());
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (running) {
            //These forloops draw lines so that our spaces are now more easily seen in a grid
            for (int i = 0; i < (HEIGHT / spaceSize); i++) {
                //x1,x2,y1,y2
                g.drawLine((i * spaceSize), 0, (i * spaceSize), HEIGHT);

            }
            for (int j = 0; j < (WIDTH / spaceSize); j++) {
                g.drawLine(0, (j * spaceSize), WIDTH, (j * spaceSize));
            }

            //Draws our apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, spaceSize, spaceSize);

            //Draws our snake
            for (int i = 0; i < snakeBody; i++) {
                //Draws the head (Easier to play when the head is a different colour)
                if (i == 0) {
                    g.setColor(Color.ORANGE);
                    g.fillRect(x[i], y[i], spaceSize, spaceSize);
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect(x[i], y[i], spaceSize, spaceSize);
                }
                g.setColor(Color.green);
                g.setFont(new Font("TimesRoman",Font.BOLD,50));
                FontMetrics metrics = getFontMetrics(g.getFont());

                //The score should now be displayed centered on the top of the panel
                g.drawString("Score: " + totApples,(WIDTH - metrics.stringWidth("Score: " + totApples))/2,g.getFont().getSize());
            }
        }else{
            stop(g);
        }
    }

    public void move(){
        //Updates where the snakes body parts are
        for(int i = snakeBody; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        //Uses direction to update where the head is
        switch (direction){
            case 'U':
                y[0] = y[0] - spaceSize;
                break;
            case 'D':
                y[0] = y[0] + spaceSize;
                break;
            case 'L':
                x[0] = x[0] - spaceSize;
                break;
            case 'R':
                x[0] = x[0] + spaceSize;
                break;
        }

    }
    public void newApple(){
        //This method a new apple when we start the game / when we eat the one already on the board

        // Gives us an int (X,Y) co-ord for where the apple will appear
        appleX = random.nextInt((int)(WIDTH / spaceSize)) * spaceSize;
        appleY = random.nextInt((int)(HEIGHT / spaceSize)) * spaceSize;

    }
    public void checkApple(){
        //Check if apple co-ords are the same as the head
        if ((x[0] == appleX) && (y[0] == appleY)){
            //If so increase both snake size and the score then place a new apple on the board
            snakeBody++;
            totApples++;
            newApple();
        }
    }

    public void checkCrash(){
        //Checks to see if the snake crashed into itself
        for(int i = snakeBody; i > 0; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }

        //checks to see if the snake crashed into a wall (L then R then T then B)
        if (x[0] < 0){
            running = false;
        }
        if (x[0] > WIDTH){
            running = false;
        }
        if (y[0] < 0){
            running = false;
        }
        if (y[0] > HEIGHT){
            running = false;
        }

        if (!running){
            timer.stop();

        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCrash();
        }
        repaint();
    }


    public class MyKeyAdaptor extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                //Check that the snake isnt trying to turn into itself
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                //The player can now pause/unpause the game using the space bar
                case KeyEvent.VK_SPACE:
                    if(paused){
                        resume();
                    }else{
                        pause();
                    }
                    break;
            }
        }
    }

    public void pause(){
        paused = true;
        timer.stop();
    }
    public void resume(){
        paused = false;
        timer.start();
    }

}
