package com.chess.engine.board;
import com.chess.engine.Moves.Move;
import com.chess.engine.pieces.Piece;

public abstract class Tile implements Cloneable{
    protected final int tileCoordinate;
    protected final ColorTile colorTile;
    private final String nameTile;

    //constructor
    protected Tile(final int coordinate, ColorTile colorTile)
    {
        tileCoordinate = coordinate;
        this.colorTile = colorTile;
        this.nameTile = createNameTile();
    }

    public Tile getCopyTile() {
        try {
            return (Tile) this.clone();
        } catch (Exception e){System.out.println("error in clone tile"); return null;}
    }


    private String createNameTile() {
        String letter = "";
        String number = "";
        if (BoardUtils.FIRST_RAW.get(this.tileCoordinate)) { number = "1";}
        else if (BoardUtils.SECOND_RAW.get(this.tileCoordinate)) { number = "2";}
        else if (BoardUtils.THIRD_RAW.get(this.tileCoordinate)) { number = "3";}
        else if (BoardUtils.FORTH_RAW.get(this.tileCoordinate)) { number = "4";}
        else if (BoardUtils.FIFTH_RAW.get(this.tileCoordinate)) { number = "5";}
        else if (BoardUtils.SIXTH_RAW.get(this.tileCoordinate)) { number = "6";}
        else if (BoardUtils.SEVENTH_RAW.get(this.tileCoordinate)) { number = "7";}
        else if (BoardUtils.EIGHTH_RAW.get(this.tileCoordinate)) { number = "8";}
        if (BoardUtils.FIRST_COLUMN.get(this.tileCoordinate)) { letter = "a";}
        else if (BoardUtils.SECOND_COLUMN.get(this.tileCoordinate)) { letter = "b";}
        else if (BoardUtils.THIRD_COLUMN.get(this.tileCoordinate)) { letter = "c";}
        else if (BoardUtils.FOURTH_COLUMN.get(this.tileCoordinate)) { letter = "d";}
        else if (BoardUtils.FIFTH_COLUMN.get(this.tileCoordinate)) { letter = "e";}
        else if (BoardUtils.SIXTH_COLUMN.get(this.tileCoordinate)) { letter = "f";}
        else if (BoardUtils.SEVENTH_COLUMN.get(this.tileCoordinate)) { letter = "g";}
        else if (BoardUtils.EIGHTH_COLUMN.get(this.tileCoordinate)) { letter = "h";}
        return letter+number;
    }

    public ColorTile getColorTile() {
        return colorTile;
    }

    public String getNameTile() {
        return nameTile;
    }

    public static Tile createTile(final int coordinate, Piece piece){
        ColorTile colorTile1 = ColorTile.WHITE;
        if (BoardUtils.FIRST_RAW.get(coordinate) || BoardUtils.THIRD_RAW.get(coordinate) ||
                BoardUtils.FIFTH_RAW.get(coordinate) || BoardUtils.SEVENTH_RAW.get(coordinate))
        {
            if (coordinate % 2 == 0) {
                colorTile1 = ColorTile.BLACK;
            } else { colorTile1 = ColorTile.WHITE;}
        } else {
            if (coordinate % 2 == 0) {
                colorTile1 = ColorTile.WHITE;
            } else { colorTile1 = ColorTile.BLACK;}
        }
        if (piece == null) {
            return new EmptyTile(coordinate, colorTile1);
        } else {
            return new OccupiedTile(coordinate, piece, colorTile1);
        }
    }

    public static boolean isTheCondidateTileAtTheBorders(int condidate) {
        if (BoardUtils.FIRST_COLUMN.get(condidate) ||
                BoardUtils.EIGHTH_COLUMN.get(condidate) ||
                BoardUtils.FIRST_RAW.get(condidate) ||
                BoardUtils.EIGHTH_RAW.get(condidate)) {
            return true;
        }
        return false;
    }

    // getter
    public int getTileCoordinate() {
        return tileCoordinate;
    }

    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{

        // constractor
        public EmptyTile(final int coordinate, ColorTile colorTile) {
            super(coordinate, colorTile);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {return null;}
    }

    public static final class OccupiedTile extends Tile{
        private Piece piece;
        // constractor
        OccupiedTile(int coordinate, Piece p, ColorTile colorTile) {
            super(coordinate, colorTile);
            this.piece = p;

        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.piece;
        }
    }
}
