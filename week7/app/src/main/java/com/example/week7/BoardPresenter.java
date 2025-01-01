package com.example.week7;

import android.util.Log;
import android.view.View;

public class BoardPresenter implements BoardListener {
    private BoardView boardView;
    private Board board;

    public BoardPresenter(BoardView boardView) {
        this.boardView = boardView;
        board = new Board(this);
    }

    @Override
    public void playAt(byte player, byte row, byte col) {
        if(player== BoardListener.PLAYER_1){
            boardView.putSymbol(BoardView.PLAYER_1_SYMBOL, row, col);
        }else{
            boardView.putSymbol(BoardView.PLAYER_2_SYMBOL, row, col);
        }

    }

    @Override
    public void gameEnded(byte winner) {
        switch (winner) {
            case BoardListener.NO_ONE:
                boardView.gameEnded(BoardView.DRAW);
                break;
            case BoardListener.PLAYER_1:
                boardView.gameEnded(BoardView.PLAYER_1_WINNER);
                break;
            case BoardListener.PLAYER_2:
                boardView.gameEnded(BoardView.PLAYER_2_WINNER);
                break;
        }

    }

    public void move(byte row, byte col){
        board.move(row, col);
    }

    @Override
    public void invalidPlay(byte row, byte col) {
        boardView.invalidPlay(row, col);
    }

    static class CellClickListener implements View.OnClickListener {
        BoardPresenter boardPresenter;
        byte row, col;
        public CellClickListener(BoardPresenter boardPresenter, byte row, byte col) {
            this.boardPresenter = boardPresenter;
            this.row = row;
            this.col = col;
        }


        @Override
        public void onClick(View v) {
            Log.d("CellClickListener", "Cell clicked at row: " + row + " col: " + col);
            boardPresenter.move(row, col);
        }
    }
}