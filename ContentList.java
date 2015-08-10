
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class ContentList  {
	String direct;
	char flag; 
	File directory; 
	File [] files;
	Date d;
	
	public ContentList(String[] args) {
		
		boolean con = true;
		
		if(args.length==4){	//handle two much input/too many flags
			printUsage();
			System.exit(1);
		}
		
		if (args.length == 1){
			flag = ' ';
			direct = System.getProperty("user.dir");
			directory = new File(direct);
			con = false;
		}
		
		if(con == true && args[1].charAt(0)=='-'){//case in which there is no specified directory 
			direct = "";			//blank directory value indicates default, as to allow for a specified directory named default
			
			if (args.length == 3){	//handle two much input/too many flags or wrong order of inputs
				printUsage();
				System.exit(1);
			}
			
			if (args[1].charAt(1) == 'a' || args[1].charAt(1) == 'l' || args[1].charAt(1) == 's'){
				flag = args[1].charAt(1);
				direct = System.getProperty("user.dir");
				directory = new File(direct);
			}

			else {					//no specified directory and wrong flag -> printusage and exit
				printUsage();
				System.exit(1);
			}
		}
		else if (con == true){	//case in which directory name is specified 
			direct = args[1];
			directory = new File (direct);
			if (!directory.isDirectory()){//take care of invalid directory
				System.out.println("Invalid directory name\n");
				printUsage();
				System.exit(1);
			}
			
			if(args[2].charAt(0) == '-'){
				if (args[2].charAt(1) == 'a' || args[2].charAt(1) == 'l' || args[2].charAt(1) == 's'){
					flag = args[2].charAt(1);
				}
				else{	//if directory is specified but flag char is wrong 
					printUsage();
					System.exit(1);
				}
			}
			else {	//if flag is inputed in a wrong manner
				printUsage();
				System.exit(1);
			}
	
		}
		
		files = directory.listFiles();
	     /*  for (int i = 0; i < files.length; i++) {
	   			d = new Date(files[i].lastModified());
	            System.out.println(files[i].getName() + " " + d.getDate());
	        }*/
		sort(files);
        for (int i = 0; i < files.length; i++)
        {
        	d = new Date(files[i].lastModified());
        	DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        	String formatted = format.format(d);
            System.out.println( files[i].length() + "     " + formatted + "     " +files[i].getName() );
        }
	}
	
	

	public void sort (File [] files){ //order files w respective tags
		if(flag== 'a'){
			Arrays.sort(files, new AlphaCompare());
		}
		if (flag == 's'){
			Arrays.sort(files, new LengthCompare());
		}
		if (flag == 'l'){
			Arrays.sort(files, new DateCompare());
		}
	}
	
	public void format (File f){	//format indivisual file to resemble desired output 
		
	}
	
	public void printUsage(){
		System.out.println("usage:  java -jar hw4.jar [directory] [-a | -l | -s]\n(current directory is default)");
		System.out.println("-a alphabetical sorting\n-l last time modified sorting\n-s file size");
	}

	
	public static void main(String[] args) {
		ContentList norm = new ContentList(args); 
	}

}

class AlphaCompare implements Comparator<File>
{
   public int compare(File f1, File f2)
   {
      return (f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase()));
   }
}

class LengthCompare implements Comparator<File>
{
   public int compare(File f1, File f2)
   {
      return (int) (f1.length() - f2.length());
   }
}

class DateCompare implements Comparator<File>
{
   public int compare(File f1, File f2)
   {
   return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
   }
}
