#version 330 core
precision highp float;

uniform sampler2D texture_font;
uniform int drawMode;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

out vec4 out_Color;

void main(void) {
	if(drawMode == 0)
		out_Color = pass_Color*texture2D(texture_font,pass_TextureCoord);
	else
		out_Color = pass_Color;
}