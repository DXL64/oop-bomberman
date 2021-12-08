package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class FlameItem extends Items {
    private static final int flameBonus = 1;
    public static final int code = 2;
    public FlameItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUp(Bomber bomber) {
        bomber.setFlame(bomber.getFlame() + flameBonus);
    }
}
