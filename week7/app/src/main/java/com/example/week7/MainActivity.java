package com.example.week7;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements BoardView {
    TableLayout board;
    BoardPresenter boardPresenter;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void newGame() {
        TableLayout board = findViewById(R.id.board);
        for(byte row = 0; row < 3; row++){
            TableRow tableRow = (TableRow) board.getChildAt(row);
            for(byte col = 0; col < 3; col++){
                Button button = (Button) tableRow.getChildAt(col);
                button.setText("");
                button.setEnabled(true);
            }
        }

    }

    @Override
    public void putSymbol(char symbol, byte row, byte col) {
        TableRow tableRow = (TableRow) board.getChildAt(row);
        Button button = (Button) tableRow.getChildAt(col);
        button.setText(String.valueOf(Character.toString(symbol)));


    }

    @Override
    public void gameEnded(byte winner) {
        for(byte row = 0; row < 3; row++){
            TableRow tableRow = (TableRow) board.getChildAt(row);
            for(byte col = 0; col < 3; col++){
                Button button = (Button) tableRow.getChildAt(col);
                button.setText("");
                button.setEnabled(false);
            }
        }

        switch (winner) {
            case BoardView.DRAW:
                Toast.makeText(this, "Game is Draw", Toast.LENGTH_LONG).show();
                break;
            case PLAYER_1_WINNER:
                Toast.makeText(this, "Player 1 Wins", Toast.LENGTH_LONG).show();
                break;
            case PLAYER_2_WINNER:
                Toast.makeText(this, "Player 2 Wins", Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void invalidPlay(byte row, byte col) {
        Toast.makeText(this, "Invalid Move", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        board = findViewById(R.id.board);
        boardPresenter = new BoardPresenter(this);
        for(byte row = 0; row < 3; row++){
            TableRow tableRow = (TableRow) board.getChildAt(row);
            for(byte col = 0; col < 3; col++){
                Button button = (Button) tableRow.getChildAt(col);
                BoardPresenter.CellClickListener cellClickListener = new BoardPresenter.CellClickListener(boardPresenter, row, col);
                button.setOnClickListener(cellClickListener);

            }
        }

    }
}