package com.example.week7;

public class Board {

    private static byte PLAYER_1_SYMBOL = 1;
    private static byte PLAYER_2_SYMBOL = 2;
    private boolean player1Turn = true;

    byte[][] board = new byte[3][3];
    BoardListener boardListener;

    public Board(BoardListener boardListener) {
        this.boardListener = boardListener;
    }

    public void move(byte row, byte col) {
        if(player1Turn) {
            board[row][col] = PLAYER_1_SYMBOL;
            boardListener.playAt(PLAYER_1_SYMBOL, row, col);
        } else {
            board[row][col] = PLAYER_2_SYMBOL;
            boardListener.playAt(PLAYER_2_SYMBOL, row, col);
        }
        player1Turn = !player1Turn;
    }
}