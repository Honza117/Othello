package xvican02.src.backend.board;

public interface Rules {

	/**
	 * Prototypy funkci
	 */

	int getSize();

	int numberDisks();

	Field createField(int row, int col);
}
