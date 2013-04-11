package cdg.swi.game.menu;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cdg.swi.game.util.Matrix4x4;
import cdg.swi.game.util.StaticManager;
import cdg.swi.game.util.Utility;
import cdg.swi.game.util.VertexData;
import cdg.swi.game.util.interfaces.IFocusListener;
import cdg.swi.game.util.interfaces.IKeyboardListener;

public class Textbox extends Component {

	private float[] backgroundColor = new float[]{0.4f,0.4f,1.0f,1.0f};
	
	private int textPosition = 0;
	
	public Textbox(float x, float y) 
	{
		super(x, y);
		this.setHeight(0.2f);
		this.setWidth(0.3f);
		this.setText("");
		this.setTextOffset(0.01f, 0.0f);
		this.addfocusListener(new IFocusListener(){

			@Override
			public void focused() {
				backgroundColor = new float[]{1.0f,0.0f,0.0f,1.0f};
				setupGL();
			}

			@Override
			public void unfocused() {
				backgroundColor = new float[]{0.4f,0.4f,1.0f,1.0f};
				setupGL();
			}});
		
		this.addKeyboardListener(new IKeyboardListener(){

			@Override
			public void keyDown(int key, char keyChar) {
				if(key == Keyboard.KEY_BACK)
				{
					String ntxt = "";
					if(textPosition <= getText().length() && textPosition != 0)
					{
						char[] chars = getText().toCharArray();
						
						for(int i = 0; i < textPosition-1; i++)
						{
							ntxt += chars[i];
						}						
						
						for(int i = textPosition+1; i < getText().length(); i++)
						{
							ntxt += chars[i];
						}
						
						textPosition--;
					}
					setText(ntxt);
				}
				else if(key == Keyboard.KEY_DELETE)
				{
					//TODO implement delete key
				}
				else
				{
					String ntxt = (getText()+keyChar).replaceAll("\\p{C}", "");
					if(!ntxt.equals(getText()))
					{
						setText(ntxt);
						textPosition++;
					}
				}
				setupGL();
			}});
		
		this.setupGL();
	}
	
	private void setupGL()
	{
		if(this.drawVAO == -1)
			this.drawVAO = GL30.glGenVertexArrays(); //generate id for VAO
		if(this.drawVBO == -1)
			this.drawVBO = GL15.glGenBuffers(); //generate id for VBO
		
		glBindVertexArray(this.drawVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.drawVBO); //bind our VBO
		
		//create our selection frame
		VertexData[] points = new VertexData[]{new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, this.backgroundColor),
											   new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, this.backgroundColor),
											   new VertexData(new float[]{this.x+this.width,this.y-this.height,0.0f,1.0f}, this.backgroundColor),
											   new VertexData(new float[]{this.x+this.width,this.y,0.0f,1.0f}, this.backgroundColor)};
		
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
		
		if(this.drawIndiciesVBO == -1)
			this.drawIndiciesVBO = GL15.glGenBuffers(); //generate buffer id for indicies
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO); //bind indicies buffer
		
		//upload indicies
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind buffer
		
		GL30.glBindVertexArray(0); //unbind VAO
	}

	@Override
	public void setAlpha(float alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawUI() {
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		
		GL20.glUseProgram(StaticManager.MENU_SHADER_PROGRAM_ID);
		GL30.glBindVertexArray(this.drawVAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		// Bind to the index VBO that has all the information about the order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO);
		
		FloatBuffer mat = BufferUtils.createFloatBuffer(16);
		mat.put(new Matrix4x4(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f).toArray());
		mat.flip();
			
		
		GL20.glUniform1i(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "texture_font"), 0);
		GL20.glUniformMatrix4(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "windowMatrix"), false, mat);		
		GL20.glUniform1i(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "state"), -1);
		GL20.glUniform2f(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "position"), x, y);
		GL20.glUniform2f(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "dim"), this.width, this.height);
	
		
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
