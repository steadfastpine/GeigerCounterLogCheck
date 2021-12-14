

          _____                    _____                    _____            _____          
         /\    \                  /\    \                  /\    \          /\    \         
        /::\    \                /::\    \                /::\____\        /::\    \        
       /::::\    \              /::::\    \              /:::/    /       /::::\    \       
      /::::::\    \            /::::::\    \            /:::/    /       /::::::\    \      
     /:::/\:::\    \          /:::/\:::\    \          /:::/    /       /:::/\:::\    \     
    /:::/  \:::\    \        /:::/  \:::\    \        /:::/    /       /:::/  \:::\    \    
   /:::/    \:::\    \      /:::/    \:::\    \      /:::/    /       /:::/    \:::\    \   
  /:::/    / \:::\    \    /:::/    / \:::\    \    /:::/    /       /:::/    / \:::\    \  
 /:::/    /   \:::\ ___\  /:::/    /   \:::\    \  /:::/    /       /:::/    /   \:::\    \ 
/:::/____/  ___\:::|    |/:::/____/     \:::\____\/:::/____/       /:::/____/     \:::\____\
\:::\    \ /\  /:::|____|\:::\    \      \::/    /\:::\    \       \:::\    \      \::/    /
 \:::\    /::\ \::/    /  \:::\    \      \/____/  \:::\    \       \:::\    \      \/____/ 
  \:::\   \:::\ \/____/    \:::\    \               \:::\    \       \:::\    \             
   \:::\   \:::\____\       \:::\    \               \:::\    \       \:::\    \            
    \:::\  /:::/    /        \:::\    \               \:::\    \       \:::\    \           
     \:::\/:::/    /          \:::\    \               \:::\    \       \:::\    \          
      \::::::/    /            \:::\    \               \:::\    \       \:::\    \         
       \::::/    /              \:::\____\               \:::\____\       \:::\____\        
        \::/____/                \::/    /                \::/    /        \::/    /        
                                  \/____/                  \/____/          \/____/         
                                                                                            


Geiger Counter Log Check



# Contact: https://www.linkedin.com/in/steadfastpine/

# Release Date: 2019-08-03
# Version: .1



Description

	Analyze Geiger counter log files. Determine the absolute minima and maxima count values, radiation spikes within a variance of the maximum value, the count of log files per day, the total count per day, and the average count values per day.



Prerequisites

	Java Virtual Machine



Methods

	// extract values from the log file
	public void parseLog()

	// print a table of radiation spikes within a given variance (default 5) of the maximum reading
	public void getRadiationSpikes()
	
	
	// print the total radiation count per day
	public void getCountPerDay() 
	
	
	// print the average radiation count per day
	public void getAveragePerDay()
	
	
	// print the number of logs taken each day
	public void getLogsPerDay()
	

	// print the maximum radiation reading
	public int getMax()
	
	
	// print the maximum radiation reading date
	public String getMaxDate()
	
	
	// print the minimum radiation reading
	public int getMin()
	
	
	// return the minimum radiation reading date
	public String getMinDate()
	
	
	// return the current logfile name
	public String getLogFile()
	
	
	// set radiationSpikeVariance
	public void setRadiationSpikeVariance(int inputRadiationSpikeVariance)
	
	
	// set setLogFile
	public void setLogFile(String inputLogFile)



Example Usage
	
		// create gclc object with the example filename 7_14_2019_short.csv
		GeigerCounterLogCheck gclc = new GeigerCounterLogCheck("7_14_2019_short.csv");
		
		System.out.println("\n# Display log file name");
		System.out.println(gclc.getLogFile());
		
		System.out.println("\n# Display maximum radiation reading");
		System.out.println(gclc.getMax());
		
		System.out.println("\n# Display maximum radiation reading date");
		System.out.println(gclc.getMaxDate());
		
		System.out.println("\n# Display minimum radiation reading");
		System.out.println(gclc.getMin());
		
		System.out.println("\n# Display minimum radiation reading date");
		System.out.println(gclc.getMinDate());
		
		System.out.println("\n# Display radiation spikes within default varience of 5");
		System.out.println();
		gclc.getRadiationSpikes();
		
		System.out.println("\n# Display log counts per day");
		gclc.getLogsPerDay();
		
		System.out.println("\n# Display log counts per day");
		gclc.getCountPerDay();
		
		System.out.println("\n# Display average count per day");
		gclc.getAveragePerDay();
		
		// Set new logfile in existing object
		//gclc.setLogFile("7_14_2019_short.csv");	
		
		// Reparse the logfile to extract min and max values
		//gclc.parseLog();	



License 

	This program is licensed under the GPL License, view the LICENSE.md file for more information.














