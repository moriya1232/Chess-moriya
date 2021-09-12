package GUI.Panels;

import Game.Status;
import Players.TypePlayer;
import com.chess.engine.Moves.Move;
import com.chess.engine.Moves.MoveFactory;
import com.chess.engine.board.Board;
import com.chess.engine.board.ColorTile;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class TilePanel extends JPanel {

    //public static String COLOR_BLACK_TILE = "#533b3b";
    public static String COLOR_BLACK_TILE = "#000000";
    public static String COLOR_WHITE_TILE = "#ffffff";
    public static String COLOR_GREEN_TILE_SELECTED = "#49bf25";
    public static int GAME_OVER = -2;
    private Tile tileSrc;
    private final Tile tile;
    private BoardPanel boardPanel;

    public TilePanel(BoardPanel boardPanel, Tile tile){
        super(new CardLayout());
        this.tile = tile;
        this.boardPanel = boardPanel;
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (BoardPanel.tileCoordinatePressed == GAME_OVER) {
                    return;
                }
                if (boardPanel.getBoard().getCurrentPlayer().getTypePlayer() == TypePlayer.HUMAN) {
                    int tilePressed = BoardPanel.tileCoordinatePressed;
                    if (isRightMouseButton(e) || tilePressed == tile.getTileCoordinate()) {
                        BoardPanel.tileCoordinatePressed = -1;
                        paintTileSelected();
                    } else if (isLeftMouseButton(e)) {
                        if (tilePressed == -1) {
                            Piece piece = tile.getPiece();
                            if (piece != null && piece.getAlliance() == boardPanel.getBoard().getTurn()) {
                                BoardPanel.tileCoordinatePressed = tile.getTileCoordinate();
                                paintTileSelected();
                                paintOptionalMoves(tile.getTileCoordinate(), boardPanel.getBoard());
                            }
                        }
                        else {
                            MoveFactory moveFactory = new MoveFactory();
                            Move move = moveFactory.createMove(boardPanel.getBoard(), boardPanel.getBoard().getTile(tilePressed).getPiece(), tile.getTileCoordinate());
                            if (move == null) {
                                boardPanel.getTilePanels().get(BoardPanel.tileCoordinatePressed).setRegularColorToTiles();
                                BoardPanel.tileCoordinatePressed = -1;
                                return;
                            }

                            boardPanel.getBoard().getCurrentPlayer().makeMove(boardPanel.getBoard(), move);
                            Game.Game.printWritingMoves(move, boardPanel.getBoard());
                            boardPanel.drawMove(boardPanel.getBoard());
                            BoardPanel.tileCoordinatePressed = -1;
                            if(boardPanel.getBoard().getStatus() == Status.CHECKMATE
                            || boardPanel.getBoard().getStatus() == Status.DRAW) {
                                System.out.println("GAME OVER");
                                BoardPanel.tileCoordinatePressed = GAME_OVER;
                            }
                            boardPanel.doneHumanMove();
//                            boardPanel.getGame().doneHumanMove();
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void paintOptionalMoves(int tileSelected, Board board){
        if (BoardPanel.tileCoordinatePressed == -1 || BoardPanel.tileCoordinatePressed == -2) {
            return;
        }
        Piece piece = board.getPieceByCoordinate(tileSelected);
        if (piece != null) {
            for (Move m :board.getAllLegalMovesOfPiece(piece)) {
                boardPanel.getTilePanels().get(m.getDestination()).setBackground(Color.decode(COLOR_GREEN_TILE_SELECTED));
                boardPanel.getTilePanels().get(m.getDestination()).setBorder(BorderFactory.createRaisedBevelBorder());
            }
            validate();
            repaint();
        }
    }

    public void drawTile(){
        setRegularColorToTiles();
        if (this.tile.isTileOccupied()) {
            add(new JLabel(tile.getPiece().getImageIcon()));
        }
        validate();
        repaint();
    }

    public void paintTileSelected(){
        if (this.tile.getTileCoordinate() == BoardPanel.tileCoordinatePressed) {
            setBackground(Color.decode(COLOR_GREEN_TILE_SELECTED));
        } else {
            setRegularColorToTiles();
        }
        validate();
        repaint();
    }

    public void setRegularColorToTiles() {
        for (TilePanel t: boardPanel.getTilePanels()) {
            if (t.tile.getColorTile() == ColorTile.BLACK) {
                t.setBackground(Color.decode(COLOR_BLACK_TILE));
            } else {
                t.setBackground(Color.decode(COLOR_WHITE_TILE));
            }
        }
    }
}
