package com.chess.engine.pieces;

import com.chess.engine.Moves.AttackMajorMove;
import com.chess.engine.Moves.Move;
import com.chess.engine.Moves.RegularMajorMove;
import com.chess.engine.board.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece {
    private final static int[] CANDIDATE_BISHOP_MOVES_ONE_STEP = {-9, -7, 7, 9};

    public Bishop(final int position, final Alliance alliance) {
        super(position, alliance, PieceType.BISHOP);
        try {
            if (alliance == Alliance.BLACK) {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/BlackBishop.png"));
            } else {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/WhiteBishop.png"));
            }
        } catch (Exception e) {
            System.out.println("bug in create bishop");
        }
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int move : CANDIDATE_BISHOP_MOVES_ONE_STEP) {
            int condidateDestination = this.getPosition() + move;

            if (!BoardUtils.isInTilesRange(condidateDestination)) {
                continue;
            }
            Piece destinationPiece =board.getTile(condidateDestination).getPiece();
            if (destinationPiece != null &&
                    destinationPiece.getAlliance() !=this.alliance &&
                    board.getTile(condidateDestination).isTileOccupied()) {
                Piece pieceDestination = board.getTile(condidateDestination).getPiece();
                if (pieceDestination != null && pieceDestination.getAlliance() != this.alliance) {
                    legalMoves.add(new AttackMajorMove(this, condidateDestination, pieceDestination));
                    continue;
                }
            } else if(destinationPiece != null && destinationPiece.getAlliance() ==this.alliance) {
                continue;
            } else if (Tile.isTheCondidateTileAtTheBorders(condidateDestination)) {
                    legalMoves.add(new RegularMajorMove(this, condidateDestination));
                    continue;
            } else {
                while (true) {
                    legalMoves.add(new RegularMajorMove(this, condidateDestination));
                    condidateDestination += move;
                     if (!BoardUtils.isInTilesRange(condidateDestination)) {
                        break;
                    }
                    destinationPiece = board.getTile(condidateDestination).getPiece();
                    if (destinationPiece!=null && destinationPiece.getAlliance() != this.alliance) {
                        if (board.getTile(condidateDestination).isTileOccupied()) {
                            legalMoves.add(new AttackMajorMove(this, condidateDestination, board.getTile(condidateDestination).getPiece()));
                            break;
                        }
                    } else if (destinationPiece!=null && destinationPiece.getAlliance() == this.alliance) {
                        break;
                    } else if (Tile.isTheCondidateTileAtTheBorders(condidateDestination)) {
                            legalMoves.add(new RegularMajorMove(this, condidateDestination));
                            break;
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

}
