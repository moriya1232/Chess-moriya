package com.chess.engine.pieces;

import com.chess.engine.Moves.Move;
import com.chess.engine.board.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    public Queen(int position, Alliance alliance) {
        super(position, alliance, PieceType.QUEEN);
        try {
            if (alliance == Alliance.BLACK) {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/BlackQueen.png"));
            } else {
                this.imageIcon = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("resources/icons/WhiteQueen.png"));
            }
        } catch (Exception e) {
            System.out.println("bug in create queen");
        }
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        try {
            Collection<Move> tempRook = new Rook(this.getPosition(), this.alliance).calculateMoves(board);
            Collection<Move> tempBishop = new Bishop(this.getPosition(), this.alliance).calculateMoves(board);
            legalMoves.addAll(tempRook);
            legalMoves.addAll(tempBishop);
            for (Move move:legalMoves) {
                move.setPiece(this);
            }
        } catch (Exception e) {
            System.out.println("error in create Rook and Bishop in Queen class.");
        }
        return legalMoves;
    }

    @Override
    public double getValue() {
        return 9;
    }
}

