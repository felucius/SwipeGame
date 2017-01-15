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

    public String getName(){
        return player.getName();
    }

    public int getHighScore(){
        return player.getScore();
    }

    public void setHighScore(int score){
        this.player.setScore(score);
    }

    public int enemyDoDamage(int damage){
        int playerHealth = player.getHealth();
        playerHealth -= damage;
        player.setHealth(playerHealth);
        return playerHealth;
    }
}
