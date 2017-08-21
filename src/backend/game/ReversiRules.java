package xvican02.src.backend.game;

import xvican02.src.backend.board.BoardField;
import xvican02.src.backend.board.BorderField;
import xvican02.src.backend.board.Field;
import xvican02.src.backend.board.Rules;

public class ReversiRules extends java.lang.Object implements Rules {

	public int boardSize;
	private Field myField = null;

	/**
	 * ReversiRules - konstruktor
	 * 
	 * @param size velikost hraci desky
	 */
	public ReversiRules(int size) {
		boardSize = size;
	}

	/**
	 * getSize - vraci velikost hraci desky
	 * 
	 * @return boardSize velikost hraci desky
	 */
	public int getSize() {
		return this.boardSize;
	}

	/**
	 * numberDisks - vraci pocet kamenu hracu
	 *
	 * @return pocet kamenu
	 */
	public int numberDisks() {
		return this.boardSize * this.boardSize / 2;
	}

	/**
	 * createField - vytvori policko hraci desky na souradnicich row,col
	 * 
	 * @param row pozice na radku
	 * 
	 * @param col pozice ve sloupci
	 * 
	 * @return myFiedl pole hraci desky
	 */
	public Field createField(int row, int col) {
		
		if ((row == 0) || (col == 0) || (row == boardSize+2) || (col == boardSize+2)){
			return myField = new BorderField();
		}
		else {
			return myField = new BoardField(row, col);
		}
	}
}
