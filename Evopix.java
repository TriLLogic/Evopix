//Imports
import javax.imageio.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Evopix extends JPanel implements MouseListener, KeyListener, ActionListener
{
	//Initialisers
	private int maxOthers = 20;
	private JPanel pane;
	private ArrayList<Cell> cells = new ArrayList<Cell>();
	private Type highlighted = Type.FLESH;
	private Timer[] timers = new Timer[4];
	private int glucose = 0;
	private int[][] combos = new int[1][3];
	private Random rng = new Random();
	private boolean menu = true;
	private boolean load = false;
	private int bubNum = 10;
	private BufferedImage[] palette = new BufferedImage[12];
	private BufferedImage[] flagellum = new BufferedImage[3];
	private BufferedImage[] menuImages = new BufferedImage[2];
	private BufferedImage highlight, glucoseImage, bubbleImage, popImage, spike;
	private Bubble[] bubs = new Bubble[bubNum];
	private int flagella = 0;
	private boolean forwards = false;
	private int offsetV = 0;
	private int offsetH = 0;
	private Background[] bgs = new Background[375];
	private BufferedImage[] bg1 = new BufferedImage[7];
	private BufferedImage[] bg2 = new BufferedImage[4];
	private BufferedImage[] bg3 = new BufferedImage[4];
	private double brainLevel = 1;
	private boolean cheat = false;
	private int rotation = 0;
	private BufferedImage organism;
	private BufferedImage[] othersI = new BufferedImage[maxOthers];
	private boolean showCOM = false;
	public static int otherOrgs = 0;
	private Organism[] others = new Organism[maxOthers];


	//Constructor
	public Evopix()
	{
		super(new BorderLayout());

		try
		{
			//GUI
			pane = new MainPane();
			pane.setBackground(new Color(188, 205, 255));
			pane.setPreferredSize(new Dimension(720, 480));
			pane.addMouseListener(this);
			pane.addKeyListener(this);
			pane.setFocusable(true);
			add(pane, BorderLayout.CENTER);
			
			//Import images
			menuImages[0] = ImageIO.read(new File("res/images/menuNew.png"));
			menuImages[1] = ImageIO.read(new File("res/images/menuLoad.png"));
			palette[0] = ImageIO.read(new File("res/images/photosynthesis.png"));
			palette[1] = ImageIO.read(new File("res/images/brain.png"));
			palette[2] = ImageIO.read(new File("res/images/shell.png"));
			palette[3] = ImageIO.read(new File("res/images/flesh.png"));
			palette[4] = ImageIO.read(new File("res/images/yellow2.png"));
			palette[5] = ImageIO.read(new File("res/images/purple2.png"));
			palette[6] = ImageIO.read(new File("res/images/blue2.png"));
			palette[7] = ImageIO.read(new File("res/images/red2.png"));
			palette[8] = ImageIO.read(new File("res/images/yellow3.png"));
			palette[9] = ImageIO.read(new File("res/images/purple3.png"));
			palette[10] = ImageIO.read(new File("res/images/blue3.png"));
			palette[11] = ImageIO.read(new File("res/images/red3.png"));
			glucoseImage = ImageIO.read(new File("res/images/glucose.png"));
			highlight = ImageIO.read(new File("res/images/highlight1.png"));
			flagellum[0] = ImageIO.read(new File("res/images/flagellum.png"));
			flagellum[1] = ImageIO.read(new File("res/images/flagellum2.png"));
			bubbleImage = ImageIO.read(new File("res/images/bubbleBlue.png"));
			popImage = ImageIO.read(new File("res/images/popBlue.png"));
			spike = ImageIO.read(new File("res/images/spike.png"));
			bg1[0] = ImageIO.read(new File("res/images/bg11.png"));
			bg1[1] = ImageIO.read(new File("res/images/bg12.png"));
			bg1[2] = ImageIO.read(new File("res/images/bg13.png"));
			bg1[3] = ImageIO.read(new File("res/images/bg14.png"));
			bg1[4] = ImageIO.read(new File("res/images/bg15.png"));
			bg1[5] = ImageIO.read(new File("res/images/bg16.png"));
			bg1[6] = ImageIO.read(new File("res/images/bg17.png"));
			bg2[0] = ImageIO.read(new File("res/images/bg21.png"));
			bg2[1] = ImageIO.read(new File("res/images/bg22.png"));
			bg2[2] = ImageIO.read(new File("res/images/bg23.png"));
			bg2[3] = ImageIO.read(new File("res/images/bg24.png"));
			bg3[0] = ImageIO.read(new File("res/images/bg31.png"));
			bg3[1] = ImageIO.read(new File("res/images/bg32.png"));
			bg3[2] = ImageIO.read(new File("res/images/bg33.png"));
			bg3[3] = ImageIO.read(new File("res/images/bg34.png"));

			//TODO only draw orgs inview
			
			//Other Organisms
			int width = 720;
			int height = 480;

			BufferedImage[] bis = new BufferedImage[maxOthers];
			Graphics2D[] g2s = new Graphics2D[maxOthers];
			File[] fs = new File[maxOthers];

			otherOrgs = rng.nextInt(maxOthers);
			System.out.println("  "+otherOrgs);
			
			FileWriter[] imgs = new FileWriter[otherOrgs];

			for(int i = 0; i<otherOrgs; i++){
				//others[0].moves = false;
				imgs[i] = new FileWriter("saves/other"+i+".png");

				System.out.println("1");

				String type = "";
				switch(rng.nextInt(10)){
				case 1: type = "phytoplankton"; break;
				case 2: type = "bacteria"; break;
				default: break;
				}
				others[i] = new Organism(type, new Coordinate(rng.nextInt(12)-6, rng.nextInt(12)-6));

				bis[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				g2s[i] = bis[i].createGraphics();

				for(Cell c : others[i].cells){
					g2s[i].drawImage(palette[c.iType], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 24, null);
				}
				

				fs[i] = new File("saves/other"+i+".png");
				try{
					ImageIO.write(bis[i], "png", fs[i]);				

				}
				catch (IOException ex){
					ex.printStackTrace();
				}
				

			}





			//Import music
			File[] music = new File[2];
			File redGiant = new File("res/music/stellardroneRedGiant.wav");
			File ultraDeepField = new File("res/music/stellardroneUltraDeepField.wav");
			music[0] = redGiant;
			music[1] = ultraDeepField;
			AudioInputStream stream = AudioSystem.getAudioInputStream(music[rng.nextInt(2)]);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} 
		catch (Exception e)
		{
			System.err.println();
		}

		
	}

	public void start()
	{
		if(!load)
		{
			cells.add(new Cell(true, true, new Coordinate(0, 0), Type.BRAIN, 0, false));
			cells.add(new Cell(true, true, new Coordinate(0, 1), Type.PHOTOSYNTHESIS, 0, false));
			cells.add(new Cell(true, true, new Coordinate(1, 0), Type.FLESH, 0, false));

			combos[0][0] = 4;
			combos[0][1] = rng.nextInt(4) + 4;
			combos[0][2] = rng.nextInt(4) + 4;
		}
		else
		{
			//Load game
			FileReader fr;
			BufferedReader br;
			try
			{
				fr = new FileReader("saves/save.txt");
				br = new BufferedReader(fr);

				String line = br.readLine();
				for (int i = 0; i < line.length() / 9; i++)
				{
					Boolean powered, controlled;
					if(line.charAt(i * 9) == 'p')
						powered = true;
					else
						powered = false;
					if(line.charAt(i * 9 + 1) == 'c')
						controlled = true;
					else
						controlled = false;

					Type t = iTypeToType(Integer.parseInt(line.charAt(i * 9 + 2) + ""));

					int x, y;
					if(line.charAt(i * 9 + 3) == '+')
						x = 10 * Integer.parseInt(line.charAt(i * 9 + 4) + "") + Integer.parseInt(line.charAt(i * 9 + 5) + "");
					else
						x = -1 * (10 * Integer.parseInt(line.charAt(i * 9 + 4) + "") + Integer.parseInt(line.charAt(i * 9 + 5) + ""));
					if(line.charAt(i * 9 + 6) == '+')
						y = 10 * Integer.parseInt(line.charAt(i * 9 + 7) + "") + Integer.parseInt(line.charAt(i * 9 + 8) + "");
					else
						y = -1 * (10 * Integer.parseInt(line.charAt(i * 9 + 7) + "") + Integer.parseInt(line.charAt(i * 9 + 8) + ""));

					cells.add(new Cell(powered, controlled, new Coordinate(x, y), t, 0, false));
				}

				line = br.readLine();
				glucose = Integer.parseInt(line);

				line = br.readLine();
				for (int i = 0; i < 3; i++)
					combos[0][i] = Integer.parseInt(line.charAt(i)+"");

				br.close();
			}catch(Exception e){
				e.printStackTrace();                     
			}

		}

		for(int i = 0; i<bubNum; i ++){
			bubs[i] = new Bubble();
			bubs[i].randBub();
		}
		for(int i = 0; i<38; i++){
			bgs[i] = new Background();
			bgs[i].newBack(1);
		}
		for(int i = 38; i<150; i++){
			bgs[i] = new Background();
			bgs[i].newBack(2);
		}
		for(int i = 150; i<375; i++){
			bgs[i] = new Background();
			bgs[i].newBack(3);
		}

		pane.repaint();

		//Start update clocks
		timers[0] = new Timer(1000, this);
		timers[0].setActionCommand("t");
		timers[1] = new Timer(100, this);
		timers[1].setActionCommand("t2");
		timers[2] = new Timer(100, this);
		timers[2].setActionCommand("t3");
		timers[3] = new Timer(10, this);
		timers[3].setActionCommand("t4");
		for (int i = 0; i < timers.length; i++)
			timers[i].start();
	}

	public Type iTypeToType(int i)
	{
		Type t = null;
		switch(i)
		{
		case 0 : t = Type.PHOTOSYNTHESIS; break;
		case 1 : t = Type.BRAIN; break;
		case 2 : t = Type.SHELL; break;
		case 3 : t = Type.FLESH; break;
		case 4 : t = Type.YELLOW; break;
		case 5 : t = Type.PURPLE; break;
		case 6 : t = Type.BLUE; break;
		case 7 : t = Type.RED; break;
		}
		return t;
	}

	public Coordinate getCentreOfMass()
	{
		int x = 0;
		int y = 0;
		for(Cell c : cells)
		{
			x += c.loc.x;
			y += c.loc.y;
		}
		x /= cells.size();
		y /= cells.size();
		return new Coordinate(x, y);
	}

	public int getMaxGlucose()
	{
		int fCells = 0;
		int not = 0;
		for(Cell c : cells)
		{
			if(c.type == Type.FLESH)
			{
				fCells++;
			}
			else
			{
				not++;
			}
		}
		return fCells * 10 + not;
	}

	public double getGlucoseInc()
	{
		int psCells = 0;
		for(Cell c : cells)
			if(c.type == Type.PHOTOSYNTHESIS)
				psCells++;
		return psCells*brainLevel;
	}

	public double getGlucoseProfit()
	{
		double inc = getGlucoseInc();
		int cost = 0;
		if(forwards)
			for(Cell c : cells)
				cost += c.energyUsed;
		return inc - cost;
	}

	public boolean isAdjacent(Cell c, Cell d)
	{
		if((d.loc.x==c.loc.x+1&&d.loc.y==c.loc.y)||(d.loc.x==c.loc.x-1&&d.loc.y==c.loc.y)||(d.loc.x==c.loc.x&&d.loc.y==c.loc.y+1)||(d.loc.x==c.loc.x&&d.loc.y==c.loc.y-1))
			return true;
		else
			return false;
	}

	public boolean checkConnected(Cell c)
	{
		for(Cell d : cells)
		{
			if(isAdjacent(c, d))
			{
				if(c.type == Type.BRAIN || d.type == Type.BRAIN)
					return true;
				if(d.type == Type.FLESH)
					if(checkConnected(d))
						return true;
			}
		}
		return false;
	}

	//Updates info pane
	public class MainPane extends JPanel 
	{
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);

			if(!menu)
			{
				//BG
				Graphics2D g2d = (Graphics2D)g;
				AffineTransform trans = new AffineTransform();

				//Background
				for (int i = 0; i < bgs.length; i++)
				{

					trans.setTransform(new AffineTransform());
					trans.setToTranslation(bgs[i].x + offsetH, bgs[i].y + offsetV);
					trans.rotate(Math.toRadians(bgs[i].rot));
					switch(bgs[i].level)
					{
					case 1: g2d.drawImage(bg1[bgs[i].num], trans, this); g.drawImage(bg1[bgs[i].num], bgs[i].x+offsetH, bgs[i].y + offsetV, bgs[i].xsiz, bgs[i].ysiz, null); break;
					case 2: g2d.drawImage(bg2[bgs[i].num], trans, this); g.drawImage(bg2[bgs[i].num], bgs[i].x+offsetH, bgs[i].y + offsetV, bgs[i].xsiz, bgs[i].ysiz, null); break;
					case 3: g2d.drawImage(bg3[bgs[i].num], trans, this); g.drawImage(bg3[bgs[i].num], bgs[i].x+offsetH, bgs[i].y + offsetV, bgs[i].xsiz, bgs[i].ysiz, null); break;
					}
				}

				//Bubbles
				for(int i = 0; i < bubNum; i++)
					if(bubs[i].pop)
						g.drawImage(popImage, bubs[i].x-4, bubs[i].y-4, 24, 24, null);
					else
						g.drawImage(bubbleImage, bubs[i].x, bubs[i].y, 16, 16, null);

				//HUD
				g.setColor(Color.GRAY);
				g.fillRect(pane.getWidth() - 210, 0, 200, 40);
				g.setColor(Color.BLACK);
				g.drawRect(pane.getWidth() - 210, 0, 200, 40);
				// GlucoseDisplay
				//Draw an image of glucose
				g.drawImage(glucoseImage, pane.getWidth() - 208, 2, 18, 14, null);
				//Display amount of glucose
				g.setFont(new Font("Arial", Font.BOLD, 14));
				g.drawString("Glucose: "+glucose+"/"+getMaxGlucose()+" ("+getGlucoseProfit()+"/s)\n", pane.getWidth() - 188, 15);

				//Palette
				g.drawImage(palette[0], pane.getWidth() - 48, pane.getHeight() - 96, 24, 24, null);
				g.drawImage(palette[1], pane.getWidth() - 24, pane.getHeight() - 96, 24, 24, null);
				g.drawImage(palette[2], pane.getWidth() - 48, pane.getHeight() - 72, 24, 24, null);
				g.drawImage(palette[3], pane.getWidth() - 24, pane.getHeight() - 72, 24, 24, null);
				g.drawImage(palette[4], pane.getWidth() - 48, pane.getHeight() - 48, 24, 24, null);
				g.drawImage(palette[5], pane.getWidth() - 24, pane.getHeight() - 48, 24, 24, null);
				g.drawImage(palette[6], pane.getWidth() - 48, pane.getHeight() - 24, 24, 24, null);
				g.drawImage(palette[7], pane.getWidth() - 24, pane.getHeight() - 24, 24, 24, null);

				switch(highlighted)
				{
				case PHOTOSYNTHESIS: g.drawImage(highlight, pane.getWidth() - 50, pane.getHeight() - 98, 28, 28, null); break;
				case BRAIN: g.drawImage(highlight, pane.getWidth() - 26, pane.getHeight() - 98, 28, 28, null); break;
				case SHELL: g.drawImage(highlight, pane.getWidth() - 50, pane.getHeight() - 74, 28, 28, null); break;
				case FLESH: g.drawImage(highlight, pane.getWidth() - 26, pane.getHeight() - 74, 28, 28, null); break;
				case YELLOW: g.drawImage(highlight, pane.getWidth() - 50, pane.getHeight() - 50, 28, 28, null); break;
				case PURPLE: g.drawImage(highlight, pane.getWidth() - 26, pane.getHeight() - 50, 28, 28, null); break;
				case BLUE: g.drawImage(highlight, pane.getWidth() - 50, pane.getHeight() - 26, 28, 28, null); break;
				case RED: g.drawImage(highlight, pane.getWidth() - 26, pane.getHeight() - 26, 28, 28, null); break;
				}

				//Check for combos
				flagella = 0;
				brainLevel = 1;

				//rotation check
				if(rotation > 360) rotation -= 360;
				if(rotation < 0) rotation += 360;

				//Flagella check
				for(Cell c : cells)
				{
					if(c.iType == combos[0][0])
					{
						for(Cell d : cells)
						{
							if(d.loc.x==c.loc.x&&d.loc.y==c.loc.y+1&&d.iType==combos[0][1])
							{
								for(Cell e : cells)
								{
									if(e.loc.x==d.loc.x&&e.loc.y==d.loc.y+1&&e.iType==combos[0][2])
									{
										for(Cell f : cells)
										{
											if(f.loc.x==c.loc.x&&f.loc.y==c.loc.y-1)
											{
												Boolean wrong = false;
												for(Cell h : cells)
												{
													if(h.loc.x==e.loc.x&&h.loc.y==e.loc.y+1)
													{
														wrong = true;
													}
												}
												if(!wrong)
												{
													if(!f.usedInCombo&&checkControlled(c))
													{
														c.setUsedInCombo(true);
														d.setUsedInCombo(true);
														e.setUsedInCombo(true);
														flagella++;
													}
													else
													{
														c.setUsedInCombo(false);
														d.setUsedInCombo(false);
														e.setUsedInCombo(false);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}

				//Brain check
				for(Cell c: cells)
				{
					if(c.type == Type.BRAIN)
					{
						for(Cell d : cells)
						{
							if(d.type == Type.BRAIN&&d.loc.x==c.loc.x&&d.loc.y==c.loc.y+1)
							{
								for(Cell e : cells)
								{
									if(e.type == Type.BRAIN&&e.loc.x==c.loc.x+1&&e.loc.y==c.loc.y)
									{
										for(Cell f : cells)
										{
											if(f.type == Type.BRAIN&&f.loc.x==c.loc.x+1&&f.loc.y==c.loc.y+1)
											{
												brainLevel=1.5;
											}
										}
									}
								}
							}
						}
					}
				}

				try
				{
					organism = ImageIO.read(new File("saves/image.png"));
					for(int i = 0; i < otherOrgs; i++){
						othersI[i] = ImageIO.read(new File("saves/other"+i+".png"));
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				trans.setTransform(new AffineTransform());
				trans.setToTranslation(0, 0);
				rotation = rotation * -1;
				Coordinate centre = getCentreOfMass();
				trans.rotate(Math.toRadians(rotation), (organism.getWidth() / 2)+(24*centre.x), (organism.getHeight() / 2)+(24*centre.y));
				rotation = rotation * -1;

				g2d.drawImage(organism, trans, this);
				for(int i = 0; i < otherOrgs; i++) {
					g2d.drawImage(othersI[i], offsetH, offsetV, this);
				}

				g.setColor(Color.RED);
				if(showCOM)
					g.fillOval((organism.getWidth() / 2)+(24*centre.x)-5, (organism.getHeight() / 2)+(24*centre.y)-5, 10, 10);

				saveGame();
			}
			else
			{
				g.drawImage(menuImages[0], 288, 167, null);
				g.drawImage(menuImages[1], 288, 239, null);
			}
		}
	}

	private boolean checkControlled(Cell c)
	{
		for(Cell d : cells)
		{
			if(isAdjacent(c, d))
			{
				if(d.type == Type.BRAIN)
					return true;
				if(d.type == Type.FLESH)
					if(checkConnected(d))
						return true;
			}
		}
		return false;
	}

	public void saveGame() // Returns code that can be used to load again
	{
		String code1 = "";
		for(int i = 0; i < cells.size(); i++) {
			code1 += cells.get(i).toString();
		}

		String comboCode2 = "";
		for(int i = 0; i < combos[0].length; i++)
		{
			comboCode2 += combos[0][i];
		}

		FileWriter fw;
		PrintWriter pw;
		try {
			fw = new FileWriter("saves/save.txt", false);
			pw = new PrintWriter(fw);
			pw.println(code1);
			pw.println(glucose);
			pw.println(comboCode2);
			pw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Create gui
	private static void createGUI() 
	{
		JFrame frame = new JFrame("Evopix");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		JComponent newContentPane = new Evopix();
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);

		frame.pack();
		frame.setVisible(true);
	}

	//Main
	public static void main(String[] args) 
	{    
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				//Create gui
				createGUI();
			}
		});
	}

	public void mouseClicked(MouseEvent me)
	{
		if(!menu)
		{
			int x = (me.getX() - pane.getWidth() / 2)<0? (me.getX() - pane.getWidth() / 2) / 24 - 1: (me.getX() - pane.getWidth() / 2) / 24;
			int y = (me.getY() - pane.getHeight() / 2)<0? (me.getY() - pane.getHeight() / 2) / 24 - 1: (me.getY() - pane.getHeight() / 2) / 24;
			int cost = 10;
			if(highlighted==Type.BRAIN)
				cost = 100;
			if(x>12&&y>5)
			{
				switch(y)
				{
				case 6: highlighted=(x==13)?Type.PHOTOSYNTHESIS:Type.BRAIN;break;
				case 7: highlighted=(x==13)?Type.SHELL:Type.FLESH;break;
				case 8: highlighted=(x==13)?Type.YELLOW:Type.PURPLE;break;
				case 9: highlighted=(x==13)?Type.BLUE:Type.RED;break;
				}
			}
			else if(glucose >= cost && !(x>=6&&y<=-9))
			{
				Boolean valid = false;
				for(Cell c : cells)
				{
					if((x==c.loc.x&&(y==c.loc.y-1||y==c.loc.y+1))||(y==c.loc.y&&(x==c.loc.x-1||x==c.loc.x+1)))
						valid=true;
					if(x==c.loc.x&&y==c.loc.y)
						c.beingDeleted = true;
				}
				if(valid)
				{
					int toDelete = -1;
					for(Cell c : cells)
					{
						if(c.beingDeleted)
							toDelete = cells.indexOf(c);
					}
					if(toDelete >= 0)
						cells.remove(toDelete);
					Cell c = new Cell(true, true, new Coordinate(x, y), highlighted, 0, false);
					if(!checkConnected(c))
						c.controlled = false;
					cells.add(c);
					glucose -= cost;
				}
				else
				{
					for(Cell c : cells)
					{
						c.beingDeleted = false;
					}
				}
			}
			pane.repaint();
		}
		else
		{
			if(me.getY()>240)
				load = true;
			menu = false;
			start();
		}
	}

	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}

	public void actionPerformed(ActionEvent ae)
	{
		switch(ae.getActionCommand())
		{
		case "t":
		{
			//Update glucose
			glucose += getGlucoseProfit();
			if(glucose>getMaxGlucose()&&!cheat)
				glucose=getMaxGlucose();
			if(glucose<0)
				glucose=0;
			pane.repaint();
			break;
		}
		case "t2":
		{
			//Flip flagella
			if(forwards)
			{
				flagellum[2] = flagellum[0];
				flagellum[0] = flagellum[1];
				flagellum[1] = flagellum[2];
				pane.repaint();
			}
			break;
		}
		case "t3":
		{
			//Animate bubbles
			for(int i = 0; i < bubNum; i++)
			{
				if(bubs[i].pop)
					bubs[i].randBub();
				bubs[i].pop = false;
				bubs[i].act();
			}
			for(int i = 0; i<375; i++)
				bgs[i].act();
			pane.repaint();
			break;
		}
		case "t4":
		{
			//Animate movement
			if(forwards)
			{
				int movementV = 0;
				int movementH = 0;
				if(glucose > 0){
					movementV = (int) (48*flagella/cells.size()*Math.cos(Math.toRadians(rotation)));
					movementH = (int) (48*flagella/cells.size()*Math.sin(Math.toRadians(rotation)));
				}

				if(cheat)
				{
					movementV=(int)(25*Math.cos(Math.toRadians(rotation)));
					movementH=(int)(25*Math.sin(Math.toRadians(rotation)));
				}

				for (int i = 0; i < bubNum; i++){
					bubs[i].y += movementV;
					bubs[i].x += movementH;
				}
				offsetV += movementV;
				offsetH += movementH;
			}

			//pos to neg
			if(offsetV > 5500)
				offsetV = -5500;
			if(offsetH > 5500){
				offsetH = -5500;
			}

			//neg to pos
			if(offsetV < -5500)
				offsetV = 5500;
			if(offsetH < -5500){
				offsetH = 5500;
			}
			pane.repaint();

			int width = 720;
			int height = 480;

			//Save new image of organism
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = bi.createGraphics();
			for(Cell c : cells)
			{
				if(!c.usedInCombo)
				{
					g2.drawImage(palette[c.iType], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 24, null);
				}
				else
				{
					g2.drawImage(palette[c.iType+4], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 24, null);
				}
			}

			//save image of different cells
			BufferedImage[] bis = new BufferedImage[maxOthers];
			Graphics2D[] g2s = new Graphics2D[maxOthers];
			for(int i = 0; i < otherOrgs; i++){
				if(others[i].moves){
					bis[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					g2s[i] = bis[i].createGraphics();
					for(Cell c : others[i].cells)
					{
						g2s[i].drawImage(palette[c.iType], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 24, null);
					}
				}
			}

			//Flagella check
			for(Cell c : cells)
			{
				if(c.iType == combos[0][0])
				{
					for(Cell d : cells)
					{
						if(d.loc.x==c.loc.x&&d.loc.y==c.loc.y+1&&d.iType==combos[0][1])
						{
							for(Cell e : cells)
							{
								if(e.loc.x==d.loc.x&&e.loc.y==d.loc.y+1&&e.iType==combos[0][2])
								{
									for(Cell f : cells)
									{
										if(f.loc.x==c.loc.x&&f.loc.y==c.loc.y-1)
										{
											Boolean wrong = false;
											for(Cell h : cells)
											{
												if(h.loc.x==e.loc.x&&h.loc.y==e.loc.y+1)
												{
													wrong = true;
												}
											}
											if(!wrong)
											{
												if(!f.usedInCombo&&checkControlled(c))
												{
													g2.drawImage(flagellum[0], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 72, null);
													c.setUsedInCombo(true);
													d.setUsedInCombo(true);
													e.setUsedInCombo(true);
													flagella++;
												}
												else
												{
													c.setUsedInCombo(false);
													d.setUsedInCombo(false);
													e.setUsedInCombo(false);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

			//Spike check
			for(Cell c: cells)
			{
				if(c.type == Type.BLUE)
				{
					for(Cell d : cells)
					{
						if(d.type == Type.BLUE&&d.loc.x==c.loc.x&&d.loc.y==c.loc.y+1)
						{
							for(Cell e : cells)
							{
								if(e.type == Type.BLUE&&e.loc.x==d.loc.x&&e.loc.y==d.loc.y+1)
								{
									g2.drawImage(spike, (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 72, null);
									c.setUsedInCombo(true);
									d.setUsedInCombo(true);
									e.setUsedInCombo(true);
								}
							}
						}
					}
				}
			}
			File f = new File("saves/image.png");
			File[] fs = new File[maxOthers];

			for(int i = 0; i<otherOrgs; i++){
				if(others[i].moves)
					fs[i] = new File("saves/other"+i+".png");
			}

			try{
				ImageIO.write(bi, "png", f);
				for(int i = 0; i<otherOrgs; i++)
					if(others[i].moves)
						ImageIO.write(bis[i], "png", fs[i]);
			}
			catch (IOException ex){
				ex.printStackTrace();
			}

			break;
		}
		}
	}

	public void keyPressed(KeyEvent ke)
	{
		if(ke.getKeyCode() == 38 || ke.getKeyCode() == 87)
		{
			if(flagella > 0 && glucose > 0 || cheat)
				forwards = true;
			else
				forwards = false;
		}
		else if(ke.getKeyCode() == 109)
		{
			cheat = true;
			glucose = 10000;
		}
		else if((ke.getKeyCode() == 37 || ke.getKeyCode() == 65)&& glucose > 0)
			rotation ++;
		else if((ke.getKeyCode() == 39 || ke.getKeyCode() == 68)&& glucose > 0)
			rotation --;
		else if(ke.getKeyChar() == 91)
			showCOM = true;
		else if(ke.getKeyChar() == 93)
			showCOM = false;
	}

	public void keyReleased(KeyEvent ke)
	{
		forwards = false;
	}

	public void keyTyped(KeyEvent ke){}
}
