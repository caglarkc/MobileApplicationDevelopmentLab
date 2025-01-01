package msku.ceng.madlab.week5;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    static final String PLAYER_1 = "X";
    static final String PLAYER_2 = "O";
    boolean player1Turn = true;

    byte[][] board = new byte[3][3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button btn = (Button) row.getChildAt(j);
                btn.setOnClickListener(new CellListener(i, j));
            }
        }

    }

    class CellListener implements View.OnClickListener {
        int row, col;

        public CellListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {

            if (!isValidMove(row, col)) {
                Toast.makeText(MainActivity.this, "Cell is already occupied", Toast.LENGTH_LONG).show();
                return;
            }

            if (player1Turn) {
                ((Button) v).setText(PLAYER_1);
                board[row][col] = 1;
            } else {
                ((Button) v).setText(PLAYER_2);
                board[row][col] = 2;
            }

            if (gameEnded(row, col) == -1) {
                player1Turn = !player1Turn;
            } else if (gameEnded(row, col) == 0) {
                Toast.makeText(MainActivity.this, "It is a draw", Toast.LENGTH_LONG).show();
            } else if (gameEnded(row, col) == 1) {
                Toast.makeText(MainActivity.this, "Player 1 wins!", Toast.LENGTH_LONG).show();
            } else if (gameEnded(row, col) == 2) {
                Toast.makeText(MainActivity.this, "Player 2 wins!", Toast.LENGTH_LONG).show();
            }

        }

        public int gameEnded(int row, int col) {
            int symbol = board[row][col];
            boolean win = true;

            // Check the column
            for (int i = 0; i < 3; i++) {
                if (board[i][col] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return symbol;
            }

            // Check the row
            win = true;
            for (int j = 0; j < 3; j++) {
                if (board[row][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return symbol;
            }

            // Check the main diagonal (top-left to bottom-right)
            if (row == col) {
                win = true;
                for (int i = 0; i < 3; i++) {
                    if (board[i][i] != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return symbol;
                }
            }

            // Check the anti-diagonal (top-right to bottom-left)
            if (row + col == 2) {
                win = true;
                for (int i = 0; i < 3; i++) {
                    if (board[i][2 - i] != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) {
                    return symbol;
                }
            }

            // Check for a draw (no empty cells left)
            boolean draw = true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) { // Assuming 0 represents an empty cell
                        draw = false;
                        break;
                    }
                }
                if (!draw) {
                    break;
                }
            }
            if (draw) {
                return 0; // Game is a draw
            }

            return -1; // Game is ongoing
        }


        public boolean isValidMove(int row, int col) {

            return board[row][col] == 0;
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("player1Turn", player1Turn);
        byte[] boardSingle = new byte[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardSingle[3 * i + j] = board[i][j];
            }
        }
        outState.putByteArray("board", boardSingle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        player1Turn = savedInstanceState.getBoolean("player1Turn");
        byte[] boardSingle = savedInstanceState.getByteArray("board");

        if (boardSingle != null) {
            for (int i = 0; i < 9; i++) {
                board[i / 3][i % 3] = boardSingle[i];
            }
        }

        // Update the board UI
        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button button = (Button) row.getChildAt(j);
                if (board[i][j] == 1) {
                    button.setText(PLAYER_1);
                } else if (board[i][j] == 2) {
                    button.setText(PLAYER_2);
                } else {
                    button.setText(""); // Clear button text if empty
                }
            }
        }
    }
}