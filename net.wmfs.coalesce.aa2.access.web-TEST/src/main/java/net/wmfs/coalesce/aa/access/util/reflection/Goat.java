package net.wmfs.coalesce.aa.access.util.reflection;

public class Goat extends Animal implements Locomotion {
	@Override
	protected String getSound() {
		return "bleat";
	}

	@Override
	public String getLocomotion() {
		return "walks";
	}

	@Override
	public String eats() {
		return "grass";
	}
}
