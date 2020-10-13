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

public class Knight extends Piece {
    private static final int[] CONDIDATE_KNIGHT_MOVES = { -17, -15 , -10, -6, 6, 10, 15, 17};

    //constructor
    public Knight(final int position, final Alliance alliance) {
        super(position, alliance, PieceType.KNIGHT);
        try {
            if (alliance == Alliance.BLACK) {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/BlackKnight.png"));
            } else {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/WhiteKnight.png"));
            }
        } catch (Exception e) {
            System.out.println("bug in create knight");
        }
    }

    @Override
    public Collection<Move> calculateMoves(final Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int condidateMove : CONDIDATE_KNIGHT_MOVES) {
            int candidateDestinationCoordinate = condidateMove + this.getPosition();

            if (isValidMoveInEmptyBoard(this.getPosition(), candidateDestinationCoordinate)) {

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                // if the tile is not occupied
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new RegularMajorMove(this, candidateDestinationCoordinate));

                }
                // if the tile is occupied by other color
                else {
                    Piece pieceDestination = candidateDestinationTile.getPiece();
                    if (pieceDestination != null &&
                            pieceDestination.getAlliance() != this.alliance) {
                        legalMoves.add(new AttackMajorMove(this, candidateDestinationCoordinate, candidateDestinationTile.getPiece()));
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public double getValue() {
        return 3;
    }

    private boolean isValidMoveInEmptyBoard(int currentPosition, int destination) {
        if (!BoardUtils.isInTilesRange(destination) || !isInTilesRange(currentPosition)) {return false;}
        if (isFirstColumnButIllegalDestination(currentPosition,destination)) {return false;}
        if (isSecondColumnButIllegalDestination(currentPosition,destination)) {return false;}
        if (isSeventhColumnButIllegalDestination(currentPosition,destination)) {return false;}
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
         List<Integer> legalMoves = Arrays.asList(-15,-6,10,17);
        return legalMoves;
    }

    private boolean isSecondColumnButIllegalDestination(final int currentPosition,
                                                  final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN.get(currentPosition) && !currentPositionAtSecondColumnCheckIfDestinationIsLegal(currentPosition, candidateOffset);
    }

    private boolean currentPositionAtSecondColumnCheckIfDestinationIsLegal(int cur, int des) {
        List<Integer> legalMoves = legalMovesFromSecondColumn();
        for (int i: legalMoves) {if (cur+i==des) {return true;} }
        return false;
    }

    private List<Integer> legalMovesFromSecondColumn(){
        List<Integer> legalMoves = Arrays.asList(-17,-15,-6,10,15,17);
        return legalMoves;
    }

    private boolean isSeventhColumnButIllegalDestination(final int currentPosition,
                                                  final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN.get(currentPosition) && !currentPositionAtSeventhColumnCheckIfDestinationIsLegal(currentPosition, candidateOffset);
    }

    private boolean currentPositionAtSeventhColumnCheckIfDestinationIsLegal(int cur, int des) {
        List<Integer> legalMoves = legalMovesFromSeventhColumn();
        for (int i: legalMoves) {if (cur+i==des) {return true;} }
        return false;
    }

    private List<Integer> legalMovesFromSeventhColumn(){
        List<Integer> legalMoves = Arrays.asList(-17,-15,-10,6,15,17);
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
        List<Integer> legalMoves = Arrays.asList(-17,-10,6,15);
        return legalMoves;
    }
}

