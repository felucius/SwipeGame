package maximedelange.clickgame.Screens;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import maximedelange.clickgame.Controller.PlayerController;
import maximedelange.clickgame.Database.Database;
import maximedelange.clickgame.Domain.Coordinates;
import maximedelange.clickgame.R;

public class PlayScreen extends AppCompatActivity {

    // Fields
    private int score = 0;
    private int highScore = 0;
    private long time = 0;
    private CountDownTimer countDownMovement;
    private CountDownTimer countDownTime;
    private PlayerController playerController;
    private boolean isColliding = false;
    private int xPos = 0;
    private int yPos = 0;
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;
    int x;
    int y;
    RelativeLayout linearLayout;
    private ImageView enemy;
    private Coordinates coordinates;
    private int direction;
    private int enemyMovement1;
    private int enemyMovement2;
    private int enemyMovement3;
    private int enemyHealthBar1;
    private int enemyHealthBar2;
    private int enemyHealthBar3;
    private int enemyHealthShow1;
    private int enemyHealthShow2;
    private int enemyHealthShow3;
    private int enemyHealthCounter = 0;
    private int enemyHealthBegin = 0;
    private Database database;

    // GUI components
    private ImageView imgPlayer;
    private TextView textTimer;
    private ProgressBar healthBar;
    private ProgressBar enemyHealth;
    private TextView playerName;
    private TextView enemyHealthTxt;
    private TextView playerHealthTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = new Database(this, null, null, 1);
        initializeGameInformation();
        createEnemy();
        setEnemyMovement();
    }

    /*
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();

                //if left to right sweep event on screen
                if (x1 < x2)
                {
                    linearLayout.removeView(enemy);
                    createEnemy();
                }
                // if right to left sweep event on screen
                if (x1 > x2)
                {
                    linearLayout.removeView(enemy);
                    createEnemy();
                }

                // if UP to Down sweep event on screen
                if (y1 < y2)
                {
                    linearLayout.removeView(enemy);
                    createEnemy();
                }

                //if Down to UP sweep event on screen
                if (y1 > y2)
                {

                }
                break;
            }
        }
        return false;
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createPlayer(){
        playerHealthTxt = (TextView)findViewById(R.id.txtHealthShow);
        playerHealthTxt.setText(String.valueOf(playerController.getHealth() + " / " + playerController.getHealth()));
        imgPlayer = (ImageView)findViewById(R.id.imagePlayer);
        healthBar = (ProgressBar)findViewById(R.id.playerHealth);
        healthBar.setMax(playerController.getHealth());
        healthBar.setProgress(playerController.getHealth());

        playerName = (TextView)findViewById(R.id.txtName);
        playerName.setTextSize(16);
        playerName.setTypeface(null, Typeface.BOLD);
        playerName.setText(playerController.getName());

        /*
        imgPlayer.setOnTouchListener(new OnSwipeTouchListener(PlayScreen.this) {
            public void onSwipeTop() {
                //linearLayout.removeView(enemy);
                //linearLayout.removeView(enemyHealth);
                //createEnemy();
            }
            public void onSwipeRight() {
                //test();
                //linearLayout.removeView(enemy);
                //linearLayout.removeView(enemyHealth);
                //createEnemy();
            }
            public void onSwipeLeft() {
                //linearLayout.removeView(enemy);
                //linearLayout.removeView(enemyHealth);
                //createEnemy();
            }
            public void onSwipeBottom() {
                //linearLayout.removeView(enemy);
                //linearLayout.removeView(enemyHealth);
                //createEnemy();
            }

        });
*/

        //imgPlayer.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        score ++;
        //        changeScoreBar(score);
        //    }
        //});


        /*
        imgPlayer.setOnTouchListener(new OnSwipeTouchListener(PlayScreen.this) {
            public void onSwipeTop() {
                Toast.makeText(PlayScreen.this, "top", Toast.LENGTH_SHORT).show();
                onSwipeTouchListener.onSwipeTop();
            }
            public void onSwipeRight() {
                float x = onSwipeTouchListener.getDiffX();
                System.out.println("VALUE OF RIGHT SWIPE" + x);
            }
            public void onSwipeLeft() {
            }
            public void onSwipeBottom() {
                Toast.makeText(PlayScreen.this, "bottom", Toast.LENGTH_SHORT).show();
                onSwipeTouchListener.onSwipeBottom();
                System.out.println(yPos);
            }
        });
        */
    }

    // WERKT NIET
    public void changeScoreBar(int score){
        ActionBar scoreBar = getSupportActionBar();
        if(score > highScore){
            scoreBar.setTitle("high score: " + score);
            highScore = score;
        }else{
            scoreBar.setTitle("score: " + score);
        }
    }

    /*
    Timer for the total amount of play time
     */
    public void timer(){
        textTimer = (TextView)findViewById(R.id.txtTime);
        countDownTime = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished / 1000;
                textTimer.setText("time: " + Long.valueOf(time));
            }

            @Override
            public void onFinish() {
                textTimer.setText("times up!");
                // Stops the availability to click again if the time is up
                imgPlayer.setOnTouchListener(null);
                enemy.setX(imgPlayer.getX());
            }
        };

        countDownTime.start();
    }

    /*
    Checks for enemy with player collision
     */
    public void collisionDetection(){
        Rect playerCollision = new Rect();
        Rect enemyCollision = new Rect();

        imgPlayer.getHitRect(playerCollision);
        enemy.getHitRect(enemyCollision);

        if(Rect.intersects(playerCollision, enemyCollision)){
            if(healthBar.getProgress() <= 0){
                Toast.makeText(PlayScreen.this, "GAME OVER", Toast.LENGTH_LONG).show();
                countDownMovement.onFinish();
                countDownTime.onFinish();
            }else{
                isColliding = true;
                //enemyMovement = 0;
                //enemyMovement = 0;
                //enemy.setX(0);
                //enemy.setY(670);
                healthBar.setProgress(playerController.enemyDoDamage(1));
            }
        }
    }

    /*
    Moves the enemy on the playground
     */
    public void setEnemyMovement(){
        imgPlayer = (ImageView)findViewById(R.id.imagePlayer);
        countDownMovement = new CountDownTimer(60 * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isColliding){
                    //enemyMovement = 0;
                    isColliding = false;
                }else{

                    getDirection(direction);

                    collisionDetection();
                    isColliding = false;
                }
            }

            @Override
            public void onFinish() {

            }
        };

        countDownMovement.start();
    }

    public void initializeGameInformation(){
        linearLayout = (RelativeLayout)findViewById(R.id.playGround);

        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                // gets called after layout has been done but before display.
                linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                xPos = linearLayout.getWidth();
                yPos = linearLayout.getHeight();
            }
        });

        score = Integer.valueOf(database.getHighscore());

        coordinates = new Coordinates();
        playerController = new PlayerController("Jack");
        changeScoreBar(score);
        createPlayer();
        timer();
    }

    public void createEnemy(){
        // Enemy position
        enemyMovement1 = 0;
        enemyMovement2 = 855;
        enemyMovement3 = 1340;
        direction = coordinates.createCoordinates();
        x = coordinates.getxPos();
        y = coordinates.getyPos();

        // Enemy health
        enemyHealthBar1 = 0;
        enemyHealthBar2 = 855;
        enemyHealthBar3 = 1490;
        enemyHealthShow1 = 0;
        enemyHealthShow2 = 855;
        enemyHealthShow3 = 1510;
        enemyHealthCounter += 1;
        enemyHealth = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        enemyHealth.setMax(enemyHealthCounter);
        enemyHealth.setProgress(enemyHealthCounter);
        enemyHealthBegin += 1;
        enemyHealth.setX(x);
        enemyHealth.setY(y + 150);
        enemyHealthTxt = new TextView(this);
        enemyHealthTxt.setTypeface(null, Typeface.BOLD);
        enemyHealthTxt.setX(x);
        enemyHealthTxt.setY(y + 170);

        // If statement for correcting the layout of displaying health
        if(enemyHealthBegin >= 10){
            enemyHealthTxt.setText(String.valueOf(enemyHealthBegin + " / " + enemyHealthCounter));
        }else{
            enemyHealthTxt.setText(String.valueOf("  " + enemyHealthBegin + " / " + enemyHealthCounter));
        }

        // Creating the actual views
        enemy = new ImageView(this);
        enemy.setImageResource(R.mipmap.ic_launcher);
        enemy.setX(x);
        enemy.setY(y);
        linearLayout.addView(enemy);
        linearLayout.addView(enemyHealth);
        linearLayout.addView(enemyHealthTxt);

        // Attack an enemy untill it is killed an created again
        enemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enemyHealthCounter -= 1 ;
                enemyHealth.setProgress(enemyHealthCounter);

                // If statement for correcting the layout of displaying health
                if(enemyHealthBegin >= 10){
                    enemyHealthTxt.setText(String.valueOf(enemyHealthBegin + " / " + enemyHealthCounter));
                }else{
                    enemyHealthTxt.setText(String.valueOf("  " + enemyHealthBegin + " / " + enemyHealthCounter));
                }

                // If enemy has been killed. Views are destroyed, update score and recreate an new enemy
                if(enemyHealthCounter < 1){
                    Toast.makeText(PlayScreen.this, "killed", Toast.LENGTH_SHORT).show();

                    score ++;
                    linearLayout.removeView(enemy);
                    linearLayout.removeView(enemyHealth);
                    linearLayout.removeView(enemyHealthTxt);
                    enemyHealthCounter = enemyHealthBegin;
                    createEnemy();
                    changeScoreBar(score);
                    playerController.setHighScore(score);
                    database.updateHighscore(playerController.getHighScore());
                }
            }
        });
    }

    public void getDirection(int direction){
        switch(direction){
            case 0:
                enemyMovement1 += 1;
                enemyHealthBar1 += 1;
                enemyHealthShow1 += 1;
                enemy.setX(enemyMovement1);
                enemyHealth.setX(enemyHealthBar1);
                enemyHealthTxt.setX(enemyHealthShow1);
                break;
            case 1:
                enemyMovement2 -= 1;
                enemyHealthBar2 -= 1;
                enemyHealthShow2 -= 1;
                enemy.setX(enemyMovement2);
                enemyHealth.setX(enemyHealthBar2);
                enemyHealthTxt.setX(enemyHealthShow2);
                break;
            case 2:
                enemyMovement3 -= 1;
                enemyHealthBar3 -= 1;
                enemyHealthShow3 -= 1;
                enemy.setY(enemyMovement3);
                enemyHealth.setY(enemyHealthBar3);
                enemyHealthTxt.setY(enemyHealthShow3);
                break;
        }
    }
}
