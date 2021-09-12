package Game;

import ComputerMoves.AppreciateBoard;
import ComputerMoves.ComputerMoveMaker;
import GUI.Panels.BoardPanel;
import Players.TypePlayer;
import com.chess.engine.Moves.Move;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Alliance;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game {

    private final JFrame jframe;
    private final BoardPanel boardPanel;

    //create the gui of the game
    public Game(Board board){
        jframe = new JFrame("Chess Game - Moriya");
        this.jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.jframe.setSize(new Dimension(600,600));
        jframe.setResizable(false);
        Board.Builder builder = new Board.Builder();
        this.boardPanel = new BoardPanel(board, this);
        jframe.add(this.boardPanel, BorderLayout.CENTER);
        this.jframe.setVisible(true);
    }

//    public void doneHumanMove(){
//        Board board =this.boardPanel.getBoard();
//        if (board.getCurrentPlayer().getTypePlayer() == TypePlayer.COMPUTER) {
//            ComputerMoveMaker.makeComputerMove(this.boardPanel);
//        }
//    }
    public static void printWritingMoves(Move move, Board board) {
        if (BoardUtils.getOppositeAlliance(board.getTurn()) == Alliance.WHITE) {
            int counterMoves = (board.getCounterMoves()/2)+1;

            System.out.print(counterMoves + ".");
        }
        System.out.print(" " + move.getMoveWrittenWithoutStatus());
        System.out.print(board.checkStatusForWrite());
        if (BoardUtils.getOppositeAlliance(board.getTurn()) == Alliance.BLACK) {
            System.out.println();
        }
    }
}
