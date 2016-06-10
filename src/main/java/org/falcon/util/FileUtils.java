package org.falcon.util;

import java.io.*;

public class FileUtils
{

	public static void copy(String source, String destination)
	{
		InputStream inputStream;
		OutputStream outputStream;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(destination);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void makeDirectory(File f) {
		if(!f.isDirectory()) {
			if(!f.mkdir()) {
				System.out.println("[INFO] Failed to make directory ");
			}
		}
	}

	public static void deleteDirectory(File f) {
		if (f.isDirectory()) {
			for (File c : f.listFiles()) {
				if (!c.delete())
					System.out.println("[INFO] Failed to delete " + c.getName());
			}
		}

		if (!f.delete())
			System.out.println("[ERROR] Failed to delete " + f.getName());
	}
}
