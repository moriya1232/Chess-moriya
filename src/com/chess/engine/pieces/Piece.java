package com.chess.engine.pieces;
import com.chess.engine.Moves.Move;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Tile;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;


public abstract class Piece {
    private int position;
    protected final Alliance alliance;
    protected PieceType pieceType;
    protected ImageIcon imageIcon;
    protected boolean notMovesYet;

    public String getNamePosition() {
        return BoardUtils.getNameTileByCoordinate.get(this.getPosition());
    }

    // constructor
    protected Piece(final int position, final Alliance alliance, PieceType pt) {
        this.position = position;
        this.alliance = alliance;
        this.pieceType = pt;
        this.notMovesYet = true;
    }

    public void setPosition(int d) {
        this.position = d;
        this.notMovesYet = false;
    }

    public boolean getNotMovedYet(){
        return this.notMovesYet;
    }

    public void setNotMovesYet(boolean notMovesYet) {
        this.notMovesYet = notMovesYet;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Alliance getAlliance() {
        if (this == null) { return null;}
        return alliance;
    }

    public Alliance getOppositeAlliance() {
        if (this == null) {return null;}
        if (this.alliance == Alliance.WHITE) {
            return Alliance.BLACK;
        } else {
            return Alliance.WHITE;
        }
    }

    public int getPosition() {
        return position;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public abstract Collection<Move> calculateMoves(final Board board);

    public abstract double getValue();

    public boolean isLockPiece(Board board) {
        boolean result = false;
        //get all moves that now attacked king
        ArrayList<Piece> piecesAttackedKing = new ArrayList<>();
        Collection<Move> arr = board.getAllMovesOfColor(BoardUtils.getOppositeAlliance(this.alliance));
        for (Move move: arr) {
            if(move.getDestination()==board.getKing(this.alliance).getPosition()) {
                piecesAttackedKing.add(move.getPiece());
                board.removePieceFromCollectionPieces(move.getPiece());
            }
        }
        // until here the check if pieces attack king.

        Tile srcTile = board.getTile(this.position);
        Tile.EmptyTile dstTile = new Tile.EmptyTile(this.position, srcTile.getColorTile());
        board.setTile(this.position, dstTile);
        Collection<Move> allMoves = board.getAllMovesOfColor(BoardUtils.getOppositeAlliance(this.alliance));
        for (Move move: allMoves) {
            if(move.getDestination()==board.getKing(this.alliance).getPosition()) {
                result = true;
                break;
            }
        }
        board.setTile(this.position, srcTile);
        //return the pieces that attack King to collections pieces
        for (Piece piece: piecesAttackedKing) {
            board.addPieceToCollectionPieces(piece);
        }
        return result;
    }
}
