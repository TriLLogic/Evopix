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
    private BufferedImage palette;
    private ArrayList<Cell> cells = new ArrayList<Cell>();

	//Constructor
	public Evopix()
	{
		super(new BorderLayout());
		//Init gui
	    try {                
	        palette = ImageIO.read(new File("palette.png"));
	    } catch (IOException ex) {
	        // handle exception...
	    }
		
		pane = new MainPane();
		pane.setBackground(Color.white);
		pane.setPreferredSize(new Dimension(800, 500));
		pane.addMouseListener(this);
		add(pane, BorderLayout.CENTER);
		
		cells.add(new Cell(true, true, new Coordinate(0, 0), Type.BRAIN));
		cells.add(new Cell(true, true, new Coordinate(0, 1), Type.PHOTOSYNTHESIS));
	}

	//Updates info pane
	public class MainPane extends JPanel 
	{
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			g.drawImage(palette, pane.getWidth() - 32, pane.getHeight() - 64, 32, 64, null);
		}
	}

	//Create gui
	private static void createGUI() 
	{
		JFrame frame = new JFrame("Pokemon");
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
