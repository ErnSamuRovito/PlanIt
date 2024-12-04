package model;

import javax.swing.*;

public class HappyState extends State {
    @Override
    public ImageIcon getGifImage() {
        return gifImage;
    }

    @Override
    public void setGifImage(ImageIcon image) {
        this.gifImage = image;
    }
}
