package Snake;

import java.awt.event.ActionEvent;

public class MouseMode extends Spanel {
    /* In this game mode instead of the APPLE we have the MOUSE. the
    mouse is just like the apple except it can move around the board */

    //To make the mouse move at a reasonable pace it needs to move slower than DELAY, movecount will help with this
    int moveCount = 0;


    public void mouseMove(){
        //Gets a random number 0, 1, 2 or 3 (Up, down, left or right)
        int mouseDirection;
        int newX = 0;
        int newY = 0;


        //loopCount keeps track of how many times we've tried to move out of a certain space, valid will represent if [newX,newY] is a valid space or not
        int loopCount = 0;
        boolean valid = false;


        do{
            //If we go through the loop 6 times we'll assume there are no valid spaces for the mouse to move to and will let it stay still
            if(loopCount == 6){
                newX = appleX;
                newY = appleY;
                break;
            }
            //Gen a new direction for the mouse
            mouseDirection = random.nextInt(3);
            switch (mouseDirection) {
                //Up
                case 0:
                    newX = appleX - spaceSize;
                    break;

                //Down
                case 1:
                    newX = appleX + spaceSize;
                    break;

                //Left
                case 2:
                    newY = appleY - spaceSize;
                    break;

                //Right
                case 3:
                    newY = appleY + spaceSize;
                    break;
            }

            //Check to see if the new space is valid
            valid = checkSpace(newX,newY);
            loopCount++;
        }while(valid == false);

        //Now that we have a valid move for the mouse we assign its co-ords to that
        appleX = newX;
        appleY = newY;

    }

    public boolean checkSpace(int newX, int newY){
        boolean valid = true;

        //Loop through the snake bodyparts to see if the mouses position would collide
        for(int i = snakeBody; i > 0; i--){
            if((x[i] == newX) && (y[i] == newY)){
                valid = false;
            }
        }

        //If it doesnt collide with the snake then check to see if it goes out of bounds
        if (valid == false) {
            if (newX < 0) {
                valid = false;
            }
            if (newX > WIDTH) {
                valid = false;
            }
            if (newY < 0) {
                valid = false;
            }
            if (newY > HEIGHT) {
                valid = false;
            }
        }

        return valid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCrash();

            //This means we move the mouse at a 10th of the speed we move the snake
            moveCount++;
            if((moveCount % 10) == 0) {
                moveCount = 0;
                mouseMove();
            }
        }
        repaint();
    }
}
