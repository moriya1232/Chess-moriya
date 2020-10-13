package com.chess.engine.pieces;

import com.chess.engine.Moves.Move;
import com.chess.engine.Moves.Promotion;
import com.chess.engine.Moves.AttackPromotion;
import com.chess.engine.Moves.AttackMajorMove;
import com.chess.engine.Moves.RegularMajorMove;
import com.chess.engine.board.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends Piece{
    private static final int CONDIDATE_BEATING_RIGHT_WHITE = -7;
    private static final int CONDIDATE_WHITE_MOVE = -8;
    private static final int CONDIDATE_WHITE_MOVE_FIRST_STEP_TO_FOURTH_LINE = -16;
    private static final int CONDIDATE_BEATING_LEFT_WHITE = -9;
    private static final int CONDIDATE_BEATING_RIGHT_BLACK = 9;
    private static final int CONDIDATE_BLACK_MOVE = 8;
    private static final int CONDIDATE_BLACK_MOVE_FIRST_STEP_TO_FIFTH_LINE = 16;
    private static final int CONDIDATE_BEATING_LEFT_BLACK = 7;

    public Pawn(int position, Alliance alliance) {
        super(position, alliance, PieceType.PAWN);
        try {
            if (alliance == Alliance.BLACK) {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/BlackPawn.png"));
            } else {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/WhitePawn.png"));
            }
        } catch (Exception e) {
            System.out.println("bug in create pawn");
        }
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        Collection<Move> legalMoves = new ArrayList<>();
        int candidateDestination;
        int candidateBeatingRight;
        int candidateBeatingLeft;
        //white pawn
        if (this.alliance == Alliance.WHITE) {
            candidateDestination = this.getPosition() + CONDIDATE_WHITE_MOVE;
            if (BoardUtils.EIGHTH_COLUMN.get(this.getPosition())) {
                candidateBeatingRight = -1;
                candidateBeatingLeft = this.getPosition() + CONDIDATE_BEATING_LEFT_WHITE;
            } else if (BoardUtils.FIRST_COLUMN.get(this.getPosition())) {
                candidateBeatingLeft = -1;
                candidateBeatingRight = this.getPosition() + CONDIDATE_BEATING_RIGHT_WHITE;
            } else {
                candidateBeatingLeft = this.getPosition() + CONDIDATE_BEATING_LEFT_WHITE;
                candidateBeatingRight = this.getPosition() + CONDIDATE_BEATING_RIGHT_WHITE;
            }
        //black pawn
        } else {
            //compute candidate destinations
            candidateDestination = this.getPosition() + CONDIDATE_BLACK_MOVE;
            if (BoardUtils.EIGHTH_COLUMN.get(this.getPosition()))
            {candidateBeatingRight = -1; candidateBeatingLeft = this.getPosition() + CONDIDATE_BEATING_LEFT_BLACK;}
            else if (BoardUtils.FIRST_COLUMN.get(this.getPosition()))
            {candidateBeatingLeft = -1; candidateBeatingRight = this.getPosition() +CONDIDATE_BEATING_RIGHT_BLACK;}
            else {candidateBeatingLeft = this.getPosition() + CONDIDATE_BEATING_LEFT_BLACK; candidateBeatingRight = this.getPosition() + CONDIDATE_BEATING_RIGHT_BLACK;}
        }
        //next step is coronation
        if ((this.getAlliance() == Alliance.WHITE && BoardUtils.SEVENTH_RAW.get(this.getPosition())) ||
                    this.getAlliance() ==  Alliance.BLACK && BoardUtils.SECOND_RAW.get(this.getPosition())){

            if (!board.getTile(candidateDestination).isTileOccupied()) {
                legalMoves.add(new Promotion(this, candidateDestination, new Queen(candidateDestination,this.alliance)));
                legalMoves.add(new Promotion(this, candidateDestination, new Rook(candidateDestination,this.alliance)));
                legalMoves.add(new Promotion(this, candidateDestination, new Knight(candidateDestination,this.alliance)));
                legalMoves.add(new Promotion(this, candidateDestination, new Bishop(candidateDestination,this.alliance)));
            }
            if (candidateBeatingLeft != -1) {
                Piece pieceDestination = board.getTile(candidateBeatingLeft).getPiece();
                if (pieceDestination != null && pieceDestination.getAlliance() == getOppositeAlliance()) {
                    legalMoves.add(new AttackPromotion(this, candidateBeatingLeft, new Queen(candidateBeatingLeft, this.alliance)));
                    legalMoves.add(new AttackPromotion(this, candidateBeatingLeft, new Rook(candidateBeatingLeft, this.alliance)));
                    legalMoves.add(new AttackPromotion(this, candidateBeatingLeft, new Knight(candidateBeatingLeft, this.alliance)));
                    legalMoves.add(new AttackPromotion(this, candidateBeatingLeft, new Bishop(candidateBeatingLeft, this.alliance)));
                }
            }
            if (candidateBeatingRight!=-1) {
                Piece pieceDestination = board.getTile(candidateBeatingRight).getPiece();
                if (pieceDestination != null &&
                        pieceDestination.getAlliance() == getOppositeAlliance()) {
                    legalMoves.add(new AttackPromotion(this, candidateBeatingRight, new Queen(candidateBeatingRight, this.alliance)));
                    legalMoves.add(new AttackPromotion(this, candidateBeatingRight, new Rook(candidateBeatingRight, this.alliance)));
                    legalMoves.add(new AttackPromotion(this, candidateBeatingRight, new Bishop(candidateBeatingRight, this.alliance)));
                    legalMoves.add(new AttackPromotion(this, candidateBeatingRight, new Knight(candidateBeatingRight, this.alliance)));
                }
            }

        } else {
                //regular step
                if (!board.getTile(candidateDestination).isTileOccupied()) {
                    legalMoves.add(new RegularMajorMove(this, candidateDestination));
                }
                if (candidateBeatingLeft != -1) {
                    Piece pieceDestination = board.getTile(candidateBeatingLeft).getPiece();
                    if (pieceDestination != null && pieceDestination.getAlliance() !=this.alliance) {
                        legalMoves.add(new AttackMajorMove(this, candidateBeatingLeft, pieceDestination));
                    }
                }
                if (candidateBeatingRight!=-1) {
                    Piece pieceDestination = board.getTile(candidateBeatingRight).getPiece();
                    if (pieceDestination != null &&
                            pieceDestination.getAlliance() !=this.alliance) {
                        legalMoves.add(new AttackMajorMove(this, candidateBeatingRight, pieceDestination));
                    }
                }
            }
        if ((this.getAlliance() == Alliance.WHITE && BoardUtils.SECOND_RAW.get(this.getPosition())) ||
                (this.getAlliance() == Alliance.BLACK && BoardUtils.SEVENTH_RAW.get(this.getPosition()))) {
            //first step
            if (this.alliance == Alliance.WHITE) {
                candidateDestination = this.getPosition() + CONDIDATE_WHITE_MOVE_FIRST_STEP_TO_FOURTH_LINE;

            } else {
                candidateDestination = this.getPosition() + CONDIDATE_BLACK_MOVE_FIRST_STEP_TO_FIFTH_LINE;
            }
            if (!board.getTile(candidateDestination).isTileOccupied()) {
                legalMoves.add(new RegularMajorMove(this, candidateDestination));
            }

        }
        return legalMoves;
    }

    @Override
    public double getValue() {
        return 1;
    }
}
