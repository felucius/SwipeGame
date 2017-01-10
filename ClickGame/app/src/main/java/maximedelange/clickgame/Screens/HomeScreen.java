package maximedelange.clickgame.Screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import maximedelange.clickgame.Domain.OnSwipeTouchListener;
import maximedelange.clickgame.R;

public class HomeScreen extends AppCompatActivity {

    // Fields
    private int score = 0;
    private int highScore = 0;
    private long time = 0;
    private CountDownTimer countDownTimer;
    private GestureDetector gestureDetector;

    // GUI components
    private ImageView imgPlayer;
    private TextView textTimer;
    private TextView textScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        changeScoreBar(score);
        playerDrawing();
        timer();

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
        imgPlayer = (ImageView)findViewById(R.id.imagePlayer);
        //imgPlayer.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        score ++;
        //        changeScoreBar(score);
        //    }
        //});

        imgPlayer.setOnTouchListener(new OnSwipeTouchListener(HomeScreen.this) {
            public void onSwipeTop() {
                Toast.makeText(HomeScreen.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(HomeScreen.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(HomeScreen.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(HomeScreen.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
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

    public void timer(){
        textTimer = (TextView)findViewById(R.id.txttime);
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished / 1000;
                textTimer.setText("time: " + Long.valueOf(time));
            }

            @Override
            public void onFinish() {
                textTimer.setText("times up!");
                // Stops the availability to click again if the time is up
                imgPlayer.setOnClickListener(null);
            }
        };

        countDownTimer.start();
    }
}
