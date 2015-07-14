import java.util.Random;

public class Background
{

	Random r = new Random();

	int x;
	int y;
	int xspd;
	int yspd;
	int level;
	int xsiz;
	int num;
	int ysiz;
	int rat;

	int[] wid1 = new int[]{370, 894, 860, 838, 804, 163, 472};
	int[] wid2 = new int[]{425, 150, 234, 223};
	int[] wid3 = new int[]{189, 140, 171, 89};
	int[] hig1 = new int[]{385, 348, 626, 528, 892, 356, 330};
	int[] hig2 = new int[]{625, 192, 293, 673};
	int[] hig3 = new int[]{196, 232, 86, 210};


	public void act(){
		x += xspd*level;
		y += yspd*level;

		if(x > 10000+xsiz)
			x = -xsiz;

		if(x < -xsiz)
			x = 10000+xsiz;

		if(y > 10000+ysiz)
			y = -ysiz;

		if(y < -ysiz)
			y = 10000+ysiz;

	}

	public void newBack(int lev){
		x = r.nextInt(10000)+1;
		y = r.nextInt(10000)+1;
		xspd = r.nextInt(11)-5;
		yspd = r.nextInt(11)-5;
		level = lev;
		xsiz = (r.nextInt(950)+51)/level;

		switch(level){
		case 1: 
			num = r.nextInt(7);
			rat = wid1[num]/hig1[num];
			ysiz = rat*xsiz;
			break;

		case 2: 
			num = r.nextInt(4);

			rat = wid2[num]/hig2[num];
			ysiz = rat*xsiz;
			break;
		case 3: 
			num = r.nextInt(4);

			rat = wid3[num]/hig3[num];
			ysiz = rat*xsiz;
			break;
		}

	}
}
