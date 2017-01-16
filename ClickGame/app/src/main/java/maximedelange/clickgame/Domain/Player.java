package maximedelange.clickgame.Domain;

import maximedelange.clickgame.R;

/**
 * Created by M on 1/10/2017.
 */

public class Player {

    // Fields
    private int score;
    private int highScore;
    private String name;
    private int health;
    private int image;
    private int experience;

    // Constructor
    public Player(String name, int health, int image){
        this.name = name;
        this.health = health;
        this.score = 0;
        this.highScore = 0;
        this.image = image;
        this.experience = 0;
    }

    // Methods
    public void setExperience(int experience){
        this.experience = experience;
    }

    public int getExperience(){
        return this.experience;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setHighScore(int score){
        this.highScore = score;
    }

    public int getHighScore(){
        return this.highScore;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void setImage(int image){
        this.image = image;
    }

    public int getImage(){
        return this.image;
    }
}
