package xvican02.src.backend.board;

public class Disk extends java.lang.Object {

	private boolean color; //barva
	private boolean status; //zamrznuti

	public Disk() {

	}

	/**
	 * Disk - konstruktor
	 * 
	 * @param isWhite barva kamene
	 */
	public Disk(boolean isWhite) {
		color = isWhite;
	}

	/**
	 * setFreeze - kontrola, zda je kamen zamrzly
	 * 
	 * @param true je zamrznuty
	 * 
	 * @param false neni zamrznuty
	 */
	public void setFreeze(boolean itIs) {
		this.status = itIs;
	}

	/**
	 * isFreeze - test, zda je kamen zamrznuty
	 * 
	 * @return true je zamrznuty
	 * 
	 * @return false neni zamrznuty
	 */
	public boolean isFreeze() {
		return this.status;
	}

	/**
	 * turn - zmena barvy kamene
	 * 
	 * @return !color	barva kamene
	 */
	public void turn() {
		this.color = !this.color;
	}

	/**
	 * isWhite - vraci barvu kamene
	 * 
	 * @return color barva kamene
	 */
	public boolean isWhite() {
		return color;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Disk other = (Disk) obj;
		if (this.color != other.color) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + (this.color ? 1 : 0);
		return hash;
	}

}
