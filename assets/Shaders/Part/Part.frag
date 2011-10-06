// GLSL 1.2

// IN
varying vec2 texCoord;

// CONST
uniform sampler2D m_Texture;

void main()
{

  // OUT
  gl_FragColor = texture2D(m_Texture, texCoord);
}