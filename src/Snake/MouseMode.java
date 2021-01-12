package Snake;

import java.awt.event.ActionEvent;

public class MouseMode extends Spanel {
    /* In this game mode instead of the APPLE we have the MOUSE. the
    mouse is just like the apple except it can move around the board */

    //We'll use this so the mouse moves at half the speed of the snake
    boolean letMove = false;

    public void mouseMove(){
        //Gets a random number 0, 1, 2 or 3 (Up, down, left or right)
        int mouseDirection;
        int newX = 0;
        int newY = 0;

        int count = 0;
        boolean valid = false;


        while(valid == false) {
            //If we go through the loop 6 times we'll assume there are no valid spaces for the mouse to move to and will let it stay still
            if(count == 6){
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
            count++;
        }

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
            mouseMove();
            checkApple();
            checkCrash();
        }
        repaint();
    }
}
