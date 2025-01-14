package model.plantStates;

import core.GlobalResources;

public class NormalState implements State {
    @Override
    public void becomeHappy() {
        AvatarPlant.getInstance().setState(AvatarPlant.getInstance().getHappyState());
    }

    @Override
    public void becomeSad() {
        AvatarPlant.getInstance().setState(AvatarPlant.getInstance().getSadState());
    }

    @Override
    public void becomeNormal() {
        System.out.println("la piantina ha già lo stato normal");
    }

    @Override
    public String getPathGifImage() {
        return GlobalResources.plantNormalState;
    }
}
