package Players;

import com.chess.engine.Moves.Move;
import com.chess.engine.Moves.MoveTransition;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Alliance;

public abstract class Player {
    private TypePlayer typePlayer;

    public Player(TypePlayer typePlayer) {
        this.typePlayer = typePlayer;
    }

    public TypePlayer getTypePlayer() {
        return typePlayer;
    }


    public void makeMove (Board board, Move move) {
        board.executeMove(move);
        board.changeStatus(move.getPiece().getAlliance());
    }
}
