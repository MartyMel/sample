package net.wmfs.coalesce.aa.access.util.reflection;

public class Bird extends Animal {

	private boolean walks;

	public Bird() {
		super();
	}

	public Bird(String name, boolean walks) {
		super();
		setWalks(walks);
	}

	public Bird(String name) {
		super();
	}

	public boolean walks() {
		return walks;
	}

	@Override
	public String eats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSound() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWalks() {
		return walks;
	}

	public void setWalks(boolean walks) {
		this.walks = walks;
	}
}
