package xvican02.src.backend.board;

public class BorderField extends java.lang.Object implements Field {

	public BorderField() {

	}

	public void addNextField(Field.Direction dirs, Field field) {

	}

	public Field nextField(Field.Direction dirs) {
		return null;
	}

	public boolean putDisk(Disk disk) {
		return false;
	}

	public Disk getDisk() {
		return null;
	}

	public boolean canPutDisk(Field field) {
		return false;
	}
}
