#version 330 core
precision highp float;

uniform sampler2D texture_font;
uniform int state;
uniform float time;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

out vec4 out_Color;

void main(void) {

	vec4 color = pass_Color*texture2D(texture_font,pass_TextureCoord);
	if(state == 1)
	{
		float alpha = 1.0;
		if(time <= 400)
			alpha = (1.0/400)*time;
		if(time >= 600)
			alpha = (1.0/400)*((time-1000.0)*-1);
		color = vec4(color.x, color.y, color.z, alpha);
	}
	out_Color = color;
}