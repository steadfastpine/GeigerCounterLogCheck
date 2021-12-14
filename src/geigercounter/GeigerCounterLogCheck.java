/*
# Author: Scott Forsberg
# Contact: contact.scottforsberg@gmail.com
# Release Date: 2019-08-03
# Version: .1


In the example log file, the following dates recorded the highest radiation counts, with default value of 5 variance from the highest:

2018-08-02        38
2018-08-17        38
2019-06-01        38

However, the following dates recorded the highest averages per day:

2019-05-32	22
2019-06-01	21
2018-08-18	19
2018-08-19	19
2018-08-14	19
2018-08-15	19
2018-08-16	19
2018-08-17	19
2018-08-21	19
2018-08-22	19
2018-08-23	19
2018-08-20	19
2018-08-13	19


- Accessing the Readings

Higher average daily values could indicate the geiger counter was moved to a greater elevation during that timeframe, or a region with increased exposure to radiation.

The absolute maxima readings (2018-08-02, 2018-08-17, 2019-06-01) did not occur on the date which recorded the highest average.


*/


package geigercounter;

import java.io.File;
import java.util.*;
import java.io.*;

public class GeigerCounterLogCheck {
	
	// instance data members
	int radiationMax;
	int radiationMin;
	int radiationSpikeVariance;
	String radiationMinDate;
	String radiationMaxDate;
	String logFileName;
	
	HashMap<String, Integer> mapCountPerDay = new HashMap<String, Integer>();
	HashMap<String, Integer> mapLogPerDay = new HashMap<String, Integer>();
	HashMap<String, Integer> mapAveragePerDay = new HashMap<String, Integer>();
	
	// constructor
	public GeigerCounterLogCheck(String inputLogFileName) {
	
		// set default values
		radiationMin=9999999;
		radiationMax=0;
		radiationSpikeVariance = 0;
		logFileName = inputLogFileName;
	
		// run the log parse at object creation
		parseLog();
	}
	
	
	// main method - example class usage
	public static void main(String[] args) {
		
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
	}
	
	
	// extract values from the log file
	public void parseLog() {
		
		// set local variables
		int logEntryRadiationLevel;
		String logEntryTimeStamp, logEntryDay;
		String[] logEntry;
		
		File logFile = new File(logFileName);
		
		try {
			Scanner scanner = new Scanner(logFile);
			
			while (scanner.hasNextLine()) {
			
			String line = scanner.nextLine();
			
			// populate logEntry array with the current entry
			logEntry=line.split(",");
			
			// extract csv comma delimited entries from the log line
			logEntryTimeStamp=logEntry[0];                
			logEntryRadiationLevel=Integer.parseInt(logEntry[2]);
			
			logEntryDay=logEntryTimeStamp.split(" ")[0];    
	
			
			// iterate a log entry count for each day
			if (mapLogPerDay.get(logEntryDay)==null) {
				mapLogPerDay.put(logEntryDay, 1);
			}else {
				int uU=mapLogPerDay.get(logEntryDay);
				mapLogPerDay.put(logEntryDay, 1+uU);               	
			}
	
	
			// iterate a total count for each day
			if (mapCountPerDay.get(logEntryDay)==null) {
				mapCountPerDay.put(logEntryDay, 1);
			}else {
				int uU=mapCountPerDay.get(logEntryDay);
				mapCountPerDay.put(logEntryDay, logEntryRadiationLevel+uU);               	
			}
	
	
			// set max radiation values
			if (radiationMax<logEntryRadiationLevel) {
				radiationMax=logEntryRadiationLevel;
				radiationMaxDate=logEntryTimeStamp;                	
			}
	
			
			// set min radiation values
			if (radiationMin>logEntryRadiationLevel) {
				radiationMin=logEntryRadiationLevel;
				radiationMinDate=logEntryTimeStamp;                	
			}
		}
	
		// close the scanner object
		scanner.close();
		
		// display error messages
		} catch (Exception e) {
			System.out.println(e);
		}
	
	
		// build day averages HashMap
		for (String i : mapLogPerDay.keySet()) {
			int dayAverage = mapCountPerDay.get(i) / mapLogPerDay.get(i);
			mapAveragePerDay.put(i, dayAverage);
		}	 
	}

	
	// print a table of radiation spikes within a given variance (default 5) of the maximum reading
	public void getRadiationSpikes() {
	
		// set local variables
		int radiationSpikeThreashold = radiationMax - radiationSpikeVariance;	
		int logEntryRadiationLevel;
		String logEntryDate, logEntryDay, logEntryTimeStamp;
		String[] logEntry;
	
		File logFile = new File(logFileName);
	
		try {
			Scanner scanner = new Scanner(logFile);
	
			String logEntryDateHeader, logEntryRadiationLevelHeader;
			logEntryDateHeader="Date";
			logEntryRadiationLevelHeader="Counts per Minute";
			
			// output header for radiation spike table
			System.out.format("%s%35s", logEntryDateHeader, logEntryRadiationLevelHeader);
			System.out.println();
			System.out.println();
	
			while (scanner.hasNextLine()) {
	
				String line = scanner.nextLine();
				
				// populate logEntry array with the current entry
				logEntry=line.split(",");
				
				logEntryTimeStamp=logEntry[0];                
				logEntryRadiationLevel=Integer.parseInt(logEntry[2]);
				
				logEntryDay=logEntryTimeStamp.split(" ")[0];                   
	
				
				if (radiationSpikeThreashold<=logEntryRadiationLevel) {
	
					System.out.format("%s%10s", logEntryDay, logEntryRadiationLevel);
					System.out.println();
				}
			}
	
			// close the scanner object
			scanner.close();
	
			// display error messages
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	// print the total radiation count per day
	public void getCountPerDay() {
		for (String i : mapCountPerDay.keySet()) {
			System.out.println("date: " + i + " count total: " + mapCountPerDay.get(i));
		}
	}
	
	
	// print the average radiation count per day
	public void getAveragePerDay() {
		for (String i : mapAveragePerDay.keySet()) {
			System.out.println("date: " + i + " average count: " + mapAveragePerDay.get(i));
		}
	}
	
	
	// print the number of logs taken each day
	public void getLogsPerDay() {
		for (String i : mapLogPerDay.keySet()) {
			System.out.println("date: " + i + " logs: " + mapLogPerDay.get(i));
		}
	}
	

	// print the maximum radiation reading
	public int getMax() {
		return radiationMax;
	}
	
	
	// print the maximum radiation reading date
	public String getMaxDate() {
		return radiationMaxDate;
	}
	
	
	// print the minimum radiation reading
	public int getMin() {
		return radiationMin;
	}
	
	
	// return the minimum radiation reading date
	public String getMinDate() {
			return radiationMinDate;
	}
	
	
	// return the current logfile name
	public String getLogFile() {
		return logFileName;
	}
	
	
	// set radiationSpikeVariance
	public void setRadiationSpikeVariance(int inputRadiationSpikeVariance) {
		radiationSpikeVariance=inputRadiationSpikeVariance;
	}
	
	
	// set setLogFile
	public void setLogFile(String inputLogFile) {
		logFileName=inputLogFile;
	}	
}