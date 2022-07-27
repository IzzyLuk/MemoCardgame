import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	static String [][] cards;
	static String [][] board;
	static Scanner scanner = new Scanner(System.in);
	static int chances;
	static String level;
	static int numberOfWords;
	static ArrayList<String> listOfWords = new ArrayList<String>();
	static String gameStatus;
	static int wins;
	static int losses;
		
	
	public static void main(String[] args) {
				
		System.out.println("Welcome to Memory Cards game!");
		System.out.println("Press N for new game or Q to quit:");
		
		gameRunning();
		while (gameOver()) {
			System.out.println();
			System.out.println("To restart game press N. To quit press Q");
			gameRunning();
			continue;
			} 					
	}
	
	
	
	
	
	//opening file
	static void openFile() {
		File file = new File("Words.txt");
		
		try {
			BufferedReader bufRead = new BufferedReader(new FileReader(file));
			
			String line = bufRead.readLine();
		    while (line != null) {
		      listOfWords.add(line);
		      line = bufRead.readLine();
		    }
		    bufRead.close();
	
		}
		catch(FileNotFoundException e) {
			System.out.println("\"Words.txt\" file is missing!");
		}
		catch(IOException e) {
			System.out.println("IOException");
		}
	}
	
	
	
	//starting game
	static void gameRunning() {
		while(true) {
			String gameStatus = scanner.next();
			if(gameStatus.equalsIgnoreCase("Q")) {
				System.out.println("Bye, bye!");
				System.exit(0);	
			} else if (gameStatus.equalsIgnoreCase("N")) {
				level();
				shuffleCards();
				for(int i=0; i<board.length; i++) {
					for(int j=0; j<board[0].length; j++) {
						board[i][j] = " X ";
					}
				}
				showBoard();
				gamePlay(cards);
				break;
			} else {
				System.out.println("Invalid command.");
				continue;
			}
		}
	}
	
	
	
		
	
	//choosing level
	static void level() {
		System.out.println("Please choose your level. Type easy or hard.");
		level = scanner.next();
		System.out.println();
		
		while (true) {
			if (level.equalsIgnoreCase("easy")) {
				cards = new String [2][4];
				board = new String [2][4];
				chances = 10;
				numberOfWords = 4;
				System.out.println("Find matching words. You have 10 chances. Good luck!");
				break;
			} else if (level.equalsIgnoreCase("hard")) {
				cards = new String [4][4];
				board = new String [4][4];
				chances = 15;
				numberOfWords = 8;
				System.out.println("Find matching words. You have 15 chances. Good luck!");
				break;
			} else if (level.equalsIgnoreCase("Q")) {
				System.out.println("Bye, bye!");
				System.exit(0);					
			} else {
				System.out.println("Invalid command. Please type easy or hard to choose your level.");
				level = scanner.next();
				continue;
			}
		}	
	}
	
	
	
	
	//board
	static void showBoard() {
		System.out.println("*******************************");
		System.out.println("Wins: "+wins+"   Losses: "+losses);
		System.out.println();
		System.out.println("Level: "+level);
		System.out.println("Guess chances: "+chances);
		System.out.println();
		System.out.println("    1  2  3  4");
		System.out.println("-------------------------------");
		for (int i=0; i<board.length; i++) {
			System.out.print((i+1)+" |");
			for(int j=0; j<board[0].length; j++) {
				System.out.print(board[i][j]);			
				}
			System.out.println();
			}
			System.out.println();
			System.out.println("*******************************");
			System.out.println();
	}
	
	
	
	
	
	//shuffle cards
	static void shuffleCards() {
		openFile();
		Random random = new Random();
		int index = random.nextInt(listOfWords.size());
		
		ArrayList<String> randomWords = new ArrayList<String>();
		for (int a=0; a<numberOfWords; a++) {
			randomWords.add(listOfWords.get(index));
			randomWords.add(listOfWords.get(index));
			listOfWords.remove(index);
		}
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				cards[i][j] = randomWords.get(0);
				randomWords.remove(0);
				Collections.shuffle(randomWords);				
			}			
		}
	}

			
	static void gamePlay(String [][] cards) {				
		while(true) {					
			if(!gameOver()){
				
				System.out.println("Select row number (1-"+board.length+"):" );
				int row1 = scanner.nextInt();
				row1 -= 1;
				System.out.println("Select column (1-4):" );
				int column1 = scanner.nextInt();
				column1 -= 1;
				System.out.println();
				
				if(!board[row1][column1].equals(" X ")) {
					System.out.println("Already uncovered!");
					System.out.println();
					
					showBoard();
					continue;
				}else{
					board[row1][column1] = " "+cards[row1][column1];
					showBoard();
				}
				
				System.out.println("Select row number (1-"+board.length+"):" );
				int row2 = scanner.nextInt();
				row2 -= 1;
				System.out.println("Select column (1-4):" );
				int column2 = scanner.nextInt();
				column2 -= 1;
				
				if(!board[row2][column2].equals(" X ")) {
					System.out.println("Already uncovered!");
					board[row1][column1] = " X ";
					System.out.println();
										
					showBoard();
					continue;
				}else {
					board[row2][column2] = " "+cards[row2][column2];

						if(board[row1][column1].equals(board[row2][column2])) {
							showBoard();
							System.out.println("Correct!");
							chances -= 1;
						}else{
							showBoard();
							System.out.println("No match.");
							board[row1][column1] = " X ";
							board[row2][column2] = " X ";
							chances -= 1;
						
							showBoard();
						}
				}
				
			} else if  (chances<1){
				System.out.println("No chances left. Game over!");
				losses += 1;
				break;
			} else {
				System.out.println("You win!");
				wins +=1;
				break;
			}
		}
	}	
	
	

	static boolean gameOver() {
			for(int i=0; i<board.length; i++) {
				for(int j=0; j<board[0].length; j++) {	
					if (board[i][j].equals(" X ") && chances>0) {
						return false;
					} 
			}
		}	
		return true;
	}

	
	

}
