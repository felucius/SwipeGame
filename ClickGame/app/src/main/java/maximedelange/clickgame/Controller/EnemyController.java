package maximedelange.clickgame.Controller;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;

import maximedelange.clickgame.Domain.Enemy;

/**
 * Created by M on 1/10/2017.
 */

public class EnemyController {

    // Fields
    private ArrayList<Enemy> enemies;

    // Constructor
    public EnemyController(){
        //createEnemies();
    }

    // Methods
    public ArrayList<Enemy> createEnemies(Bitmap bitmap){
        enemies = new ArrayList<>();
        enemies.add(new Enemy(0, 0, bitmap));

        return enemies;
    }
}
