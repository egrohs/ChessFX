package javachessgui2.gui;

import javafx.scene.paint.Color;

class BoardStyle implements java.io.Serializable {

	public void copy(BoardStyle what) {

		for (int i = 0; i < NUM_COLORS; i++) {
			colors[i] = what.colors[i];
		}

		for (int i = 0; i < NUM_CHECKS; i++) {
			checks[i] = what.checks[i];
		}

		for (int i = 0; i < NUM_SLIDERS; i++) {
			sliders[i] = what.sliders[i];
		}

	}

	public BoardStyle clone() {
		BoardStyle clone = new BoardStyle();

		clone.copy(this);

		return clone;
	}

	public String font = "MERIFONTNEW.TTF";

	final static String[] color_names = { "Board color", "White piece color", "Black piece color", "Light square color",
			"Dark square color", "White contour", "Black contour" };

	final static int BOARD_COLOR = 0;
	final static int WHITE_PIECE_COLOR = 1;
	final static int BLACK_PIECE_COLOR = 2;
	final static int LIGHT_SQUARE_COLOR = 3;
	final static int DARK_SQUARE_COLOR = 4;
	final static int WHITE_CONTOUR_COLOR = 5;
	final static int BLACK_CONTOUR_COLOR = 6;

	final static int NUM_COLORS = 7;

	public String colors[] = { "#FFFFFF", "#000000", "#000000", "#FFFFFF", "#FFFFFF", "#7F7F7F", "#7F7F7F" };

	final static int NUM_SLIDERS = 2;

	public static int slider_mins[] = { 0, 0 };
	public static int slider_maxs[] = { 10, 50 };

	public static String slider_labels[] = { "Padding", "Inner padding" };

	public Color colors(int i) {
		return Color.web(colors[i]);
	}

	final static String[] check_names = { "Font only", "Do fill", "Do stroke" };

	final static int FONT_ONLY = 0;
	final static int DO_FILL = 1;
	final static int DO_STROKE = 2;

	final static int NUM_CHECKS = 3;

	public Boolean[] checks = { true, true, true };

	final static int PADDING_PERCENT = 0;
	final static int INNER_PADDING_PERCENT = 1;

	public int[] sliders = { 8, 0 };

	public BoardStyle() {

	}

}