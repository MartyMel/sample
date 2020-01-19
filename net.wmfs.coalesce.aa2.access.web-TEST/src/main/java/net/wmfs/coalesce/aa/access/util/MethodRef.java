package net.wmfs.coalesce.aa.access.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class MethodRef {

	public static void main(String[] args) {
		List<String> messages = Arrays.asList("hello", "baeldung", "readers!");
		messages.forEach(word -> StringUtils.capitalize(word));
		System.out.println("messages 1 - " + messages);
		messages.forEach(StringUtils::capitalize);
		System.out.println("messages 2 - " + messages);
		
		List<String> upped = messages.stream().map(String::toUpperCase).collect(Collectors.toList());
		System.out.println("upped - " + upped);
		
		messages.replaceAll(String::toUpperCase);
		System.out.println("messages - " + messages);
		
		List<Integer> numbers = Arrays.asList(5, 3, 50, 24, 40, 2, 9, 18);
		System.out.println("numbers before - " + numbers);
		numbers = numbers.stream().sorted(Integer::compareTo).collect(Collectors.toList());
		System.out.println("numbers after - " + numbers);
	}
	
}
