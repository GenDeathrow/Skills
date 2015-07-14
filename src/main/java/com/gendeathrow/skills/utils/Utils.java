package com.gendeathrow.skills.utils;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.logging.log4j.Level;

import com.gendeathrow.skills.core.Skillz;

public class Utils {

	/**
	 * Will compare Versions numbers and give difference
	 * @param oldVer
	 * @param newVer
	 * @return
	 */
	public static int compareVersions(String oldVer, String newVer)
	{
		if(oldVer == null || newVer == null || oldVer.isEmpty() || newVer.isEmpty())
		{
			return -2;
		}
		
		int result = 0;
		int[] oldNum;
		int[] newNum;
		String[] oldNumStr;
		String[] newNumStr;
		
		try
		{
			oldNumStr = oldVer.split("\\.");
			newNumStr = newVer.split("\\.");
			
			oldNum = new int[]{Integer.valueOf(oldNumStr[0]),Integer.valueOf(oldNumStr[1]),Integer.valueOf(oldNumStr[2])};
			newNum = new int[]{Integer.valueOf(newNumStr[0]),Integer.valueOf(newNumStr[1]),Integer.valueOf(newNumStr[2])};
		} catch(IndexOutOfBoundsException e)
		{
			Skillz.logger.log(Level.WARN, "An IndexOutOfBoundsException occured while checking version!", e);
			return -2;
		} catch(NumberFormatException e)
		{
			Skillz.logger.log(Level.WARN, "A NumberFormatException occured while checking version!\n", e);
			return -2;
		}
		
		for(int i = 0; i < 3; i++)
		{
			if(oldNum[i] < newNum[i])
			{
				return -1;
			} else if(oldNum[i] > newNum[i])
			{
				return 1;
			}
		}
		return result;
	}
	
	
	
	/**
	 * Set the value of a private field. This one allows you to pass multiple
	 * field names (useful for obfuscation).
	 * 
	 * @param arg
	 *            The object whose field we're setting.
	 * @param clazz
	 *            The declaring class of the private field. Use a class literal
	 *            and not getClass(). This might be a superclass of the class of
	 *            the object.
	 * @param value
	 *            The value we're assigning to the field.
	 * @param name
	 *            The field name.
	 * @throws SecurityException If a security error occurred
	 * @throws FieldNotFoundException If some other error occurred
	 */
	public static <E> void setPrivateField(E instance, Class<? super E> declaringClass, Object value, String... names){
		for (String name : names) {
			try {
				Field field = declaringClass.getDeclaredField(name);
				field.setAccessible(true);
				try {
					field.set(instance, value);
					return;
				} catch (Exception e) {
					Skillz.logger.log(Level.ERROR, "Error setting field", e);
				}
			} catch (NoSuchFieldException nsfe) {
				continue;
			}
		}
		Skillz.logger.log(Level.ERROR, "Names not found: " + Arrays.toString(names));

	}
	
	
	/**
	 * Get the value of a private field. This one allows you to pass multiple
	 * field names (useful for obfuscation).
	 * 
	 * @param instance
	 *            The object whose field we're retrieving.
	 * @param declaringClass
	 *            The declaring class of the private field. Use a class literal
	 *            and not getClass(). This might be a superclass of the class of
	 *            the object.
	 * @param name
	 *            The multiple field names of the field to retrieve.
	 * @return The value of the field. or null
	 */
	@SuppressWarnings("unchecked")
	public static <T, E> T getPrivateField(E instance, Class<? super E> declaringClass,String... names) {
		for (String name : names) {
			try {
				Field field = declaringClass.getDeclaredField(name);
				field.setAccessible(true);
				try {
					return (T) field.get(instance);
				} catch (Exception e) {
					Skillz.logger.log(Level.ERROR, "Cannot get value of field", e);
				}
			} catch (NoSuchFieldException nsfe) {
				continue;
			}
		}
		Skillz.logger.log(Level.ERROR, "Names not found: " + Arrays.toString(names));
		return null;
	}
}
