/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author nikhillo
 * Class that parses a given file into a Document
 */
public class Parser {
	/**
	 * Static method to parse the given file into the Document object
	 * @param filename : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException In case any error occurs during parsing
	 */
	public static Document parse(String filename) throws ParserException {
		// TODO YOU MUST IMPLEMENT THIS
		try {
			//Read the file contents into a buffer qwdw
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			//line buffer to read a line once at a time
			String newLine;
			while((newLine=reader.readLine())!=null){
				
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found ::"+filename);
			e.printStackTrace();
		} catch(IOException e){
			System.out.println("I/O Error occured while reding the file ::"+filename);
			e.printStackTrace();
		}
		return null;
	}

}
