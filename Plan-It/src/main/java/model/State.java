package model;

import javax.swing.*;

public abstract class State {
    String name;
    ImageIcon gifImage;

    public abstract ImageIcon getGifImage();
    public abstract void setGifImage(ImageIcon image);
}
