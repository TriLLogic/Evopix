import java.util.Random;

public class Bubble {

	Random r = new Random();
	
	int x;
	int y;
	int xmove;
	int ymove;

	boolean alive = true;

	public void act(){
		if(xmove == 0 && ymove == 0 && alive){
			alive = false;
			newBub(r.nextInt(720), r.nextInt(480), r.nextInt(20)-10, r.nextInt(20)-10);
		}else{
			x += xmove;
			y += ymove;
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
