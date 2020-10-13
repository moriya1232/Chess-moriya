package com.chess.engine.board;

import Game.Status;
import Players.BlackPlayer;
import Players.Player;
import Players.TypePlayer;
import Players.WhitePlayer;
import com.chess.engine.Moves.*;
import com.chess.engine.pieces.*;
import java.util.*;

public final class Board {
    private Map<Integer, Tile> tiles;
    private Alliance turn;
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;
    private Status status;
    private BlackPlayer blackPlayer;
    private WhitePlayer whitePlayer;
    private int counterMoves;
    private King whiteKing;
    private King blackKing;

    // constructor
    public Board(TypePlayer white, TypePlayer black) {
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.whiteKing = null;
        this.blackKing = null;
        this.tiles = new HashMap<>();
        this.turn = null;
        this.status = null;
        this.blackPlayer = new BlackPlayer(black);
        this.whitePlayer = new WhitePlayer(white);
        this.counterMoves = 0;
    }

    public void setTile(int coordinate, Tile tile) {
        this.tiles.put(coordinate, tile);
    }

    public int getCounterMoves() {
        return counterMoves;
    }

    public void removePieceFromCollectionPieces(Piece piece) {
        if (piece.getAlliance() == Alliance.WHITE) {
            this.whitePieces.remove(piece);
        } else {
            this.blackPieces.remove(piece);
        }
    }

    public boolean isTileAttacked(int tileCoordinate, Alliance color) {
        for (Move move: getAllMovesOfColor(color)) {
            if(move.getDestination() == tileCoordinate) {
                return true;
            }
        }
        return false;
    }

    public void addPieceToCollectionPieces(Piece piece) {
        if (piece.getAlliance() == Alliance.WHITE) {
            this.whitePieces.add(piece);
        } else {
            this.blackPieces.add(piece);
        }
    }

    public Collection<Piece> getPiecesByColor(Alliance color) {
        if (color == Alliance.WHITE) {
            return this.whitePieces;
        } else {
            return this.blackPieces;
        }
    }
//
//    public String getWritingMovesOfAllGame() {
//        String string = "";
//        int counter = 1;
//        int counterMoves = 0;
//        for (Move move: this.historyMoves) {
//            if (counter%2 == 1) {
//                counterMoves++;
//                string += counterMoves;
//                string += ".";
//            }
//            string+=" ";
//            string += move.getMoveWrittenWithoutStatus();
//            string += checkStatusForWrite();
//            if (counter%2 == 0) {
//                string += '\n';
//            }
//            counter++;
//        }
//        return string;
//    }

    public String checkStatusForWrite() {
        if (this.status == getCheckStatusByAlliance(this.turn)) { return "+";}
        else if (this.status == Status.CHECKMATE) {return "#" + '\n';}
        else if (this.status == Status.DRAW) {return " 1/2"; }
        return "";
    }

    public Status getCheckStatusByAlliance(Alliance alliance) {
        if (alliance == Alliance.WHITE) {return Status.CHECK_ON_WHITE;}
        else {return Status.CHECK_ON_BLACK;}
    }

    public Player getCurrentPlayer() {
        if (this.turn == Alliance.WHITE) {
            return this.whitePlayer;
        } else {
            return this.blackPlayer;
        }
    }

    //this function dont return the member isMovedYet and if the move was an attack move it doesnt return the piece that attacked!!
    public void redo(Move move, Piece destinationPiece) {
        Piece p = move.getPiece();
        ColorTile srcColorTile = getTile(move.getSourceTileCoordinate()).getColorTile();
        ColorTile dstColorTile = getTile(move.getDestination()).getColorTile();
        Tile dstTile;
        if (destinationPiece == null) {
            dstTile = new Tile.EmptyTile(move.getDestination(), dstColorTile);
        } else {
            dstTile = new Tile.OccupiedTile(move.getDestination(), destinationPiece, dstColorTile);
            addPieceToCollectionPieces(destinationPiece);
            setKing(destinationPiece.getAlliance());
        }
        this.tiles.put(move.getDestination(), dstTile);
        if (p.getAlliance() == Alliance.WHITE) {
            whitePieces.remove(p);
            if (move instanceof Promotion || move instanceof AttackPromotion) {
                p = move.getPiece();
            } else {
                p.setPosition(move.getSourceTileCoordinate());
            }
            whitePieces.add(p);

        } else {
            blackPieces.remove(p);
            if (move instanceof Promotion || move instanceof AttackPromotion) {
                p = move.getPiece();
            } else {
                p.setPosition(move.getSourceTileCoordinate());
            }
            blackPieces.add(p);
        }
        Tile.OccupiedTile srcOccupiedTile= new Tile.OccupiedTile(move.getSourceTileCoordinate(), p,srcColorTile);
        this.tiles.put(move.getSourceTileCoordinate(), srcOccupiedTile);
        if (move instanceof Casteling) {
            int srcRookPos = ((Casteling)move).getRook().getPosition();
            Tile.EmptyTile rookEmptyTile = new Tile.EmptyTile(srcRookPos, getTile(srcRookPos).getColorTile());
            this.tiles.put(srcRookPos, rookEmptyTile);
            int rookPos = 0;
            if (srcRookPos == 5) {
                rookPos = 7;
            } else if (srcRookPos == 61) {
                rookPos = 63;
            } else if (srcRookPos == 59) {
                rookPos = 56;
            }
            Tile.OccupiedTile rookOccupiedTile= new Tile.OccupiedTile(rookPos, ((Casteling)move).getRook() , getTile(rookPos).getColorTile());
            this.tiles.put(rookPos, rookOccupiedTile);
            ((Casteling)move).getRook().setPosition(rookPos);
        }
        this.counterMoves--;
        changeTurn();
    }

    public void setKing(Alliance color) {
        for (Piece p : getPiecesByColor(color)) {
            if (p.getPieceType()==PieceType.KING && p.getAlliance() == Alliance.WHITE) {
                this.whiteKing = (King) p;
            } else if (p.getPieceType()==PieceType.KING && p.getAlliance() == Alliance.BLACK) {
                this.blackKing = (King) p;
            }
        }
    }

    public void executeMove(Move move) {
        if (this == null) {System.out.println("problem here! in board class");return;}
        Piece p = move.getPiece();
        ColorTile srcColorTile = getTile(p.getPosition()).getColorTile();
        ColorTile dstColorTile = getTile(move.getDestination()).getColorTile();
        Tile.EmptyTile srcEmptyTile = new Tile.EmptyTile(p.getPosition(), srcColorTile);
        this.tiles.put(p.getPosition(), srcEmptyTile);
            removePieceFromCollectionPieces(p);
            if (move instanceof Promotion) {
                p =((Promotion) move).getPieceAfterCoronation();
            } else if ( move instanceof AttackPromotion) {
                if (getTile(move.getDestination()).getPiece() != null) {
                    removePieceFromCollectionPieces(getTile(move.getDestination()).getPiece());
                }
                p =((AttackPromotion) move).getPieceAfterCoronation();
            } else {
                if (getTile(move.getDestination()).getPiece() != null) {
                    removePieceFromCollectionPieces(getTile(move.getDestination()).getPiece());
                }
                p.setPosition(move.getDestination());
            }
            addPieceToCollectionPieces(p);

        Tile.OccupiedTile dstOccupiedTile= new Tile.OccupiedTile(p.getPosition(), p,dstColorTile);
        this.tiles.put(p.getPosition(), dstOccupiedTile);
        if ( move instanceof Casteling){
            int destRook = ((Casteling)move).getDestRook();
            Rook rook = ((Casteling)move).getRook();
            ColorTile srcRookColor = getTile(rook.getPosition()).getColorTile();
            ColorTile dstRookColor = getTile(destRook).getColorTile();
            Tile.EmptyTile srcRookEmptyTile = new Tile.EmptyTile(rook.getPosition(), srcRookColor);
            this.tiles.put(rook.getPosition(), srcRookEmptyTile);
            if (rook.getAlliance() == Alliance.WHITE) {
                whitePieces.remove(rook);
                rook.setPosition(destRook);
                whitePieces.add(rook);
            } else {
                blackPieces.remove(rook);
                rook.setPosition(destRook);
                blackPieces.add(rook);
            }
            Tile.OccupiedTile dstRookTile= new Tile.OccupiedTile(destRook, rook,dstRookColor);
            this.tiles.put(destRook, dstRookTile);
        }

        changeTurn();
        this.counterMoves++;
    }
    
    public void changeStatus(Alliance lastMoveColor) {
        King king = getKingByColor(BoardUtils.getOppositeAlliance(lastMoveColor));
        ArrayList<Move> movesAttackKing = movesAttackKing(king);
        if (movesAttackKing != null && movesAttackKing.size() > 0) {
                this.status = getCheckStatusByAlliance(BoardUtils.getOppositeAlliance(lastMoveColor));
                Collection<Move> optionalMove = getAllLegalMovesOfColor(king.getAlliance());
                if (optionalMove.size() == 0) {
                    this.status = Status.CHECKMATE;
                }
        } else {
            this.status = Status.REGULARY_TURN;
        }
    }

    public ArrayList<Move> movesAttackKing(King king) {
        ArrayList<Move> movesAttackKing = new ArrayList<>();
            for (Move move: getAllMovesOfColor(BoardUtils.getOppositeAlliance(king.getAlliance()))) {
                if (move.getDestination() == king.getPosition()) {
                    movesAttackKing.add(move);
                }
            }
        return movesAttackKing;
    }

    //this function will return all the moves (except of king move), but it will return illegal moves too (like detention move)
    public Collection<Move> getAllMovesOfColor(Alliance color) {
        Collection<Move> allMoves = new ArrayList<>();
        for (Piece p : getPiecesByColor(color)) {
           if (p.getPieceType()==PieceType.KING) {continue;}
           allMoves.addAll(p.calculateMoves(this));
        }
        return allMoves;
    }

    public Collection<Move> getAllLegalMovesOfColor (Alliance color) {
        if (this.status == Status.REGULARY_TURN || this.status == getCheckStatusByAlliance(BoardUtils.getOppositeAlliance(color))) {
            Collection<Move> allMoves = new ArrayList<>();
            for (Piece p : getPiecesByColor(color)) {
                if (!p.isLockPiece(this)) {
                    allMoves.addAll(p.calculateMoves(this));
                }
                // if the piece is detention
                else {
                    Tile src = this.tiles.get(p.getPosition());
                    Tile.EmptyTile emptyTile = new Tile.EmptyTile(p.getPosition(), null);
                    this.tiles.put(p.getPosition(), emptyTile);
                    ArrayList<Integer> positions = getAllPositionsToSaveKing(getKingByColor(color));
                    for (Move attackTheAttacker: p.calculateMoves(this)) {
                        if (positions.contains(attackTheAttacker.getDestination())) {
                            allMoves.add(attackTheAttacker);
                        }
                    }
                    this.tiles.put(p.getPosition(), src);
                }
            }
            return allMoves;
            // check
        } else if(this.status == getCheckStatusByAlliance(color)) {
            Collection<Move> allMoves = new ArrayList<>();
            for (Piece p : getPiecesByColor(color)) {
                allMoves.addAll(p.calculateMoves(this));
            }
            // check all the moves if after them, still the king is attacked.
            ArrayList<Move> legalMoves = new ArrayList<>();
            for (Move move: allMoves) {
                Piece dstPieceForRedo = getTile(move.getDestination()).getPiece();
                boolean notMovedYet = move.getPiece().getNotMovedYet();
                Status status = this.status;
                executeMove(move);
                ArrayList<Move> movesAttack = movesAttackKing(getKingByColor(color));
                if (movesAttack.size() == 0) {
                    legalMoves.add(move);
                }

                redo(move, dstPieceForRedo);
                move.getPiece().setNotMovesYet(notMovedYet);
                setStatus(status);
            }
            return legalMoves;
        } else {
            System.out.println("get all moves, but the status is checkmate!");
            return null;
        }
    }

    public ArrayList<Move> getAllLegalMovesOfPiece(Piece piece) {
        Collection<Move> arr = getAllLegalMovesOfColor(piece.getAlliance());
        ArrayList<Move> result= new ArrayList<>();
        for (Move m: arr) {
            if (m.getPiece() == piece) {
                result.add(m);
            }
        }
        return result;
    }

    public ArrayList<Integer> getAllPositionsToSaveKing(King king) {
        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Move> attackers = movesAttackKing(king);
        if (attackers.size()>1 || attackers.size()<=0) {
            return arr;
        }
        Move attacker = attackers.get(0);
        arr.add(attacker.getPiece().getPosition());
        for (Move m: attacker.getPiece().calculateMoves(this)){
            if (m.getPiece().getPosition() > king.getPosition()) {
                if(m.getDestination() <= m.getPiece().getPosition() &&
                    m.getDestination() >= king.getPosition()) {arr.add(m.getDestination());}
            } else {
                if(m.getDestination() >= m.getPiece().getPosition() &&
                       m.getDestination() <= king.getPosition()) {arr.add(m.getDestination());}

            }
        }
        return arr;
    }

    public King getKing (Alliance color) {
        if (color == Alliance.WHITE) {
            for (Piece p: this.whitePieces) {
                if (p.getPieceType() == PieceType.KING) {
                    return (King) p;
                }
            }
        }  else {
            for (Piece p: this.blackPieces) {
                if (p.getPieceType() == PieceType.KING) {
                    return (King) p;
                }
            }
        }
        System.out.println("no king!!!");
        return null;
    }

    public King getKingByColor (Alliance color) {
        if (color == Alliance.WHITE) {
            return this.whiteKing;
        } else {
            return this.blackKing;
        }
    }

    public Piece getPieceByCoordinate (int tile) {
        return this.tiles.get(tile).getPiece();
    }

    public Collection<Piece> getBlackPieces() {
        return blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return whitePieces;
    }

    public Map<Integer, Tile> getTiles() {
        return tiles;
    }

    public Alliance getTurn() {
        return turn;
    }

    public void setTurn(Alliance turn) {
        this.turn = turn;
    }

    public void setTiles(Map<Integer, Tile> tiles) {
        this.tiles = tiles;
    }

    public Collection<Piece> getAllPieces(){
        List<Piece> pieces = new ArrayList<>();
        for (Piece p: this.whitePieces) {
            pieces.add(p);
        }
        for (Piece p:this.blackPieces) {
            pieces.add(p);
        }
        return pieces;
    }

    public void changeTurn(){
        if (this.turn == Alliance.BLACK) {this.turn = Alliance.WHITE;}
        else if (this.turn == Alliance.WHITE) {this.turn = Alliance.BLACK;}
    }

    public Tile getTile (int coordinate) {
        if (coordinate == -1) {System.out.println("insert invalid coordinate"); return null;  }
        return tiles.get(coordinate);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status s) {
        this.status = s;
    }

    public void printTheBoard() {
        int counter = 0;
        for (Tile tile : this.tiles.values()) {
            if (!tile.isTileOccupied()) {
                System.out.print(" - ");
            } else {
                if (tile.getPiece().getAlliance() == Alliance.WHITE) {
                    if (tile.getPiece().getPieceType() == PieceType.KING) {
                        System.out.print(" X ");
                    } else if (tile.getPiece().getPieceType() == PieceType.QUEEN) {
                        System.out.print(" Q ");
                    } else if (tile.getPiece().getPieceType() == PieceType.ROOK) {
                        System.out.print(" R ");
                    } else if (tile.getPiece().getPieceType() == PieceType.BISHOP) {
                        System.out.print(" B ");
                    } else if (tile.getPiece().getPieceType() == PieceType.KNIGHT) {
                        System.out.print(" K ");
                    } else if (tile.getPiece().getPieceType() == PieceType.PAWN) {
                        System.out.print(" P ");
                    }
                } else if (tile.getPiece().getAlliance() == Alliance.BLACK) {
                    if (tile.getPiece().getPieceType() == PieceType.KING) {
                        System.out.print(" x ");
                    } else if (tile.getPiece().getPieceType() == PieceType.QUEEN) {
                        System.out.print(" q ");
                    } else if (tile.getPiece().getPieceType() == PieceType.ROOK) {
                        System.out.print(" r ");
                    } else if (tile.getPiece().getPieceType() == PieceType.BISHOP) {
                        System.out.print(" b ");
                    } else if (tile.getPiece().getPieceType() == PieceType.KNIGHT) {
                        System.out.print(" k ");
                    } else if (tile.getPiece().getPieceType() == PieceType.PAWN) {
                        System.out.print(" p ");
                    }
                }
            }
            counter++;
            if (counter % BoardUtils.NUM_TILES_IN_ROW == 0) {
                System.out.println();
            }
        }
    }

    public void printNameTiles() {
        int counter = 0;
        for (Tile tile : this.tiles.values()) {
            System.out.print(" " + tile.getNameTile() +" ");
            counter++;
            if (counter % BoardUtils.NUM_TILES_IN_ROW == 0) {
                System.out.println();
            }
        }
    }

    public Map<Integer, Tile> createTiles() {
        Map<Integer, Tile> map = new HashMap<>();
        int counter = 0;
        for (int i=0; i<BoardUtils.NUM_TILES_IN_ROW; i++){
            for( int j=0; j<BoardUtils.NUM_TILES_IN_COLUMN; j++) {
                Tile t = Tile.createTile(counter, null);
                map.put(t.getTileCoordinate(),t);
                counter++;
            }
        }
        for (Piece piece : getAllPieces()) {
            Tile t = Tile.createTile(piece.getPosition(), piece);
            map.put(t.getTileCoordinate(), t);
        }
        return map;
    }

    public void putPiecesBlackStandart(){
        try {
            blackPieces.add(new Rook(0, Alliance.BLACK));
            blackPieces.add(new Knight(1, Alliance.BLACK));
            blackPieces.add(new Bishop(2, Alliance.BLACK));
            blackPieces.add(new Queen(3, Alliance.BLACK));
            blackPieces.add(new King(4, Alliance.BLACK));
            blackPieces.add(new Bishop(5, Alliance.BLACK));
            blackPieces.add(new Knight(6, Alliance.BLACK));
            blackPieces.add(new Rook(7, Alliance.BLACK));
            int currTile = BoardUtils.NUM_TILES_IN_ROW;
            for (int i = 0; i < BoardUtils.NUM_TILES_IN_ROW; i++) {
                blackPieces.add(new Pawn(currTile + i, Alliance.BLACK));
            }
        } catch (Exception e){
            System.out.println("Error in create Pieces in Board Class");
        }
    }

    public void putPiecesWhiteStandart(){
        try {
            whitePieces.add(new Rook(56, Alliance.WHITE));
            whitePieces.add(new Knight(57, Alliance.WHITE));
            whitePieces.add(new Bishop(58, Alliance.WHITE));
            whitePieces.add(new Queen(59, Alliance.WHITE));
            whitePieces.add(new King(60, Alliance.WHITE));
            whitePieces.add(new Bishop(61, Alliance.WHITE));
            whitePieces.add(new Knight(62, Alliance.WHITE));
            whitePieces.add(new Rook(63, Alliance.WHITE));
            int currTile = BoardUtils.NUM_TILES_IN_ROW * 6;
            for (int i = 0; i < BoardUtils.NUM_TILES_IN_ROW; i++) {
                whitePieces.add(new Pawn(currTile + i, Alliance.WHITE));
            }
        } catch (Exception e) {
            System.out.println("Error in create Pieces in Board Class");
        }
    }

    public static class Builder {
        private Collection<Piece> whitePiecesBuilder;
        private Collection<Piece> blackPiecesBuilder;
        Alliance turnOfPlayer;

        public Builder(){
            this.whitePiecesBuilder = new ArrayList<>();
            this.blackPiecesBuilder = new ArrayList<>();
            this.turnOfPlayer = null;
        }

        public void addPiece (Piece piece) {
            if (piece == null) {return;}
            if (piece.getAlliance() == Alliance.WHITE) {
                this.whitePiecesBuilder.add(piece);
            } else {
                this.blackPiecesBuilder.add(piece);
            }
        }

        public void setTurnOfPlayer(Alliance turnOfPlayer) {
            this.turnOfPlayer = turnOfPlayer;
        }

        public Board buildStandartBoard(TypePlayer whiteType, TypePlayer blackType){
            Board board = new Board(whiteType, blackType);
            board.putPiecesWhiteStandart();
            board.putPiecesBlackStandart();
            board.setTiles(board.createTiles());
            board.setTurn(Alliance.WHITE);
            board.status = Status.REGULARY_TURN;
            board.setKing(Alliance.WHITE);
            board.setKing(Alliance.BLACK);
            board.whitePlayer = new WhitePlayer(whiteType);
            board.blackPlayer = new BlackPlayer(blackType);
            return board;
        }

        public Board build(TypePlayer whiteType, TypePlayer blackType){
            return new Board(whiteType, blackType);
        }
    }
}
