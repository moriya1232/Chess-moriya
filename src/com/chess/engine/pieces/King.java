package com.chess.engine.pieces;

import com.chess.engine.Moves.AttackMajorMove;
import com.chess.engine.Moves.Casteling;
import com.chess.engine.Moves.Move;
import com.chess.engine.Moves.RegularMajorMove;
import com.chess.engine.board.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static com.chess.engine.board.BoardUtils.isInTilesRange;

public class King extends Piece{
    private final static int[] CANDIDATE_KING_MOVES = {-9,-8,-7,-1,1,7,8,9};

    public King(int position, Alliance alliance) {
        super(position, alliance, PieceType.KING);
        try {
            if (alliance == Alliance.BLACK) {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/BlackKing.png"));
            } else {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/WhiteKing.png"));
            }
        } catch (Exception e) {
            System.out.println("bug in create king");
        }
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int move: CANDIDATE_KING_MOVES) {
            int candidateDestination = this.getPosition() + move;
            if (!isValidMoveInEmptyBoard(this.getPosition(),candidateDestination)) {
                continue;
            } else if (board.getTile(candidateDestination).isTileOccupied()) {
                Piece pieceDestination = board.getTile(candidateDestination).getPiece();
                if (pieceDestination == null
                || pieceDestination.getAlliance() == this.getAlliance()
                || board.getTile(candidateDestination).getPiece().getPieceType() == PieceType.KING) {
                    continue;
                } else {
                 legalMoves.add(new AttackMajorMove(this, candidateDestination, board.getTile(candidateDestination).getPiece()));
                continue;
                }
            } else {
                legalMoves.add(new RegularMajorMove(this, candidateDestination));
                continue;
            }
        }
        Piece smallRook = checkSmallCasteling(board);
        if (smallRook!=null) {legalMoves.add(new Casteling(this, smallRook));}
        Piece bigRook = checkBigCasteling(board);
        if(bigRook!=null) {legalMoves.add(new Casteling(this, bigRook));}
        return legalMoves;
    }

    @Override
    public double getValue() {
        return 999999;
    }

    private Piece checkSmallCasteling(Board board) {
        if (this.getAlliance() == Alliance.WHITE) {
            Piece rook = board.getTile(63).getPiece();
            if (rook!=null &&
                    rook.getPieceType() == PieceType.ROOK &&
                    rook.notMovesYet &&
                    this.getPosition() == 60 &&
                    this.notMovesYet &&
                    !board.getTile(61).isTileOccupied() &&
                    !board.getTile(62).isTileOccupied() &&
                    !board.isTileAttacked(61, Alliance.BLACK) &&
                    !board.isTileAttacked(62, Alliance.BLACK)) { return rook;}
            return null;
        } else{
            Piece rook = board.getTile(7).getPiece();
            if (rook!=null &&
                    rook.getPieceType() == PieceType.ROOK &&
                    rook.notMovesYet &&
                    this.getPosition() == 4 &&
                    this.notMovesYet &&
                    !board.getTile(5).isTileOccupied() &&
                    !board.getTile(6).isTileOccupied() &&
            !board.isTileAttacked(5, Alliance.WHITE) &&
                    !board.isTileAttacked(6, Alliance.WHITE)) { return rook;}
            return null;
        }
    }

    public boolean isLookPiece(Board board) {
        return false;
    }

    private Piece checkBigCasteling(Board board) {
        if (this.getAlliance() == Alliance.WHITE) {
            Piece rook = board.getTile(56).getPiece();
            if (rook!=null &&
                    rook.getPieceType() == PieceType.ROOK &&
                    rook.notMovesYet &&
                    this.getPosition() == 60 &&
                    this.notMovesYet &&
                    !board.getTile(57).isTileOccupied() &&
                    !board.getTile(58).isTileOccupied() &&
                    !board.getTile(59).isTileOccupied() &&
                    !board.isTileAttacked(58, Alliance.BLACK) &&
                    !board.isTileAttacked(59, Alliance.BLACK)) { return rook;}
            return null;
        } else{
            Piece rook = board.getTile(0).getPiece();
            if (rook!=null &&
                    rook.getPieceType() == PieceType.ROOK &&
                    rook.notMovesYet &&
                    this.getPosition() == 4 &&
                    this.notMovesYet &&
                    !board.getTile(1).isTileOccupied() &&
                    !board.getTile(2).isTileOccupied() &&
                    !board.getTile(3).isTileOccupied() &&
                    !board.isTileAttacked(2, Alliance.WHITE) &&
                    !board.isTileAttacked(3, Alliance.WHITE))
            { return rook;}
            return null;
        }
    }

    private boolean isValidMoveInEmptyBoard(int currentPosition, int destination) {
        if (!BoardUtils.isInTilesRange(destination) || !isInTilesRange(currentPosition)) {return false;}
        if (isFirstColumnButIllegalDestination(currentPosition,destination)) {return false;}
        if (isEighthColumnButIllegalDestination(currentPosition,destination)) {return false;}
        return true;
    }

    private boolean isFirstColumnButIllegalDestination(final int currentPosition,
                                                        final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN.get(currentPosition) && !currentPositionAtFirstColumnCheckIfDestinationIsLegal(currentPosition, candidateOffset);
    }
    private boolean currentPositionAtFirstColumnCheckIfDestinationIsLegal(int cur, int des) {
        List<Integer> legalMoves = legalMovesFromFirstColumn();
        for (int i: legalMoves) {if (cur+i==des) {return true;} }
        return false;
    }

    private List<Integer> legalMovesFromFirstColumn(){
        List<Integer> legalMoves = Arrays.asList(-8,-7,1,8,9);
        return legalMoves;
    }

    private boolean isEighthColumnButIllegalDestination(final int currentPosition,
                                                               final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN.get(currentPosition) && !currentPositionAtEighthColumnCheckIfDestinationIsLegal(currentPosition, candidateOffset);
    }
    private boolean currentPositionAtEighthColumnCheckIfDestinationIsLegal(int cur, int des) {
        List<Integer> legalMoves = legalMovesFromEighthColumn();
        for (int i: legalMoves) {if (cur+i==des) {return true;} }
        return false;
    }

    private List<Integer> legalMovesFromEighthColumn(){
        List<Integer> legalMoves = Arrays.asList(-9,-8,-1,7,8);
        return legalMoves;
    }
}
