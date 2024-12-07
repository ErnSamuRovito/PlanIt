package model.Plant;

import javax.swing.*;

public class SadState extends State {
    @Override
    public ImageIcon getGifImage() {
        return gifImage;
    }

    @Override
    public void setGifImage(ImageIcon image) {
        this.gifImage = image;
    }
}
