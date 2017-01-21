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

    public void setUpgradeHealth(int health){
        this.player.setUpgradeHealth(health);
    }

    public void setUpgradeDamage(int health){
        this.player.setUpgradeDamage(health);
    }

    public String getName(){
        return player.getName();
    }

    public void setDamage(int damage){
        this.player.setDamage(damage);
    }

    public int getDamage(){
        return player.getDamage();
    }

    public void setGold(int gold){
        this.player.setGold(gold);
    }

    public int getGold(){
        return this.player.getGold();
    }

    public void setBuyUpgrade(int upgradeCost){
        this.player.setBuyUpgrade(upgradeCost);
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

    public void setAmountOfUpgrades(int amountOfUpgrades){
        this.player.setAmountOfUpgrades(amountOfUpgrades);
    }

    public int getAmoutOfUpgrades(){
        return this.player.getAmountOfUpgrades();
    }

    public int enemyDoDamage(int damage){
        int playerHealth = player.getHealth();
        playerHealth -= damage;
        player.setHealth(playerHealth);
        return playerHealth;
    }
}
