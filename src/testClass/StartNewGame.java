package xvican02.src.testClass;

import xvican02.src.backend.board.Board;
import xvican02.src.backend.board.Field;
import xvican02.src.backend.board.Rules;
import xvican02.src.backend.game.Game;
import xvican02.src.backend.game.Player;
import xvican02.src.backend.game.ReversiRules;
import xvican02.src.gui.MainWindow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

/* ------------------------------------ Vytvoreni hry ------------------------------------ */
public class StartNewGame implements Serializable {

    private static final long serialVersionUID = 1L;
    private int boardSize;
    private boolean mAI;
    private boolean easy; // obtiznost
    private boolean mFreeze; // zamrzani kamenu
    private int intervalI; // casovy interval I
    private int intervalB; // casovy interval B
    private int freezCount; // pocet zamrzlych kamenu
    private int row = 0;
    private int col = 0;
    private boolean p1Can, p2Can, gameOver;
    public ReversiRules rules;
    public Board board;
    public Game game;
    public Player p1;
    public Player p2;
    public Field[] f_field;

    public ReversiRules oldRules;
    public int p1OldStone;
    public int p2OldStone;


    /**
     * StartNewGame - konstruktor
     *
     * @param size       velikost desky
     * @param set        zda je nastavena AI
     * @param difficulty zvolena obtiznost
     * @param freeze     sty hry
     * @param I          spodni okraj intervalu
     * @param B          horni okraj intervalu
     * @param C          pocet kamenu k zamrznuti
     */
    public StartNewGame(int size, boolean set, boolean difficulty, boolean freeze, int I, int B, int C) {
        boardSize = size;
        mAI = set;
        easy = difficulty;
        mFreeze = freeze;
        intervalI = I;
        intervalB = B;
        freezCount = C;
        f_field = new Field[16];
    }

    /**
     * saveGame - ulozi aktualni hru do souboru
     *
     * @throws IOException
     */

    public void saveGame() throws IOException {
        //Ulozim soubor s nazvem data
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        try {
            File f = new File("src/xvican02/examples/" + fileName);
            f.getParentFile().mkdirs();
            f.createNewFile();
            FileWriter fileWriter = new FileWriter(f);
            //velikost desky
            fileWriter.write(this.boardSize + "\n");
            //je nastavena AI - true / false
            if (this.mAI) {
                fileWriter.write("true\n");
            }
            if (!this.mAI) {
                fileWriter.write("false\n");
            }
            //je obtiznost easy - true / false
            if (this.easy) {
                fileWriter.write("true\n");
            }
            if (!this.easy) {
                fileWriter.write("false\n");
            }
            //hraje se se zamrzanim - true / false
            if (this.mFreeze) {
                fileWriter.write("true\n");
                fileWriter.write(intervalI + "\n");
                fileWriter.write(intervalB + "\n");
                fileWriter.write(freezCount + "\n");
            }
            if (!this.mFreeze) {
                fileWriter.write("false\n");
                fileWriter.write("0\n");
                fileWriter.write("0\n");
                fileWriter.write("0\n");
            }
            //pocet kamenu p1
            fileWriter.write(String.valueOf(this.p1.currentStoneCount(this.board)) + "\n");
            //pocet kamenu p2
            fileWriter.write(String.valueOf(this.p2.currentStoneCount(this.board)) + "\n");
            //aktualni hrac na tahu
            fileWriter.write(this.game.currentPlayer().toString() + "\n");
            //indexy bilych kamenu
            fileWriter.write("white\n");
            for (int i = 0; i < this.boardSize + 2; i++) {
                for (int j = 0; j < this.boardSize + 2; j++) {
                    if (this.board.getField(i, j).getDisk() != null) {
                        if (this.board.getField(i, j).getDisk().isWhite() == true) {
                            fileWriter.write(i + " " + j + "\n");
                        }
                    }
                }
            }
            //indexy cernych kamenu
            fileWriter.write("black\n");
            for (int i = 0; i < this.boardSize + 2; i++) {
                for (int j = 0; j < this.boardSize + 2; j++) {
                    if (this.board.getField(i, j).getDisk() != null) {
                        if (this.board.getField(i, j).getDisk().isWhite() == false) {
                            fileWriter.write(i + " " + j + "\n");
                        }
                    }
                }
            }
            //konec
            fileWriter.write("END\n");
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * getRwoColTable - prebira souradnice na ktere hrac kliknul
     *
     * @param r radek
     * @param c sloupec
     */
    public void getRowColTable(int r, int c) {
        row = r;
        col = c;
    }

    /**
     * isAIset - zda se AI ucastni hry
     *
     * @return false    Hrac
     */
    public boolean isAIset() {
        if (mAI) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * gameUndo - uklada starou desku pro operaci undo
     */
   /* public Board gameUndo() {
        oldRules = new ReversiRules(boardSize);
        System.out.println("velikost stare desky " + boardSize);
        Board oldBoard = new Board(oldRules);

        p1OldStone = p1.currentStoneCount(board);
        p2OldStone = p2.currentStoneCount(board);

        // Zkopirujeme starou tabulku
        for (int i = 0; i < boardSize + 2; i++) {
            for (int j = 0; j < boardSize + 2; j++) {
                /* Kontroluji, zda jsem na kraji */
         /*       Field oldField = board.getField(i, j);
                oldBoard.myField[i][j] = oldField;
            }
        }
        System.out.println("Ukladam");
        return oldBoard;
    }

    public Field[] fieldUndo() {
        Field[] old = new Field[16];
        old = f_field; //ulozim predchozi mozne tahy
        return old;
    }*/

    public void setFirstPlayer() {
        rules = new ReversiRules(boardSize);
        board = new Board(rules);
        game = new Game(board);

		/* ---- Vytvoreni hracu ---- */
        p1 = new Player(false); // cerny hrac
        p2 = new Player(true); // bily hrac

        // Podle zvoleneho stylu hry se nastavi hraci na AI nebo PC - metoda v
        // Player class
        if (this.mAI) {
            p1.setPlayer(true); // Uzivatel
            p2.setPlayer(false); // AI
            System.out.println("Nastaveno AI");
        }
        if (!this.mAI) {
            p1.setPlayer(true); // Uzivatel
            p2.setPlayer(true); // AI
            System.out.println("Dva hraci");
        }

        // Hra ma obsahovat 2 urovne obtiznosti AI - dodela se 2. algoritmus

        this.game.addPlayer(p1); // cerny zahajuje hru
        this.game.addPlayer(p2);

        System.out.println("Pridan hrac p1: " + p1);
        System.out.println("Pridan hrac p2: " + p2);

        p1Can = true; // Jestli ma p1 tah
        p2Can = true; // Jestli ma p2 tah
        gameOver = false; // identifikator konce hry - p1 && p2 nemaji tah

        if ((this.easy) && (this.mAI)) {
            System.out.println("Zvolena lehka obtiznost");
        }
        if ((!this.easy) && (this.mAI)) {
            System.out.println("Zvolena tezsi obtiznost");
        }
        if (this.mFreeze) {
            System.out.println("Zamrzani kamenu");
        }
        if (!this.mFreeze) {
            System.out.println("Klasicka hra");
        }

        f_field = this.game.currentPlayer().getPlayFields(this.game); //Pole kamenu pro zvyrazneni*/
  	
    	

    }
    
    public Game getGame(){
    	return this.game;
    }
    

    public void ContinueGame(JTable table) {

        Field f_next = null;

        boolean findOnce = true;

		/*
         * Test konce hry
		 */
        for (int i = 0; i < boardSize + 2; i++) {
            for (int j = 0; j < boardSize + 2; j++) {
                /* Kontroluji, zda jsem na kraji */
                if (board.getField(i, j).getDisk() != null) {
                    gameOver = true;
                }
                if (board.getField(i, j).getDisk() == null) {
                    gameOver = false;
                    break;
                }
            }
        }

        //kontrola tahu prvniho hrace
        if ((p1Can) && (p2Can)) {
            for (Field item : f_field) {
                if (item == null) {
                    p1Can = false;
                }
                if (item != null) {
                    p1Can = true;
                    break;
                }
            }
        }
        //pokud prvni nema tah, prepni hrace
        if (!p1Can) {
            game.nextPlayer();
            f_field = game.currentPlayer().getPlayFields(game);
        }
        //kontrola tahu druheho hrace
        if ((!p1Can) && (p2Can)) {
            for (Field item : f_field) {
                if (item == null) {
                    p2Can = false;
                }
                if (item != null) {
                    p2Can = true;
                    break;
                }
            }
        }
        //pokud oba nemohou, konec hry
        if ((!p1Can) && (!p2Can)) {
            gameOver = true;
        }
        if (gameOver) {
            System.out.println("GAME OVER");
            return;
        }

		/*
         * Pokud je na tahu AI
		 */
        if (!this.game.currentPlayer().isHuman()) {
            System.out.println("Na tahu je AI");
            MainWindow.noHighlightPosibleStep(table, boardSize);

            System.out.println("AI " + game.currentPlayer().toString());
            f_field = this.game.currentPlayer().getPlayFields(this.game); //Pole kamenu pro zvyrazneni
            MainWindow.highlightsPosibleStep(f_field, table);
            System.out.println(f_field);
            f_next = this.game.currentPlayer().getTheCost(f_field, easy);
            if (this.game.currentPlayer().canPutDisk(f_next) == true) {
            	this.game.currentPlayer().putDisk(f_next);
                this.game.nextPlayer();
            }
        }

		/*
		 * Pokud je na tahu hrac
		 */
        if (this.game.currentPlayer().isHuman()) {

            MainWindow.noHighlightPosibleStep(table, boardSize);
            System.out.println("hrac " + this.game.currentPlayer().toString());
            System.out.println(row);
            System.out.println(col);

            // Dokud hrac neprovede spravny tah, nemeni se
            f_next = this.game.getBoard().getField(row, col);

            if (this.game.currentPlayer().canPutDisk(f_next) == true) {
                System.out.println("muzu pokladat");
                this.game.currentPlayer().putDisk(f_next);
                this.game.nextPlayer();
            }
        }


        if (findOnce) {
            f_field = this.game.currentPlayer().getPlayFields(game); //Pole kamenu pro zvyrazneni
            MainWindow.highlightsPosibleStep(f_field, table);
            findOnce = false;
        }
    }

    public void callBack(JTable table) {
        MainWindow.highlightsPosibleStep(f_field, table);
    }
    
    public void setLoadStones(List<String> whiteStones, List<String> blackStones, boolean change){
    	 for (String index : whiteStones){
         	Field f_next = null;
         	int spaceIndex = index.indexOf(" ");
         	int indexI = Integer.parseInt(index.substring(0, spaceIndex));
         	int indexJ = Integer.parseInt(index.substring(spaceIndex+1));
         	
         	f_next = this.board.getField(indexI, indexJ);
         	
         	if (this.game.currentPlayer().toString() != "white"){
         		this.game.nextPlayer();
         	}
         	
         	if (f_next.getDisk() == null){
         		this.game.currentPlayer().putDisk(f_next);
         	}
         	if (f_next.getDisk().isWhite() == false){
         		this.board.getField(indexI, indexJ).getDisk().turn();
         	}
         	
         	
        
         	System.out.println("pokladam bily na " + indexI + " " + indexJ);
         }
         
         for (String index : blackStones){
         	Field f_next = null;
         	int spaceIndex = index.indexOf(" ");
         	int indexI = Integer.parseInt(index.substring(0, spaceIndex));
         	int indexJ = Integer.parseInt(index.substring(spaceIndex+1));
         	
         	f_next = this.board.getField(indexI, indexJ);
         	
         	if (this.game.currentPlayer().toString() != "black"){
         		this.game.nextPlayer();
         	}
         	
         	if (f_next.getDisk() == null){
         		this.game.currentPlayer().putDisk(f_next);
         	}
         	if (f_next.getDisk().isWhite() == true){
         		this.board.getField(indexI, indexJ).getDisk().turn();
         	}
         	
         	System.out.println("pokladam cerny na " + indexI + " " + indexJ);
         }
         
         if (change){
        	 this.game.nextPlayer();
         }
    }

    public boolean getGameOver(){
        return gameOver;
    }
    
    public void getf_field(JTable table){
    	if (!this.mAI){
    		this.game.nextPlayer();
    	}
    	
    	f_field = this.game.currentPlayer().getPlayFields(game); //Pole kamenu pro zvyrazneni
    	MainWindow.highlightsPosibleStep(f_field, table);
    }

}

