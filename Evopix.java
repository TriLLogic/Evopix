//Imports
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Evopix extends JPanel implements MouseListener
{
	//Initialisers
	private JPanel pane;
    private BufferedImage[] palette = new BufferedImage[8];
    private ArrayList<Cell> cells = new ArrayList<Cell>();

	//Constructor
	public Evopix()
	{
		super(new BorderLayout());
		//Init gui
	    try {                
	        palette[0] = ImageIO.read(new File("photosynthesis.jpg"));
	        palette[1] = ImageIO.read(new File("brain.jpg"));
	        palette[2] = ImageIO.read(new File("shell.jpg"));
	        palette[3] = ImageIO.read(new File("flesh.jpg"));
	        palette[4] = ImageIO.read(new File("yellow.jpg"));
	        palette[5] = ImageIO.read(new File("purple.jpg"));
	        palette[6] = ImageIO.read(new File("blue.jpg"));
	        palette[7] = ImageIO.read(new File("red.jpg"));
	    } catch (Exception e) {
	        System.err.println();
	    }
		
		pane = new MainPane();
		pane.setBackground(Color.white);
		pane.setPreferredSize(new Dimension(800, 500));
		pane.addMouseListener(this);
		add(pane, BorderLayout.CENTER);
		
		cells.add(new Cell(true, true, new Coordinate(0, 0), Type.BRAIN));
		cells.add(new Cell(true, true, new Coordinate(0, 1), Type.PHOTOSYNTHESIS));
		
		pane.repaint();
	}

	//Updates info pane
	public class MainPane extends JPanel 
	{
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			//Palette
			g.drawImage(palette[0], pane.getWidth() - 48, pane.getHeight() - 96, 24, 24, null);
			g.drawImage(palette[1], pane.getWidth() - 24, pane.getHeight() - 96, 24, 24, null);
			g.drawImage(palette[2], pane.getWidth() - 48, pane.getHeight() - 72, 24, 24, null);
			g.drawImage(palette[3], pane.getWidth() - 24, pane.getHeight() - 72, 24, 24, null);
			g.drawImage(palette[4], pane.getWidth() - 48, pane.getHeight() - 48, 24, 24, null);
			g.drawImage(palette[5], pane.getWidth() - 24, pane.getHeight() - 48, 24, 24, null);
			g.drawImage(palette[6], pane.getWidth() - 48, pane.getHeight() - 24, 24, 24, null);
			g.drawImage(palette[7], pane.getWidth() - 24, pane.getHeight() - 24, 24, 24, null);
			
			//Organism
			for(Cell c : cells)
			{
				g.drawImage(palette[c.iType], (pane.getWidth() / 2)+(24*c.loc.x), (pane.getHeight() / 2)+(24*c.loc.y), 24, 24, null);
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

	public void mouseClicked(MouseEvent arg0){}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
}
