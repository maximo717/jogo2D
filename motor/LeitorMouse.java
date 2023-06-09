package jogo2d.motor;

public class LeitorMouse {
	private static LeitorMouse instance;
	private double scrollX, scrollY;
	private double xPos, yPos, lastX, lastY;
	private boolean mouseButtonPressed[] = new boolean[3];
	private boolean isDragging;
}
