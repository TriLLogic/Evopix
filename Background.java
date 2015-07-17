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
	double rat;
	int rot;
	int rotspd = 0;

	int[] wid1 = new int[]{370, 894, 860, 838, 804, 163, 472};
	int[] wid2 = new int[]{425, 150, 234, 223};
	int[] wid3 = new int[]{189, 140, 171, 89};
	int[] hig1 = new int[]{385, 348, 626, 528, 892, 356, 330};
	int[] hig2 = new int[]{625, 192, 293, 673};
	int[] hig3 = new int[]{196, 232, 86, 210};


	public void act(int offset)
	{
		x += xspd*level;
		y += yspd*level;
		rot += rotspd;

		if(x > 6000)
			x = -6000;
		if(x < -6000)
			x = 6000;
		if(y > 6000)
			y = -6000;
		if(y < -6000)
			y = 6000;
		//System.out.println(rot+" "+rotspd);
	}

	public void newBack(int lev){
		x = r.nextInt(10001)-5000;
		y = r.nextInt(10001)-5000;
		xspd = r.nextInt(11)-5;
		yspd = r.nextInt(11)-5;
		level = lev;

		while(rotspd == 0)
			rotspd = r.nextInt(6)-3;
		rot = r.nextInt(360);


		switch(level)
		{
		case 1:
		{
			xsiz = (r.nextInt(600)+601);
			num = r.nextInt(7);
			rat = (double)hig1[num]/wid1[num];
			ysiz = (int) (rat*xsiz);
			break;
		}
		case 2: 
		{
			xsiz = (r.nextInt(375)+126);
			num = r.nextInt(4);
			rat = (double)hig2[num]/wid2[num];
			ysiz = (int) (rat*xsiz);
			break;
		}
		case 3: 
		{
			xsiz = (r.nextInt(150)+51);
			num = r.nextInt(4);
			rat = (double)hig3[num]/wid3[num];
			ysiz = (int) (rat*xsiz);
			break;
		}
		}

	}
}
