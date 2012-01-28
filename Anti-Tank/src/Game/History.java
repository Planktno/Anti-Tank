package Game;

public class History {
	
	private static int MAXIMUM_HISTORY = 8;
	
	public History() {
		//Look if a history.txt file exists
		//if not -> create one
	}
	
	public void addMatch(String str) {
		//check how many matches are already in the file
		//if below 8, just add str as a new line
		//if 8 or more, trim down to last 7 and add str as the 8th one
	}
	
	public String[] getMatches() {
		String[] str = null;
		//read matches from file
		//every line is one String in the Array str
		//return str
		return str;
	}

}
