package org.falcon.util;

import org.falcon.Config;

import java.io.*;

import static java.lang.System.getProperty;

public class IOUtils
{
	/**
	 * Prints system independent directory path string.
	 *
	 * @param DIR The name of the directory.
	 * @return Path relative to /home/chucknorris/Falcon.
	 * */
	public static String PWD(String DIR)
	{
		String fileSeparator = getProperty("file.separator");
		String userRoot = getProperty("user.home") + fileSeparator + Config.USER_ROOT ;

		if(DIR.endsWith("/"))
			return userRoot;

		return userRoot + fileSeparator + DIR;
	}

	/**
	 * Removes files or directories.
	 *
	 * @param FILE The file or directory to delete.
	 * */
	public static void DEL(File FILE)
	{
		if (FILE.isDirectory()) {
			for (File c : FILE.listFiles()) {
				if (!c.delete())
					System.out.println("[INFO] Failed to DEL " + c.getName());
			}
		}
		if (!FILE.delete())
			System.out.println("[ERROR] Failed to DEL " + FILE.getName());
	}

	/**
	 * Copy files and directories.
	 *
	 * @param SOURCE The file or directory to copy from.
	 * @param DEST The file or directory to copy to.
	 * @throws IOException
	 * */
	public static void COPY(String SOURCE, String DEST) throws IOException
	{
		InputStream inputStream;
		OutputStream outputStream;
		inputStream = new FileInputStream(SOURCE);
		outputStream = new FileOutputStream(DEST);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
	}

	/**
	 * Make the directory(ies), if not already made.
	 *
	 * @param DIRECTORY The name of directory.
	 * */
	public static void MKDIR(File DIRECTORY)
	{
		if(!DIRECTORY.isDirectory()) {
			if(!DIRECTORY.mkdir()) {
				System.out.println("[INFO] Failed to make directory ");
			}
		}
	}
}
