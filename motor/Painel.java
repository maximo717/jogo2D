package jogo2d.motor;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

public class Painel implements Runnable{
	
	final int originalTileSize = 16;	// 16x16 tile
	final int scale = 3;
	
	final int tileSize = originalTileSize * scale;	// 48x48 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	
	final int screenWidth = tileSize * maxScreenCol;	// 768 pixels
	final int screenHeight = tileSize * maxScreenRow;	// 576 pixels
	
	Thread gameThread;
	
	public Painel() {
		if(glfwInit() != true) {
			System.err.println("Falha ao iniciar GLFW");
			System.exit(1);
		}
		
		long janela = glfwCreateWindow(screenWidth, screenHeight, "Jogo de Aventura 2D", 0, 0);
		
		glfwShowWindow(janela);
		
		glfwMakeContextCurrent(janela);
		
		GL.createCapabilities();
		
		while(glfwWindowShouldClose(janela) != true) {
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
//			glBegin(GL_QUADS);
//				glColor4f(1, 0, 0, 0);
//				glVertex2f(-0.5f, 0.5f);
//				
//				glColor4f(0, 1, 0, 0);
//				glVertex2f(0.5f, 0.5f);
//				
//				glColor4f(0, 0, 1, 0);
//				glVertex2f(0.5f, -0.5f);
//				
//				glColor4f(1, 1, 1, 0);
//				glVertex2f(-0.5f, -0.5f);
//			glEnd();
			
			glfwSwapBuffers(janela);
		}
		
		glfwTerminate();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.run();
	}

	@Override
	public void run() {
		while(gameThread != null) {
			System.out.println("O jogo esta rodando!");
		}
	}
	
}
