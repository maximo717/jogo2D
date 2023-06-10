package jogo2d.motor;

import static org.lwjgl.glfw.GLFW.*;

public class LeitorTeclado {
	private static LeitorTeclado instance;
	private boolean keyPressed[] = new boolean[350];
	
	private LeitorTeclado() {
		
	}
	
	public static LeitorTeclado get() {
		if(LeitorTeclado.instance == null) {
			LeitorTeclado.instance = new LeitorTeclado();
		}
		
		return LeitorTeclado.instance;
	}
	
	public static void keyCallback(long window, int key, int scancode, int action, int mods) {
		if(action == GLFW_PRESS) {
			get().keyPressed[key] = true;
		}
		else if(action == GLFW_RELEASE) {
			get().keyPressed[key] = false;
		}
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return get().keyPressed[keyCode];	
	}

}
