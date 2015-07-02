package main;

//Imports
import javax.imageio.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Evopix extends JPanel implements MouseListener, ActionListener
{
	//Initialisers
	private JPanel pane;
    private BufferedImage[] palette = new BufferedImage[12];
    private BufferedImage highlight, glucoseImage;
    private BufferedImage[] flagellum = new BufferedImage[3];
    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private Type highlighted = Type.FLESH;
    private Timer t = new Timer(1000, this);
    private Timer t2 = new Timer(300, this);
    private int glucose = 0;
    private int[][] combos = new int[1][3];
    private Random rng = new Random();

	//Constructor
	public Evopix()
	{
		super(new BorderLayout());
		
	    try {                
	        palette[0] = ImageIO.read(new File("photosynthesis.jpg"));
	        palette[1] = ImageIO.read(new File("brain.jpg"));
	        palette[2] = ImageIO.read(new File("shell.jpg"));
	        palette[3] = ImageIO.read(new File("flesh.jpg"));
	        palette[4] = ImageIO.read(new File("yellow2.jpg"));
	        palette[5] = ImageIO.read(new File("purple2.jpg"));
	        palette[6] = ImageIO.read(new File("blue2.jpg"));
	        palette[7] = ImageIO.read(new File("red2.jpg"));
	        palette[8] = ImageIO.read(new File("yellow3.jpg"));
	        palette[9] = ImageIO.read(new File("purple3.jpg"));
	        palette[10] = ImageIO.read(new File("blue3.jpg"));
	        palette[11] = ImageIO.read(new File("red3.jpg"));
	        glucoseImage = ImageIO.read(new File("glucose.jpg"));
	        highlight = ImageIO.read(new File("highlight1.jpg"));
	        flagellum[0] = ImageIO.read(new File("flagellum.jpg"));
	        flagellum[1] = ImageIO.read(new File("flagellum2.jpg"));
	    } 
	    catch (Exception e)
	    {
	        System.err.println();
	    }
		
		pane = new MainPane();
		pane.setBackground(Color.white);
		pane.setPreferredSize(new Dimension(720, 480));
		pane.addMouseListener(this);
		add(pane, BorderLayout.CENTER);
		
		cells.add(new Cell(true, true, new Coordinate(0, 0), Type.BRAIN, 0));
		cells.add(new Cell(true, true, new Coordinate(0, 1), Type.PHOTOSYNTHESIS, 0));
		cells.add(new Cell(true, true, new Coordinate(1, 0), Type.FLESH, 0));
		
		combos[0][0] = 4;
		combos[0][1] = rng.nextInt(4) + 4;
		combos[0][2] = rng.nextInt(4) + 4;
		
		pane.repaint();
		
		//Start update clocks
		t.setActionCommand("t");
		t.start();
		t2.setActionCommand("t2");
		t2.start();
	}
	
	public int getMaxGlucose()
	{
		int fCells = 0;
		for(Cell c : cells)
			if(c.type == Type.FLESH)
				fCells++;
		return fCells * 10;
	}
		
	public int getGlucoseInc()
	{
		int psCells = 0;
		for(Cell c : cells)
			if(c.type == Type.PHOTOSYNTHESIS)
				psCells++;
		return psCells;
	}
	
	public int getGlucoseProfit()
	{
		int inc = getGlucoseInc();
		int cost = 0;
		for(Cell c : cells)
			cost += c.energyUsed;
		
		return inc - cost;
	}

	//Updates info pane
	public class MainPane extends JPanel 
	{
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
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
											if(!f.usedInCombo)
											{
												g.drawImage(flagellum[0], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 72, null);
												c.usedInCombo = true;
												d.usedInCombo = true;
												e.usedInCombo = true;
											}
											else
											{
												c.usedInCombo = false;
												d.usedInCombo = false;
												e.usedInCombo = false;
											}
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
		}
	}

	//Create gui
	private static void createGUI() 
	{
		JFrame frame = new JFrame("Evopix");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		
		int x = (me.getX() - pane.getWidth() / 2)<0? (me.getX() - pane.getWidth() / 2) / 24 - 1: (me.getX() - pane.getWidth() / 2) / 24;
		int y = (me.getY() - pane.getHeight() / 2)<0? (me.getY() - pane.getHeight() / 2) / 24 - 1: (me.getY() - pane.getHeight() / 2) / 24;
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
		else if(glucose >= 10)
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
				cells.add(new Cell(true, true, new Coordinate(x, y), highlighted, 0));
				glucose -= 10;
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
			pane.repaint();
			break;
		}
		case "t2":
		{
			//Flip flagella
			flagellum[2] = flagellum[0];
			flagellum[0] = flagellum[1];
			flagellum[1] = flagellum[2];
			pane.repaint();
			break;
		}
		}
	}
}
