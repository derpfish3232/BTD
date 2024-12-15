package indy;

import javafx.scene.layout.Pane;

import java.util.HashSet;

/**
 * This is the Board Class, which makes the board by a 2-D Array of tiles
 */
public class Board {
    Tile[][] board;

    /**
     * This is the constructor class for board, initializing it as a 2D array
     * then makes a path until a valid one is made
     */
    public Board(Pane boardPane, Game game) {
        this.board = new Tile[Constants.ROWS][Constants.COLS];
        do {
            for (int i = 0; i < this.board.length; i++) {
                for (int j = 0; j < this.board[i].length; j++) {
                    this.board[i][j] = new Tile(i, j, Direction.NONE, Direction.NONE);
                }
            }
            this.makePath( 0, 0);
            if(this.board[Constants.ROW_I][Constants.COLS_I].getDirection() != Direction.NONE) break;
        } while(this.board[Constants.ROW_I][Constants.COLS_I].getDirection() == Direction.NONE);
        
        //sets up functionality
        for(Tile[] tiles : this.board) {
            for(Tile tile : tiles){
                tile.makeImage(boardPane);
                tile.setUpClick(game);
                tile.unsell();
            }
        }
    }

    /**
     * Picks the first direction of either down or right from the top left corner
     */
    public void makePath(int row, int col){
        if(Math.random() < .5){
            this.board[row][col] = new Tile(row, col, Direction.DOWN, Direction.NONE);
            this.makePath(row+1, col, Direction.DOWN);
        }
        else {
            this.board[row][col] = new Tile(row, col, Direction.RIGHT, Direction.NONE);
            this.makePath( row, col + 1, Direction.RIGHT);
        }

    }

    /**
     * This is the makePath method, which recursively finds a working path, returning
     * false if there are no more valid directions to go in or goes past the board boundaries
     */
    public boolean makePath( int row, int col, Direction dir){
        if(row == Constants.ROW_I && col == Constants.COLS_I){
            this.board[row][col] = new Tile(row, col, Direction.DOWN, dir);
            return true;
        }
        if(row >=Constants.ROWS || col >=Constants.COLS) return false;
        HashSet<Direction> visitedDirection = new HashSet<>();
        Direction nextDir = dir.nextDirection();
        if(nextDir.nextWorks(row, col, this)){
            this.board[row][col] = new Tile(row, col, nextDir, dir);
            this.makePath(nextDir.nextRow(row), nextDir.nextCol(col), nextDir);
            return true;
        }
        while(!nextDir.nextWorks(row, col, this)){
            if(visitedDirection.size() == 4){
                this.board[row][col] = new Tile(row, col, Direction.NONE, dir);
                return false;
            }
            visitedDirection.add(nextDir);
            if(nextDir.nextWorks(row, col, this)){
                if(this.makePath(nextDir.nextRow(row), nextDir.nextCol(col), nextDir)){
                    this.board[row][col] = new Tile(row, col, nextDir, dir);
                    return true;
                }
            }
            nextDir = nextDir.nextDirection();
        }
        this.board[row][col] = new Tile(row, col, Direction.NONE, dir);
        return this.makePath(row, col, dir);
    }

    /**
     * Checks if the given row and col is an allowable place to put a path on
     * such that it won't intersect with another path or go off screen
     */
    public boolean checkValid(int row, int col){
        int tiles = 0;
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(row + i < 0 || row + i >= Constants.ROWS || col + j < 0 || col + j >= Constants.COLS) continue;
                if(this.board[row + i][col + j].getDirection() != Direction.NONE) tiles++;
            }
        }
        return tiles < 2;
    }

    /**
     * Getter for the tile at a certain point
     */
    public Tile getTile(int row, int col){
        return this.board[row][col];
    }

    /**
     * This is a method that resets the placabilty of each tile
     */
    public void restart(){
        for(Tile[] tiles : this.board) {
            for(Tile tile : tiles) {
                tile.unsell();
            }
        }
    }
}
