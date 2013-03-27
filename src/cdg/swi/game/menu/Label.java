package cdg.swi.game.menu;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import cdg.swi.game.util.FontFinals;
import cdg.swi.game.util.StaticManager;
import cdg.swi.game.util.VertexData;

public class Label extends Component {

	private VertexData[] points;
	private int indiciesCount;
	private int indiciesType;
	private String text;
	
	public Label(float x, float y, String text) 
	{
		super(x, y);
		this.text = text;
		this.width = FontFinals.getWidth('E');
		this.height = FontFinals.getHeight('A');
		this.setupGL2();
	}
	
	
	private void setupGL2()
	{
		this.drawVAO= GL30.glGenVertexArrays(); //generate id for VAO
		this.drawVBO = GL15.glGenBuffers(); //generate id for VBO
		
		glBindVertexArray(this.drawVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.drawVBO); //bind our VBO
		
		float xOff1 = FontFinals.getWidth('E');
		float xOff2 = xOff1 + FontFinals.getWidth('V');
		float xOff3 = xOff2 + FontFinals.getWidth('A');
		
		points = new VertexData[]{new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('E'),FontFinals.getY('E')}),
		   new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('E'),FontFinals.getY('E')+FontFinals.getHeight('E')}),
		   new VertexData(new float[]{this.x+xOff1,this.y-this.height,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('E')+FontFinals.getWidth('E'),FontFinals.getY('E')+FontFinals.getHeight('E')}),
		   new VertexData(new float[]{this.x+xOff1,this.y,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('E')+FontFinals.getWidth('E'),FontFinals.getY('E')}),
		   new VertexData(new float[]{this.x+xOff1,this.y,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('V'),FontFinals.getY('V')}),
		   new VertexData(new float[]{this.x+xOff1,this.y-this.height,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('V'),FontFinals.getY('V')+FontFinals.getHeight('V')}),
		   new VertexData(new float[]{this.x+xOff2,this.y-this.height,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('V')+FontFinals.getWidth('V'),FontFinals.getY('V')+FontFinals.getHeight('V')}),
		   new VertexData(new float[]{this.x+xOff2,this.y,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('V')+FontFinals.getWidth('V'),FontFinals.getY('V')}),
		   new VertexData(new float[]{this.x+xOff2,this.y,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('A'),FontFinals.getY('A')}),
		   new VertexData(new float[]{this.x+xOff2,this.y-this.height,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('A'),FontFinals.getY('A')+FontFinals.getHeight('A')}),
		   new VertexData(new float[]{this.x+xOff3,this.y-this.height,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('A')+FontFinals.getWidth('A'),FontFinals.getY('A')+FontFinals.getHeight('A')}),
		   new VertexData(new float[]{this.x+xOff3,this.y,0.0f,1.0f}, 
				new float[]{1.0f,1.0f,1.0f,1.0f},
				new float[]{FontFinals.getX('A')+FontFinals.getWidth('A'),FontFinals.getY('A')})};
		
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
		ByteBuffer indiciesBuffer = BufferUtils.createByteBuffer(18);
		indiciesBuffer.put(new byte[]{0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 8, 9, 10, 10, 11, 8}); //put in indicies
		indiciesBuffer.flip();
		
		this.drawIndiciesVBO = GL15.glGenBuffers(); //generate buffer id for indicies
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO); //bind indicies buffer
		
		//upload indicies
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL15.GL_STATIC_DRAW);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind buffer
		
		GL30.glBindVertexArray(0); //unbind VAO
	}
	
	private void setupGL()
	{
		this.drawVAO= GL30.glGenVertexArrays(); //generate id for VAO
		this.drawVBO = GL15.glGenBuffers(); //generate id for VBO
		
		glBindVertexArray(this.drawVAO); //bind our VAO
		glBindBuffer(GL_ARRAY_BUFFER, this.drawVBO); //bind our VBO		
		
		setupGLText();
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); //unbind buffer
		
		GL30.glBindVertexArray(0); //unbind VAO
	}
	
	private void setupGLText()
	{
		points = new VertexData[this.text.length()*4];
		int indiciesOffset = 0;
		float xOffset = 0.0f;
		
		ByteBuffer indiciesBuffer = BufferUtils.createByteBuffer(this.text.length()*3*2);	
		this.indiciesCount = this.text.length()*3*2;
		
		for(int i = 0; i < this.text.length(); i++)
		{
		
			char c = this.text.charAt(i);
			
			VertexData v0 = new VertexData();
			v0.setRGBA(1.0f, 0.0f, 0.0f, 1.0f);
			v0.setXYZW(this.x+xOffset, this.y, 0.0f, 1.0f);
			v0.setST(FontFinals.getX(c), FontFinals.getY(c));
			
			VertexData v1 = new VertexData();
			v1.setRGBA(1.0f, 0.0f, 0.0f, 1.0f);
			v1.setXYZW(this.x+xOffset, this.y+FontFinals.getHeight(c), 0.0f, 1.0f);
			v1.setST(FontFinals.getX(c), FontFinals.getY(c)+FontFinals.getHeight(c));
			
			VertexData v2 = new VertexData();
			v2.setRGBA(1.0f, 0.0f, 0.0f, 1.0f);
			v2.setXYZW(this.x+xOffset+FontFinals.getWidth(c), this.y+FontFinals.getHeight(c), 0.0f, 1.0f);
			v2.setST(FontFinals.getX(c)+FontFinals.getWidth(c), FontFinals.getY(c)+FontFinals.getHeight(c));
			
			VertexData v3 = new VertexData();
			v3.setRGBA(1.0f, 0.0f, 0.0f, 1.0f);
			v3.setXYZW(this.x+xOffset+FontFinals.getWidth(c), this.y, 0.0f, 1.0f);
			v3.setST(FontFinals.getX(c)+FontFinals.getWidth(c), FontFinals.getY(c));
			
			points[0+indiciesOffset] = v0;
			points[1+indiciesOffset] = v1;
			points[2+indiciesOffset] = v2;
			points[3+indiciesOffset] = v3;
			
			indiciesBuffer.put(new byte[]{(byte) (0+indiciesOffset),
										 (byte) (1+indiciesOffset), 
										 (byte) (2+indiciesOffset), 
										 (byte) (2+indiciesOffset), 
										 (byte) (3+indiciesOffset), 
										 (byte) (0+indiciesOffset)}); //put in indicies
			indiciesOffset += 4;
			xOffset += FontFinals.getWidth(c);
			
			System.out.println("Current char: '"+c+"'");
		}
		
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
		
		//buffer for storing indicies
		
		
		this.drawIndiciesVBO = GL15.glGenBuffers(); //generate buffer id for indicies
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO); //bind indicies buffer
		
		//upload indicies
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL15.GL_STATIC_DRAW);
		
		this.height = FontFinals.getHeight('A');
		this.width = xOffset;
	}
	
	
	@Override
	public void drawUI() 
	{
		
		System.out.println("Label: "+
						   this.width+"/"+this.height+
						   " SID: "+StaticManager.MENU_SHADER_PROGRAM_ID+
						   " FTID: "+StaticManager.FONT_TEXTURE_ID+
						   " FTUID: "+StaticManager.FONT_TEXTURE_UNIFORM_ID);
		
		
		//GL20.glUseProgram(StaticManager.SELECTION_SHADER_PROGRAM_ID);
		
		//GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, StaticManager.FONT_TEXTURE_ID);
		/*
		GL30.glBindVertexArray(this.drawVAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		// Bind to the index VBO that has all the information about the order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO);
		
		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, this.indiciesCount, GL11.GL_UNSIGNED_BYTE, 0);
		
		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
		*/
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL20.glUseProgram(StaticManager.MENU_SHADER_PROGRAM_ID);
		GL30.glBindVertexArray(this.drawVAO);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, StaticManager.FONT_TEXTURE_ID);
		GL20.glUniform1i(StaticManager.FONT_TEXTURE_UNIFORM_ID, 0);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		// Bind to the index VBO that has all the information about the order of the vertices
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.drawIndiciesVBO);
		
		
		// Draw the vertices
		GL11.glDrawElements(GL11.GL_TRIANGLES, 18, GL11.GL_UNSIGNED_BYTE, 0);
		
		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
		

	}

}
