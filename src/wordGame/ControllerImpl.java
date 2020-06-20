package wordGame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringBuilder;

/**
*  @author Pooja Renganathan, Sasha Devi-Bangar, Shivani Mohan
*  @version 12/10/19
*/


public class ControllerImpl implements Controller{
	
	boolean playedOnce = false;
	String validWords = "";
	int random;
	ArrayList<Character> tileRack = new ArrayList<>();
	final int allLetters = 25;
	char[][] board = 
		{{'_','_','_','_','_','_','_','_','_','_',},
		 {'_','_','_','_','+','+','_','_','_','_',},
		 {'_','_','_','_','_','_','_','_','_','_',},
		 {'_','_','_','+','_','_','+','_','_','_',},
		 {'_','_','_','_','_','_','_','_','_','_',},
		 {'_','_','_','_','_','_','_','_','_','_',},
		 {'_','_','_','+','_','_','+','_','_','_',},
		 {'_','_','_','_','_','_','_','_','_','_',},
		 {'_','_','_','_','+','+','_','_','_','_',},
		 {'_','_','_','_','_','_','_','_','_','_',} 
		};
	
			
	/* 	Return the contents of the player's tile rack
 		as a String object 
 	*/
	public String refillRack() {
		
		ArrayList<Character> letters = new ArrayList<>();
		
		// adding alphabet to ArrayList letters
		for(char c = 'A'; c <= 'Z'; ++c) { 
			letters.add(c);
		}
		
		// generate random letters to fill the tile rack when size is not equal to 5
		 while(tileRack.size()!=5){  
			 random = getRandom(letters.size());	  
			 tileRack.add(letters.get(random));
			  
		   }
		  
		 // After play, set position in tile to null and then refill played tiles with randomly generated letters 
		  for (int i = 0; i < tileRack.size(); i++) {		
				if (tileRack.get(i) == null){
					random = getRandom(letters.size());
					tileRack.set(i, letters.get(random));
			   }  
		   }
		   return "Tile rack: " + tileRack.toString();    
	}
	
	
	// generates a random index to obtain a random letter from the alphabet
	private int getRandom(int size) {
		Random random = new Random();
		return random.nextInt(allLetters);
	}

	// creates 10x10 game board
	public void gameBoard() {
		
		System.out.print("\n    A B C D E F G H I J \n");
		System.out.println("    _ _ _ _ _ _ _ _ _ _");
					
			for(int x = 0; x < 10; x++)
			{		
				if (x == 9) {
					System.out.print((x + 1) + " |");
				}
				else {
					System.out.print((" " + (x + 1)) + " |");
				}
				for(int y = 0; y < 10; y++)
					
				{	
					System.out.print(board[x][y]);
					System.out.print("|");	
					if (y == 9) {
						System.out.print(" " + (x + 1));
					}
				}  
				System.out.print("\n");	
			}	
			
		System.out.print(" \n    A B C D E F G H I J \n");
	}
		
	
	/* 	return the current state of the game board and the contents of the player's tile rack
		as a String object
	*/
	public String gameState() {
		
		gameBoard();
		return "\n Tile rack: " + tileRack.toString(); 
	}

	
	// 
	public String letterFromTileRack(String letterPositionsInRack) {
		int posInRack;
		String word = "";
		
		for (int i = 0; i < letterPositionsInRack.length(); i++) {
			posInRack = Character.getNumericValue(letterPositionsInRack.charAt(i) - 1);
		}
		return word;
	}

	
	public boolean isTileEmpty(Play play) {
		
		String positionsInRack = play.letterPositionsInRack();
		Direction dir = play.dir();
		String cell = play.cell();
		int col = charToNum((cell.charAt(0)));
		int row = Integer.parseInt(cell.substring(1,cell.length())) - 1;
		
		
		for (int i = 0; i < positionsInRack.length(); i++) {
			
			if ((dir.toString() == "DOWN") && (board[row][col] == '_' || board[row][col] == '+')) {
				row++;
				if (row > 9 || col > 9){
					System.out.print("Cannot place tile outside the game board");
					return false;
				}
			}

			
			else if ((dir.toString() == "ACROSS") && (board[row][col] == '_' || board[row][col] == '+')) {
				col++;
				if (row > 9 || col > 9) {
					System.out.print("Cannot place tile outside the game board");
					return false;
				}
			}
			else {
				System.out.print("Tile is occupied");
				return false;
				}
		}
		
		return true;
	}
	
	
	public String play(Play play) {
		
		//String word;
		int posInRack;
		String cell = play.cell();
	    int col = charToNum((cell.charAt(0)));
		int row = Integer.parseInt(cell.substring(1,cell.length())) - 1;
		Direction dir = play.dir();
		String positionsInRack = play.letterPositionsInRack();
		
		//validOnBoard();
		
		//word =  letterFromTileRack(positionsInRack);
		
		// if (validOnBoard() == "Valid") {

		if (tileRack.size() == 0){
			System.out.print("Refill tile rack first");
			return "";
		}
			
			if (isTileEmpty(play)){
				
				for (int i = 0; i < positionsInRack.length(); i++) {
			
			posInRack = Character.getNumericValue(positionsInRack.charAt(i) - 1);
			//System.out.print(" tileVal = " + tileRack.get(posInRack));
	
			
				if (dir.toString() == "DOWN") {
				board[row][col] = tileRack.get(posInRack);
				tileRack.set(posInRack, null);
				row++;
				}
			
				else {
				board[row][col] = tileRack.get(posInRack);
				tileRack.set(posInRack, null);
				col++;
				}
			
				}

				playedOnce = true;
				refillRack();
				gameBoard();
		
			}
			
		return "";
	}
	
	
	public String calculateScore(Play play) {
		
		String positionsInRack = play.letterPositionsInRack();
		int posInRack, score = 0;
		HashMap<Character, Integer> points = new HashMap<Character, Integer>();
		String cell = play.cell();
	    int col = charToNum((cell.charAt(0)));
		int row = Integer.parseInt(cell.substring(1,cell.length())) - 1;
		Direction dir = play.dir();

		// points assigned to each letter in the English alphabet
		points.put('A', 1);
		points.put('B', 2);
		points.put('C', 1);
		points.put('D', 1);
		points.put('E', 1);
		points.put('F', 1);
		points.put('G', 2);
		points.put('H', 1);
		points.put('I', 1);
		points.put('J', 2);
		points.put('K', 2);
		points.put('L', 1);
		points.put('M', 2);
		points.put('N', 2);
		points.put('O', 1);
		points.put('P', 1);
		points.put('Q', 3);
		points.put('R', 1);
		points.put('S', 1);
		points.put('T', 1);
		points.put('U', 1);
		points.put('V', 1);
		points.put('W', 1);
		points.put('X', 3);
		points.put('Y', 3);
		points.put('Z', 3);
		
		if (tileRack.size() == 0){
			System.out.print("Refill tile rack first");
			return "";
		}
		
		for (int i = 0; i < positionsInRack.length(); i++) {
			
			posInRack = Character.getNumericValue(positionsInRack.charAt(i) - 1);
			
			if (isSpecial(row,col)) {
				score += (points.get(tileRack.get(posInRack)) * 2);
				
			}
			else {
				score += points.get(tileRack.get(posInRack));
	            
			}
			
			
			if (dir.toString() == "DOWN") {
				row++;
			}
			
			else {
				col++;
			}
			
			
		}
			return "Your points for the tiles placed on the game board is " + score;
		}

	
	

		
	public boolean isSpecial(int row, int col) {
			
			if (((row == 1 ) && (col == 4)) || 
			    ((row == 1 ) && (col == 5)) ||
			    ((row == 3 ) && (col == 3)) ||
			    ((row == 3 ) && (col == 6)) ||
				((row == 6 ) && (col == 3)) ||
				((row == 6 ) && (col == 6)) ||
				((row == 8 ) && (col == 4)) ||
		    	((row == 8 ) && (col == 5)))		
			{
				return true;
			}
			
			else 
			{
				return false;		
			}
		}
	
	
	public String wordsFromBoard(char[][] iBoard) {
		
		//String rowWordList = "";
		//String colWordList = "";
		StringBuilder allWords = new StringBuilder();
		//char[][] boardNow = board;
		
		
		// Loop through each row to extract a new word from the board and store in a comma separated list of words
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {

				if ((col == 0 ) && (!isCellEmpty(iBoard[row][col]) && !isCellEmpty(iBoard[row][col+1]))){
					allWords.append(iBoard[row][col]);
				}
				
				else if ((col == 9 ) && (!isCellEmpty(iBoard[row][col]) && !isCellEmpty(iBoard[row][col-1]))){
					allWords.append(iBoard[row][col]);
				}
				
				else if ((!isCellEmpty(iBoard[row][col]) && !isCellEmpty(iBoard[row][col+1]))){
					allWords.append(iBoard[row][col]);
				}
				
				else if (col > 0 && col < 10) {
				       if ((!isCellEmpty(iBoard[row][col]) && 
				    	  (isCellEmpty(iBoard[row][col+1]) && !isCellEmpty(iBoard[row][col-1])))) {
				    	   allWords.append(iBoard[row][col]); 
				    	   allWords.append(",");
				       }
				}
				  
			}
		}
		
		//allWords.setLength(Math.max(allWords.length() - 1, 0));
		
		for (int col = 0; col < 10; col++) {
			for (int row = 0; row < 10; row++) {

				if ((row == 0 ) && (!isCellEmpty(iBoard[row][col]) && !isCellEmpty(iBoard[row+1][col]))){
					allWords.append(iBoard[row][col]);
				}
				
				else if ((row == 9 ) && (!isCellEmpty(iBoard[row][col]) && !isCellEmpty(iBoard[row-1][col]))){
					allWords.append(iBoard[row][col]);
				}
				
				else if ((!isCellEmpty(iBoard[row][col]) && !isCellEmpty(iBoard[row+1][col]))){
					allWords.append(iBoard[row][col]);
				}
				
				else if (row > 0 && row < 10) {
				       if ((!isCellEmpty(iBoard[row][col]) && 
				    	  (isCellEmpty(iBoard[row+1][col]) && !isCellEmpty(iBoard[row-1][col])))) {
				    	   allWords.append(iBoard[row][col]); 
				    	   allWords.append(",");
				       }
				}
				  
			}
		
		}
		
		//allWords.setLength(Math.max(allWords.length(), 0));
		
		//	Split rowWordList and colWordList and check against dictionary and add it to string builder 
		//rowWordList = rowWordList.replaceAll(",$", ""); 
		//colWordList = colWordList.replaceAll(",$", ""); 
		//System.out.print("\n row word list: " + rowWordList);
		//System.out.print("\n col word list: " + colWordList);
		
		
		return allWords.toString();
		
	}
	
	
	public String retValidWords(String aWords) {
		String[] result;
		StringBuilder validWords = new StringBuilder();

		result = aWords.split(",");
	    for (String s : result) {
	    	if (isWordInDict(s)) {
	    		validWords.append(s);
	    		validWords.append(",");
	    	}
	    	//validWords.setLength(Math.max(validWords.length() - 1, 0));    

	    }
	    return validWords.toString();
	}
	
	
	public String retInvalidWords(String aWords) {
		String[] result;
		StringBuilder invalidWords = new StringBuilder();

		result = aWords.split(",");
	    for (String s : result) {
	    	if (!isWordInDict(s)) {
	    		invalidWords.append(s);
	    		invalidWords.append(",");
	    	}
	    	//invalidWords.setLength(Math.max(invalidWords.length() - 1, 0));    

	    }
	    return invalidWords.toString();
	}
	
	
	
	// Creating a temporary board to play on, to check for valid words
	public char[][] playValidityWord(Play play) {
		
		String positionsInRack = play.letterPositionsInRack();
		int posInRack;
		char[][] tempBoard = new char[10][10];
		String cell = play.cell();
		int col = charToNum((cell.charAt(0)));
		int row = Integer.parseInt(cell.substring(1,cell.length())) - 1;
		Direction dir = play.dir();
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				tempBoard[i][j] = board[i][j];
			}
		}
		
		
		if (isTileEmpty(play)){
			
			for (int i = 0; i < positionsInRack.length(); i++) {
		
		posInRack = Character.getNumericValue(positionsInRack.charAt(i) - 1);
		//System.out.print(" tileVal = " + tileRack.get(posInRack));

		
			if (dir.toString() == "DOWN") {
			tempBoard[row][col] = tileRack.get(posInRack);
			row++;
			}
		
			else {
			tempBoard[row][col] = tileRack.get(posInRack);
			col++;
			}
		
			}
		}
		
		return tempBoard;
		
		
	}
	
	
	// check the validity of a specified play
	// displays a message stating "Valid" or "Invalid" after the check
	public String checkValidity(Play play) {
		
		try {
			
			File f1 = new File ("/Users/poojarenganathan/Documents/Second_year/Data_structures/Coursework/180201200/src/wordGame/wordlist.txt");
		    FileReader fr = new FileReader(f1);
		    String input;
		    String[] result;
		    char[][] tBoard = new char[10][10]; // create a new temporary board to store intended play
		  
		    String allWords, validWords, invalidWords, tBoardWords, newInvalidWords;
		    boolean isInvalid = false;
		    
		    //System.out.print(input);
		    
		    if (tileRack.size() == 0){
				System.out.print("\n Refill tile rack first");
				return "\n Invalid";
			}
		    
		    input = letterFromTileRack(play.letterPositionsInRack());
		    gameBoard();
		    
		    if (!IsAdjacent(play)) {
		    	System.out.print("Invalid play: word is not adjacent\n");
		    	isInvalid = true;		    	
		    }
		    
		    
		    allWords = wordsFromBoard(board).toString();
		    
		    

		    invalidWords = retInvalidWords(allWords); 
		    invalidWords = invalidWords.replaceAll(",$", "");  
		    String[] totalInvalid = invalidWords.split(",");
		    
		    tBoard = playValidityWord(play);
		    
		    tBoardWords = wordsFromBoard(tBoard).toString();
		    tBoardWords = tBoardWords.replaceAll(",$", "");
		    
		    validWords = retValidWords(tBoardWords); 
		    validWords = validWords.replaceAll(",$", "");  
		    System.out.print("\n Valid words: " + validWords + "\n");
		      
		    newInvalidWords = retInvalidWords(tBoardWords); 
		    newInvalidWords = newInvalidWords.replaceAll(",$", "");  
		    String[] newTotalInvalid = newInvalidWords.split(",");
		    System.out.print("\n Invalid words: " + newInvalidWords + "\n");
		   
		    
		    if ((totalInvalid.length != newTotalInvalid.length) && (newInvalidWords.length() == 0)){
		    	isInvalid = true;
		    }
		    else {
		    	isInvalid = false;

		    }
		    
		    if (isInvalid) {
		    	return "\n Invalid";
		    }
		    else {
		    	return "\n Valid";
		    }
		}
		
		catch (FileNotFoundException e) {
			System.out.print("Error: FileNotFound");
		}
		catch (IOException e) {
			System.out.print("Error: IOException");
		}
		
		return "\n Invalid";
	}
	
	
	// check if intended play is adjacent to current words on board
	// returns true if adjacent to previous play otherwise it returns false
	public boolean IsAdjacent(Play play) {
			
			String cell = play.cell();
			int col = charToNum((cell.charAt(0)));
			int row = Integer.parseInt(cell.substring(1,cell.length())) - 1;
			Direction dir = play.dir();
			
			if (!playedOnce) { 
				return true;
			}
			
			if (row == 0 && col == 0) {
				if (!isCellEmpty(board[row + 1][col]) || (!isCellEmpty(board[row][col + 1]))) {
					return true;
				}
			}
			
			else if (row == 0 && col == 9){
				if (!isCellEmpty(board[row + 1][col]) || (!isCellEmpty(board[row][col - 1]))) {
					return true;
				}
			}
			
			else if (row == 9 && col == 0) {
				if (!isCellEmpty(board[row - 1][col]) || (!isCellEmpty(board[row][col + 1]))) {
					return true;
				}
			}
			
			else if (row == 9 && col == 9) {
				if (!isCellEmpty(board[row - 1][col]) || (!isCellEmpty(board[row][col - 1]))) {
					return true;
				}
			}
			
			else if (row == 0 && (col > 0 && col < 9)) {
				if (!isCellEmpty(board[row][col + 1]) || (!isCellEmpty(board[row][col - 1])) 
						||(!isCellEmpty(board[row + 1][col]))){
					return true;
				}
			}
			
			else if (row == 9 && (col > 0 && col < 9)) {
				if (!isCellEmpty(board[row][col + 1]) || (!isCellEmpty(board[row][col - 1])) 
						||(!isCellEmpty(board[row - 1][col]))){
					return true;
				}
			}
			
			else if (col == 0 && (row > 0 && row < 9)) {
				if (!isCellEmpty(board[row - 1][col]) || (!isCellEmpty(board[row][col + 1])) 
						||(!isCellEmpty(board[row + 1][col]))){
					return true;
				}
			}
			
			else if (col == 9 && (row > 0 && col < 9)) {
				if (!isCellEmpty(board[row - 1][col]) || (!isCellEmpty(board[row][col - 1])) 
						||(!isCellEmpty(board[row + 1][col]))){
					return true;
				}
			}
			
			else {	
				if ((col > 0 && (col < 9)) && (row > 0 && row < 9)){  // check for middle of board
					if (!isCellEmpty(board[row][col + 1]) || !isCellEmpty(board[row][col - 1])
						|| !isCellEmpty(board[row + 1][col]) || !isCellEmpty(board[row - 1][col])){
					return true;
					
					}
				}	
			}
			
			
			
			return false;
	}

	
	public boolean isWordInDict(String word) {
		
		try {
			
			File f1 = new File ("/Users/poojarenganathan/Documents/Second_year/Data_structures/Coursework/180201200/src/wordGame/wordlist.txt");
		    FileReader fr = new FileReader(f1);    
		    BufferedReader br = new BufferedReader(fr);
		    String dictWord;
		  
		    
		    while ((dictWord = br.readLine()) != null){
		    	if ((word != null) && (word != "")) {
		    		if (dictWord.equalsIgnoreCase(word)) {   // search for the given word 
	                	return true;
	                 }
		    	}
		    }
		    return false;
		}
		
		catch (FileNotFoundException e) {
			System.out.print("Error: FileNotFound");
		}
		catch (IOException e) {
			System.out.print("Error: IOException");
		}
		return false;
	}
	
	
	public boolean isCellEmpty(char cellValue) {

		if (cellValue == '_' || cellValue == '+') {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	// converts a character to a number for board coordinates
	public int charToNum(char ch)
	{
	
            int num = 0;
    
            switch(ch)
            {
            
             case 'A':   num = 0; break;
        
             case 'B':   num = 1; break;
             
             case 'C':   num = 2; break;
             
             case 'D':   num = 3; break;   
             
             case 'E':   num = 4; break;   
        
             case 'F':   num = 5; break;   
             
             case 'G':   num = 6; break;   
             
             case 'H':   num = 7; break;       
        
             case 'I':   num = 8; break;    
             
             case 'J':   num = 9; break;   
             
             default: break;
             
            }
            
             return num;
	}
	
	public char tileRackVal(int pos) {
		return 'a';
	}
}












