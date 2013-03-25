package cdg.swi.game.menu;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cdg.swi.game.util.Utility;
import cdg.swi.game.util.VertexData;
import cdg.swi.game.util.interfaces.IClickListener;
import cdg.swi.game.util.interfaces.ISelectListener;

public abstract class Component {
	
	protected int id;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	private int selectionVAO;
	private int selectionVBO;
	private int selectionIndiciesVBO;
	
	protected int drawVAO;
	protected int drawVBO;
	protected int drawIndiciesVBO;
	
	private IClickListener clickListener = null;
	private ISelectListener selectListener = null;
	
	public Component(float x, float y)
	{
		this.x = x;
		this.y = y;
		
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
		this.setupSelectionGL();
	}
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	public abstract void drawUI();
	
	private void setupSelectionGL()
	{
		this.selectionVAO = GL30.glGenVertexArrays(); //generate id for VAO
		this.selectionVBO = GL15.glGenBuffers(); //generate id for VBO
		
		glBindVertexArray(this.selectionVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.selectionVBO); //bind our VBO
		
		//create our selection frame
		VertexData[] points = new VertexData[]{new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, Utility.idToGlColor(this.id, false)),
											   new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, Utility.idToGlColor(this.id, false)),
											   new VertexData(new float[]{this.x+this.width,this.y-this.height,0.0f,1.0f}, Utility.idToGlColor(this.id, false)),
											   new VertexData(new float[]{this.x+this.width,this.y,0.0f,1.0f}, Utility.idToGlColor(this.id, false))};
		
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
		
		this.selectionIndiciesVBO = GL15.glGenBuffers(); //generate buffer id for indicies
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.selectionIndiciesVBO); //bind indicies buffer
		
		//upload indicies
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind buffer
		
		GL30.glBindVertexArray(0); //unbind VAO
	}
	
	public void drawSelection()
	{
		GL30.glBindVertexArray(this.selectionVAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		// Bind to the index VBO that has all the information about the order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.selectionIndiciesVBO);
		
		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
		
		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	public IClickListener getClickListener()
	{
		return this.clickListener;
	}
	
	public void setClickListener(IClickListener listener)
	{
		this.clickListener = listener;
	}
	
	public ISelectListener getSelectListener()
	{
		return this.selectListener;
	}
	
	public void setSelectListener(ISelectListener listener)
	{
		this.selectListener = listener;
	}

}
