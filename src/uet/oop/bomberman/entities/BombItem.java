package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class BombItem extends Items {
    private static final int bombBonus = 1;
    public static final int code = 3;
    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUp(Bomber bomber) {
        bomber.setNumberOfBombs(bomber.getNumberOfBombs() + bombBonus);
    }
}
