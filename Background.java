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

/*

private Background[] bgs = new Background[]{};
private BufferedImage[] bg1 = new BufferedImage[7];
private BufferedImage[] bg2 = new BufferedImage[4];
private BufferedImage[] bg3 = new BufferedImage[4];

bg1[0] = ImageIO.read(new File("res/images/bg11.jpg"));
bg1[1] = ImageIO.read(new File("res/images/bg12.jpg"));
bg1[2] = ImageIO.read(new File("res/images/bg13.jpg"));
bg1[3] = ImageIO.read(new File("res/images/bg14.jpg"));
bg1[4] = ImageIO.read(new File("res/images/bg15.jpg"));
bg1[5] = ImageIO.read(new File("res/images/bg16.jpg"));
bg1[6] = ImageIO.read(new File("res/images/bg17.jpg"));
bg2[0] = ImageIO.read(new File("res/images/bg21.jpg"));
bg2[1] = ImageIO.read(new File("res/images/bg22.jpg"));
bg2[2] = ImageIO.read(new File("res/images/bg23.jpg"));
bg2[3] = ImageIO.read(new File("res/images/bg24.jpg"));
bg3[0] = ImageIO.read(new File("res/images/bg31.jpg"));
bg3[1] = ImageIO.read(new File("res/images/bg32.jpg"));
bg3[2] = ImageIO.read(new File("res/images/bg33.jpg"));
bg3[3] = ImageIO.read(new File("res/images/bg34.jpg"));

for(int i = 0; i<15; i++){
	bgs[i] = new Backgorund();
	bgs[i].newBack(1);
}
for(int i = 15; i<60; i++){
	bgs[i] = new Backgorund();
	bgs[i].newBack(2);
}
for(int i = 60; i<150; i++){
	bgs[i] = new Backgorund();
	bgs[i].newBack(2);
}

switch(bgs[i].level){
	case 1: g.drawImage(bg1[bgs[i].num], bgs[i].x, bgs[i].y, bgs[i].xsiz, bgs[i].ysiz, null); break;
	case 2: g.drawImage(bg2[bgs[i].num], bgs[i].x, bgs[i].y, bgs[i].xsiz, bgs[i].ysiz, null); break;
	case 3: g.drawImage(bg3[bgs[i].num], bgs[i].x, bgs[i].y, bgs[i].xsiz, bgs[i].ysiz, null); break;
}

bubs[i].act();



 */
