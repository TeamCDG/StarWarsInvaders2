package cdg.swi.game.menu;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cdg.swi.game.util.StaticManager;
import cdg.swi.game.util.Utility;
import cdg.swi.game.util.VertexData;
import cdg.swi.game.util.interfaces.ISelectListener;

public class Button extends Component {

	private VertexData[] points = new VertexData[]{new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, 
														new float[]{0.51484375f,0.74921875f,1.0f,1.0f}),
												   new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, 
														new float[]{0.51484375f,0.74921875f,1.0f,1.0f}),
												   new VertexData(new float[]{this.x+this.width,this.y-this.height,0.0f,1.0f}, 
														new float[]{0.17578125f,0.00390625f,0.984375f,1.0f}),
												   new VertexData(new float[]{this.x+this.width,this.y,0.0f,1.0f}, 
														new float[]{0.17578125f,0.00390625f,0.984375f,1.0f})};
	public Button(float x, float y) {
		super(x, y);
		this.width = 0.5f;
		this.height = 0.15f;		
		this.setTextOffset(0.15f, 0.06f);
		this.setText("Button");
		setupGL();
		
		this.addSelectListener(new ISelectListener()
		{

			@Override
			public void selected(int sx, int sy) {
				onSelected(sx,sy);
			}

			@Override
			public void unselected() {
				onUnselected();
			}
		});
	}
	
	public void setAlpha(float alpha)
	{
		
	}
	
	public void onSelected(int xy, int sy)
	{
		points = new VertexData[]{new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, 
													new float[]{1.0f,0.74921875f,0.51484375f,1.0f}),
											   new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, 
													new float[]{1.0f,0.74921875f,0.51484375f,1.0f}),
											   new VertexData(new float[]{this.x+this.width,this.y-this.height,0.0f,1.0f}, 
													new float[]{0.984375f,0.00390625f,0.17578125f,1.0f}),
											   new VertexData(new float[]{this.x+this.width,this.y,0.0f,1.0f}, 
													new float[]{0.984375f,0.00390625f,0.17578125f,1.0f})};
		
		glBindVertexArray(this.drawVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.drawVBO); //bind our VBO
		
		//buffer to store data
		FloatBuffer f = BufferUtils.createFloatBuffer(points.length * VertexData.ELEMENT_COUNT);
		for (int i = 0; i < points.length; i++) 
		{
			//add position, color and texture floats to the buffer
			f.put(points[i].getElements());
		}
		f.flip();
		
		//upload our data
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, f, GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //unbind buffer
		
		GL30.glBindVertexArray(0); //unbind VAO
		
		this.setTextColor(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public void onUnselected()
	{
		points = new VertexData[]{new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, 
													new float[]{0.51484375f,0.74921875f,1.0f,1.0f}),
											   new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, 
													new float[]{0.51484375f,0.74921875f,1.0f,1.0f}),
											   new VertexData(new float[]{this.x+this.width,this.y-this.height,0.0f,1.0f}, 
													new float[]{0.17578125f,0.00390625f,0.984375f,1.0f}),
											   new VertexData(new float[]{this.x+this.width,this.y,0.0f,1.0f}, 
													new float[]{0.17578125f,0.00390625f,0.984375f,1.0f})};
		
		glBindVertexArray(this.drawVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.drawVBO); //bind our VBO
		
		//buffer to store data
		FloatBuffer f = BufferUtils.createFloatBuffer(points.length * VertexData.ELEMENT_COUNT);
		for (int i = 0; i < points.length; i++) 
		{
			//add position, color and texture floats to the buffer
			f.put(points[i].getElements());
		}
		f.flip();
		
		//upload our data
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, f, GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //unbind buffer
		
		GL30.glBindVertexArray(0); //unbind VAO
		
		this.setTextColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	private void setupGL()
	{
		this.drawVAO= GL30.glGenVertexArrays(); //generate id for VAO
		this.drawVBO = GL15.glGenBuffers(); //generate id for VBO
		
		glBindVertexArray(this.drawVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.drawVBO); //bind our VBO
		
		
		points = new VertexData[]{new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, 
				new float[]{0.51484375f,0.74921875f,1.0f,1.0f}),
		   new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, 
				new float[]{0.51484375f,0.74921875f,1.0f,1.0f}),
		   new VertexData(new float[]{this.x+this.width,this.y-this.height,0.0f,1.0f}, 
				new float[]{0.17578125f,0.00390625f,0.984375f,1.0f}),
		   new VertexData(new float[]{this.x+this.width,this.y,0.0f,1.0f}, 
				new float[]{0.17578125f,0.00390625f,0.984375f,1.0f})};
		
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
		
		//buffer for storing indicies
		ByteBuffer indiciesBuffer = BufferUtils.createByteBuffer(6);
		indiciesBuffer.put(new byte[]{0, 1, 2, 2, 3, 0}); //put in indicies
		indiciesBuffer.flip();
		
		this.drawIndiciesVBO = GL15.glGenBuffers(); //generate buffer id for indicies
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO); //bind indicies buffer
		
		//upload indicies
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind buffer
		
		GL30.glBindVertexArray(0); //unbind VAO
	}

	@Override
	public void drawUI() 
	{
		GL20.glUseProgram(StaticManager.SELECTION_SHADER_PROGRAM_ID);
		GL30.glBindVertexArray(this.drawVAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		// Bind to the index VBO that has all the information about the order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO);
		
		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
		
		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
		
		this.drawGLText();

	}

}
