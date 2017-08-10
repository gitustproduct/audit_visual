package project;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;

import javax.imageio.ImageIO;


 public class ImageGenerator {
 

public String QUESTIONS[]=new String[51];
public String OPTIONS[][]=new String[51][5];
public ImageGenerator()throws Exception 
{
	{
		BufferedReader reader = new BufferedReader(new FileReader("D:\\questions.txt"));
		int i=1,option=1,fno=0;
		while (true) 
		{
			String line = reader.readLine();
			if (line == null) 
			{
				break;
			}
			String[] feilds = line.split(",");
			for (String feild : feilds) 
			{
				if(fno==0)
						QUESTIONS[i]=feild;
				else if(fno==1)
						OPTIONS[i][1]=feild;
				else if(fno==2)
						OPTIONS[i][2]=feild;
				else if(fno==3)
						OPTIONS[i][3]=feild;
				else
						OPTIONS[i][4]=feild;

				if(fno==4)
					fno=0;
				else 
					fno++;
			}
			i++;
		}
		reader.close();
	}

}


public static void CreateImage(int n,String texts[],Point points[],String url,String name) throws Exception
{
String new_name=name+".jpg";
   final BufferedImage image = ImageIO.read(new URL(url));
	File directory = new File("audit_pictures").getAbsoluteFile();
	if (directory.exists() )
   {
		System.setProperty("user.dir", directory.getAbsolutePath());
	}
	Graphics g = image.getGraphics();
	g.setColor(Color.BLACK);
	g.setFont(g.getFont().deriveFont(20f));
	for(int i=0;i<n;i++)
	{
	    g.drawString(texts[i], points[i].x, points[i].y);
	}
 	g.dispose();
 	ImageIO.write(image, "jpeg", new File(new_name).getAbsoluteFile());
}
}