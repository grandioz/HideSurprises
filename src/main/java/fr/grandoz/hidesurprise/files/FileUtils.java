package fr.grandoz.hidesurprise.files;

import java.io.*;
import java.util.*;

public class FileUtils
{

	public static String readFileAsString(String filename) 
			throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null)
		{
			sb.append(line + "\n");
		}
		reader.close();
		return sb.toString();
	}

	public static List<String> readFileAsListOfStrings(String filename) throws Exception
	{
		List<String> records = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = reader.readLine()) != null)
		{
			records.add(line);
		}
		reader.close();
		return records;
	}

	public static Map<String, String> readPropertiesFileAsMap(String filename, String delimiter)
			throws Exception
	{
		Map<String, String> map = new HashMap();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = reader.readLine()) != null)
		{
			if (line.trim().length()==0) continue;
			if (line.charAt(0)=='#') continue;
			int delimPosition = line.indexOf(delimiter);
			String key = line.substring(0, delimPosition-1).trim();
			String value = line.substring(delimPosition+1).trim();
			map.put(key, value);
		}
		reader.close();
		return map;
	}

	public static Properties readPropertiesFile(String canonicalFilename)
			throws IOException
	{
		Properties properties = new Properties();
		properties.load(new FileInputStream(canonicalFilename));
		return properties;
	}

	public static void writeFile(String canonicalFilename, String text) 
			throws IOException
	{
		File file = new File (canonicalFilename);
		BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
		out.write(text);
		out.close();
	}

	public static void writeFileAsBytes(String fullPath, byte[] bytes) throws IOException
	{
		OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fullPath));
		InputStream inputStream = new ByteArrayInputStream(bytes);
		int token = -1;

		while((token = inputStream.read()) != -1)
		{
			bufferedOutputStream.write(token);
		}
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
		inputStream.close();
	}

	public static void copyFile(File source, File destination) throws IOException
	{

		byte[] buffer = new byte[100000];

		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try
		{
			bufferedInputStream = new BufferedInputStream(new FileInputStream(source));
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destination));
			int size;
			while ((size = bufferedInputStream.read(buffer)) > -1)
			{
				bufferedOutputStream.write(buffer, 0, size);
			}
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (bufferedInputStream != null)
				{
					bufferedInputStream.close();
				}
				if (bufferedOutputStream != null)
				{
					bufferedOutputStream.flush();
					bufferedOutputStream.close();
				}
			}
			catch (IOException ioe)
			{
				throw ioe;
			}
		}
	}
}