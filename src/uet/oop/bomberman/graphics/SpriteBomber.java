package uet.oop.bomberman.graphics;

import javafx.scene.effect.ImageInput;
import javafx.scene.image.*;


/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class SpriteBomber {
	
	public static final int DEFAULT_SIZE = 64;
	public static final int SCALED_SIZE = DEFAULT_SIZE / 2;
    private static final int TRANSPARENT_COLOR = 0xffff00ff;
	public final int SIZE;
	private int _x, _y;
	public int[] _pixels;
	protected int _realWidth;
	protected int _realHeight;
	private SpriteSheet _sheet;
    public static SpriteBomber player_up[] = {
			new SpriteBomber(DEFAULT_SIZE, 0, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 3, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 6, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 9, 1, SpriteSheet.tilesBomber, 64, 64)
	};
    public static SpriteBomber player_down[] = {
			new SpriteBomber(DEFAULT_SIZE, 0, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 3, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 6, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 9, 0, SpriteSheet.tilesBomber, 64, 64)
	};
    public static SpriteBomber player_left[] = {
		new SpriteBomber(DEFAULT_SIZE, 0, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 3, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 6, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 9, 2, SpriteSheet.tilesBomber, 64, 64)
	};
    public static SpriteBomber player_right[] = {
		new SpriteBomber(DEFAULT_SIZE, 0, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 3, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 6, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 9, 3, SpriteSheet.tilesBomber, 64, 64)
	};

    public static SpriteBomber player_up_1[] = {
		new SpriteBomber(DEFAULT_SIZE, 1, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 4, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 7, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 10, 1, SpriteSheet.tilesBomber, 64, 64)
	};
    public static SpriteBomber player_up_2[] = {
			new SpriteBomber(DEFAULT_SIZE, 2, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 5, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 8, 1, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 11, 1, SpriteSheet.tilesBomber, 64, 64)
	};

	public static SpriteBomber player_down_1[] = {
			new SpriteBomber(DEFAULT_SIZE, 1, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 4, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 7, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 10, 0, SpriteSheet.tilesBomber, 64, 64)
	};
    public static SpriteBomber player_down_2[] = {
			new SpriteBomber(DEFAULT_SIZE, 2, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 5, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 8, 0, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 11, 0, SpriteSheet.tilesBomber, 64, 64)
	};

    public static SpriteBomber player_left_1[] = {
			new SpriteBomber(DEFAULT_SIZE, 1, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 4, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 7, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 10, 2, SpriteSheet.tilesBomber, 64, 64)
	};

    public static SpriteBomber player_left_2[] = {
			new SpriteBomber(DEFAULT_SIZE, 2, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 5, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 8, 2, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 11, 2, SpriteSheet.tilesBomber, 64, 64)
	};

    public static SpriteBomber player_right_1[] = {
			new SpriteBomber(DEFAULT_SIZE, 1, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 4, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 7, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 10, 3, SpriteSheet.tilesBomber, 64, 64)
	};
    public static SpriteBomber player_right_2[] = {
			new SpriteBomber(DEFAULT_SIZE, 2, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 5, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 8, 3, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 11, 3, SpriteSheet.tilesBomber, 64, 64)
	};

    public static SpriteBomber player_dead_1[] = {
			new SpriteBomber(DEFAULT_SIZE, 0, 4, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 3, 4, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 6, 4, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 9, 4, SpriteSheet.tilesBomber, 64, 64)
	};
    public static SpriteBomber player_dead_2[] = {
			new SpriteBomber(DEFAULT_SIZE, 1, 4, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 4, 4, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 5, 4, SpriteSheet.tilesBomber, 64, 64),
			new SpriteBomber(DEFAULT_SIZE, 10, 4, SpriteSheet.tilesBomber, 64, 64)
	};
	/*
	|--------------------------------------------------------------------------
	| Bomber SpriteBombers 0
	|--------------------------------------------------------------------------
	 */
	
	// player_down[0] = new SpriteBomber(DEFAULT_SIZE, 0, 9, SpriteSheet.tilesBomber, 12, 15);
	// player_up[0] = new SpriteBomber(DEFAULT_SIZE, 1, 9, SpriteSheet.tilesBomber, 12, 16);
	// player_left[0] = new SpriteBomber(DEFAULT_SIZE, 2, 9, SpriteSheet.tilesBomber, 10, 15);
	// player_right[0] = new SpriteBomber(DEFAULT_SIZE, 5, 9, SpriteSheet.tilesBomber, 10, 16);

	// player_down[1] = new SpriteBomber(DEFAULT_SIZE, 5, 3, SpriteSheet.tilesBomber, 12, 15);
    // player_up[1] = new SpriteBomber(DEFAULT_SIZE, 6, 3, SpriteSheet.tilesBomber, 12, 16);
	// player_left[1] = new SpriteBomber(DEFAULT_SIZE, 7, 3, SpriteSheet.tilesBomber, 10, 15);
	// player_right[1] = new SpriteBomber(DEFAULT_SIZE, 10, 3, SpriteSheet.tilesBomber, 10, 16);

    // player_up[2] = new SpriteBomber(DEFAULT_SIZE, 6, 6, SpriteSheet.tilesBomber, 12, 16);
	// player_down[2] = new SpriteBomber(DEFAULT_SIZE, 5, 6, SpriteSheet.tilesBomber, 12, 15);
	// player_left[2] = new SpriteBomber(DEFAULT_SIZE, 7, 6, SpriteSheet.tilesBomber, 10, 15);
	// player_right[2] = new SpriteBomber(DEFAULT_SIZE, 10, 6, SpriteSheet.tilesBomber, 10, 16);

    // player_up[3] = new SpriteBomber(DEFAULT_SIZE, 6, 9, SpriteSheet.tilesBomber, 12, 16);
	// player_down[3] = new SpriteBomber(DEFAULT_SIZE, 5, 9, SpriteSheet.tilesBomber, 12, 15);
	// player_left[3] = new SpriteBomber(DEFAULT_SIZE, 7, 9, SpriteSheet.tilesBomber, 10, 15);
	// player_right[3] = new SpriteBomber(DEFAULT_SIZE, 10, 9, SpriteSheet.tilesBomber, 10, 16);

    // /*
	// |--------------------------------------------------------------------------
	// | Bomber Sprites 1
	// |--------------------------------------------------------------------------
	//  */
	// player_down_1[0] = new SpriteBomber(DEFAULT_SIZE, 0, 10, SpriteSheet.tilesBomber, 12, 15);
	// player_up_1[0] = new SpriteBomber(DEFAULT_SIZE, 1, 10, SpriteSheet.tilesBomber, 12, 16);
	// player_left_1[0] = new SpriteBomber(DEFAULT_SIZE, 2, 10, SpriteSheet.tilesBomber, 10, 15);
	// player_right_1[0] = new SpriteBomber(DEFAULT_SIZE, 5, 10, SpriteSheet.tilesBomber, 10, 16);

	// player_down_1[1] = new SpriteBomber(DEFAULT_SIZE, 5, 4, SpriteSheet.tilesBomber, 12, 15);
    // player_up_1[1] = new SpriteBomber(DEFAULT_SIZE, 6, 4, SpriteSheet.tilesBomber, 12, 16);
	// player_left_1[1] = new SpriteBomber(DEFAULT_SIZE, 7, 4, SpriteSheet.tilesBomber, 10, 15);
	// player_right_1[1] = new SpriteBomber(DEFAULT_SIZE, 10, 4, SpriteSheet.tilesBomber, 10, 16);

	// player_down_1[2] = new SpriteBomber(DEFAULT_SIZE, 5, 7, SpriteSheet.tilesBomber, 12, 15);
    // player_up_1[2] = new SpriteBomber(DEFAULT_SIZE, 6, 7, SpriteSheet.tilesBomber, 12, 16);
	// player_left_1[2] = new SpriteBomber(DEFAULT_SIZE, 7, 7, SpriteSheet.tilesBomber, 10, 15);
	// player_right_1[2] = new SpriteBomber(DEFAULT_SIZE, 10, 7, SpriteSheet.tilesBomber, 10, 16);

	// player_down_1[3] = new SpriteBomber(DEFAULT_SIZE, 5, 10, SpriteSheet.tilesBomber, 12, 15);
    // player_up_1[3] = new SpriteBomber(DEFAULT_SIZE, 6, 10, SpriteSheet.tilesBomber, 12, 16);
	// player_left_1[3] = new SpriteBomber(DEFAULT_SIZE, 7, 10, SpriteSheet.tilesBomber, 10, 15);
	// player_right_1[3] = new SpriteBomber(DEFAULT_SIZE, 10, 10, SpriteSheet.tilesBomber, 10, 16);

    // /*
	// |--------------------------------------------------------------------------
	// | Bomber Sprites 2
	// |--------------------------------------------------------------------------
	//  */
	// player_down_2[0] = new SpriteBomber(SCALED_SIZE, 0, 11, SpriteSheet.tilesBomber, 12, 15);
	// player_up_2[0] = new SpriteBomber(SCALED_SIZE, 1, 11, SpriteSheet.tilesBomber, 12, 16);
	// player_left_2[0] = new SpriteBomber(SCALED_SIZE, 2, 11, SpriteSheet.tilesBomber, 10, 15);
	// player_right_2[0] = new SpriteBomber(SCALED_SIZE, 5, 11, SpriteSheet.tilesBomber, 10, 16);

	// player_down_2[1] = new SpriteBomber(SCALED_SIZE, 5, 5, SpriteSheet.tilesBomber, 12, 15);
    // player_up_2[1] = new SpriteBomber(SCALED_SIZE, 6, 5, SpriteSheet.tilesBomber, 12, 16);
	// player_left_2[1] = new SpriteBomber(SCALED_SIZE, 7, 5, SpriteSheet.tilesBomber, 10, 15);
	// player_right_2[1] = new SpriteBomber(SCALED_SIZE, 10, 5, SpriteSheet.tilesBomber, 10, 16);

	// player_down_2[2] = new SpriteBomber(SCALED_SIZE, 5, 8, SpriteSheet.tilesBomber, 12, 15);
    // player_up_2[2] = new SpriteBomber(SCALED_SIZE, 6, 8, SpriteSheet.tilesBomber, 12, 16);
	// player_left_2[2] = new SpriteBomber(SCALED_SIZE, 7, 8, SpriteSheet.tilesBomber, 10, 15);
	// player_right_2[2] = new SpriteBomber(SCALED_SIZE, 10, 8, SpriteSheet.tilesBomber, 10, 16);

	// player_down_2[3] = new SpriteBomber(SCALED_SIZE, 5, 11, SpriteSheet.tilesBomber, 12, 15);
    // player_up_2[3] = new SpriteBomber(SCALED_SIZE, 6, 11, SpriteSheet.tilesBomber, 12, 16);
	// player_left_2[3] = new SpriteBomber(SCALED_SIZE, 7, 11, SpriteSheet.tilesBomber, 10, 15);
	// player_right_2[3] = new SpriteBomber(SCALED_SIZE, 10, 11, SpriteSheet.tilesBomber, 10, 16);


    // /*
	// |--------------------------------------------------------------------------
	// | Bomber dead
	// |--------------------------------------------------------------------------
	//  */
	// player_dead_1[0] = new SpriteBomber(SCALED_SIZE, 3, 11, SpriteSheet.tilesBomber, 14, 16);
    // player_dead_2[0] = new SpriteBomber(SCALED_SIZE, 4, 9, SpriteSheet.tilesBomber, 13, 15);
    // player_dead_3[0] = new SpriteBomber(SCALED_SIZE, 4, 10, SpriteSheet.tilesBomber, 13, 15);

    // player_dead_1[1] = new SpriteBomber(SCALED_SIZE, 8, 5, SpriteSheet.tilesBomber, 14, 16);
    // player_dead_2[1] = new SpriteBomber(SCALED_SIZE, 9, 3, SpriteSheet.tilesBomber, 13, 15);
    // player_dead_3[1] = new SpriteBomber(SCALED_SIZE, 9, 4, SpriteSheet.tilesBomber, 13, 15);

    // player_dead_1[2] = new SpriteBomber(SCALED_SIZE, 8, 8, SpriteSheet.tilesBomber, 14, 16);
    // player_dead_2[2] = new SpriteBomber(SCALED_SIZE, 9, 6, SpriteSheet.tilesBomber, 13, 15);
    // player_dead_3[2] = new SpriteBomber(SCALED_SIZE, 9, 7, SpriteSheet.tilesBomber, 13, 15);

    // player_dead_1[3] = new SpriteBomber(SCALED_SIZE, 8, 11, SpriteSheet.tilesBomber, 14, 16);
    // player_dead_2[3] = new SpriteBomber(SCALED_SIZE, 9, 9, SpriteSheet.tilesBomber, 13, 15);
    // player_dead_3[3] = new SpriteBomber(SCALED_SIZE, 9, 10, SpriteSheet.tilesBomber, 13, 15);
	
	public SpriteBomber(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		_x = x * SIZE;
		_y = y * SIZE;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
	}
	
	public SpriteBomber(int size, int color) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				_pixels[x + y * SIZE] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.SIZE];
			}
		}
	}
	
	public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;
		
		if(calc < diff) {
			return normal;
		}
			
		if(calc < diff * 2) {
			return x1;
		}
			
		return x2;
	}
	
	public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
		int diff = time / 2;
		return (animate % time > diff) ? x1 : x2; 
	}
	
	public int getSize() {
		return SIZE;
	}

	public int getPixel(int i) {
		return _pixels[i];
	}

	public Image getFxImageOrigin() {
		WritableImage wr = new WritableImage(SIZE, SIZE);
		PixelWriter pw = wr.getPixelWriter();
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (_pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
					pw.setArgb(x, y, 0);
				} else {
					pw.setArgb(x, y, _pixels[x + y * SIZE]);
				}
			}
		}
		Image input = new ImageView(wr).getImage();
		return input;
	}

	public Image getFxImage() {
        WritableImage wr = new WritableImage(SIZE, SIZE);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if ( _pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, _pixels[x + y * SIZE]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return resample(input, SCALED_SIZE / DEFAULT_SIZE);
    }

	private Image resample(Image input, int scaleFactor) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(
				W * S,
				H * S
		);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}

		return output;
	}
}
