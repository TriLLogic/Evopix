import java.util.Random;

public class Bubble
{

	Random r = new Random();
	
	int x;
	int y;
	int xmove;
	int ymove;
	boolean pop = false;
	
	public void act(){
		if(xmove == 0 && ymove == 0){
			pop = true;
			//randBub();
		}else{
			x += xmove;
			y += ymove;
			xmove /= 1.001;
			ymove /= 1.001;
		}
	}

	public void newBub(int xpos, int ypos, int xvel, int yvel){
		x = xpos;
		y = ypos;
		xmove = xvel;
		ymove = yvel;
	}
	public void randBub(){
		newBub(r.nextInt(1000)-140, r.nextInt(620)-140, r.nextInt(20)-10, r.nextInt(20)-10);
	}
}
