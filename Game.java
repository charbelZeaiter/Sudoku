import java.util.ArrayList;
import java.util.Random;

/**
 * This Class is where all the back end logic is handled. It generates
 * a new sudoku game based on the difficulty selected.
 */
public class Game {
	
	/**
	 * Creates a new game  based on the selected difficulty.
	 * @param level, the difficulty of the game to be generated.
	 */
	public Game(GameDifficulty level){
		this.level = level;
		board = new int[9][9];
		solution = new int[9][9];
		int i,j;
		Random r = new Random();
		ArrayList<Integer> seen = new ArrayList<Integer>();
		Integer temp = null;
		for (i = 0;i < 9;i++){
			for (j = 0;j < 9;j++){
				if (j != 0){
					solution[i][j] = 0;
					board[i][j] = 0;
				}else{
					while (seen.contains(temp) || temp == null){
						temp = new Integer(Math.abs(r.nextInt())%9 + 1);
					}
					seen.add(temp);
					solution[i][j] = temp.intValue();
					board[i][j] = temp.intValue();
				}
			}
		}
		genSolution(solution,board,0,1);
		
		if(level == GameDifficulty.BEGINNER){
			reduce(81 - filledBoxes[0]);
			assistCount = 10;
			emptyBoxes = 81 - filledBoxes[0];
		}else if(level == GameDifficulty.INTERMEDIATE){
			reduce(81 - filledBoxes[1]);
			assistCount = 5;
			emptyBoxes = 81 - filledBoxes[1];
		}else{
			reduce(81 - filledBoxes[2]);
			assistCount = 3;
			emptyBoxes = 81 - filledBoxes[2];
		}
	
	}

	/**
	 * generate a solution to the partially filled board, "filled" and keep a copy in "copy"
	 * Precondition: initial and copy must be identical to start with; (x,y) must point to the 
	 * unfilled box with in initial[][] with lowest y-coordinate. If multiple points with the same 
	 * y-coordinate exist, the left most one is to be passed in
	 * @param initial a partially filled board
	 * @param a copy of initial. 
	 * @param x x coordinate of the lower-leftmost box which is unfilled. 
	 * @param y y coordinate of the lower-leftmost box which is unfilled. 
	 * @return if a solution has been found
	 */
	private boolean genSolution(int[][] initial,int[][] copy,int x,int y){
		boolean legal = false;
		if (x == 0 && y == 0){
			return true;  
		}
		int i = 0;
		int xNext = x;
		int yNext = y;
		xNext++;
		if (xNext == 9){
			xNext = 0;
			yNext = (yNext + 1)%9;
		}
		while (i < 9 && !legal){
			initial[x][y] = i + 1;
			copy[x][y] = i + 1;
			if (checkConflict(x,y,initial)){
				legal = genSolution(initial,copy,xNext,yNext);
			}
			i++;
		}
		if (legal == false){
			initial[x][y] = 0;
			copy[x][y] = 0;
		}
		return legal;
	}

	/**
	 * reduce the solution[][] array down to a specified number of filled boxes. 
	 * Precondition: solution[][] must already contain a valid and complete sudoku board
	 * @param target the number of filled boxes to be removed
	 */
	private void reduce(int target){
		int i = 0;
		int x = 0;
		int y = 0;
		int temp = 0;
		int lastX = 0;
		int lastY = 0;
		Random r = new Random();
		while (i < target){
			temp = board[x][y];
			board[x][y] = 0;
			if (otherSolution(solution,board,0,0,true)){
				board[x][y] = temp;
			}else{
				i++; 
			}
			lastX = x;
			lastY = y;
			while ((x == lastX && y == lastY) || board[x][y] == 0){
				x = Math.abs(r.nextInt())%9;
				y = Math.abs(r.nextInt())%9;
			}
		}
	}

	/**
	 * Attempt to find another solution to "unfilled" other than that given by "filled". 
	 * Precondition: filled must be a complete and valid Sudoku board and unfilled must be 
	 * a proper subset of filled. x and y follow the same rule as in "genSolution". See "genSolution"
	 * for details
	 * @param filled a complete and valid Sudoku board
	 * @param unfilled a proper subset of "filled"
	 * @param sameSofar whether the numbers filled in in previous recursive calls are the same as 
	 * in the given solution
	 * @return whether an alternative solution is found
	 */
	private boolean otherSolution(int[][] filled,int[][] unfilled,int x,int y,boolean sameSofar){
		boolean solved = false;
		int i = y;
		int j = x;
		int newX = 0;
		int newY = 0;
		boolean found = false;
		while (i < 9 && !found){
			while (j < 9 && !found){
				if (unfilled[j][i] == 0 && !(y == i && x == j)){
					found = true;
					newX = j;
					newY = i;
				}
				j++;
			}
			j = 0;
			i++;
		}

		int number = 1;
		while (number <= 9 && !solved){
			unfilled[x][y] = number;
			if (checkConflict(x,y,unfilled)){
				if (found){
					solved = otherSolution(filled,unfilled,newX,newY,(sameSofar && (unfilled[x][y] == filled[x][y])));
				}else{
					if (sameSofar){
						solved = false;
					}else{
						solved = true;
					}
				}
			}
			number++;
		}
	    unfilled[x][y] = 0;
		return solved;
	}

	/**
	 * 
	 * @return the current incomplete board
	 */
	public int[][] printGame(){
		return board;
	}

	/**
	 * hint to the user what the solution to a specified box is
	 * @param x the x coordinate of the requested box
	 * @param y the y coordinate of the requested box
	 * @return the solution to the box at (x,y)
	 */
	public int hint(int x, int y){
		return solution[x][y];
	}

	/**
	 * filled a number into the box at the specified coordinates (x,y)
	 * @param x the x coordinate of the requested box
	 * @param y the y coordinate of the requested box
	 * @param content the number to be filled in
	 */
	public void place(int x, int y, int content){
		board[x][y] = content;
	}

	/**
	 * @param x the x coordinate of the requested box
	 * @param y the y coordinate of the requested box
	 * @return the content of the box at (x,y) in the board[][] array
	 */
	public int get(int x, int y){
		return board[x][y];
	}

	/**
	 * scan around a point (x,y) in the board[][] array for contradictions
	 * @param x the x coordinate of the requested box
	 * @param y the y coordinate of the requested box
	 * @return whether or not a conflict is found
	 */
	public boolean checkAround(int x,int y){
		return checkConflict(x,y,board);
	}
	
	/**
	 * if the puzzle has been solved
	 * @return true-solved false-unsolved
	 */
	public boolean isSolved(){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(board[i][j] != solution[i][j]){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * check for conflict around the box at (x,y) on the board represented by toCheck[][]
	 * @param x the x coordinate of the requested box
	 * @param y the y coordinate of the requested box
	 * @param toCheck the board in which the search takes place
	 * @return if a conflict is found
	 */
	private boolean checkConflict(int x,int y,int[][] toCheck){
		boolean legal = true;
		legal = checkArea(x,0,x,8,toCheck);
		legal = legal && checkArea(0,y,8,y,toCheck);
		int x1 = 3 * (int)(x/3);
		int y1 = 3 * (int)(y/3);
		legal = legal && checkArea(x1,y1,x1+2,y1+2,toCheck);
		return legal;
	}

	/**
	 * look for conflicts with in the rectangular with diagonal ending at  
	 * (x1,y1) and (x2,y2) within the board represented by toCheck[][]
	 * @param x1 the x coordinate of the lower-left corner
	 * @param y1 the y coordinate of the lower-left corner
	 * @param x2 the x coordinate of the upper-right corner
	 * @param y2 the y coordinate of the upper-right corner
	 * @param toCheck the board in which the search takes place
	 * @return whether a conflict is found in the area
	 */
	private boolean checkArea(int x1,int y1,int x2,int y2,int[][] toCheck){
		boolean legal = true;
		ArrayList<Integer> checkList = new ArrayList<Integer>();
		int i,j = 0;
		for (i = 0;i < 9;i++){
			checkList.add(new Integer(i+1));
		}
		i = x1;
		j = y1;
		Integer temp;
		while (i <= x2 && legal){
			while (j <= y2 && legal){
				if (toCheck[i][j] != 0){
					temp = new Integer(toCheck[i][j]);
					if (!checkList.contains(temp)){
						legal = false;
					}else{
						checkList.remove(temp);
					}
				}
				j++;
			}
			j = y1;
			i++;
		}
		return legal;
	}
	
	public GameDifficulty level;
	private int[][] board; //an incomplete Sudoku board that the user sees
	private int[][] solution; //the unique solution to the Sudoku puzzle represented by board[][]
	public static final int[] filledBoxes = {55,40,28}; //preset number of boxes filled for various difficulty settings
	public static int assistCount; //the number of times hint has been given
	public static int emptyBoxes; 
}
