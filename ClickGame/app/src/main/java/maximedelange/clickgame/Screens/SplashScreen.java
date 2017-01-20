package maximedelange.clickgame.Screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import maximedelange.clickgame.R;

public class SplashScreen extends AppCompatActivity {

    // Fields
    private ImageView splash;
    private Animation animation;
    private Animation fade;
    private TextView loadTxt;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loading();
    }

    public void loading(){
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.black)));
        loadTxt = (TextView)findViewById(R.id.txtLoading);
        loadTxt.setText("loading...");
        loadTxt.setTextSize(24);
        loadTxt.setTypeface(null, Typeface.BOLD);
        splash = (ImageView)findViewById(R.id.imgSplash);
        animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.splash);
        fade = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade);

        splash.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splash.startAnimation(fade);
                finish();
                Intent intent = new Intent(getBaseContext(), StartScreen.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
