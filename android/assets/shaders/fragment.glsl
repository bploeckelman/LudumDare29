#ifdef GL_ES 
precision mediump float;
#endif
 
 varying vec2 vTexCoord;
 uniform sampler2D u_texture;
 
void main() {
    vec4 color = texture2D(u_texture, vTexCoord);
    
    gl_FragColor = color;
    
  
}
