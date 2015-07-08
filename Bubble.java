public class Bubble {

	int x;
	int y;
	int xmove;
	int ymove;

	boolean alive = true;

	public void act(){
		if(xmove == 0 && ymove == 0 && alive){
			alive = false;
		}else{
			xmove /= 2;
			ymove /= 2;
		}
	}

	public void newBub(int xpos, int ypos, int xvel, int yvel){
		x = xpos;
		y = ypos;
		xmove = xvel;
		ymove = yvel;
	}
}
