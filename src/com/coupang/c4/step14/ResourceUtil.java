package com.coupang.c4.step14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceUtil {
	private ResourceUtil() {
		throw new UnsupportedOperationException();
	}

	public static InputStream resourceAsInputStream(String filePath) {
		return Thread.class.getResourceAsStream(filePath);
	}
	
	public static String[] readFully(InputStream inputStream) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
			System.out.println(line);
		}
		
		return lines.toArray(new String[0]);
	}
}
