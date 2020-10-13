package ComputerMoves;

import GUI.Panels.BoardPanel;
import Game.Status;
import com.chess.engine.Moves.Move;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.Alliance;
import com.chess.engine.pieces.Piece;
import java.util.Collection;

public class ComputerMoveMaker {
    public static int GAME_OVER = -2;
    public static void makeComputerMove(BoardPanel boardPanel) {
        int depth = 3;
        Board board = boardPanel.getBoard();
        Move bestMove = getTheBestNextMove(board, depth);
        System.out.println("best move is: " + BoardUtils.getNameTileByCoordinate.get(bestMove.getPiece().getPosition()) + " to " + BoardUtils.getNameTileByCoordinate.get(bestMove.getDestination()));
        board.getCurrentPlayer().makeMove(board, bestMove);
        Game.Game.printWritingMoves(bestMove, board);
        boardPanel.drawMove(board);
        if(boardPanel.getBoard().getStatus() == Status.CHECKMATE
                || boardPanel.getBoard().getStatus() == Status.DRAW) {
            System.out.println("GAME OVER");
            BoardPanel.tileCoordinatePressed = GAME_OVER;
        }
        BoardPanel.tileCoordinatePressed = -1;
    }

    public static Move getTheBestNextMove(Board board, int depth) {
        AppreciateMove bestMove;
            Alliance turn = board.getTurn();
            if (turn==Alliance.WHITE) {
                bestMove = max(board, depth);
            } else {
                bestMove = min(board, depth);
            }
        return bestMove.getMove();
    }

    public static AppreciateMove max(Board board, int depth) {
        if (board.getStatus() == Status.CHECKMATE) {return new AppreciateMove(null, -Double.MAX_VALUE);}
        if (depth<=0) {return new AppreciateMove(null, AppreciateBoard.appreciate(board));}
        Collection<Move> allMoves = board.getAllLegalMovesOfColor(Alliance.WHITE);
        double bestAppreciate = -Double.MAX_VALUE;
        Move bestMove = null;
        for (Move move: allMoves) {
            //save data that will lose in execute move.
            Piece pieceAttacked = board.getPieceByCoordinate(move.getDestination());
            boolean isMovedYet = move.getPiece().getNotMovedYet();
            Status status = board.getStatus();
            board.getCurrentPlayer().makeMove(board, move);

            AppreciateMove appreciate = min(board, depth -1);
            if (appreciate.getResult() > bestAppreciate) {
                bestAppreciate = appreciate.getResult();
                bestMove = move;
            }
            board.redo(move, pieceAttacked);
            move.getPiece().setNotMovesYet(isMovedYet);
            board.setStatus(status);
        }
        return new AppreciateMove(bestMove, bestAppreciate);
    }

    public static AppreciateMove min(Board board, int depth) {
        if (board.getStatus() == Status.CHECKMATE) {return new AppreciateMove(null, Double.MAX_VALUE);}
        if (depth<=0) {return new AppreciateMove(null, AppreciateBoard.appreciate(board));}

        Collection<Move> allMoves = board.getAllLegalMovesOfColor(Alliance.BLACK);
        double bestAppreciate = Double.MAX_VALUE;
        Move bestMove = null;
        Status status = board.getStatus();
        for (Move move: allMoves) {
            Piece pieceAttacked = board.getPieceByCoordinate(move.getDestination());
            boolean isMovedYet = move.getPiece().getNotMovedYet();
            board.getCurrentPlayer().makeMove(board, move);
            AppreciateMove appreciate = max(board, depth - 1);
            if (appreciate.getResult() < bestAppreciate) {
                bestAppreciate = appreciate.getResult();
                bestMove = move;
            }
            board.redo(move, pieceAttacked);
            move.getPiece().setNotMovesYet(isMovedYet);
            board.setStatus(status);
        }
        return new AppreciateMove(bestMove, bestAppreciate);
    }
//    public static Move randomMove(Collection<Move> coll) {
//        int num = (int) (Math.random()* coll.size());
//        for (Move move:coll) {
//            if (--num<0) {
//                return move;
//            }
//        }
//        throw new AssertionError();
//    }
}
