package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class SpeedItem extends Items {
    private static final int speedBonus = 1;
    public static final int code = 1;
    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUp(Bomber bomber) {
        bomber.setSpeedBomber(Math.min(bomber.getSpeedBomber() + speedBonus, 5));
    }
}
