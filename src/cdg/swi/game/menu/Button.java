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

import cdg.swi.game.util.Matrix4x4;
import cdg.swi.game.util.StaticManager;
import cdg.swi.game.util.Utility;
import cdg.swi.game.util.VertexData;
import cdg.swi.game.util.interfaces.ISelectListener;

public class Button extends Component {

	private float[] color = new float[]{0.0f,0.2745098034f,0.7294117637f,0.4f};
	private float borderVW = 0.016f;
	private float borderHW = 0.02f;
	private float borderHH = 0.012f;
	private float[] borderColor = new float[]{0.91f, 0.91f, 0.91f, 0.5f};
	
	public Button(float x, float y) {
		super(x, y);
		this.width = 0.3f;
		this.height = 0.15f;		

		this.setTextColor(0.91f, 0.91f, 0.91f, 0.7f);
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
		color = new float[]{0.02352941096f,0.71372548884f,0.98431372362f,0.4f};
		
		this.setupGL();		
	}
	
	public void onUnselected()
	{
		color = new float[]{0.0f,0.2745098034f,0.7294117637f,0.4f};
		
		this.setupGL();
	}
	
	private void setupGL()
	{
		if(this.drawVAO == -1)
			this.drawVAO= GL30.glGenVertexArrays(); //generate id for VAO
		if(this.drawVBO == -1)
			this.drawVBO = GL15.glGenBuffers(); //generate id for VBO
		
		GL30.glBindVertexArray(this.drawVAO); //bind our VAO
		GL15.glBindBuffer(GL_ARRAY_BUFFER, this.drawVBO); //bind our VBO
		
		
		VertexData[] points = new VertexData[]{new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, 
				new float[]{this.color[0],this.color[1],this.color[2],this.color[3]},
				new float[]{0,0}),
		   new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, 
				new float[]{this.color[0],this.color[1],this.color[2],this.color[3]},
				new float[]{0,0}),
		   new VertexData(new float[]{this.x+this.width,this.y-this.height,0.0f,1.0f}, 
				new float[]{this.color[0],this.color[1],this.color[2],this.color[3]},
				new float[]{0,0}),
		   new VertexData(new float[]{this.x+this.width,this.y,0.0f,1.0f}, 
				new float[]{this.color[0],this.color[1],this.color[2],this.color[3]},
				new float[]{0,0}),
				
		//-----------------------------------------------------------------------------		
		new VertexData(new float[]{this.x,this.y,0.0f,1.0f}, 
				this.borderColor,
				new float[]{0,0}),
		new VertexData(new float[]{this.x,this.y-this.height,0.0f,1.0f}, 
				this.borderColor,
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.borderVW,this.y-this.height,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.borderVW,this.y,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
				
		//-----------------------------------------------------------------------------		
		new VertexData(new float[]{this.x+this.width-this.borderVW,this.y,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width-this.borderVW,this.y-this.height,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width,this.y-this.height,0.0f,1.0f}, 
				this.borderColor,
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width,this.y,0.0f,1.0f}, 
				this.borderColor,
				new float[]{0,0}),
				
		//-----------------------------------------------------------------------------		
		new VertexData(new float[]{this.x+this.borderVW,this.y,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.borderVW,this.y-this.borderHH,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.borderVW+this.borderHW,this.y-this.borderHH,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.borderVW+this.borderHW,this.y,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
				
		//-----------------------------------------------------------------------------		
		new VertexData(new float[]{this.x+this.borderVW,this.y-this.height+this.borderHH,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.borderVW,this.y-this.height,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.borderVW+this.borderHW,this.y-this.height,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.borderVW+this.borderHW,this.y-this.height+this.borderHH,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
				
		//-----------------------------------------------------------------------------		
		new VertexData(new float[]{this.x+this.width-this.borderVW-this.borderHW,this.y,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width-this.borderVW-this.borderHW,this.y-this.borderHH,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width-this.borderVW,this.y-this.borderHH,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width-this.borderVW,this.y,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
						
		//-----------------------------------------------------------------------------		
		new VertexData(new float[]{this.x+this.width-this.borderVW-this.borderHW,this.y-this.height+this.borderHH,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width-this.borderVW-this.borderHW,this.y-this.height,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width-this.borderVW,this.y-this.height,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0}),
		new VertexData(new float[]{this.x+this.width-this.borderVW,this.y-this.height+this.borderHH,0.0f,1.0f}, 
				this.getTextColor(),
				new float[]{0,0})
				
		};
		
		
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
		ByteBuffer indiciesBuffer = BufferUtils.createByteBuffer(6*7);
		indiciesBuffer.put(new byte[]{0, 1, 2, 2, 3, 0,
									  4, 5, 6, 6, 7, 4,
									  8, 9, 10, 10, 11, 8,
									  12, 13, 14, 14, 15, 12,
									  16, 17, 18, 18, 19, 16,
									  20, 21, 22, 22, 23, 20,
									  24, 25, 26, 26, 27, 24}); //put in indicies
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
	public void drawUI() 
	{
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
		
		GL20.glUniform1i(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "state"), 10);
		GL20.glUniform2f(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "borderDim"), this.borderVW, this.height);
		GL11.glDrawElements(GL11.GL_TRIANGLES, 12, GL11.GL_UNSIGNED_BYTE, 6);
		
		GL20.glUniform1i(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "state"), 11);
		GL20.glUniform2f(GL20.glGetUniformLocation(StaticManager.MENU_SHADER_PROGRAM_ID, "borderDim"), this.borderHW, this.borderHH);
		GL11.glDrawElements(GL11.GL_TRIANGLES, 24, GL11.GL_UNSIGNED_BYTE, 18);
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
