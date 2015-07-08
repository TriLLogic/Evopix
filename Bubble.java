import java.util.Random;

public class Bubble {

	Random r = new Random();
	
	int x;
	int y;
	int xmove;
	int ymove;
	
	public void act(){
		if(xmove == 0 && ymove == 0){
			randBub();
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
	public void randBub(){
		newBub(r.nextInt(720), r.nextInt(480), r.nextInt(20)-10, r.nextInt(20)-10);
	}
}

/*
int bubNum = 10;
Bubble[] bubs = new Bubble[bubNum];

bubbleImage = ImageIO.read(new File("bubbleBlue.jpg"));

for(int i = 0; i<bubNum; i ++){
	bubs[i] = new Bubble();
	bubs[i].randBub();
}

for(int i = 0; i<bubNum; i ++){
	bubs[i].act();
	g.drawImage(bubbleImage, bubs[i].x, bubs[i].y, 16, 16, null);
}
*/
