package jogo2d.motor;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	// The window handle
	private static Window window = null;
	private long glfwWindow;
	private int width, height;
	private String title;
	
	private float r, g, b, a;
	private boolean fadeToBlack = false;
	
	private Window() {
		this.width = 300;
		this.height = 300;
		this.title = "Hello World!";
		r = 1;
		g = 1;
		b = 1;
		a = 1;
	}
	
	public static Window get() {
		if(Window.window == null) {
			Window.window = new Window();
		}
		return Window.window;
	}

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		if (glfwWindow == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		glfwSetCursorPosCallback(glfwWindow, LeitorMouse::mousePosCallback);
		glfwSetMouseButtonCallback(glfwWindow, LeitorMouse::mouseButtonCallback);
		glfwSetScrollCallback(glfwWindow, LeitorMouse::mouseScrollCallback);
		glfwSetKeyCallback(glfwWindow, LeitorTeclado::keyCallback);

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
//		glfwSetKeyCallback(glfwWindow, (window, key, scancode, action, mods) -> {
//			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
//				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
//		});

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(glfwWindow, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
					glfwWindow,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(glfwWindow);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(glfwWindow);
	}

	public void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!glfwWindowShouldClose(glfwWindow)) {
			glClearColor(r, g, b, a);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			if(fadeToBlack) {
				r = Math.max(r - 0.01f, 0);
				g = Math.max(g - 0.01f, 0);
				b = Math.max(b - 0.01f, 0);
			}
			
			if(LeitorTeclado.isKeyPressed(GLFW_KEY_SPACE)) {
				fadeToBlack = true;
			}
			
			glfwSwapBuffers(glfwWindow); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

//	public static void main(String[] args) {
//		new Painel2().run();
//	}

}