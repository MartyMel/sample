package net.wmfs;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import net.wmfs.coalesce.aa.access.util.reflection.Bird;
import net.wmfs.coalesce.aa.access.util.reflection.Goat;
import net.wmfs.coalesce.aa.access.util.reflection.Person;

@RunWith(MockitoJUnitRunner.class)
public class ReflectionTest {

	@Test
	public void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
		Object person = new Person();
		Field[] fields = person.getClass().getDeclaredFields();

		List<String> actualFieldNames = getFieldNames(fields);

		assertTrue(Arrays.asList("name", "age").containsAll(actualFieldNames));
	}

	private static List<String> getFieldNames(Field[] fields) {
		List<String> fieldNames = new ArrayList<>();
		for (Field field : fields)
			fieldNames.add(field.getName());
		return fieldNames;
	}

	@Test
	public void givenObject_whenGetsClassName_thenCorrect() {
		Object goat = new Goat();
		Class<?> clazz = goat.getClass();

		assertEquals("Goat", clazz.getSimpleName());
		assertEquals("net.wmfs.coalesce.aa.access.util.reflection.Goat", clazz.getName());
		assertEquals("net.wmfs.coalesce.aa.access.util.reflection.Goat", clazz.getCanonicalName());
	}

	@Test
	public void givenClassName_whenCreatesObject_thenCorrect() throws ClassNotFoundException {
		Class<?> clazz = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Goat");

		assertEquals("Goat", clazz.getSimpleName());
		assertEquals("net.wmfs.coalesce.aa.access.util.reflection.Goat", clazz.getName());
		assertEquals("net.wmfs.coalesce.aa.access.util.reflection.Goat", clazz.getCanonicalName());
	}

	@Test
	public void givenClass_whenRecognisesModifiers_thenCorrect() throws ClassNotFoundException {
		Class<?> goatClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Goat");
		Class<?> animalClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Animal");

		int goatMods = goatClass.getModifiers();
		int animalMods = animalClass.getModifiers();

		assertTrue(Modifier.isPublic(goatMods));
		assertTrue(Modifier.isAbstract(animalMods));
		assertTrue(Modifier.isPublic(animalMods));
	}

	@Test
	public void givenClass_whenGetsPackageInfo_thenCorrect() {
		Goat goat = new Goat();
		Class<?> goatClass = goat.getClass();
		Package pkg = goatClass.getPackage();

		assertEquals("net.wmfs.coalesce.aa.access.util.reflection", pkg.getName());
	}

	@Test
	public void givenClass_whenGetsSuperClass_thenCorrect() {
		Goat goat = new Goat();
		String str = "any string";

		Class<?> goatClass = goat.getClass();
		Class<?> goatSuperClass = goatClass.getSuperclass();

		assertEquals("Animal", goatSuperClass.getSimpleName());
		assertEquals("Object", str.getClass().getSuperclass().getSimpleName());
	}

	@Test
	public void givenClass_whenGetsImplementedInterfaces_thenCorrect() throws ClassNotFoundException {
		Class<?> goatClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Goat");
		Class<?> animalClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Animal");

		Class<?>[] goatInterfaces = goatClass.getInterfaces();
		Class<?>[] animalInterfaces = animalClass.getInterfaces();

		assertEquals(1, goatInterfaces.length);
		assertEquals(1, animalInterfaces.length);
		assertEquals("Locomotion", goatInterfaces[0].getSimpleName());
		assertEquals("Eating", animalInterfaces[0].getSimpleName());
	}

	@Test
	public void givenClass_whenGetsConstructor_thenCorrect() throws ClassNotFoundException {
		Class<?> goatClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Goat");

		Constructor<?>[] constructors = goatClass.getConstructors();

		assertEquals(1, constructors.length);
		assertEquals("net.wmfs.coalesce.aa.access.util.reflection.Goat", constructors[0].getName());
	}

	@Test
	public void givenClass_whenGetsFields_thenCorrect() throws ClassNotFoundException {
		Class<?> animalClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Animal");
		Field[] fields = animalClass.getDeclaredFields();

		List<String> actualFields = getFieldNames(fields);

		assertEquals(2, actualFields.size());
		assertTrue(actualFields.containsAll(Arrays.asList("name", "CATEGORY")));
	}

	@Test
	public void givenClass_whenGetsMethods_thenCorrect() throws ClassNotFoundException {
		Class<?> animalClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Animal");
		Method[] methods = animalClass.getDeclaredMethods();
		List<String> actualMethods = getMethodNames(methods);

		assertNotEquals(4, actualMethods.size());
		assertFalse(actualMethods.containsAll(Arrays.asList("getName", "setName", "getSound")));

		assertEquals(1, actualMethods.size());
		assertTrue(actualMethods.containsAll(Arrays.asList("getSound")));
	}

	private static List<String> getMethodNames(Method[] methods) {
		List<String> methodNames = new ArrayList<>();
		for (Method method : methods)
			methodNames.add(method.getName());
		return methodNames;
	}

	@Test
	public void givenClass_whenGetsAllConstructors_thenCorrect() throws ClassNotFoundException {
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
		Constructor<?>[] constructors = birdClass.getConstructors();

		assertEquals(3, constructors.length);
	}

	@Test
	public void givenClass_whenGetsEachConstructorByParamTypes_thenCorrect()
			throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");

		Constructor<?> cons1 = birdClass.getConstructor();
		Constructor<?> cons2 = birdClass.getConstructor(String.class);
		Constructor<?> cons3 = birdClass.getConstructor(String.class, boolean.class);
	}

	@Test
	public void givenClass_whenInstantiatesObjectsAtRuntime_thenCorrect()
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
		Constructor<?> cons1 = birdClass.getConstructor();
		Constructor<?> cons2 = birdClass.getConstructor(String.class);
		Constructor<?> cons3 = birdClass.getConstructor(String.class, boolean.class);

		// run time / slow / don't know what obejct at compile time
		Bird bird1 = (Bird) cons1.newInstance();
		Bird bird2 = (Bird) cons2.newInstance("Weaver bird");
		Bird bird3 = (Bird) cons3.newInstance("dove", true);

		assertFalse(bird1.walks());
		assertTrue(bird3.walks());

		// compile time / fast/ preferred / at compile time know the object
		Bird myBird = new Bird("aaa", true);
		assertTrue(myBird.walks());
	}

	@Test
	public void givenClass_whenGetsPublicFields_thenCorrect() throws ClassNotFoundException {
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
		Field[] fields = birdClass.getFields();

		assertEquals(1, fields.length);
		assertEquals("CATEGORY", fields[0].getName());
	}

	@Test
	public void givenClass_whenGetsPublicFieldByName_thenCorrect()
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
		Field field = birdClass.getField("CATEGORY");

		assertEquals("CATEGORY", field.getName());
	}

	@Test
	public void givenClass_whenGetsDeclaredFields_thenCorrect() throws ClassNotFoundException {
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
		Field[] fields = birdClass.getDeclaredFields();

		assertEquals(1, fields.length);
		assertEquals("walks", fields[0].getName());
	}

	@Test
	public void givenClass_whenGetsFieldsByName_thenCorrect()
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
		Field field = birdClass.getDeclaredField("walks");

		assertEquals("walks", field.getName());
	}

	@Test
	public void givenClassField_whenGetsType_thenCorrect()
			throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		Field field = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird").getDeclaredField("walks");
		Class<?> fieldClass = field.getType();

		assertEquals("boolean", fieldClass.getSimpleName());
	}

	@Test
	public void givenClassField_whenSetsAndGetsValue_thenCorrect() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		// setting and getting value of field
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
		Bird bird = (Bird) birdClass.newInstance();
		Field field = birdClass.getDeclaredField("walks");
		field.setAccessible(true);

		assertFalse(field.getBoolean(bird));
		assertFalse(bird.walks());

		field.set(bird, true);

		assertTrue(field.getBoolean(bird));
		assertTrue(bird.walks());
	}

	@Test
	public void givenClassField_whenGetsAndSetsWithNull_thenCorrect() throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// getting a public static field
		Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
		Field field = birdClass.getField("CATEGORY");
		field.setAccessible(true);

		assertEquals("domestic", field.get(null));
	}
	
	@Test
	public void givenClass_whenGetsAllPublicMethods_thenCorrect() throws ClassNotFoundException{
	    Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
	    Method[] methods = birdClass.getMethods();
	    List<String> methodNames = getMethodNames(methods);
	    
	    System.out.println("----- Public Method Names----");
	    methodNames.forEach(System.out::println);
	    
	    assertTrue(methodNames.containsAll(Arrays
	      .asList("equals", "notifyAll", "hashCode",
	        "walks", "eats", "toString")));
	}
	
	@Test
	public void givenClass_whenGetsOnlyDeclaredMethods_thenCorrect() throws ClassNotFoundException{
	    Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
	    List<String> actualMethodNames
	      = getMethodNames(birdClass.getDeclaredMethods());
	 
	    System.out.println("----- Declared Method Names----");
	    actualMethodNames.forEach(System.out::println);
	    
	    List<String> expectedMethodNames = Arrays
	      .asList("setWalks", "walks", "getSound", "eats", "isWalks");
	 
	    assertEquals(expectedMethodNames.size(), actualMethodNames.size());
	    assertTrue(expectedMethodNames.containsAll(actualMethodNames));
	    assertTrue(actualMethodNames.containsAll(expectedMethodNames));
	}
	
	@Test
	public void givenMethodName_whenGetsMethod_thenCorrect() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
	    Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
	    Method walksMethod = birdClass.getDeclaredMethod("walks");
	    Method setWalksMethod = birdClass.getDeclaredMethod("setWalks", boolean.class);
	 
	    assertFalse(walksMethod.isAccessible());
	    assertFalse(setWalksMethod.isAccessible());
	 
	    walksMethod.setAccessible(true);
	    setWalksMethod.setAccessible(true);
	 
	    assertTrue(walksMethod.isAccessible());
	    assertTrue(setWalksMethod.isAccessible());
	}
	
	@Test
	public void givenMethod_whenInvokes_thenCorrect() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
	    Class<?> birdClass = Class.forName("net.wmfs.coalesce.aa.access.util.reflection.Bird");
	    Bird bird = (Bird) birdClass.newInstance();
	    Method setWalksMethod = birdClass.getDeclaredMethod("setWalks", boolean.class);
	    Method walksMethod = birdClass.getDeclaredMethod("walks");
	    boolean walks = (boolean) walksMethod.invoke(bird);
	 
	    assertFalse(walks);
	    assertFalse(bird.walks());
	 
	    setWalksMethod.invoke(bird, true);
	 
	    boolean walks2 = (boolean) walksMethod.invoke(bird);
	    assertTrue(walks2);
	    assertTrue(bird.walks());
	}
}
