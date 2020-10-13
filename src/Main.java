import Game.Game;
import Players.TypePlayer;
import com.chess.engine.board.Board;

public class Main {
    
    public static void main(String[] args) {
        Board.Builder builder = new Board.Builder();
        Board board = builder.buildStandartBoard(TypePlayer.HUMAN, TypePlayer.COMPUTER);
        Game t = new Game(board);
    }
}
