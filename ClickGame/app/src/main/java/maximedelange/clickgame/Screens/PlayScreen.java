package maximedelange.clickgame.Screens;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import maximedelange.clickgame.Controller.PlayerController;
import maximedelange.clickgame.Database.Database;
import maximedelange.clickgame.Domain.Coordinates;
import maximedelange.clickgame.R;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class PlayScreen extends AppCompatActivity {

    // Fields
    private int score = 0;
    private int currentScore = 0;
    private int highScore = 0;
    private int xPos = 0;
    private int yPos = 0;
    private int x = 0;
    private int y = 0;
    private String tutorialID = null;
    private int direction;
    private int enemyMovement1, enemyMovement2, enemyMovement3 = 0;
    private int enemyHealthBar1, enemyHealthBar2, enemyHealthBar3 = 0;
    private int enemyHealthShow1, enemyHealthShow2, enemyHealthShow3 = 0;
    private int enemyHealthCounter = 0;
    private int playerHealthBegin = 0;
    private int enemyHealthBegin = 0;
    private boolean isColliding = false;
    private CountDownTimer countDownMovement = null;
    private PlayerController playerController = null;
    private Coordinates coordinates = null;
    private Database database = null;
    private boolean IS_ACTIVATED = false;
    private boolean IS_KILLABLE = false;

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
    private TextView highScoreTxt = null;
    private ImageButton btnStart = null;
    private ImageButton btnUpgradeScreen = null;
    private ImageButton btnStatusScreen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create database
        database = new Database(this, null, null, 1);
        // Initialize gameplay information
        initializeGameInformation();
        createEnemy();
        //showTutorial("22");
        playGame();
    }

    public void playGame(){
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IS_ACTIVATED){
                    setEnemyMovement();
                    damageEnemy();
                    IS_ACTIVATED = true;
                    IS_KILLABLE = true;
                }
                else if(IS_ACTIVATED){
                    countDownMovement.cancel();
                    Toast.makeText(PlayScreen.this, "paused", Toast.LENGTH_SHORT).show();
                    enemy.setOnClickListener(null);
                    IS_ACTIVATED = false;
                }
            }
        });
    }

    public void upgradeScreen(){
        btnUpgradeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void statusScreen(){
        btnStatusScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //btnStart = (ImageView)findViewById(R.id.action_play);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_play) {
            if(!IS_ACTIVATED){
                setEnemyMovement();
                damageEnemy();
                IS_ACTIVATED = true;
                IS_KILLABLE = true;
            }
            else if(IS_ACTIVATED){
                countDownMovement.cancel();
                Toast.makeText(PlayScreen.this, "paused", Toast.LENGTH_SHORT).show();
                enemy.setOnClickListener(null);
                IS_ACTIVATED = false;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
    /*
    Create a player with some specifications
     */
    public void createPlayer(){
        playerHealthTxt = (TextView)findViewById(R.id.txtHealthShow);
        playerHealthBegin = playerController.getHealth();
        playerHealthTxt.setText(String.valueOf(playerHealthBegin + " / " + playerController.getHealth()));
        player = (ImageView)findViewById(R.id.imagePlayer);
        healthBar = (ProgressBar)findViewById(R.id.playerHealth);
        healthBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        healthBar.setMax(playerController.getHealth());
        healthBar.setProgress(playerController.getHealth());

        playerName = (TextView)findViewById(R.id.txtName);
        playerName.setTextSize(16);
        playerName.setTypeface(null, Typeface.BOLD);
        playerName.setText(playerController.getName());
        playerName.setY(100);
    }

    /*
    Updating the current and highscore
     */
    public void changeScoreBar(int score){
        //scoreBar = getSupportActionBar();
        //scoreBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.black)));
        currentScoreTxt.setText("score: " + score);
        highScoreTxt.setText("high score: " + highScore);
        //scoreBar.setTitle("high score: " + highScore);
        if(score > highScore){
            highScoreTxt.setText("high score: " + score);
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

        // Collision detection between player and enemy
        if(Rect.intersects(playerCollision, enemyCollision)){
            // Player has been killed. Specs are being set for the death of the player
            if(healthBar.getProgress() <= 1){
                playerHealthTxt.setText(String.valueOf(playerHealthBegin + " / " + 0));
                healthBar.setProgress(0);
                Toast.makeText(PlayScreen.this, "GAME OVER", Toast.LENGTH_LONG).show();
                countDownMovement.onFinish();
                linearLayout.removeView(enemy);
                linearLayout.removeView(enemyHealth);
                linearLayout.removeView(enemyHealthTxt);
            }else{
                isColliding = true;
                // Setting new player health after attack
                playerController.setHealth(playerController.enemyDoDamage(1));
                healthBar.setProgress(playerController.getHealth());
                playerHealthTxt.setText(String.valueOf(playerHealthBegin + " / " + playerController.getHealth()));

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
        countDownMovement = new CountDownTimer(10000000 * 1000, 10) {
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

        btnStart = (ImageButton)findViewById(R.id.btnPlay);
        btnUpgradeScreen = (ImageButton)findViewById(R.id.btnUpgrade);
        btnStatusScreen = (ImageButton)findViewById(R.id.btnStatus);

        highScoreTxt = (TextView)findViewById(R.id.txtHighScore);
        highScoreTxt.setTextSize(24);
        highScoreTxt.setTypeface(null, Typeface.BOLD);
        currentScoreTxt = (TextView)findViewById(R.id.txtScore);
        currentScoreTxt.setTextSize(24);
        currentScoreTxt.setTypeface(null, Typeface.BOLD);
        currentScoreTxt.setText(String.valueOf(currentScore));
        highScore = Integer.valueOf(database.getHighscore());
        changeScoreBar(highScore);

        coordinates = new Coordinates();
        playerController = new PlayerController("Jack");
        changeScoreBar(score);
        createPlayer();
    }

    public void createEnemy(){
        // Enemy position
        enemyMovement1 = 0;
        enemyMovement2 = 855;
        enemyMovement3 = 1435;
        direction = coordinates.createCoordinates();
        x = coordinates.getxPos();
        y = coordinates.getyPos();

        // Enemy health
        enemyHealthBar1 = 0;
        enemyHealthBar2 = 855;
        enemyHealthBar3 = 1585;
        enemyHealthShow1 = 0;
        enemyHealthShow2 = 855;
        enemyHealthShow3 = 1605;
        enemyHealthCounter += 1;
        enemyHealth = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        enemyHealth.setMax(enemyHealthCounter);
        enemyHealth.setProgress(enemyHealthCounter);
        enemyHealth.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
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
        if(IS_KILLABLE){
            damageEnemy();
        }
    }

    public void damageEnemy(){
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

    public void showTutorial(String tutorialID){
        // Sequence
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        config.setContentTextColor(Color.RED);
        config.setDismissTextColor(Color.WHITE);
        config.setMaskColor(Color.DKGRAY);

        final MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);//,tutorialID);

        sequence.setConfig(config);
        sequence.addSequenceItem(player, "This is Jack", "GOT IT");
        sequence.addSequenceItem(enemy, "Jack needs to defend himself from enemies", "GOT IT");
        sequence.addSequenceItem(currentScoreTxt, "For each kill, gain a new score count", "GOT IT");
        sequence.addSequenceItem(btnStart, "After tutorial, press here to start and pause the game", "GOT IT");
        sequence.addSequenceItem(btnUpgradeScreen, "Here you can purchase upgrades for " + playerController.getName(), "GOT IT");
        sequence.addSequenceItem(btnStatusScreen, "Here you can see the status of " + playerController.getName(), "GOT IT");
        sequence.addSequenceItem(player, "Goodluck to you, Jack!", "FIGHT");


        sequence.start();
    }
}
