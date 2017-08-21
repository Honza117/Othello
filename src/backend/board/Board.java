package xvican02.src.backend.board;

public class Board extends java.lang.Object {

	public Field[][] myField;
	private int boardSize;
	private Rules gameRules;

	/**
	 * Konstruktor Board - vytvori hraci desku
	 *
	 * @param rules pravidla hry
	 */
	public Board(Rules rules) {
		gameRules = rules;
		boardSize = gameRules.getSize();
		myField = new Field[boardSize + 2][boardSize + 2];

		for (int i = 0; i < boardSize + 2; i++) {
			for (int j = 0; j < boardSize + 2; j++) {
				/* Kontroluji, zda jsem na kraji */
				myField[i][j] = gameRules.createField(i, j);
			}
		}
		/* Pro bordfield */
		for (int x = 1; x < boardSize + 1; x++) {
			for (int y = 1; y < boardSize + 1; y++) {
				myField[x][y].addNextField(Field.Direction.D, myField[x + 1][y]);
				myField[x][y].addNextField(Field.Direction.L, myField[x][y - 1]);
				myField[x][y].addNextField(Field.Direction.LD, myField[x + 1][y - 1]);
				myField[x][y].addNextField(Field.Direction.LU, myField[x - 1][y - 1]);
				myField[x][y].addNextField(Field.Direction.R, myField[x][y + 1]);
				myField[x][y].addNextField(Field.Direction.RD, myField[x + 1][y + 1]);
				myField[x][y].addNextField(Field.Direction.RU, myField[x - 1][y + 1]);
				myField[x][y].addNextField(Field.Direction.U, myField[x - 1][y]);
			}
		}
	}

	/**
	 * getField - vraci pole na pozic [row][col]
	 * @param row pozice na radku
	 * 
	 * @param col pozice ve sloupci
	 * 
	 * @return myField policko na pozici [row][col]
	 */
	public Field getField(int row, int col) {
		return myField[row][col];
	}

	/**
	 * getSize - vraci velikost desky
	 * 
	 * @return boardSize velikost hraci desky
	 */
	public int getSize() {
		return (this.boardSize);
	}

	/**
	 * getRules - vraci pravidla hry
	 * 
	 * @return gameRules pravidla hry
	 */
	public Rules getRules() {
		return (this.gameRules);
	}

	/*
	 * Textove rozhrani - pozdeji SMAZAT!!!
	 */

	public void print() {
		for (int i = 1; i <= boardSize; i++) {
			System.out.println("******************************************************************");
			for (int j = 1; j <= boardSize; j++) {
				// System.out.print(" * " + i + "/" + j);

				if (myField[i][j].getDisk() == null)
					System.out.print(" * -----");
				else if (myField[i][j].getDisk().isWhite() == true)
					System.out.print(" * white");
				else if (myField[i][j].getDisk().isWhite() == false)
					System.out.print(" * black");
			}
			System.out.println(" *");
		}
		System.out.println("******************************************************************");
	}

}
