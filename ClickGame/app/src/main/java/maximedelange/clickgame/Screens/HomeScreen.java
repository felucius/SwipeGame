package maximedelange.clickgame.Screens;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;
import maximedelange.clickgame.Controller.EnemyController;
import maximedelange.clickgame.Controller.PlayerController;
import maximedelange.clickgame.Domain.Coordinates;
import maximedelange.clickgame.Domain.Enemy;
import maximedelange.clickgame.R;

public class HomeScreen extends AppCompatActivity {

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

    // GUI components
    private ImageView imgPlayer;
    private TextView textTimer;
    private ProgressBar healthBar;
    private TextView playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeGameInformation();
        createEnemy();
        setEnemyMovement();
    }

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

    public void playerDrawing(){
        //imgPlayer = (ImageView)findViewById(R.id.imagePlayer);
        //imgPlayer.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        score ++;
        //        changeScoreBar(score);
        //    }
        //});


        /*
        imgPlayer.setOnTouchListener(new OnSwipeTouchListener(HomeScreen.this) {
            public void onSwipeTop() {
                Toast.makeText(HomeScreen.this, "top", Toast.LENGTH_SHORT).show();
                onSwipeTouchListener.onSwipeTop();
            }
            public void onSwipeRight() {
                float x = onSwipeTouchListener.getDiffX();
                System.out.println("VALUE OF RIGHT SWIPE" + x);
            }
            public void onSwipeLeft() {
            }
            public void onSwipeBottom() {
                Toast.makeText(HomeScreen.this, "bottom", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HomeScreen.this, "GAME OVER", Toast.LENGTH_LONG).show();
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

        coordinates = new Coordinates();
        playerController = new PlayerController();
        changeScoreBar(score);

        healthBar = (ProgressBar)findViewById(R.id.playerHealth);
        healthBar.setMax(3);
        healthBar.setProgress(playerController.getHealth());

        playerName = (TextView)findViewById(R.id.txtName);
        playerName.setText(playerController.getName());

        playerDrawing();
        timer();
    }

    public void createEnemy(){
        enemyMovement1 = 0;
        enemyMovement2 = 855;
        enemyMovement3 = 1340;
        direction = coordinates.createCoordinates();
        x = coordinates.getxPos();
        y = coordinates.getyPos();

        enemy = new ImageView(this);
        enemy.setImageResource(R.mipmap.ic_launcher);
        enemy.setX(x);
        enemy.setY(y);
        linearLayout.addView(enemy);
    }

    public void getDirection(int direction){
        switch(direction){
            case 0:
                //System.out.println("0");
                enemyMovement1 += 1;
                enemy.setX(enemyMovement1);
                break;
            case 1:
                //System.out.println("1");
                enemyMovement2 -= 1;
                enemy.setX(enemyMovement2);
                break;
            case 2:
                //System.out.println("2");
                enemyMovement3 -= 1;
                enemy.setY(enemyMovement3);
                break;
        }
    }
}
