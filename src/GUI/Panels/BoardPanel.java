package GUI.Panels;

import Game.Game;
import com.chess.engine.Moves.Move;
import com.chess.engine.Moves.MoveTransition;
import com.chess.engine.board.Board;
import com.chess.engine.board.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoardPanel extends JPanel {

    private Board board;
    private ArrayList<TilePanel> tilePanels;
    private Game game;
    public static int tileCoordinatePressed = -1;


    public BoardPanel(Board board, Game game){
        super(new GridLayout(8,8));
        this.game = game;
        updateMembers(board);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.decode("#8B4726"));
        drawBoard(board);
        validate();
    }

    public ArrayList<TilePanel> getTilePanels() {
        return tilePanels;
    }

    public Board getBoard() {
        return board;
    }

    public void updateMembers(Board board) {
        this.board = board;
        this.tilePanels = createTilePanels(board);
    }

    private ArrayList<TilePanel> createTilePanels(Board board) {
        ArrayList<TilePanel> tilePanels = new ArrayList<>();
        for (Tile t: board.getTiles().values()) {
            tilePanels.add(new TilePanel(this, t));
        }
        return tilePanels;
    }

    private Board drawBoard(final Board board) {
        updateMembers(board);
        for (final TilePanel tilePanel : this.tilePanels) {
            tilePanel.drawTile();
            add(tilePanel);
        }
        validate();
        repaint();
        return this.board;
    }

    public void drawMove(Board board){
        removeAll();
        drawBoard(board);
        validate();
        repaint();
    }

    public Game getGame() {
        return game;
    }
}
