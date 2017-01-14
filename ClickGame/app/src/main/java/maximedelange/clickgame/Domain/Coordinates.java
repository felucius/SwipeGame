package maximedelange.clickgame.Domain;

import java.util.Random;

/**
 * Created by M on 1/14/2017.
 */

public class Coordinates {

    // Fields
    private int xPos;
    private int yPos;
    private int[] left;
    private int[] right;
    private int[] bottom;
    private Random randomNumber;

    // Constructor
    public Coordinates(){
        randomNumber = new Random();
        left = new int[]{0, 665};
        right = new int[]{850, 665};
        bottom = new int[]{420, 1340};
    }

    // Methods
    public int createCoordinates(){
        int number = randomNumber.nextInt(3);

        switch (number){
            case 0:
                setxPos(left[0]);
                setyPos(left[1]);
                break;
            case 1:
                setxPos(right[0]);
                setyPos(right[1]);
                break;
            case 2:
                setxPos(bottom[0]);
                setyPos(bottom[1]);
                break;
        }

        return number;
    }

    public void setxPos(int xPos){
        this.xPos = xPos;
    }

    public int getxPos(){
        return this.xPos;
    }

    public void setyPos(int yPos){
        this.yPos = yPos;
    }

    public int getyPos(){
        return this.yPos;
    }
}
