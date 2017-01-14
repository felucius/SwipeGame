package maximedelange.clickgame.Domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.widget.ImageView;

import maximedelange.clickgame.R;

/**
 * Created by M on 1/10/2017.
 */

public class Enemy {

    // Fields
    private int xPos;
    private int yPos;
    private int health;
    private int image;
    private ImageView imageView;
    private Rect size;

    // Constructor
    public Enemy(int xPos, int yPos, Bitmap bitmap){
        this.xPos = xPos;
        this.yPos = yPos;
        this.imageView = imageView;
        this.health = 1;
        this.size = new Rect();
        size.set(100, 100, 100, 100);
        this.image = R.mipmap.ic_launcher;
    }

    // Methods
    public void setxPos(int xPos){
        this.xPos = xPos;
    }

    public int getxPos(){
        return this.xPos;
    }

    public void setyPos(int yPos){
        this.yPos = yPos;
    }

    public int getyPos(){
        return this.yPos;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void setImage(int image){
        this.image = image;
    }

    public int getImage(){
        return this.image;
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    public ImageView getImageView(){
        return this.imageView;
    }
}
