package cdg.swi.game.menu;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.*;

import cdg.swi.game.util.Matrix4x4;
import cdg.swi.game.util.Utility;
import cdg.swi.game.util.VertexData;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cdg.swi.game.util.StaticManager;
import cdg.swi.game.util.interfaces.IClickListener;
import cdg.swi.game.util.interfaces.IGameControl;
import cdg.swi.game.util.interfaces.IMenuObject;
import cdg.swi.game.util.interfaces.ISelectListener;

public abstract class MenuFrame {

	private List<Component> components;
	private int lastSelectedId;
	private IGameControl control;
	
	private int backgroundVAO = -1;
	private int backgroundVBO = -1;
	private int backgroundIndiciesVBO = -1;
	private boolean drawBackground = false;
	
	private float frame = 0;
	
	public MenuFrame(IGameControl control)
	{
		this.control = control;
		this.components = new ArrayList<Component>();
		this.setupGL();
	}
	
	protected void add(Component comp)
	{
		int newID;
		if(this.components.size() != 0)
			newID = this.components.get(this.components.size()-1).getId()+1;
		else
			newID = 1;
		comp.setId(newID);
		this.components.add(comp);
	}
	
	protected void remove(int index)
	{
		this.components.remove(0);
	}
	
	protected void remove(Component comp)
	{
		this.components.remove(comp);
	}
	
	protected void setComponent(int index, Component comp)
	{
		int id = this.components.get(index).getId();
		comp.setId(id);
		this.components.set(index, comp);
	}
	
	protected Component getComponentById(int id)
	{
		for(int i = 0; i < this.components.size(); i++)
		{
			if(this.components.get(i).getId() == id)
				return this.components.get(i);
		}
		return null;
	}
	
	protected Component getComponent(int index)
	{
		return this.components.get(index);
	}
	
	protected List<Component> getComponents()
	{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
		return this.components;
	}
	
	public void draw()
	{
		//frame+=control.getDelta();
		if(frame >= 30)
		{
			this.doSelection(true,true);
			frame = 0;
		}
		this.doSelection(true,true);
		
		if(this.drawBackground )
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, StaticManager.MAIN_MENU_BACKGROUND_TEXTURE_ID);
			GL20.glUseProgram(StaticManager.MENU_SHADER_PROGRAM_ID);
			
			GL30.glBindVertexArray(this.backgroundVAO);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			
			// Bind to the index VBO that has all the information about the order of the vertices
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.backgroundIndiciesVBO);
			
			FloatBuffer mat = BufferUtils.createFloatBuffer(16);
			mat.put(new Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f).toArray());
			mat.flip();
				
			
			GL20.glUniform1i(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "texture_font"), 0);
			GL20.glUniformMatrix4(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "windowMatrix"), false, mat);		
			GL20.glUniform1i(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "state"), 0);
			
			// Draw the vertices
			GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
			
			// Put everything back to default (deselect)
			
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		
		
		for(int i = 0; i < this.components.size(); i++)
		{
			this.components.get(i).drawUI();
		}
	}
	
	public void doSelection()
	{
		doSelection(false,false);
	}
	
	public void doSelection(boolean clearAfter)
	{
		doSelection(false,clearAfter);
	}
	
	public void doSelection(boolean clearBefore, boolean clearAfter)
	{
		if(clearBefore)
		{
			GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		}
		
		GL20.glUseProgram(StaticManager.SELECTION_SHADER_PROGRAM_ID);
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).isSelectable())
				components.get(i).drawSelection();
		}
		GL20.glUseProgram(0);
		
		ByteBuffer pixel = ByteBuffer.allocateDirect(16);
		GL11.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);
		int gotId = cdg.swi.game.util.Utility.glColorToId(new byte[]{pixel.get(0),pixel.get(1),pixel.get(2),pixel.get(3)}, false);
		
		//System.out.println("Got id: "+gotId); // TODO: remove debug;
		
		if(gotId != this.lastSelectedId)
		{
			Component c = this.getComponentById(lastSelectedId);
			if(c != null)
			{
				List<ISelectListener> l = c.getSelectListener();
				for(int i = 0; i < l.size(); i++)
				{
					l.get(i).unselected();
				}
			}
		}
		
		if(Mouse.isButtonDown(0))
		{
			Component c = this.getComponentById(gotId);
			if(c != null)
			{
				List<IClickListener> l = this.getComponentById(gotId).getClickListener();
				for(int i = 0; i < l.size(); i++)
				{
					l.get(i).clicked(Mouse.getX(),Mouse.getY(),0);
				}
			}
		}
		else if(gotId != this.lastSelectedId)
		{
			Component c = this.getComponentById(gotId);
			if(c != null)
			{
				List<ISelectListener> l = c.getSelectListener();
				for(int i = 0; i < l.size(); i++)
				{
					l.get(i).selected(Mouse.getX(),Mouse.getY());
				}
				this.lastSelectedId = gotId;
			}
		}
		
		if(clearAfter)
		{
			GL11.glClearColor(StaticManager.CLEAR_COLOR[0], 
							  StaticManager.CLEAR_COLOR[1], 
							  StaticManager.CLEAR_COLOR[2], 
							  StaticManager.CLEAR_COLOR[3]);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		}
	}

	private void setupGL()
	{
		if(this.backgroundVAO == -1)
			this.backgroundVAO = GL30.glGenVertexArrays(); //generate id for VAO
		if(this.backgroundVBO == -1)
			this.backgroundVBO = GL15.glGenBuffers(); //generate id for VBO
		
		glBindVertexArray(this.backgroundVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.backgroundVBO); //bind our VBO
		
		//create our selection frame
		VertexData[] points = new VertexData[]{new VertexData(new float[]{-1.0f,1.0f,0.0f,1.0f}, new float[]{1.0f,1.0f,1.0f,1.0f}, new float[]{0.0f,0.0f}),
											   new VertexData(new float[]{-1.0f,-1.0f,0.0f,1.0f}, new float[]{1.0f,1.0f,1.0f,1.0f}, new float[]{0.0f,1.0f/StaticManager.ASPECT_RATIO}),
											   new VertexData(new float[]{1.0f,-1.0f,0.0f,1.0f}, new float[]{1.0f,1.0f,1.0f,1.0f}, new float[]{1.0f,1.0f/StaticManager.ASPECT_RATIO}),
											   new VertexData(new float[]{1.0f,1.0f,0.0f,1.0f}, new float[]{1.0f,1.0f,1.0f,1.0f}, new float[]{1.0f,0.0f})};
		
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
		
		if(this.backgroundIndiciesVBO == -1)
			this.backgroundIndiciesVBO = GL15.glGenBuffers(); //generate buffer id for indicies
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.backgroundIndiciesVBO); //bind indicies buffer
		
		//upload indicies
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind buffer
		
		GL30.glBindVertexArray(0); //unbind VAO
	}
	
	/**
	 * @return the control
	 */
	public IGameControl getControl() {
		return control;
	}
	
	public void setDrawBackground(boolean drawBG)
	{
		this.drawBackground = drawBG;
	}
}
