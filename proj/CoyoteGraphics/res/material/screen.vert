uniform mat4 u_mvpMatrix;
uniform mat4 u_mvMatrix;

attribute vec4 a_position;

varying vec4 v_pos;

void main()
{
   gl_Position = u_mvpMatrix * a_position;
   v_pos = u_mvMatrix * a_position;
}