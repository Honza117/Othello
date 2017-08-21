package xvican02.src.backend.game;

import xvican02.src.backend.board.Board;

public class Game extends java.lang.Object {

	private Board gameBoard;
	private Player playerOne = null;
	private Player playerTwo = null;
	private Player currentPlayer;
	private Player nextPlayer;
	private Player emptyPlayer;

	/**
	 * Game - konstruktor
	 * 
	 * @param board hraci deska
	 */

	public Game(Board board) {
		gameBoard = board;
	}

	/**
	 * addPlayer - prida hrace do hry
	 * 
	 * @param player hrac
	 * 
	 * @return true uspech
	 * 
	 * @return false neuspech
	 */
	public boolean addPlayer(Player player) {
		if (playerOne == null) {
			playerOne = player;
			playerOne.init(gameBoard);
			currentPlayer = playerOne;
			return true;
		}
		if (playerOne != null) {
			playerTwo = player;
			playerTwo.init(gameBoard);
			nextPlayer = playerTwo;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * currentPlayer - vraci aktualniho hrace
	 * 
	 * @return currentPlayer hrac na tahu
	 */
	public Player currentPlayer() {
		return (currentPlayer);
	}

	/**
	 * nextPlayer - meni aktualniho hrace
	 * 
	 * @return currentPlayer druhy hrac
	 */
	public Player nextPlayer() {
		emptyPlayer = currentPlayer;
		currentPlayer = nextPlayer;
		nextPlayer = emptyPlayer;
		return (currentPlayer);
	}
	

	/**
	 * getBoard - vraci herni desku
	 * 
	 * @return gameBoard hraci deska
	 */
	public Board getBoard() {
		return (this.gameBoard);
	}
}
