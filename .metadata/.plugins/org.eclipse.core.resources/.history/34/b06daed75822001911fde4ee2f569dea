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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import com.opencsv.CSVWriter;
import java.io.FileWriter;

public class DirectoryWalker {
	
	public int skipped_count=0;
	public int valid_count=0;
	public boolean headerflag=false;
	 
	
	public void walk( String path ) {

        File root = new File( path );
        File[] list = root.listFiles();

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
                
                CSVparser(f.getAbsolutePath());
                
                } 
            
        }
		
    }
	
	
	
	public void CSVparser(String path) {
		//System.out.println( "File:" +path);
		String data[]=new String[15] ;
		String header[]= {"First Name", "Last Name", "Street Number", "Street", "City", "Province", "Country","Postal Code", "Phone Number", "email Address"};
		Reader in;
		int flag=1;
		int iValidFile = 0;
		try {
			in = new FileReader(path);	
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			
			if(headerflag==false) {
				Writer(header);
				//System.out.println(header[0]);
				headerflag=true;
			}
			
			for (CSVRecord record : records)
				
				{
				  if(flag==1) 
				  {
					  flag++;
					  continue;
				   }
				  
					for(int i =0; i < record.size(); i++)
					{ 
						data[i]=record.get(i);
						if(record.get(i).isEmpty()) 
						{      
								iValidFile=1;
						}
						
						
				      }
					if(iValidFile == 0)
				    {
				    	Writer(data);	
				    }
					else {
						skipped_count++;
					}
				    			    
				  	iValidFile = 0;
				    					
				}
					
			    			    		    
				
			
		} catch ( IOException e) {
			e.printStackTrace();
		}	

	}

	public void Writer(String data[] ) throws IOException {
		//System.out.println(data[0]);
		File file = new File("C:\\\\Users\\\\Meghashyam\\\\Documents\\\\GitHub\\\\MCDA5510_Assignments\\\\result.csv");
		
			CSVWriter writer = new CSVWriter(new FileWriter(file, true));		
			writer.writeNext(data);  
			valid_count++;
	        writer.close(); 
		
	}
    
	
	
	
	
	
	
	
	
	public void logger(long a,int b, int c) {
		Handler consoleHandler = null;

		Handler fileHandler = null;
		Formatter simpleFormatter = null;
		
		
		Logger logger = Logger.getLogger(DirectoryWalker.class.getName());
		
		consoleHandler = new ConsoleHandler();
		try {
			fileHandler = new FileHandler("C:\\Users\\Meghashyam\\Documents\\GitHub\\MCDA5510_Assignments\\logfile.log");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		logger.addHandler(consoleHandler);
		logger.addHandler(fileHandler);
		// Setting levels to handlers and LOGGER
		consoleHandler.setLevel(Level.ALL);
		fileHandler.setLevel(Level.FINE);
		logger.setLevel(Level.ALL);
		
		
		simpleFormatter = new SimpleFormatter();
		
		// Setting formatter to the handler
		fileHandler.setFormatter(simpleFormatter);

		
		logger.log(Level.INFO, "Execution time :"+a+ "ms");
		logger.log(Level.INFO, "# of valid rows :"+b);
		logger.log(Level.INFO, "# of skipped rows :"+c);

		
	}
	
		
	
    public static void main(String[] args) {
    	final long startTime = System.currentTimeMillis();
    	DirectoryWalker fw = new DirectoryWalker();
    	
        fw.walk("C:\\Users\\Meghashyam\\Documents\\GitHub\\MCDA5510_Assignments\\Sample Data" );
        final long endTime = System.currentTimeMillis();
        final long exec=endTime - startTime;
        fw.logger(exec, fw.valid_count, fw.skipped_count);
        
        
        
    }
    
    
    



}


