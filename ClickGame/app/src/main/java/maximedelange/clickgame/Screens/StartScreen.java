package maximedelange.clickgame.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import maximedelange.clickgame.Database.Database;
import maximedelange.clickgame.R;

public class StartScreen extends AppCompatActivity {

    // Fields
    private Button btnPlay;
    private Button btnCredits;
    private int score = 0;
    private Database database;
    private TextView textScore;
    private TextView textScoreShow;
    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = new Database(this, null, null, 1);
        initializeStartScreen();
        getCredits();
        setupForPlay();
    }

    public void initializeStartScreen(){
        appName = (TextView)findViewById(R.id.txtAppName);
        appName.setText("Tapper");
        textScoreShow = (TextView)findViewById(R.id.txtHighscoreShow);
        textScoreShow.setText("current highscore");
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setText("PLAY");
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlayScreen.class);
                intent.putExtra("score", score);
                startActivity(intent);
            }
        });
    }

    public void getCredits(){
        btnCredits = (Button)findViewById(R.id.btnCredits);
        btnCredits.setText("CREDITS");
        btnCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StartScreen.this, "this is not working yet.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupForPlay(){
        textScore = (TextView)findViewById(R.id.txtHighscore);
        score = Integer.valueOf(database.getHighscore());
        textScore.setText(String.valueOf(score));
    }

}
