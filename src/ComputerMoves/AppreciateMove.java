package ComputerMoves;

import com.chess.engine.Moves.Move;

public class AppreciateMove {
    private Move move;
    private double result;

    public AppreciateMove(Move move, double result) {
        this.move = move;
        this.result = result;
    }

    public Move getMove() {
        return move;
    }

    public double getResult() {
        return result;
    }
}
