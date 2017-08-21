package xvican02.src.backend.board;

public interface Field {

	/**
	 * Direction - vyctovy typ
	 */
	public enum Direction {
		D, L, LD, LU, R, RD, RU, U
	}

	/**
	 * Prototypy funkci
	 */

	void addNextField(Field.Direction dirs, Field field);

	Field nextField(Field.Direction dirs);

	boolean putDisk(Disk disk);

	Disk getDisk();
}