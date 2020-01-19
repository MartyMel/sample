package net.wmfs.coalesce.aa.access.util.reflection;

public abstract class Animal implements Eating {
	 
    public static String CATEGORY = "domestic";
    private String name;
 
    protected abstract String getSound();
 
    // constructor, standard getters and setters omitted 
}