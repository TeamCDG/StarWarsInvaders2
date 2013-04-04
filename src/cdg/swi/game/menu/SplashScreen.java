package cdg.swi.game.menu;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cdg.swi.game.util.FontFinals;
import cdg.swi.game.util.Matrix4x4;
import cdg.swi.game.util.StaticManager;
import cdg.swi.game.util.Utility;
import cdg.swi.game.util.Vertex2;
import cdg.swi.game.util.VertexData;
import cdg.swi.game.util.interfaces.IGameControl;

public class SplashScreen {

	private IGameControl c;
	private int drawVAO = -1;
	private int drawVBO = -1;
	private int drawIndiciesVBO = -1;
	private float time = 1000;
	
	public SplashScreen(IGameControl c) 
	{
		this.c = c;
		this.setupGL();
		Utility.printFloatArray(StaticManager.WINDOW_MATRIX.toArray());
	}
	
	private void setupGL()
	{
		if(this.drawVAO == -1)
			this.drawVAO = GL30.glGenVertexArrays(); //generate id for VAO
		if(this.drawVBO == -1)
			this.drawVBO = GL15.glGenBuffers(); //generate id for VBO
			
			
		VertexData[] points = new VertexData[4];
		
		ByteBuffer indiciesBuffer = BufferUtils.createByteBuffer(6);
		points[0] = new VertexData(new float[]{-1.0f, 1.0f, 0.0f, 1.0f}, 
				 new float[]{1.0f, 1.0f, 1.0f, 1.0f},
				 new float[]{0.0f, 0.0f});

		points[1] = new VertexData(new float[]{-1.0f, -1.0f, 0.0f, 1.0f}, 
				 new float[]{1.0f, 1.0f, 1.0f, 1.0f},
				 new float[]{0.0f, 1.0f});

		points[2] = new VertexData(new float[]{1.0f, -1.0f, 0.0f, 1.0f}, 
				 new float[]{1.0f, 1.0f, 1.0f, 1.0f},
				 new float[]{1.0f, 1.0f});

		points[3] = new VertexData(new float[]{1.0f, 1.0f, 0.0f, 1.0f}, 
				 new float[]{1.0f, 1.0f, 1.0f, 1.0f},
				 new float[]{1.0f, 0.0f});
			
		glBindVertexArray(this.drawVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.drawVBO); //bind our VBO		
		
		indiciesBuffer.put(new byte[]{(byte) 0,	(byte) 1, (byte) 2, 
				(byte) 2, (byte) 3, (byte) 0});	
		indiciesBuffer.flip();
			
		//buffer to store data
		FloatBuffer f = BufferUtils.createFloatBuffer(points.length * VertexData.ELEMENT_COUNT);
		for (int i = 0; i < points.length; i++) 
		{
			//add position, color and texture floats to the buffer
			f.put(points[i].getElements());
		}
		f.flip();
			
		//upload our data
		GL15.glBufferData(GL_ARRAY_BUFFER, f, GL_STATIC_DRAW);
			
		// Put the position coordinates in attribute list 0
		GL20.glVertexAttribPointer(0, VertexData.POSITION_ELEMENT_COUNT, GL11.GL_FLOAT, 
				false, VertexData.STRIDE, VertexData.POSITION_BYTE_OFFSET);
			
		// Put the color components in attribute list 1
		GL20.glVertexAttribPointer(1, VertexData.COLOR_ELEMENT_COUNT, GL11.GL_FLOAT, 
				false, VertexData.STRIDE, VertexData.COLOR_BYTE_OFFSET);
			
		// Put the texture coordinates in attribute list 2
		GL20.glVertexAttribPointer(2, VertexData.TEXTURE_ELEMENT_COUNT, GL11.GL_FLOAT, 
				false, VertexData.STRIDE, VertexData.TEXTURE_BYTE_OFFSET);
				
		//unbind buffer
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
		//buffer for storing indices
			
		if(this.drawIndiciesVBO  == -1)
			this.drawIndiciesVBO = GL15.glGenBuffers(); //generate buffer id for indices
			
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO); //bind indices buffer
			
		//upload indices
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL15.GL_STATIC_DRAW);
			
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind buffer		
		GL30.glBindVertexArray(0); //unbind VAO
			
	}
	
	public void draw()
	{
		double delta = this.c.getDelta();
		GL11.glClearColor(1.0f, 
		  		  1.0f,
		  		  1.0f, 
		  		  1.0f);
		if(this.drawVAO != -1)
		{
			time -= 1000.0/5000.0*delta;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
			//GL20.glUseProgram(StaticManager.CREDITS_PROGRAM_ID);
			GL20.glUseProgram(StaticManager.MENU_SHADER_PROGRAM_ID);
			GL30.glBindVertexArray(this.drawVAO);
				
			FloatBuffer mat = BufferUtils.createFloatBuffer(16);
			mat.put(StaticManager.WINDOW_MATRIX.toArray());
			mat.flip();
				
			GL20.glUniform1i(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "texture_font"), 0);
			GL20.glUniformMatrix4(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "windowMatrix"), false, mat);		
			GL20.glUniform1i(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "state"), 1);
			GL20.glUniform1f(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "time"), time);
				
				
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			
			// Bind to the index VBO that has all the information about the order of the vertices
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO);
				
				
			// Draw the vertices
			GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
				
			if(time <= 0)
			{
				c.showMainMenu();
			}
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
			GL20.glUseProgram(0);
				
		}
			
		
		
	}

}
