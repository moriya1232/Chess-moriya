package com.chess.engine.board;

import Players.TypePlayer;
import com.chess.engine.pieces.Alliance;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardUtils {
    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_IN_ROW = 8;
    public static final int NUM_TILES_IN_COLUMN = 8;
    public static final List<Boolean> FIRST_COLUMN = initColumn(0);
    public static final List<Boolean> SECOND_COLUMN = initColumn(1);
    public static final List<Boolean> THIRD_COLUMN = initColumn(2);
    public static final List<Boolean> FOURTH_COLUMN = initColumn(3);
    public static final List<Boolean> FIFTH_COLUMN = initColumn(4);
    public static final List<Boolean> SIXTH_COLUMN = initColumn(5);
    public static final List<Boolean> SEVENTH_COLUMN = initColumn(6);
    public static final List<Boolean> EIGHTH_COLUMN = initColumn(7);
    public static final List<Boolean> FIRST_RAW = initRaw(0);
    public static final List<Boolean> SECOND_RAW = initRaw(1);
    public static final List<Boolean> THIRD_RAW = initRaw(2);
    public static final List<Boolean> FORTH_RAW = initRaw(3);
    public static final List<Boolean> FIFTH_RAW = initRaw(4);
    public static final List<Boolean> SIXTH_RAW = initRaw(5);
    public static final List<Boolean> SEVENTH_RAW = initRaw(6);
    public static final List<Boolean> EIGHTH_RAW = initRaw(7);
    public static final HashMap<Integer, String> getNameTileByCoordinate= createMapNameTileByCoordinate();
    public static final HashMap<String, Integer> getCoordinateTileByName= createMapCoordinateTileByName();

    public static Boolean isInTilesRange(int numTile) { return numTile>=0 && numTile < 64; }

    public static List<Boolean> initRaw(final int raw) {
        List<Boolean> tiles = initAllListToFalse();
        int firstTileInRaw = (NUM_TILES-NUM_TILES_IN_ROW) - raw*NUM_TILES_IN_ROW;
        for (int i=0; i<NUM_TILES_IN_ROW; i++){
            tiles.set(firstTileInRaw+i, true);
        }
        return tiles;
    }

    public static HashMap<Integer, String> createMapNameTileByCoordinate () {
        HashMap<Integer, String> map = new HashMap<>();
        Board.Builder builder = new Board.Builder();
        Board board = builder.buildStandartBoard(TypePlayer.HUMAN, TypePlayer.HUMAN);
        for (Tile t : board.getTiles().values()) {
            map.put(t.getTileCoordinate(), t.getNameTile());
        }
        return map;
    }

    public static HashMap<String, Integer> createMapCoordinateTileByName(){
        HashMap<String, Integer> map = new HashMap<>();
        Board.Builder builder = new Board.Builder();
        Board board = builder.buildStandartBoard(TypePlayer.HUMAN, TypePlayer.HUMAN);
        for (Tile t : board.getTiles().values()) {
            map.put(t.getNameTile(), t.getTileCoordinate());
        }
        return map;
    }

    public static List<Boolean> initColumn(final int column) {
        int counter = column;
        List<Boolean> tiles = initAllListToFalse();
        while (counter<NUM_TILES) {
            tiles.set(counter, true);
            counter+=NUM_TILES_IN_ROW;
        }
        return tiles;

    }

    public static List<Boolean> initAllListToFalse() {
        List<Boolean> tiles = new ArrayList<>();
        for (int i = 0; i<NUM_TILES; i++) {
            tiles.add(false);
        }
        return tiles;
    }

    public static Alliance getOppositeAlliance (Alliance alliance) {
        if (alliance == Alliance.BLACK) {return Alliance.WHITE;}
        else { return Alliance.BLACK;}
    }
}
