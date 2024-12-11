package model.Plant;

import core.GlobalResources;
import javax.swing.*;

public class SadState implements State {
    @Override
    public void becomeHappy() {
        System.out.println("Sad can't becomes Happy");
    }

    @Override
    public void becomeSad() {
        System.out.println("Sad can't becomes Sad");
    }

    @Override
    public void becomeNormal() {
        AvatarPlant.getInstance().getState().becomeNormal();
    }

    @Override
    public String getPathGifImage() {
        return GlobalResources.plantSadState;
    }
}
