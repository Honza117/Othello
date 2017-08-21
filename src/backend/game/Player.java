package xvican02.src.backend.game;

import java.util.Arrays;

import xvican02.src.backend.board.Board;
import xvican02.src.backend.board.Disk;
import xvican02.src.backend.board.Field;
import xvican02.src.backend.board.Field.Direction;

public class Player extends java.lang.Object {

    private boolean color;
    public int stoneCount;
    private Field.Direction rightDir;
    private String[] rightDirs = new String[8];

    private boolean IamHuman; // clovek - true / PC - false
    private Field[] playFields; // Pole moznych tahu
    private int fieldCount; // counter pro indexovani pole playFields
    private int costCount; // pocitadlo cen kazdeho pole
    int count;

    /**
     * Player - konstruktor
     *
     * @param isWhite barva hrace
     */
    public Player(boolean isWhite) {
        this.color = isWhite;
        this.stoneCount = 0;
        this.fieldCount = 0; // nastavim countery na 0
        this.costCount = 0;
    }

    /**
     * isWhite - vraci barvu hrace
     *
     * @return color barva hrace
     */
    public boolean isWhite() {
        return (this.color);
    }

    /**
     * isHuman - test, zda je hrac clovek / AI
     *
     * @return false AI
     */
    public boolean isHuman() {
        return (this.IamHuman);
    }

    @Override
    public String toString() {
        if (this.color == true) {
            return "white";
        } else {
            return "black";
        }
    }

    /**
     * emptyPool - test prazdne sady kamenu
     *
     * @return false neprazdna
     */
    public boolean emptyPool() {
        if (this.stoneCount <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * canPutDisk - test, zda mohu vlozit kamen na pole
     *
     * @param field hraci pole
     * @return false neuspech
     */
    public boolean canPutDisk(Field field) {

        Field canField;
        rightDirs = new String[8];
        boolean youCan = false; // zda muzu vlozit
        boolean searchColor = this.color; // hledana barva
        boolean firstSearch;
        boolean secondSearch;
        boolean searchDisk;
        int index = 0;

        if (field.getDisk() == null) {
            for (Field.Direction dir : Field.Direction.values()) {
                firstSearch = true;
                secondSearch = false;
                canField = field;            
                
               while (canField.nextField(dir).getDisk() != null) {           

                    searchDisk = canField.nextField(dir).getDisk().isWhite();

					/* Pokud prvni kamen je stejne barvy */
                    if ((searchDisk == searchColor) && (firstSearch)) {
                        break;
                    }
                    /* Pokud prvni kamen je jine barvy */
                    if ((searchDisk != searchColor) && (!secondSearch)) {
                        firstSearch = false;
                        secondSearch = true;
                    }
					/* Pokud je nalezen dalsi kamen stejen barvy */
                    if ((searchDisk == searchColor) && (secondSearch)) {
                        youCan = true;
                        rightDir = dir; // ulozim smer

                        if (rightDir == Direction.D) {
                            rightDirs[index] = "D";
                        }
                        if (rightDir == Direction.L) {
                            rightDirs[index] = "L";
                        }
                        if (rightDir == Direction.LD) {
                            rightDirs[index] = "LD";
                        }
                        if (rightDir == Direction.LU) {
                            rightDirs[index] = "LU";
                        }
                        if (rightDir == Direction.R) {
                            rightDirs[index] = "R";
                        }
                        if (rightDir == Direction.RD) {
                            rightDirs[index] = "RD";
                        }
                        if (rightDir == Direction.RU) {
                            rightDirs[index] = "RU";
                        }
                        if (rightDir == Direction.U) {
                            rightDirs[index] = "U";
                        }
                        index++;
                        break;
                    }
                    if (canField.nextField(dir).getDisk() != null) {
                        canField = canField.nextField(dir);
                    }
                }
            }
        }

        if (youCan)
            return true;
        else
            return false;
    }

    /**
     * putDisk - vlozi kamen na pole
     *
     * @param field hraci pole
     * @return false neuspech
     */
    public boolean putDisk(Field field) {

        this.playFields = new Field[16]; // Po kazdem tahu se vycisti jeho pole
        // tahu
        this.fieldCount = 0; // vynuluji counter

        Field putField;
        this.stoneCount += 1;
        Disk addDisk = new Disk(this.color);
        field.putDisk(addDisk);

        System.out.println(Arrays.toString(rightDirs));

        for (String dir : rightDirs) {
            putField = field;
            if (dir != null) {

                if (dir == "D") {
                    rightDir = Direction.D;
                }
                if (dir == "L") {
                    rightDir = Direction.L;
                }
                if (dir == "LD") {
                    rightDir = Direction.LD;
                }
                if (dir == "LU") {
                    rightDir = Direction.LU;
                }
                if (dir == "R") {
                    rightDir = Direction.R;
                }
                if (dir == "RD") {
                    rightDir = Direction.RD;
                }
                if (dir == "RU") {
                    rightDir = Direction.RU;
                }
                if (dir == "U") {
                    rightDir = Direction.U;
                }

                Disk mDisk = putField.nextField(rightDir).getDisk();

                while (mDisk != null) {
                    if (mDisk.isWhite() != this.color) {
                        putField.nextField(rightDir).getDisk().turn();
                        putField = putField.nextField(rightDir);
                    } else {
                        break;
                    }
                    mDisk = putField.nextField(rightDir).getDisk();

                }
            }
        }
        return true;
    }

    /**
     * setPlayer - nastavi hrace / AI
     */
    public void setPlayer(boolean value) {
        this.IamHuman = value;
    }

    /**
     * tryPutDisk - spocita cenu nasledujiciho tahu
     *
     * @param field hraci pole
     * @return costCount cena prechodu
     */
    public int tryPutDisk(Field field) {

        this.costCount = 0; // pocitadlo se bude inkrementovat v kazdem cyklu
        Field putField;
        //Disk addDisk = new Disk(this.color);
        //field.putDisk(addDisk);

        for (String dir : rightDirs) {
            putField = field;

            if (dir != null) {

                if (dir == "D") {
                    rightDir = Direction.D;
                }
                if (dir == "L") {
                    rightDir = Direction.L;
                }
                if (dir == "LD") {
                    rightDir = Direction.LD;
                }
                if (dir == "LU") {
                    rightDir = Direction.LU;
                }
                if (dir == "R") {
                    rightDir = Direction.R;
                }
                if (dir == "RD") {
                    rightDir = Direction.RD;
                }
                if (dir == "RU") {
                    rightDir = Direction.RU;
                }
                if (dir == "U") {
                    rightDir = Direction.U;
                }

                while (putField.nextField(rightDir).getDisk().isWhite() != this.color) {
                    putField = putField.nextField(rightDir);
                    // kontrola zda je na poli zamrzly disk
					/*if (putField.getDisk().isFreeze()){
						return -1; //nelze vlozit
					}*/
                    this.costCount++;
                }
            }
        }
        return this.costCount;
    }

    /**
     * getPlayFields - ulozi do pole vsechna hraci pole dostupna v dalsim tahu
     * hry
     *
     * @param board hraci deska
     * @return playFields pole hracich poli
     */
    public Field[] getPlayFields(Game game) {

        playFields = new Field[60];

        for (int i = 1; i < (game.getBoard().getSize() + 1); i++) {
            for (int j = 1; j < (game.getBoard().getSize() + 1); j++) {

                Field PField = game.getBoard().getField(i, j); // Vemu aktualni pole

				/* Pokud metoda canPutDisk vrati true, ulozim pole */
                if (canPutDisk(PField) == true) {
                    System.out.println(i + " " + j);
                    System.out.println("counter: " + this.fieldCount);
                    this.playFields[fieldCount] = PField;
                    this.fieldCount++;
                }
            }
        }
        this.fieldCount = 0;
        return playFields; // Vraci pole policek pro tah
    }

    /**
     * getTheCost - podle zvolene obtiznosti zvoli cenu prechodu pro AI
     *
     * @param field pole hracich poli
     * @param easy  zvolena obtiznost AI
     * @return playField pole pro nasledujici tah
     */
    public Field getTheCost(Field[] field, boolean easy) {

        Field playField = null;
        int currentCost = 0; // cena soucasneho tahu
        int oldCost = 0; // cena stareho tahu

		/*
		 * Pres vsechny pole pro dalsi tah zkusim kolik se otoci kamenu, ulozim
		 * nejvetsi pocet
		 */
        if (!easy) {
            for (Field nextField : field) {
                if (nextField != null) {
                    if (canPutDisk(nextField)) {
                        if ((currentCost = tryPutDisk(nextField)) > oldCost) {
                            oldCost = currentCost;
                            System.out.println("ulozil jsem pole");
                            playField = nextField;
                        }
                    }
                }
            }
        }
        if (easy) {
            boolean firstTurn = true;
            for (Field nextField : field) {
                if (nextField != null) {
                    if (firstTurn) {
                        oldCost = currentCost;
                        playField = nextField;
                        firstTurn = false;
                    }
                    if (canPutDisk(nextField)) {
                        if (((currentCost = tryPutDisk(nextField)) > 0) && (!firstTurn)) {
                            if (currentCost < oldCost) {
                                playField = nextField;
                            }
                            oldCost = currentCost;
                        }
                    }
                }
            }
        }
        return playField;
    }

    /**
     * init - inicializace hraci desky, vlozeni pocatecnich kamenu
     *
     * @param board hraci deska
     */
    public void init(Board board) {
        count = board.getSize();
        //this.stoneCount = count * count / 2;

        Disk whiteDiskOne;
        Disk whiteDiskTwo;
        Disk blackDiskOne;
        Disk blackDiskTwo;
        whiteDiskOne = new Disk(true);
        whiteDiskTwo = new Disk(true);
        blackDiskOne = new Disk(false);
        blackDiskTwo = new Disk(false);

		/* Polozim pocatecni kameny na desku */
        if (this.color == true) {
            board.myField[board.getSize() / 2][board.getSize() / 2].putDisk(whiteDiskOne);
            board.myField[(board.getSize() / 2) + 1][(board.getSize() / 2) + 1].putDisk(whiteDiskTwo);
        } else {
            board.myField[board.getSize() / 2][(board.getSize() / 2) + 1].putDisk(blackDiskOne);
            board.myField[(board.getSize() / 2) + 1][board.getSize() / 2].putDisk(blackDiskTwo);
        }
    }

    /**
     * currentStoneCount - vraci aktualni pocet kamenu na desce
     *
     * @return stoneCount        pocet kamenu hrace
     */
    public int currentStoneCount(Board board) {
        stoneCount = 0;
        for (int i = 1; i < board.getSize() + 1; i++) {
            for (int j = 1; j < board.getSize() + 1; j++) {
				/* Kontroluji, zda jsem na kraji */
                if (board.getField(i, j).getDisk() != null) {
                    if (board.getField(i, j).getDisk().isWhite() == this.color) {
                        stoneCount++;
                    }
                }
            }
        }
        return stoneCount;
    }

    public void putLoadDisk(Field field, boolean white){
        if (white == true){
            Disk addWDisk = new Disk(true);
            field.putDisk(addWDisk);
        }
        if (white == false){
            Disk addBDisk = new Disk(false);
            field.putDisk(addBDisk);
        }
    }


}