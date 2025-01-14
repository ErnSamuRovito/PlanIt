package model.plantStates;

public interface State {
//    public abstract String getPathGifImage();
//    public abstract void setPathGifImage(String image);

    void becomeHappy();
    void becomeSad();
    void becomeNormal();
    String getPathGifImage();
}
