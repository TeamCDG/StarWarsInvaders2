package cdg.swi.game.menu;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

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
import cdg.swi.game.util.Vertex4;
import cdg.swi.game.util.VertexData;
import cdg.swi.game.util.interfaces.IClickListener;
import cdg.swi.game.util.interfaces.ISelectListener;

public abstract class Component {
	
	protected int id;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float alpha = 1.0f;
	
	private String text = "";
	private float textXOffset = 0.0f;
	private float textYOffset = 0.0f;
	protected Matrix4x4 textScalingMatrix = new Matrix4x4(0.5f, 0.0f, 0.0f, 0.0f,
														  0.0f, 0.5f, 0.0f, 0.0f,
														  0.0f, 0.0f, 0.5f, 0.0f,
														  0.0f, 0.0f, 0.0f, 1.0f);
	private float[] textColor = new float[]{1.0f,1.0f,1.0f};
	private VertexData[] textPoints;
	
	private int selectionVAO = -1;
	private int selectionVBO = -1;
	private int selectionIndiciesVBO = -1;
	
	protected int drawVAO = -1;
	protected int drawVBO = -1;
	protected int drawIndiciesVBO = -1;
	
	protected int textVAO = -1;
	protected int textVBO = -1;
	protected int textIndiciesVBO = -1;
	
	private List<IClickListener> clickListener = null;
	private List<ISelectListener> selectListener = null;
	
	public Component(float x, float y)
	{
		this.x = x;
		this.y = y;
		//this.setupSelectionGL();
		this.clickListener = new ArrayList<IClickListener>();
		this.selectListener = new ArrayList<ISelectListener>();
	}
	
	public int getSelectionVAO()
	{
		return this.selectionVAO;
	}
	
	public int getSelectionVBO()
	{
		return this.selectionVBO;
	}
	
	public int getSelectionIndiciesVBO()
	{
		return this.selectionIndiciesVBO;
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
		this.setupSelectionGL();
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
		this.setupSelectionGL();
	}
	
	public float getAlpha()
	{
		return this.alpha;
	}
	
	public abstract void setAlpha(float alpha);
	
	public String getText()
	{
		return this.text;
	}
	
	public void setText(String text)
	{
		this.text = text.toUpperCase();
		setupGLText();
	}
	
	public Vertex2 getTextOffset()
	{
		return new Vertex2(this.textXOffset, this.textYOffset);
	}
	
	public void setTextOffset(float x, float y)
	{
		this.textXOffset = x;
		this.textYOffset = y;
	}
	
	public void setTextOffset(Vertex2 txtOffset)
	{
		this.textXOffset = txtOffset.getX();
		this.textYOffset = txtOffset.getY();
	}
	
	public float[] getTextColor()
	{
		return new float[]{this.textColor[0],this.textColor[1],this.textColor[2],this.alpha};
	}

	public void setTextColor(float r, float g, float b, float a)
	{
		this.textColor = new float[]{r,g,b};
		this.alpha = a;
		this.setupGLText();
	}
	
	public abstract void drawUI();
	
	protected void setupGLText()
	{
		if(this.text != "")
		{
			if(this.textVAO == -1)
				this.textVAO = GL30.glGenVertexArrays(); //generate id for VAO
			if(this.textVBO == -1)
				this.textVBO = GL15.glGenBuffers(); //generate id for VBO
			
			
			textPoints = new VertexData[this.text.length()*4];
			float xoff = 0.0f;
			float yoff = 0.0f;
			float xoffmax = 0.0f;
			IntBuffer indiciesBuffer = BufferUtils.createIntBuffer(this.text.length()*3*2);
			
			glBindVertexArray(this.textVAO); //bind our VAO
			glBindBuffer(GL_ARRAY_BUFFER, this.textVBO); //bind our VBO		
			
			for(int i = 0; i < this.text.length(); i++)
			{
				char c = this.text.charAt(i);
				if(c == '\n')
				{
					xoff = 0.0f;
					yoff = FontFinals.getHeight('A');
				}
				textPoints[i*4+0] = new VertexData(new float[]{xoff,-yoff,0.0f,1.0f}, 
											 new float[]{this.textColor[0],this.textColor[1],this.textColor[2],this.alpha},
											 new float[]{FontFinals.getX(c),FontFinals.getY(c)});
				
				textPoints[i*4+1] = new VertexData(new float[]{xoff,-yoff-FontFinals.getHeight(c),0.0f,1.0f}, 
											 new float[]{this.textColor[0],this.textColor[1],this.textColor[2],this.alpha},
						 					 new float[]{FontFinals.getX(c),FontFinals.getY(c)+FontFinals.getHeight(c)});
				
				textPoints[i*4+2] = new VertexData(new float[]{xoff+FontFinals.getWidth(c),-yoff-FontFinals.getHeight(c),0.0f,1.0f}, 
											 new float[]{this.textColor[0],this.textColor[1],this.textColor[2],this.alpha},
						 					 new float[]{FontFinals.getX(c)+FontFinals.getWidth(c),FontFinals.getY(c)+FontFinals.getHeight(c)});
				
				textPoints[i*4+3] = new VertexData(new float[]{xoff+FontFinals.getWidth(c),-yoff,0.0f,1.0f}, 
											 new float[]{this.textColor[0],this.textColor[1],this.textColor[2],this.alpha},
						 					 new float[]{FontFinals.getX(c)+FontFinals.getWidth(c),FontFinals.getY(c)});	
				xoff += FontFinals.getWidth(c);
				
				if(xoff > xoffmax)
					xoffmax = xoff;
				
				indiciesBuffer.put(new int[]{(int) (0+i*4),
						 (int) (1+i*4), 
						 (int) (2+i*4), 
						 (int) (2+i*4), 
						 (int) (3+i*4), 
						 (int) (0+i*4)}); //put in indicies
				
			}
			indiciesBuffer.flip();
			
			//buffer to store data
			FloatBuffer f = BufferUtils.createFloatBuffer(textPoints.length * VertexData.ELEMENT_COUNT);
			for (int i = 0; i < textPoints.length; i++) 
			{
				//add position, color and texture floats to the buffer
				f.put(textPoints[i].getElements());
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
			
			if(this.textIndiciesVBO == -1)
				this.textIndiciesVBO = GL15.glGenBuffers(); //generate buffer id for indicies
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.textIndiciesVBO); //bind indicies buffer
			
			//upload indicies
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL15.GL_STATIC_DRAW);
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind buffer		
			GL30.glBindVertexArray(0); //unbind VAO
			
			Vertex4 textEdge = (Vertex4) this.textScalingMatrix.multiply(new Vertex2(xoffmax, yoff+FontFinals.getHeight('A')));
			if(textEdge.getY()+this.textYOffset > this.height)
				this.setHeight(textEdge.getY()+this.textYOffset);
			if(textEdge.getX()+this.textXOffset > this.width)
				this.setWidth(textEdge.getX()+this.textXOffset);
		}
		
	}
	
	public void drawGLText() 
	{
		
		if(this.textVAO != -1)
		{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, StaticManager.FONT_TEXTURE_ID);
			
			GL20.glUseProgram(StaticManager.TEXT_SHADER_PROGRAM_ID);
			GL30.glBindVertexArray(this.textVAO);
			
			FloatBuffer mat = BufferUtils.createFloatBuffer(16);
			mat.put(this.textScalingMatrix.toArray());
			mat.flip();
			
			GL20.glUniform1i(StaticManager.FONT_TEXTURE_UNIFORM_ID, 0);
			GL20.glUniformMatrix4(StaticManager.FONT_SCALING_MATRIX_UNIFORM_ID, false, mat);		
			GL20.glUniform2f(StaticManager.TEXT_POSITION_UNIFORM_ID, this.x+this.textXOffset, this.y-this.textYOffset);		
			
			
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			
			// Bind to the index VBO that has all the information about the order of the vertices
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.textIndiciesVBO);
			
			
			// Draw the vertices
			GL11.glDrawElements(GL11.GL_TRIANGLES, this.text.length()*3*2, GL11.GL_UNSIGNED_INT, 0);
			
			// Put everything back to default (deselect)
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
			GL20.glUseProgram(0);
		
		}
		

	}
	
	private void setupSelectionGL()
	{
		if(this.selectionVAO == -1)
			this.selectionVAO = GL30.glGenVertexArrays(); //generate id for VAO
		if(this.selectionVBO == -1)
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
		
		if(this.selectionIndiciesVBO == -1)
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

	public List<IClickListener> getClickListener()
	{
		return this.clickListener;
	}
	
	public void addClickListener(IClickListener listener)
	{
		this.clickListener.add(listener);
	}
	
	public void removeClickListener(IClickListener listener)
	{
		this.clickListener.remove(listener);
	}
	
	public List<ISelectListener> getSelectListener()
	{
		return this.selectListener;
	}
	
	public void addSelectListener(ISelectListener listener)
	{
		this.selectListener.add(listener);
	}
	
	public void removeSelectListener(ISelectListener listener)
	{
		this.selectListener.remove(listener);
	}

}
