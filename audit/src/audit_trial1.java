/********************************************************
AIM : AUDIT TRIAL VISUALIZATION
AUTHOR : ESTHER BABY
********************************************************/
import project.*;
import java.util.*;
import java.util.Calendar;
import java.awt.Point;
import java.io.*;
import java.text.SimpleDateFormat;



public class audit_trial1 {
    public static void main (String[] args) throws Exception {
String texts[]=new String[500];
Point points[]=new Point[500];
ImageGenerator I=new ImageGenerator();
int n=0,lines_no=0;
int [] questions=new int[75];
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
Date starttime,endtime;
Calendar myCal = new GregorianCalendar();
Scanner input = new Scanner(System.in);
for(int i=0;i<500;i++)
{
	points[i]=new Point();
	points[i].y=0;
	points[i].x=0;
}

//output folder creation
File dir = new File("audit_pictures");
dir.mkdir();
for(File file:dir.listFiles())
{
	file.delete();
}

// Open audit trail text file.
BufferedReader reader = new BufferedReader(new FileReader("D:\\sample.txt"));


while (true) 
{
	String liness = reader.readLine();
	if (liness == null) 
	{
		break;
	}
	String[] lines = liness.split("\\),\\(");
	for (String line : lines) 
	{
			/* If the audit trail is meant to fetch the answers then the counter begins.
			 * so starttime is initialized with time stamp given in audit trail.
			 * End time of exam is calculated by the exam duration with start time*/ 
			if(line.contains("Get All Answers USER ID:"))//exam beginning-timer initializing-reading start time
			{
				System.out.println("\n"+line);
				starttime=simpleDateFormat.parse(line.substring(line.indexOf("-")-4,line.lastIndexOf(":")+3));
				myCal.setTime(starttime);
				myCal.set(Calendar.SECOND,Integer.parseInt(line.substring(line.lastIndexOf(":")+1,line.lastIndexOf(":")+3)));
				System.out.println("start date : "+myCal.getTime());
				myCal.add(Calendar.HOUR,1);
				System.out.println("ending time calculated : "+myCal.getTime());
			}
			else if(line.contains("Email Examinee Score USER ID:"))
			{
				n=1;
				points[0].x=90;
				points[0].y=525;
				texts[0]=line.substring(line.indexOf("EmailId:")+9,line.length()-23);
				System.out.println("\nEmail id "+texts[0]);
				lines_no++;
				String imgname;
				imgname=String.format("%03d",lines_no);
				ImageGenerator.CreateImage(n,texts,points,"file:////D:/ESTHER/audit/src/submit.png",imgname);
				n=0;
			}
	
			else
			{
				String[] parts = line.split(",");
				for (String part : parts) 
				{
					if(part.contains("Valid USER ID="))//login successful entry
					{
						for(int k=0;k<75;k++)
							questions[k]=0;
						n=2;
						points[0].x=355;
						points[0].y=312;
						points[1].x=355;
						points[1].y=428;
						texts[0]=part.substring(part.indexOf("=") + 1, part.length()-1);
						texts[1]="******";
						lines_no++;
						String imgname;
						imgname=String.format("%03d",lines_no);
						ImageGenerator.CreateImage(n,texts,points,"file:////D:/ESTHER/audit/src/login.jpeg",imgname);
						n=0;
					}
	
					if(part.contains("Submit Qno= "))
					{
	
						if(n!=0)
							n=n-8;
						String str_question=part.substring(part.indexOf("= ") + 1, part.indexOf("Ans=")-1);
						String str_answer=part.substring(part.indexOf("Ans=") + 4, part.indexOf("Ans=")+5);
						int int_question=Integer.parseInt(str_question.trim());
						int int_answer=Integer.parseInt(str_answer.trim());
						texts[n]="✔";
						points[n].x=int_question*47;
						if(int_question>25)
						points[n].x=points[n].x-(25*47);
						if(int_question>25)
						points[n].y=315;
						else
						points[n].y=230;
						n++;
	
						//place questions
						texts[n]=I.QUESTIONS[int_question];
						points[n].x=80;
						points[n].y=510;
						n++;
	
						//place options
						texts[n]=I.OPTIONS[int_question][1];
						points[n].x=120;
						points[n].y=625;
						n++;
						texts[n]=I.OPTIONS[int_question][2];
						points[n].x=120;
						points[n].y=690;
						n++;
						texts[n]=I.OPTIONS[int_question][3];
						points[n].x=120;
						points[n].y=755;
						n++;
						texts[n]=I.OPTIONS[int_question][4];
						points[n].x=120;
						points[n].y=820;
						n++;
						switch(int_answer)
						{
							case 1:
							{
								texts[n]="*";
								points[n].x=92;
								points[n].y=629;
								n++;
								break;
							}
							case 2:
							{
								texts[n]="*";
								points[n].x=92;
								points[n].y=690;
								n++;
							break;
							}
							case 3:
							{
								texts[n]="*";	
								points[n].x=92;
								points[n].y=758;
								n++;
								break;
							}
							case 4:
							{					
								texts[n]="*";
								points[n].x=92;
								points[n].y=820;
								n++;
								break;
							}
						}
	
					/*Remaining time extraction
					 *Form current timestamp the endtime is subtracted to find the remaining time.
					 *Its found by converting both current and end time into millisecs.
					 */
					Calendar myCal2 = new GregorianCalendar();
					endtime=simpleDateFormat.parse(line.substring(line.indexOf("-")-4,line.lastIndexOf(":")+3));
					myCal2.setTime(endtime);
					myCal2.set(Calendar.SECOND,Integer.parseInt(line.substring(line.lastIndexOf(":")+1,line.lastIndexOf(":")+3)));
					long timer=myCal.getTimeInMillis()-myCal2.getTimeInMillis();
					long sec=(timer/1000)%60;
					System.out.print(sec+"  ");
					long min=(timer/(1000*60))%60;
					long hour=(timer/(1000*60*60))%60;
					String temp_timer_hour,temp_timer_min,temp_timer_sec;
					if(hour<10)
						temp_timer_hour="0"+hour;
					else
						temp_timer_hour=String.valueOf(hour);
					if(min<10)
						temp_timer_min="0"+min;
					else
						temp_timer_min=String.valueOf(min);
					if(sec<10)
						temp_timer_sec="0"+sec;
					else
						temp_timer_sec=String.valueOf(sec);
					texts[n]=temp_timer_hour+":"+temp_timer_min+":"+temp_timer_sec;				
					points[n].x=1100;
					points[n].y=380;				
					n++;
					texts[n]="QUESTION NO : "+str_question;
					points[n].x=80;
					points[n].y=380;
					n++;
					lines_no++;
					String imgname;
					imgname=String.format("%03d",lines_no);
					ImageGenerator.CreateImage(n,texts,points,"file:////D:/ESTHER/audit/src/question.png",imgname);;
					}
				}
			}	
	}
}
reader.close();

//calling ffmpeg through commandline for mp4 video creation
Runtime.getRuntime().exec("ffmpeg -r 1 -i audit_pictures/%3d.jpg -s 1000*750 audit_pictures/output.mp4");	
System.out.println();
System.out.println(lines_no+" images and video created.\nDo you want to save these files for further use? Press Y for yes");
String choice=input.nextLine();
switch(choice)
{
	case "Y":
	{
		System.out.println("Enter the folder name :");
		String fname=input.nextLine();
		File newName = new File(fname);
		dir.renameTo(newName);
	}

	case "y":
	{
		System.out.println("Enter the folder name :");
		String fname=input.nextLine();
		File newName = new File(fname);
		dir.renameTo(newName);
	}
}
input.close();
}
}
