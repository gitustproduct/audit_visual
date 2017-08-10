import java.awt.BorderLayout;  
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon; 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JPanel; 
import javax.swing.JToggleButton; 
import javax.swing.Timer; 

public class ImageAnimationPanel extends JPanel 
{ 
	private ImageIcon fIcon;
	private int ListIndex; 
	private JLabel fIconLabel; 
	private Timer fTimer; 
	private ActionListener fImageCycler; 
	private File directory = new File("audit_pictures");
	private List<String> list = new ArrayList<>();
	
	public ImageAnimationPanel(int timing) 
	{ 
		for(File f : directory.listFiles()) 
			if(f.isFile())
						list.add(f.getPath());
		Collections.sort(list);
		fIconLabel = new JLabel(); 
		add(fIconLabel); 
		fImageCycler = new ImageCycler(); 
		fTimer = new Timer(timing, fImageCycler); 
	} 
	
	public void startAnimation() 
	{ 
		fTimer.start(); 
	} 
	
	public void stopAnimation() 
	{ 
		fTimer.stop(); 
	}
	
	// inner class 
	class ImageCycler implements ActionListener 
	{ 
		@Override 
		public void actionPerformed(ActionEvent e) 
		{
			fIcon=new ImageIcon(list.get(ListIndex));
			//image resizing
			Image image = fIcon.getImage(); 
			Image newimg = image.getScaledInstance(690, 490,  java.awt.Image.SCALE_SMOOTH);
			fIcon = new ImageIcon(newimg);
			//setting new image into display
			fIconLabel.setIcon(fIcon); 
			ListIndex = (++ListIndex) % list.size(); 
		} 
	} 
	 
	public static void main(String[] args) 
	{ 
		EventQueue.invokeLater(new Runnable() 
		{ 
			@Override
			public void run() 
			{ 
				ImageAnimationPanel panel = new ImageAnimationPanel(1000); 
				JFrame f = new DemoFrame(panel); 
				f.setSize(800,600); 
				f.setVisible(true); 
			} 
		}); 
	
	} 
} 

class DemoFrame extends JFrame 
{ 
	ImageAnimationPanel fImagePanel; 	
	public DemoFrame(ImageAnimationPanel images) 
	{ 
		fImagePanel = images; 
		getContentPane().add(fImagePanel); 
		final JToggleButton play = new JToggleButton("Play"); 
		play.addActionListener(new ActionListener() 
		{ 
			@Override 
			public void actionPerformed(ActionEvent e) 
			{ 
				boolean state = play.isSelected(); 
				if (state) 
				{ 
					play.setText("Stop"); 
					fImagePanel.startAnimation(); 
				} 
				else 
				{ 
					play.setText("Play"); 
					fImagePanel.stopAnimation(); 
				} 
			} 
		}); 
		JPanel buttonPanel = new JPanel(); 
		buttonPanel.add(play); 
		getContentPane().add(buttonPanel, BorderLayout.SOUTH); 
	} 
}