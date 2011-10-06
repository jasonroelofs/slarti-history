// GLSL 1.2

// IN
attribute vec3 inPosition;
attribute vec2 inTexCoord;

// OUT
varying vec2 texCoord;

// CONST
uniform mat4 g_WorldViewProjectionMatrix;

void main()
{
  texCoord = inTexCoord;

  // OUT
  gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
}