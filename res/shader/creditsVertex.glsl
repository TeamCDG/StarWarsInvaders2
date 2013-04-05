#version 330 core

in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;

uniform mat4 font_scaling_matrix;
uniform mat4 windowMatrix;
uniform vec2 position;
uniform int state;
uniform float time;
uniform float maxy;

out vec4 pass_Color;
out vec2 pass_TextureCoord;

void main(void) {
	vec4 scaledVertex = font_scaling_matrix * in_Position;
	vec4 endColor = in_Color;
	
	if(state == 0 || state == 1)
	{
		vec4 newVertex = vec4(scaledVertex.x + position.x, scaledVertex.y + position.y, scaledVertex.z, scaledVertex.w);
		gl_Position = windowMatrix * newVertex;
	}
	else
	{
		float yadd = -0.003475 * time + 0.7;
		float newy = scaledVertex.y + position.y + yadd;
		if(newy > 0.6 && newy < 0.75)
			endColor = vec4(in_Color.x, in_Color.y, in_Color.z, (0.8/0.15)*((newy-0.75)*-1));
		else if(newy > 0.75)
			endColor = vec4(in_Color.x, in_Color.y, in_Color.z, 0.0);
			
		float xres = (-0.5 * newy) + 1.5;	
		if(scaledVertex.x < 0)
		{
			xres *= -1;
		}
			
		float mapf = 1.0 / xres;
		float newx = (scaledVertex.x+position.x)*0.5 / mapf;
		
		vec4 newVertex = vec4(newx, newy, scaledVertex.z, scaledVertex.w);
		gl_Position = windowMatrix * newVertex;
	}
	
	pass_Color = endColor;
	pass_TextureCoord = in_TextureCoord;
}