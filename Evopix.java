//Imports
import javax.imageio.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
//import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Evopix extends JPanel implements MouseListener, KeyListener, ActionListener
{
	//Initialisers
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
	private BufferedImage highlight, glucoseImage, bubbleImage, popImage, spike/*, background*/;
	private Bubble[] bubs = new Bubble[bubNum];
	private int flagella = 0;
	private int leftFlagella = 0;
	private int rightFlagella = 0;
	private boolean forwards = false;
	private int offset = 0;
	private int rotateOffset = 0;
	private Background[] bgs = new Background[375];
	private BufferedImage[] bg1 = new BufferedImage[7];
	private BufferedImage[] bg2 = new BufferedImage[4];
	private BufferedImage[] bg3 = new BufferedImage[4];
	private double brainLevel = 1;

	//Constructor
	public Evopix()
	{
		super(new BorderLayout());

		try
		{
			//Import images
			menuImages[0] = ImageIO.read(new File("res/images/menuNew.jpg"));
			menuImages[1] = ImageIO.read(new File("res/images/menuLoad.jpg"));
			palette[0] = ImageIO.read(new File("res/images/photosynthesis.jpg"));
			palette[1] = ImageIO.read(new File("res/images/brain.jpg"));
			palette[2] = ImageIO.read(new File("res/images/shell.jpg"));
			palette[3] = ImageIO.read(new File("res/images/flesh.jpg"));
			palette[4] = ImageIO.read(new File("res/images/yellow2.jpg"));
			palette[5] = ImageIO.read(new File("res/images/purple2.jpg"));
			palette[6] = ImageIO.read(new File("res/images/blue2.jpg"));
			palette[7] = ImageIO.read(new File("res/images/red2.jpg"));
			palette[8] = ImageIO.read(new File("res/images/yellow3.jpg"));
			palette[9] = ImageIO.read(new File("res/images/purple3.jpg"));
			palette[10] = ImageIO.read(new File("res/images/blue3.jpg"));
			palette[11] = ImageIO.read(new File("res/images/red3.jpg"));
			glucoseImage = ImageIO.read(new File("res/images/glucose.jpg"));
			highlight = ImageIO.read(new File("res/images/highlight1.jpg"));
			flagellum[0] = ImageIO.read(new File("res/images/flagellum.jpg"));
			flagellum[1] = ImageIO.read(new File("res/images/flagellum2.jpg"));
			bubbleImage = ImageIO.read(new File("res/images/bubbleBlue.jpg"));
			popImage = ImageIO.read(new File("res/images/popBlue.jpg"));
			spike = ImageIO.read(new File("res/images/spike.jpg"));
			//background = ImageIO.read(new File("res/images/background2.jpg"));
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

		//GUI
		pane = new MainPane();
		pane.setBackground(new Color(188, 205, 255));
		pane.setPreferredSize(new Dimension(720, 480));
		pane.addMouseListener(this);
		pane.addKeyListener(this);
		pane.setFocusable(true);
		add(pane, BorderLayout.CENTER);
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

	// public void generateCombo(Random randGen) {
	// 	int
	// }

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
				/*AffineTransform trans = new AffineTransform();

				for (int i = -50; i < 49; i++)
				{
					for (int j = -50; j < 49; j++)
					{
						trans.setTransform(new AffineTransform());
						trans.translate(0, offset);
						trans.rotate(Math.toRadians(rotateOffset));
						trans.translate(750 + i * 3000, 750 + j * 3000);
						g2d.drawImage(background, trans, this);
						g.drawImage(background, 750 + i * 3000, 750 + j * 3000 + offset, 3000, 3000, null);
					}
				}*/
				AffineTransform trans = new AffineTransform();
				//Background
				for (int i = 0; i < bgs.length; i++)
				{
					
					trans.setTransform(new AffineTransform());
					trans.setToTranslation(bgs[i].x, bgs[i].y + offset);
					trans.rotate(Math.toRadians(bgs[i].rot));
					switch(bgs[i].level)
					{
					case 1: g2d.drawImage(bg1[bgs[i].num], trans, this); g.drawImage(bg1[bgs[i].num], bgs[i].x, bgs[i].y + offset, bgs[i].xsiz, bgs[i].ysiz, null); break;
					case 2: g2d.drawImage(bg2[bgs[i].num], trans, this); g.drawImage(bg2[bgs[i].num], bgs[i].x, bgs[i].y + offset, bgs[i].xsiz, bgs[i].ysiz, null); break;
					case 3: g2d.drawImage(bg3[bgs[i].num], trans, this); g.drawImage(bg3[bgs[i].num], bgs[i].x, bgs[i].y + offset, bgs[i].xsiz, bgs[i].ysiz, null); break;
					}
				}
				
				//Bubbles
				//if(!forwards)
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
				g.drawString("Glucose: "+glucose+"/"+getMaxGlucose()+" ("+getGlucoseProfit()+"/s)", pane.getWidth() - 188, 15);

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
				leftFlagella = 0;
				rightFlagella = 0;
				brainLevel = 1;

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
														g.drawImage(flagellum[0], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 72, null);
														c.setUsedInCombo(true);
														d.setUsedInCombo(true);
														e.setUsedInCombo(true);
														flagella++;
														if(c.loc.x > getCentreOfMass().x)
															rightFlagella++;
														if(c.loc.x < getCentreOfMass().x)
															leftFlagella++;
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
									if(e.type == Type.BLUE&&e.loc.x==d.loc.x&&e.loc.y==d.loc.y)
									{
										g.drawImage(spike, (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 72, null);
										System.out.println((pane.getWidth() / 2)+(24*c.loc.x));
										c.setUsedInCombo(true);
										d.setUsedInCombo(true);
										e.setUsedInCombo(true);
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

				//Organism
				for(Cell c : cells)
				{
					if(!c.usedInCombo)
					{
						g.drawImage(palette[c.iType], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 24, null);
					}
					else
					{
						g.drawImage(palette[c.iType+4], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 24, null);
					}
				}

				saveGame();
			}
			else
			{
				g.drawImage(menuImages[0], 288, 167, null);
				g.drawImage(menuImages[1], 288, 239, null);
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
			if(glucose>getMaxGlucose())
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
				bgs[i].act(offset);
			pane.repaint();
			break;
		}
		case "t4":
		{
			//Animate movement
			if(forwards)
			{
				for (int i = 0; i < bubNum; i++)
					bubs[i].y += 48*flagella/cells.size();
				offset+=48*flagella/cells.size();
				rotateOffset += (rightFlagella - leftFlagella)/2;
			}
			if(offset > 5500)
				offset = -5500;
			pane.repaint();
			break;
		}
		}
	}

	public void keyPressed(KeyEvent ke)
	{
		if(flagella > 0 && glucose > 0) //&& flagella * 7 >= cells.size())
			forwards = true;
		else
			forwards = false;
	}

	public void keyReleased(KeyEvent ke)
	{
		forwards = false;
	}

	public void keyTyped(KeyEvent ke){}
}
