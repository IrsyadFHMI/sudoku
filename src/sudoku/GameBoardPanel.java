/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #7
 * 1 -  5026221163 - Mohammad Geresidi Rachmadi
 * 2 -  5026221187 - Muhammad Irsyad Fahmi

 */
package sudoku;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Tambahkan tombol reset & Kunci Jawaban
    static JButton btnReset = new JButton("Reset");
    static JButton btnHint = new JButton("Hint");
    static JButton btnKunciJawaban = new JButton("Kunci Jawaban");

    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle = new Puzzle();

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }
        }


        btnReset.addActionListener(e -> {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                    if(cells[row][col].status == CellStatus.CORRECT_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS)cells[row][col].resetCell(cells[row][col].number); // Tambahkan method resetCell pada kelas Cell
                }
            }
        });

        //Tombol kunci jawaban, dilempar ke SudokuMain.java
        btnKunciJawaban.addActionListener(e -> {
            for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                    if(cells[row][col].status == CellStatus.TO_GUESS){
                        cells[row][col].status = CellStatus.CORRECT_GUESS;
                        cells[row][col].paint();

                    }
                }
            }
        });

        btnHint.addActionListener(e ->{
            outerFor:
            for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                    if(cells[row][col].status == CellStatus.TO_GUESS){
                        cells[row][col].status = CellStatus.CORRECT_GUESS;
                        cells[row][col].paint();
                        break outerFor;
                    }
                }
            }
        });

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
        // .........
        CellInputListener listener = new CellInputListener();
        CellMouseListener mouseListener = new CellMouseListener();

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) { //isEditable nya cells diatur di class cell
                    cells[row][col].addActionListener(listener);
                    cells[row][col].addMouseListener(mouseListener);
                }
                // if(cells[row][col].isEditable()) cells[row][col].addMouseListener(mouseListener);
            }
        }

        // [TODO 4] Adds this common listener to all editable cells

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }


    /**
     * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame(int level) {
        // Generate a new puzzle
        puzzle.newPuzzle(level);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]); //newGame.(a, b) di sini merupakan method dari Cell.java (overload?). Ngisi angka di sel.
                cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));


                if (col == 2 || col == 5) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.BLACK));
                }
                if (row == 2 || row == 5) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.BLACK));
                }
                if (row == 2 && col == 2 || row == 5 && col == 5 || row == 2 && col == 5 || row == 5 && col == 2) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, Color.BLACK));
                }

            }
        }
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of BELUM_DIISI or SALAH
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    // .........
    private class CellInputListener implements ActionListener { //ActionListener berfungsi untuk penekanan tomobol ENTER di dalam JTextField
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            String numberIn = sourceCell.getText(); //Diparse karena inputannya berasa dari JTextComponent yang merupakan string
            System.out.println("You entered " + numberIn);

            // TODO 5: Check the numberIn against sourceCell.number and update the cell status

            if (Integer.parseInt(numberIn) == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                sourceCell.paint(); // re-paint this cell based on its status
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
                sourceCell.paint(); // re-paint this cell based on its status
            }


            // TODO 6: Check if the puzzle is solved and show a congratulation message
            // Uncomment and complete the code when TODO 6 is implemented
            if (isSolved()) {
                // JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!");
                int isPlayAgain = JOptionPane.showOptionDialog(new JFrame(), "Congratulations! You solved the puzzle!\nPlay Again?","Congratulations!",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        new Object[] { "Yes", "No" }, JOptionPane.YES_OPTION);

                if (isPlayAgain == JOptionPane.YES_OPTION) {
                    Sudoku.board.newGame(10);
                } else if (isPlayAgain == JOptionPane.NO_OPTION) {
                    System.out.println("Selected No!");
                }
            }

        }
    }

    private Cell previousClickedCell;
    private class CellMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            Cell sourceCell = (Cell) e.getSource();

            resetPreviousRowAndColumnColors();

            if (sourceCell.status == CellStatus.GIVEN || sourceCell.status == CellStatus.CORRECT_GUESS) {
                resetPreviousRowAndColumnColors();

                // Change the background color of all cells in the same row
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    cells[sourceCell.row][col].setBackground(new Color(255, 246, 186)); // Change the color as needed
                }

                // Change the background color of all cells in the same column
                for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                    cells[row][sourceCell.col].setBackground(new Color(255, 246, 186)); // Change the color as needed
                }

                int rowMiniBox = sourceCell.row - (sourceCell.row % 3);
                int colMiniBox = sourceCell.col - (sourceCell.col % 3);
                for (int row = rowMiniBox; row < rowMiniBox + 3; row++) {
                    for(int col = colMiniBox; col < colMiniBox + 3; col++){
                        cells[row][col].setBackground(new Color(255, 246, 186)); // Change the color as needed

                    }
                }

                for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                    for(int col = 0; col < SudokuConstants.GRID_SIZE; col++){
                        if(cells[row][col].number == sourceCell.number && cells[row][col].status == CellStatus.GIVEN || cells[row][col].number == sourceCell.number && cells[row][col].status == CellStatus.CORRECT_GUESS){
                            cells[row][col].setBackground(new Color(255, 215, 13)); // Change the color as needed
                            cells[row][col].setForeground(Color.RED); // Change the color as needed
                        }

                    }
                }

                // Set the current cell as the previously clicked cell
                previousClickedCell = sourceCell;
            }

        }

        private void resetPreviousRowAndColumnColors() {
            if (previousClickedCell != null) {
                int row = previousClickedCell.row;
                int col = previousClickedCell.col;
                int number = previousClickedCell.number;
                int rowMiniBox = previousClickedCell.row - previousClickedCell.row%3;
                int colMiniBox = previousClickedCell.col - previousClickedCell.col%3;

                for (int r = rowMiniBox; r < rowMiniBox + 3; r++) {
                    for(int c = colMiniBox; c < colMiniBox + 3; c++){
                        if(cells[r][c].status == CellStatus.TO_GUESS)  cells[r][c].paint();
                        else cells[r][c].setBackground(null); // Change the color as needed

                    }
                }

                // Reset the background color of the row
                for (int c = 0; c < SudokuConstants.GRID_SIZE; c++) {
                    if(cells[row][c].status == CellStatus.TO_GUESS)  cells[row][c].paint();
                    else cells[row][c].setBackground(null); // Reset the color to the default

                }

                // Reset the background color of the column
                for (int r = 0; r < SudokuConstants.GRID_SIZE; r++) {
                    if(cells[r][col].status == CellStatus.TO_GUESS)  cells[r][col].paint();
                    else cells[r][col].setBackground(null); // Reset the color to the default
                }

                for (int r = 0; r < SudokuConstants.GRID_SIZE; r++) {
                    for(int c = 0; c < SudokuConstants.GRID_SIZE; c++){
                        if(cells[r][c].number == number && cells[r][c].status == CellStatus.GIVEN || cells[r][c].status == CellStatus.CORRECT_GUESS){
                            cells[r][c].paint(); // Change the color as needed;
                            cells[r][c].setForeground(Color.BLACK); // Change the color as needed;
                        }
                    }
                }
            }
        }
    }
}