package net.kerfuffle.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

	public static Flags flags = new Flags();
	
	public static void writeToFile(String fileName, String data)
	{
		try 
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

			bw.write(data);

			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addToFile(String fileName, String data)
	{
		try 
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));

			bw.write(data);
			
			bw.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public static byte[] readFile(String path)
	{
		Path fpath = Paths.get(path);
		byte data[] = null;
		try 
		{
			data = Files.readAllBytes(fpath);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static String readTextFile(String fileName)
	{
	        String line = null;
	        StringBuilder sb = new StringBuilder();
	        try {
	         
	            FileReader fileReader = new FileReader(fileName);

	            BufferedReader bufferedReader = new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) 
	            {
	            	sb.append(line);
	            }   
	            
	            bufferedReader.close();         
	        }
	        catch(IOException e) {
	            e.printStackTrace();          
	        }
	        
	        return sb.toString();

	}
	
}
