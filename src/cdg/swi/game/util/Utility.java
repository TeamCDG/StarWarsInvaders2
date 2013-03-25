package cdg.swi.game.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Utility 
{
	
	public static final float GL_COLOR_PER_BIT = 0.00390625f;
	
	public static float[] idToGlColor(int id, boolean useAlpha)
	{
		byte[] val = new byte[4];

		val[0] = (byte) (id >> 24);
		val[1] = (byte) (id >> 16);
		val[2] = (byte) (id >> 8);
		val[3] = (byte) (id >> 0);
		
		float[] col;
		if(useAlpha)
			col = new float[] { (float)val[3]*GL_COLOR_PER_BIT,
								(float)val[2]*GL_COLOR_PER_BIT,
								(float)val[1]*GL_COLOR_PER_BIT,
								(float)val[0]*GL_COLOR_PER_BIT};
		else
			col = new float[] { (float)val[3]*GL_COLOR_PER_BIT,
								(float)val[2]*GL_COLOR_PER_BIT,
								(float)val[1]*GL_COLOR_PER_BIT,
								1.0f};
		
		return col;
	}
	
	public static int glColorToId(byte[] color, boolean useAlpha)
	{
		if(useAlpha)
		{
			byte[] fin = new byte[]{color[0], color[1], color[2], color[3]};
			
			return   fin[0] & 0xFF |
		            (fin[1] & 0xFF) << 8 |
		            (fin[2] & 0xFF) << 16 |
		            (fin[3] & 0xFF) << 24;
		}
		else
		{
			byte[] fin = new byte[]{color[0], color[1], color[2]};
			
			return   fin[0] & 0xFF |
		            (fin[1] & 0xFF) << 8 |
		            (fin[2] & 0xFF) << 16|
		            (0 & 0xFF) << 24;
		}
		
	}
	
	public static int loadShader(String filename, int type) 
	{
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		
		if (GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		
		return shaderID;
	}
}
