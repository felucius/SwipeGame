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
    public PlayerController(){
        createPlayer();
    }

    // Methods
    public void createPlayer(){
        player = new Player("Test", 3, R.mipmap.ic_launcher);
    }

    public int getHealth(){
        return player.getHealth();
    }

    public String getName(){
        return player.getName();
    }

    public int enemyDoDamage(int damage){
        int playerHealth = player.getHealth();
        playerHealth -= damage;
        player.setHealth(playerHealth);
        return playerHealth;
    }
}
