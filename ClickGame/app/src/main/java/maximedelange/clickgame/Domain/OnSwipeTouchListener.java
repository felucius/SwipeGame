
package maximedelange.clickgame.Domain;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import maximedelange.clickgame.Screens.HomeScreen;

/**
 * Created by M on 1/10/2017.
 */
/*
public class OnSwipeTouchListener extends AppCompatActivity implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    public float diffX;
    public float diffY;

    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        public static final int SWIPE_THRESHOLD = 100;
        public static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                diffY = e2.getY() - e1.getY();
                diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            setDiffX(diffX);
                            if(diffX > 200){
                                //Toast.makeText(OnSwipeTouchListener.this, "Right Above 200", Toast.LENGTH_SHORT).show();

                            }
                            onSwipeRight();

                            System.out.println(e2.getX());
                        } else {
                            System.out.println(e2.getX());
                            if(diffX > -200){
                                Toast.makeText(OnSwipeTouchListener.this, "Left Above 200", Toast.LENGTH_SHORT).show();
                            }
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                        //System.out.println(e2.getY());
                    } else {
                        onSwipeTop();
                        //System.out.println(e2.getY());
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void setDiffX(float diffX){
        this.diffX = diffX;
    }

    public float getDiffX(){
        return this.diffX;
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}
*/