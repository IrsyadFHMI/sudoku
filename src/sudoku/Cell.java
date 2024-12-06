/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #7
 * 1 -  5026221163 - Mohammad Geresidi Rachmadi
 * 2 -  5026221187 - Muhammad Irsyad Fahmi

 */
package sudoku;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * The Cell class model the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for JTextField's colors and fonts
    //  to be chosen based on CellStatus
    public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_TO_GUESS = Color.BLACK;
    public static final Color BG_TO_GUESS  = new Color(187, 227, 242);
    public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
    public static final Color BG_WRONG_GUESS   = new Color(216, 0, 0);
    public static final Font FONT_NUMBERS = new Font("Helvetica", Font.PLAIN, 20);

    // Define Attributes (package-visible)
    /** The row and column number [0-8] of this cell */
    int row, col;
    /** The puzzle number [1-9] for this cell */
    int number;
    /** The status of this cell defined in enum CellStatus */
    CellStatus status;

    /** Constructor */
    public Cell(int row, int col) {
        super();   // JTextField
        this.row = row;
        this.col = col;
        // Inherited from JTextField: Beautify all the cells once for all
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
        super.addKeyListener(new KeyAdapter() {
            // Menangani peristiwa kunci yang ditekan
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || getText().length() >= 1 || c == '0') {
                    e.consume();
                }
            }
        });
    }

    /** Reset this cell for a new game, TERISI the puzzle number and isTerisi */
    public void newGame(int number, boolean isTerisi) {//Dipake di GameBoard.java, buat ngisi angkanya
        this.number = number;
        status = isTerisi ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        paint();    // paint itself
    }

    /** This Cell (JTextField) paints itself based on its status */
    public void paint() {
        if (status == CellStatus.GIVEN) {
            // Inherited from JTextField: Set display properties
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);

        } else if (status == CellStatus.TO_GUESS) {
            // Inherited from JTextField: Set display properties
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_TO_GUESS);
        } else if (status == CellStatus.CORRECT_GUESS) {  // from BELUM_DIISI
            super.setText(number + "");
            super.setBackground(BG_CORRECT_GUESS);
            super.setEditable(false);
        } else if (status == CellStatus.WRONG_GUESS) {    // from BELUM_DIISI
            super.setBackground(BG_WRONG_GUESS);
        }
    }

    public void resetCell(int number) {
        // Atur sel ke kondisi awal
        this.number = number;
        this.status = CellStatus.TO_GUESS;

        // Perbarui tampilan sel
        paint();
    }
}