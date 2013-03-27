package cdg.swi.game.runnable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import cdg.swi.game.menu.MainMenu;
import cdg.swi.game.util.StaticManager;
import cdg.swi.game.util.Utility;
import cdg.swi.game.util.VertexData;

public class Main {

	private MainMenu m;
	private cdg.swi.game.test.SimpleDrawable d;
	private cdg.swi.game.test.SimpleDrawable d2;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();

	}
	
	public Main()
	{
		this.initWindow();
		this.initGL();
		this.loadMenuSelectionShader();
		this.loadMenuRenderShader();
		
		StaticManager.FONT_TEXTURE_ID = Utility.loadPNGTexture("res//font//font.png", GL13.GL_TEXTURE0);
		//setupQuad();
		m = new MainMenu();
		
		while (!Display.isCloseRequested()) {
			// Do a single loop (logic/render)
			this.process();
			
			// Force a maximum FPS of about 60
			Display.sync(60);
			// Let the CPU synchronize with the GPU if GPU is tagging behind
			Display.update();
		}
	}
	
	private void process()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		m.doSelection(true,true);
		m.draw();
		
		GL20.glUseProgram(StaticManager.SELECTION_SHADER_PROGRAM_ID);
		//d.draw();
		//d2.draw();
		GL20.glUseProgram(0);
		
		this.exitOnGLError("process");
	}
	
	private void initWindow()
	{
		try 
		{
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true);
			
			Display.setDisplayMode(new DisplayMode(StaticManager.WINDOW_WIDTH, StaticManager.WINDOW_HEIGHT));
			Display.setTitle("v0.0.1b - font test");
			Display.create(pixelFormat, contextAtrributes);
			
			
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private void initGL()
	{
		GL11.glViewport(0, 0, StaticManager.WINDOW_WIDTH, StaticManager.WINDOW_HEIGHT);
		GL11.glClearColor(StaticManager.CLEAR_COLOR[0], 
						  StaticManager.CLEAR_COLOR[1],
						  StaticManager.CLEAR_COLOR[2], 
						  StaticManager.CLEAR_COLOR[3]);
	}
	
	private void setupQuad() {
		// We'll define our quad using 4 vertices of the custom 'TexturedVertex' class
		VertexData v0 = new VertexData(); 
		v0.setXYZ(-0.5f, 0.5f, 0); v0.setRGB(1, 0, 1); v0.setST(0.4f, 0.4f);
		VertexData v1 = new VertexData(); 
		v1.setXYZ(-0.5f, -0.5f, 0); v1.setRGB(0, 1, 1); v1.setST(0.4f, 0.5f);
		VertexData v2 = new VertexData(); 
		v2.setXYZ(0.5f, -0.5f, 0); v2.setRGB(1, 1, 0); v2.setST(0.5f, 0.5f);
		VertexData v3 = new VertexData(); 
		v3.setXYZ(0.5f, 0.5f, 0); v3.setRGB(0, 0, 1); v3.setST(0.5f, 0.4f);
		
		d = new cdg.swi.game.test.SimpleDrawable(0, 0, new VertexData[] {v0, v1, v2, v3}, new byte[]{0, 1, 2, 2, 3, 0}, GL11.GL_TRIANGLES);                  
		
		
		VertexData v4 = new VertexData(); 
		v4.setXYZ(0.5f, -0.5f, 0); v4.setRGB(1, 0, 0); v0.setST(0, 0);
		VertexData v5 = new VertexData(); 
		v5.setXYZ(0.5f, -1f, 0); v5.setRGB(0, 1, 0); v1.setST(0, 1);
		VertexData v6 = new VertexData(); 
		v6.setXYZ(1f, -1f, 0); v6.setRGB(0, 0, 1); v2.setST(1, 1);
		VertexData v7 = new VertexData(); 
		v7.setXYZ(1f, -0.5f, 0); v7.setRGB(1, 1, 0); v3.setST(1, 0);
		
		d2 = new cdg.swi.game.test.SimpleDrawable(0, 0, new VertexData[] {v4, v5, v6, v7}, new byte[]{0, 1, 2, 2, 3, 0}, GL11.GL_TRIANGLES);
		
		
		this.exitOnGLError("setupQuad");
	}
	
	private void loadMenuSelectionShader()
	{
		//load vertex shader
		int vsId = Utility.loadShader("res\\shader\\menuSelectionVertex.glsl", GL20.GL_VERTEX_SHADER);
		//load fragment shader
		int fsId = Utility.loadShader("res\\shader\\menuSelectionFragment.glsl", GL20.GL_FRAGMENT_SHADER);
		
		StaticManager.SELECTION_SHADER_PROGRAM_ID = GL20.glCreateProgram();
		GL20.glAttachShader(StaticManager.SELECTION_SHADER_PROGRAM_ID, vsId);
		GL20.glAttachShader(StaticManager.SELECTION_SHADER_PROGRAM_ID, fsId);
		GL20.glLinkProgram(StaticManager.SELECTION_SHADER_PROGRAM_ID);

		// Position information will be attribute 0
		GL20.glBindAttribLocation(StaticManager.SELECTION_SHADER_PROGRAM_ID, 0, "in_Position");
		// Color information will be attribute 1
		GL20.glBindAttribLocation(StaticManager.SELECTION_SHADER_PROGRAM_ID, 1, "in_Color");
		// Textute information will be attribute 2
		GL20.glBindAttribLocation(StaticManager.SELECTION_SHADER_PROGRAM_ID, 2, "in_TextureCoord");
		
		GL20.glValidateProgram(StaticManager.SELECTION_SHADER_PROGRAM_ID);
	}
	
	private void loadMenuRenderShader()
	{
		//load vertex shader
		int vsId = Utility.loadShader("res\\shader\\menuRenderVertex.glsl", GL20.GL_VERTEX_SHADER);
		//load fragment shader
		int fsId = Utility.loadShader("res\\shader\\menuRenderFragment.glsl", GL20.GL_FRAGMENT_SHADER);
		
		StaticManager.MENU_SHADER_PROGRAM_ID = GL20.glCreateProgram();
		GL20.glAttachShader(StaticManager.MENU_SHADER_PROGRAM_ID , vsId);
		GL20.glAttachShader(StaticManager.MENU_SHADER_PROGRAM_ID , fsId);
		GL20.glLinkProgram(StaticManager.MENU_SHADER_PROGRAM_ID );
		

		// Position information will be attribute 0
		GL20.glBindAttribLocation(StaticManager.MENU_SHADER_PROGRAM_ID , 0, "in_Position");
		// Color information will be attribute 1
		GL20.glBindAttribLocation(StaticManager.MENU_SHADER_PROGRAM_ID , 1, "in_Color");
		// Textute information will be attribute 2
		GL20.glBindAttribLocation(StaticManager.MENU_SHADER_PROGRAM_ID , 2, "in_TextureCoord");
		
		GL20.glValidateProgram(StaticManager.MENU_SHADER_PROGRAM_ID );
		
		StaticManager.FONT_TEXTURE_UNIFORM_ID = GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "texture_font");
	}
	
	private void exitOnGLError(String errorMessage) 
	{
		int errorValue = GL11.glGetError();
		
		if (errorValue != GL11.GL_NO_ERROR) {
			String errorString = GLU.gluErrorString(errorValue);
			System.err.println("ERROR - " + errorMessage + ": " + errorString);
			
			if (Display.isCreated()) Display.destroy();
			System.exit(-1);
		}
	}

}
