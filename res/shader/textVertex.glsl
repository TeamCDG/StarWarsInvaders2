#version 330 core

in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;

uniform mat4 font_scaling_matrix;
uniform vec2 position;

out vec4 pass_Color;
out vec2 pass_TextureCoord;

void main(void) {
	vec4 scaledVertex = font_scaling_matrix * in_Position;
	vec4 newVertex = vec4(scaledVertex.x + position.x, scaledVertex.y + position.y, scaledVertex.z, scaledVertex.w);
	gl_Position = newVertex;
	
	pass_Color = in_Color;
	pass_TextureCoord = in_TextureCoord;
}