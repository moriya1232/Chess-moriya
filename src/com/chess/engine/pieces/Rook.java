package com.chess.engine.pieces;

import com.chess.engine.Moves.AttackMajorMove;
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

public class Rook extends Piece {
    private final static int[] CANDIDATE_ROOK_MOVES_ONE_STEP = {-8,-1,1,8};

    public Rook(int position, Alliance alliance) {
        super(position, alliance, PieceType.ROOK);
        try {
            if (alliance == Alliance.BLACK) {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/BlackRook.png"));
            } else {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/WhiteRook.png"));
            }
        } catch (Exception e) {
            System.out.println("bug in create rook");
        }
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        int[] whereToGo =CANDIDATE_ROOK_MOVES_ONE_STEP;
        if (BoardUtils.EIGHTH_COLUMN.get(this.getPosition())) {whereToGo = new int[]{-8, -1, 8}; }
        else if (BoardUtils.FIRST_COLUMN.get(this.getPosition())) {whereToGo = new int[]{-8, 1, 8};}
        for (int move: whereToGo) {
            int condidateDestination = this.getPosition() + move;
            if (!isValidMoveInEmptyBoard(this.getPosition(), condidateDestination)){
                continue;
            }
            Piece destinationPiece = board.getTile(condidateDestination).getPiece();
            if ( destinationPiece!=null && destinationPiece.getAlliance() == this.alliance) {
                continue;
            } else if (board.getTile(condidateDestination).isTileOccupied() &&
                    destinationPiece.getAlliance() != this.alliance) {
                legalMoves.add(new AttackMajorMove(this, condidateDestination, destinationPiece));
                continue;
            } else if (Tile.isTheCondidateTileAtTheBorders(condidateDestination) &&
            !Tile.isTheCondidateTileAtTheBorders(this.getPosition())) {
                legalMoves.add(new RegularMajorMove(this, condidateDestination));
                continue;
            } else {
                while (true) {
                    legalMoves.add(new RegularMajorMove(this, condidateDestination));
                    if ((move == 1 && BoardUtils.EIGHTH_COLUMN.get(condidateDestination)) ||
                            (move == -1 && BoardUtils.FIRST_COLUMN.get(condidateDestination))) {
                        break;
                    }
                    condidateDestination += move;
                    if (!BoardUtils.isInTilesRange(condidateDestination)) {
                        break;
                    }
                    destinationPiece = board.getTile(condidateDestination).getPiece();
                    if (board.getTile(condidateDestination).isTileOccupied()) {
                        if (destinationPiece != null && destinationPiece.getAlliance() != this.alliance) {
                            legalMoves.add(new AttackMajorMove(this, condidateDestination, destinationPiece));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public double getValue() {
        return 5;
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
        List<Integer> legalMoves = Arrays.asList(-8,1,8);
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
        List<Integer> legalMoves = Arrays.asList(-8,-1,8);
        return legalMoves;
    }
}
