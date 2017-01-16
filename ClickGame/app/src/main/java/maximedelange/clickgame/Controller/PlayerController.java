package maximedelange.clickgame.Controller;

import maximedelange.clickgame.Domain.Player;
import maximedelange.clickgame.R;

/**
 * Created by M on 1/10/2017.
 */

public class PlayerController {

    // Fields
    private Player player;

    // Constructor
    public PlayerController(String name){
        createPlayer(name);
    }

    // Methods
    public void createPlayer(String name){
        player = new Player(name, 3, R.mipmap.ic_launcher);
    }

    public int getHealth(){
        return player.getHealth();
    }

    public void setHealth(int health){
        this.player.setHealth(health);
    }

    public String getName(){
        return player.getName();
    }

    public int getScore(){
        return this.player.getScore();
    }

    public void setScore(int score){
        this.player.setScore(score);
    }

    public int getHighScore(){
        return player.getHighScore();
    }

    public void setHighScore(int score){
        this.player.setHighScore(score);
    }

    public int enemyDoDamage(int damage){
        int playerHealth = player.getHealth();
        playerHealth -= damage;
        player.setHealth(playerHealth);
        return playerHealth;
    }
}
