package xvican02.src.backend.board;

import java.util.Arrays;

public class BoardField extends java.lang.Object implements Field {

	private final int row;
	private final int col;
	private Disk newDisk;
	private Field[] addedField;

	/**
	 * konstruktor BoardField - inicializuje hraci pole, zna sve okoli
	 * 
	 * @param row pozice na radku
	 * 
	 * @param col pozice ve sloupci
	 */
	public BoardField(int row, int col) {
		this.row = row;
		this.col = col;

		newDisk = new Disk(); // vytvořím disk (alokuji pamět)
		newDisk = null;
		addedField = new Field[8];
		// addedField = null;
	}

	/**
	 * addNextFiedl - prida pole desky v danem smeru
	 * 
	 * @param dirs smer
	 * 
	 * @param field pole desky
	 * 
	 */
	public void addNextField(Field.Direction dirs, Field field) {
		switch (dirs) {
		case D:
			addedField[0] = field;
			break;
		case L:
			addedField[1] = field;
			break;
		case LD:
			addedField[2] = field;
			break;
		case LU:
			addedField[3] = field;
			break;
		case R:
			addedField[4] = field;
			break;
		case RD:
			addedField[5] = field;
			break;
		case RU:
			addedField[6] = field;
			break;
		case U:
			addedField[7] = field;
			break;
		default:
			break;
		}
	}

	/**
	 * nextField - vraci pole ve zvolenem smeru
	 * 
	 * @param dirs smer
	 * 
	 * @return addedField pole v danem smeru
	 */
	public Field nextField(Field.Direction dirs) {
		switch (dirs) {
		case D:
			return addedField[0];

		case L:
			return addedField[1];

		case LD:
			return addedField[2];

		case LU:
			return addedField[3];

		case R:
			return addedField[4];

		case RD:
			return addedField[5];

		case RU:
			return addedField[6];

		case U:
			return addedField[7];

		default:
			break;
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardField other = (BoardField) obj;
		if (!Arrays.equals(addedField, other.addedField))
			return false;
		if (col != other.col)
			return false;
		if (newDisk == null) {
			if (other.newDisk != null)
				return false;
		} else if (!newDisk.equals(other.newDisk))
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(addedField);
		result = prime * result + col;
		result = prime * result + ((newDisk == null) ? 0 : newDisk.hashCode());
		result = prime * result + row;
		return result;
	}

	/**
	 * putDisk - vklada kamen na hraci desku
	 * 
	 * @param disk hraci kamen hrace
	 * 
	 * @return true uspesne polozeni kamene
	 * 
	 * @return false neuspesne polozeni kamene
	 */
	public boolean putDisk(Disk disk) {
		if (newDisk == null) {
			newDisk = disk;
		} else {
			return false;
		}
		return true;
	}

	/**
	 * getDisk - vraci hraci kamen
	 * 
	 * @return newDisk vraci hraci kamen
	 */
	public Disk getDisk() {
		return newDisk;
	}

	/**
	 * canPutDisk - zda muzu vlozit kamen na pole
	 * 
	 * @param field hraci pole desky
	 * 
	 * @return true uspech
	 * 
	 * @return false neuspech
	 */
	public boolean canPutDisk(Field field) {
		if (field == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * getRow - vraci radek
	 * @return row		radek
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * getCol - vraci sloupec
	 * @return col		sloupec
	 */
	public int getCol(){
		return col;
	}
}