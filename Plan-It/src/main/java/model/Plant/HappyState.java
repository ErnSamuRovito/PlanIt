package model.Plant;

import core.GlobalResources;

import javax.swing.*;

public class HappyState implements State {
    @Override
    public void becomeHappy() {
        System.out.println("sono già Happy");
    }

    @Override
    public void becomeSad() {
        System.out.println("Happy State not available");
    }

    @Override
    public void becomeNormal() {
        AvatarPlant.getInstance().setState(AvatarPlant.getInstance().getNormalState());
    }

    @Override
    public String getPathGifImage() {
        return GlobalResources.plantHappyState;
    }
}
