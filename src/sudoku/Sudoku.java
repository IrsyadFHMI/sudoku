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
import javax.swing.*;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    static GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");

    // Constructor
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        // Add a button to the south to re-start the game via board.newGame()
        btnNewGame.addActionListener(e -> {
            showNewGameOptions(); // Menampilkan opsi ketika tombol "New Game" diklik
        });
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(btnNewGame);
        buttons.add(GameBoardPanel.btnHint);
        buttons.add(GameBoardPanel.btnKunciJawaban);
        buttons.add(GameBoardPanel.btnReset);
        cp.add(buttons, BorderLayout.SOUTH);

        // Initialize the game board to start the game
        board.newGame(20);

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }

    // Method to show options when "New Game" button is clicked
    private void showNewGameOptions() {
        String[] options = {"Easy", "Medium", "Hard"};
        int selectedOption = JOptionPane.showOptionDialog(this, "Choose difficulty level:", "New Game Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (selectedOption >= 0) {
            int difficulty = (selectedOption + 1) * 20; // Adjust difficulty based on the selected option
            board.newGame(difficulty);
        }
    }

}
