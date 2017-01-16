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
    private int currentScore = 0;
    private int highScore = 0;
    private int xPos = 0;
    private int yPos = 0;
    private int x = 0;
    private int y = 0;
    private int direction;
    private int enemyMovement1, enemyMovement2, enemyMovement3 = 0;
    private int enemyHealthBar1, enemyHealthBar2, enemyHealthBar3 = 0;
    private int enemyHealthShow1, enemyHealthShow2, enemyHealthShow3 = 0;
    private int enemyHealthCounter = 0;
    private int enemyHealthBegin = 0;
    private boolean isColliding = false;
    private CountDownTimer countDownMovement = null;
    private CountDownTimer countDownTime = null;
    private PlayerController playerController = null;
    private Coordinates coordinates = null;
    private Database database = null;

    // GUI components
    private RelativeLayout linearLayout = null;
    private ImageView enemy = null;
    private ImageView player = null;
    private ProgressBar healthBar = null;
    private ProgressBar enemyHealth = null;
    private TextView playerName = null;
    private TextView enemyHealthTxt = null;
    private TextView playerHealthTxt = null;
    private TextView currentScoreTxt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create database
        database = new Database(this, null, null, 1);
        // Initialize gameplay information
        initializeGameInformation();
        createEnemy();
        setEnemyMovement();
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

    public void createPlayer(){
        playerHealthTxt = (TextView)findViewById(R.id.txtHealthShow);
        playerHealthTxt.setText(String.valueOf(playerController.getHealth() + " / " + playerController.getHealth()));
        player = (ImageView)findViewById(R.id.imagePlayer);
        healthBar = (ProgressBar)findViewById(R.id.playerHealth);
        healthBar.setMax(playerController.getHealth());
        healthBar.setProgress(playerController.getHealth());

        playerName = (TextView)findViewById(R.id.txtName);
        playerName.setTextSize(16);
        playerName.setTypeface(null, Typeface.BOLD);
        playerName.setText(playerController.getName());
    }

    /*
    Updating the current and highscore
     */
    public void changeScoreBar(int score){
        ActionBar scoreBar = getSupportActionBar();
        currentScoreTxt.setText("score: " + score);
        scoreBar.setTitle("high score: " + highScore);
        if(score > highScore){
            scoreBar.setTitle("high score: " + score);
            highScore = score;
        }
    }

    /*
    Checks for enemy with player collision
     */
    public void collisionDetection(){
        Rect playerCollision = new Rect();
        Rect enemyCollision = new Rect();

        player.getHitRect(playerCollision);
        enemy.getHitRect(enemyCollision);

        if(Rect.intersects(playerCollision, enemyCollision)){
            if(healthBar.getProgress() <= 0){
                Toast.makeText(PlayScreen.this, "GAME OVER", Toast.LENGTH_LONG).show();
                countDownMovement.onFinish();
                countDownTime.onFinish();
            }else{
                isColliding = true;

                // Setting new player health after attack
                playerController.setHealth(playerController.enemyDoDamage(1));
                healthBar.setProgress(playerController.getHealth());

                switch (direction){
                    case 0:
                        enemyMovement1 = 0;
                        enemyHealthBar1 = 0;
                        enemyHealthShow1 = 0;
                        enemy.setX(enemyMovement1);
                        enemyHealth.setX(enemyHealthBar1);
                        enemyHealthTxt.setX(enemyHealthShow1);
                        break;
                    case 1:
                        enemyMovement2 = 855;
                        enemyHealthBar2 = 855;
                        enemyHealthShow2 = 855;
                        enemy.setX(enemyMovement2);
                        enemyHealth.setX(enemyHealthBar2);
                        enemyHealthTxt.setX(enemyHealthShow2);
                        break;
                    case 2:
                        enemyMovement3 = 1340;
                        enemyHealthBar3 = 1490;
                        enemyHealthShow3 = 1510;
                        enemy.setY(enemyMovement3);
                        enemyHealth.setY(enemyHealthBar3);
                        enemyHealthTxt.setY(enemyHealthShow3);
                        break;
                }
            }
        }
    }

    /*
    Moves the enemy on the playground
     */
    public void setEnemyMovement(){
        player = (ImageView)findViewById(R.id.imagePlayer);
        countDownMovement = new CountDownTimer(60 * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isColliding){
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

        currentScoreTxt = (TextView)findViewById(R.id.txtScore);
        currentScoreTxt.setTextSize(20);
        currentScoreTxt.setTypeface(null, Typeface.BOLD);
        currentScoreTxt.setText(String.valueOf(currentScore));
        highScore = Integer.valueOf(database.getHighscore());
        changeScoreBar(highScore);

        coordinates = new Coordinates();
        playerController = new PlayerController("Jack");
        changeScoreBar(score);
        createPlayer();
        //timer();
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

                    currentScore ++;
                    linearLayout.removeView(enemy);
                    linearLayout.removeView(enemyHealth);
                    linearLayout.removeView(enemyHealthTxt);
                    enemyHealthCounter = enemyHealthBegin;
                    createEnemy();
                    changeScoreBar(currentScore);
                    if(currentScore >= highScore){
                        playerController.setHighScore(currentScore);
                        database.updateHighscore(playerController.getHighScore());
                    }else{
                        playerController.setScore(currentScore);
                    }
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
