import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class DirectoryWalker {
	
	public int skipped_count=0;
	public int valid_count=0;
	public boolean headerflag=false;
	public static Logger logger;
	public String date;
	
	public void walk( String path ) 
	{

        File root = new File( path );
        File[] list = root.listFiles();
        List<String> FileNames = new ArrayList<String>();
        
        if (list == null) 
        	return;
        
        for ( File f : list ) 
        {
            if ( f.isDirectory() ) 
            {
                walk( f.getAbsolutePath());
                
            }
            else 
            {		
                FileNames.add(f.getAbsolutePath().toString());
            
             } 
            
        }
        
        //System.out.println("File size: "+FileNames.size());
        CSVparser(FileNames);
		
    }
	
	
	
	public void CSVparser(List<String> Files) 
	{   
		//System.out.println("In CSV ");
		
		String header[]= {"Date","First Name", "Last Name", "Street Number", "Street", "City", "Province", "Country","Postal Code", "Phone Number", "email Address"};
	
		Reader in;
		
		
		try 
		{   
			for(String a: Files) 

		    {   int Recordflag=1;
		        int CFile = 1;
		        String s = null;
		        String data[]=new String[15] ;
		    	in = new FileReader(a);	
		    	Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		    	
		    	if(headerflag==false) 
				{
					System.out.println("Printing header and flag is :"+headerflag);
		    		Writer(header);
					//System.out.println(header[0]);
					headerflag=true;
				}
					for (CSVRecord record : records)
				
					{
						if(Recordflag==1) 
							{
								Recordflag++;
								continue;
							}
						
						String regex = ".*(\\d{4}\\\\\\d{1,2}\\\\\\d{0,2}).*";
						Matcher m = Pattern.compile(regex).matcher(a);
						//System.out.println(path);
						if (m.find()) 
						{
						   s = m.group(1);
						}
						
				        data[0]=s;
				    
				        for(int i =0; i < record.size(); i++)
				         	{ 
				        	 	data[i+1]=record.get(i);
				        	 	if(record.get(i).isEmpty()) 
				        	 		{      
				        	 			CFile=0;
				        	 			//skipped_count++;
				        	 			break;
				        	 			//logger.log(record.toString());
				        	 		}
				        	 }
					
					
				        if(CFile == 1)
				        {   
				        	Writer(data);
				        	//valid_count++;
				    		//System.out.println("Valid :"+valid_count);
				    	}
					
				        else 
				        {   
				        	skipped_count++;
				        	//System.out.println("Skipped :"+skipped_count);
				        }
				    			    
				        CFile = 1;
				  	
				}
				    					
		}// Recordflag=1;
		
	}
					
			    			    		   		
		catch ( IOException e) 
		{
			e.printStackTrace();
			logger.info(e.getMessage());
		}	

	}

	public void Writer(String data[] ) throws IOException {
		//System.out.println(data[0]);
		File file = new File("C:\\Users\\Meghashyam\\Documents\\GitHub\\A00432392_MCDA5510\\Assignment1\\Output\\result.csv");
	    CSVWriter writer = new CSVWriter(new FileWriter(file, true));		
		writer.writeNext(data);  
	    valid_count++;
	    //System.out.println("Valid count is: "+valid_count);
	    writer.close(); 
		
	}
    
	
	
	
	public static void logInit() {
		Handler consoleHandler = null;

		Handler fileHandler = null;
		Formatter simpleFormatter = null;
		
		
		 logger = Logger.getLogger(DirectoryWalker.class.getName());
		
		consoleHandler = new ConsoleHandler();
		try {
			fileHandler = new FileHandler("./logs/logfile.log");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		logger.addHandler(consoleHandler);
		logger.addHandler(fileHandler);
		// Setting levels to handlers and LOGGER
		//consoleHandler.setLevel(Level.ALL);
		//fileHandler.setLevel(Level.FINE);
		logger.setLevel(Level.ALL);
		
		
		simpleFormatter = new SimpleFormatter();
		
		// Setting formatter to the handler
		fileHandler.setFormatter(simpleFormatter);

		
		//logger.log(Level.INFO, "Execution time :"+a+ "ms");
		//logger.log(Level.SEVERE, "# of valid rows :"+b);
		//logger.log(Level.INFO, "# of skipped rows :"+c);

		
	}
	
		
	
    public static void main(String[] args) {
    	try {
    		logInit();
			final long startTime = System.currentTimeMillis();
			DirectoryWalker fw = new DirectoryWalker();
			
			fw.walk("C:\\Users\\Meghashyam\\Documents\\GitHub\\A00432392_MCDA5510\\Sample Data");
			final long endTime = System.currentTimeMillis();
			final long exec=endTime - startTime;
			
			//end time
			logger.info("Execution time :"+exec);
			logger.info("Number of valid rows :"+fw.valid_count);
			logger.info("Number of skipped rows :"+fw.skipped_count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
        
        
        
    }
    
    
    



}


