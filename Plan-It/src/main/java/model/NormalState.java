package model;

import javax.swing.*;

public class NormalState extends State {
    @Override
    public ImageIcon getGifImage() {
        return gifImage;
    }

    @Override
    public void setGifImage(ImageIcon image) {
        this.gifImage = image;
    }
}
