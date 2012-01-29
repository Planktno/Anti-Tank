package Game;

import java.io.*;


public class History {
	
	private static final int MAXIMUM_HISTORY = 8;
	private static final String FILEPATH = "data/history.txt";
	
	
	public History() throws IOException {
		File file = new File(FILEPATH);
		boolean exists = file.exists(); //Look if a history.txt file exists
		if (!exists) file.createNewFile(); //if not -> create one
	}
	
	public void addMatch(String str) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(FILEPATH))));
		
		int nummatches = 0;
		String temp;
		String[] matches = new String[MAXIMUM_HISTORY];
		while((temp = in.readLine()) != null) {
			matches[nummatches] = temp;
			nummatches++; //check how many matches are already in the file 
		}
		
		in.close();
		
		if (nummatches == 0){ //special case when file is empty
			BufferedWriter out = new BufferedWriter(new FileWriter(FILEPATH,true));
			out.write(str); 
			out.close();
	    } else if (nummatches < MAXIMUM_HISTORY){ //if below 8, just add str as a new line
			BufferedWriter out = new BufferedWriter(new FileWriter(FILEPATH,true));
			out.write("\n"+str); 
			out.close();
		} else { // if 8 or more, trim down to last 7 and add str as the 8th one
			File file = new File(FILEPATH);
			file.delete();
			file.createNewFile(); //clear file
			BufferedWriter out = new BufferedWriter(new FileWriter(FILEPATH,true));
			for (int i = 1; i < MAXIMUM_HISTORY; i++) out.write(matches[i]+"\n"); //replace last 7 matches
			out.write(str); //add new match
			out.close();
		}
		
	}
	
	public String[] getMatches() throws IOException {
		String[] str = new String[MAXIMUM_HISTORY];
		BufferedReader in = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(FILEPATH)))); // read matches from file
		for (int i = 0; i < MAXIMUM_HISTORY; i++) str[i] = in.readLine(); //every line is one String in the Array str
		return str;
	}

}
