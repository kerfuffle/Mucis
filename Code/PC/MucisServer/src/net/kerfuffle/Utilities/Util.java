package net.kerfuffle.Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Util {

	public static String readTextFile(String fileName)
	{
	        String line = null;
	        StringBuilder sb = new StringBuilder();
	        try {
	         
	            FileReader fileReader = new FileReader(fileName);

	            BufferedReader bufferedReader = new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) 
	            {
	            	sb.append(line+"\n");
	            }   

	            sb.deleteCharAt(sb.length()-1);
	            
	            bufferedReader.close();         
	        }
	        catch(IOException e) {
	            e.printStackTrace();          
	        }
	        
	        return sb.toString();

	}
	
}
