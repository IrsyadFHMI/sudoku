/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #7
 * 1 -  5026221163 - Mohammad Geresidi Rachmadi
 * 2 -  5026221187 - Muhammad Irsyad Fahmi

 */
package sudoku;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int cellsToGuess) {
        // I hardcode a puzzle here for illustration and testing.
        //   int[][] hardcodedNumbers =
        //      {{5, 3, 4, 6, 7, 8, 9, 1, 2},
        //       {6, 7, 2, 1, 9, 5, 3, 4, 8},
        //       {1, 9, 8, 3, 4, 2, 5, 6, 7},
        //       {8, 5, 9, 7, 6, 1, 4, 2, 3},
        //       {4, 2, 6, 8, 5, 3, 7, 9, 1},
        //       {7, 1, 3, 9, 2, 4, 8, 5, 6},
        //       {9, 6, 1, 5, 3, 7, 2, 8, 4},
        //       {2, 8, 7, 4, 1, 9, 6, 3, 5},
        //       {3, 4, 5, 2, 8, 6, 1, 7, 9}};

        int[][] angka = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        Random rand = new Random();



        // Isi Baris pertama
        int input = rand.nextInt(9) + 1;
        Map<Integer, Boolean> unikRow = new HashMap<>();
        for(int i = 1; i<SudokuConstants.GRID_SIZE+1; i++){
            unikRow.put(i, false);
        }

        for(int col = 0; col<SudokuConstants.GRID_SIZE; col++){
            while(unikRow.get(input)) input = rand.nextInt(9) + 1;
            angka[0][col] = input;
            unikRow.put(input, true);
        } //Baris pertama terisi

        // Isi jawaban dari baris pertama yang udah terisi
        if(solveBoard(angka)){
            System.out.println("Solved succesfully!");
        }
        else System.out.println("Unsolveable board");


        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = angka[row][col];
            }
        }

        // Need to use input parameter cellsToGuess!
        // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN

        // Copy from hardcodedIsGiven into array "isGiven"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                isGiven[row][col] = true;
            }
        }
        removeNumbers(cellsToGuess);

    }
    private void removeNumbers(int cellsToGuess) {
        Random random = new Random();

        while (cellsToGuess > 0) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);

            if (isGiven[row][col]) {
                isGiven[row][col] = false;
                cellsToGuess--;
            }
        }
    }

    private static boolean isNumberInRow(int[][] board, int number, int row){
        for(int i = 0; i<SudokuConstants.GRID_SIZE; i++){
            if(board[row][i] == number){
                return true;
            }
        }
        return false;
    }
    private static boolean isNumberInColumn(int[][] board, int number, int column){
        for(int i = 0; i<SudokuConstants.GRID_SIZE; i++){
            if(board[i][column] == number){
                return true;
            }
        }
        return false;
    }
    private static boolean isNumberInBox(int[][] board, int number, int row, int column){
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;
        for(int i = localBoxRow; i<localBoxRow + 3; i++){
            for(int j = localBoxColumn; j<localBoxColumn + 3; j++){
                if(board[i][j] == number){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidiPlacement(int[][] board, int number, int row, int column){
        return !isNumberInRow(board, number, row) && !isNumberInColumn(board, number, column) && !isNumberInBox(board, number, row, column);
    }

    // Backtracking
    // Traverse the board row by row, for each square if it doesnt already have a number in it, for that cell we're going to look at all the numbers between 1 and 9, and check whether each of them is valid to add in the space

    private static boolean solveBoard(int[][] board){
        for(int row = 0; row < SudokuConstants.GRID_SIZE; row++){
            for(int column = 0; column < SudokuConstants.GRID_SIZE; column++){
                if(board[row][column] == 0){
                    for(int numberToTry = 1; numberToTry<= SudokuConstants.GRID_SIZE; numberToTry++){
                        if(isValidiPlacement(board, numberToTry, row, column)){
                            board[row][column] = numberToTry;

                            if(solveBoard(board)){
                                return true;
                            }
                            else{
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false; //kalo gaada yang valid, maka return false (i cant solve this board)
                }
            }
        }
        return true;
    }



    //(For advanced students) use singleton design pattern for this class
}