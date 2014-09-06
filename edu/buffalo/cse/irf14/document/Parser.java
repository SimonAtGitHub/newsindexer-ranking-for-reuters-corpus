/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author nikhillo Class that parses a given file into a Document
 */
public class Parser {
	/**
	 * Static method to parse the given file into the Document object
	 * 
	 * @param filename
	 *            : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException
	 *             In case any error occurs during parsing
	 */
	public static Document parse(String filename) throws ParserException {
		// TODO YOU MUST IMPLEMENT THIS
        System.out.println("\nFileName:: "+filename);
		// create the Document object
		Document document = new Document();
		try {
            // variables to hold the "Document" attributes
			String title,author,authorOrg, place="", newsDate,content="";
			
			//flags to hold if the fields have been populated or not
			boolean hasAuthor=false; 
			boolean hasTitle =false;
			boolean hasPlaceDate =false;
			// Read the file contents into a buffer
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			// line buffer to read a line once at a time
			String line;
			
			//populate Field Id and Category
			populateFileIdCat(document,filename);
						
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 0){
					//Populate the title
					if (!hasTitle) {
						title = line.trim();
						document.setField(FieldNames.TITLE, title);
						hasTitle=true;
					}
					else if(!hasPlaceDate){
						//try{
						String placeDateContent[]=line.split("-");
						if(placeDateContent!=null && placeDateContent.length>1){
							String placeDate = placeDateContent[0];
							int placeDateContLength = placeDateContent.length;
							//Populate content-If there are multiple '-' within the same line where there are place and date
							for(int i=1;i<=placeDateContLength-1;i++){
								content = content+placeDateContent[i];
							}
							//Populate place and date
							String [] placeDateArr = placeDate.split(",");
							if(placeDateArr!=null && placeDateArr.length>1){
								int placeDateArrLength = placeDateArr.length;
								newsDate = placeDateArr[placeDateArrLength-1];
								
								for(int i=0;i<=placeDateArrLength-2;i++){
									place = placeDateArr[i]+",";
								}
								System.out.println("\nPlace "+ place);
								place = place.substring(0,place.length()-1);
								place=place.trim();
								document.setField(FieldNames.PLACE, place);
								newsDate=newsDate.trim();
								document.setField(FieldNames.NEWSDATE, newsDate);
							}
							else{
								System.out.println("No place and date");
							}
						}
						hasPlaceDate=true;
						/*}catch(Exception e){
							System.out.println("Exception occured");
							System.out.println("Handle it");
						}*/
					}
					else{
						content = content+ line;
					}
					
				}
				content=content.trim();
				document.setField(FieldNames.CONTENT, content);
			}
			

		} catch (FileNotFoundException e) {
			System.out.println("File not found ::" + filename);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O Error occured while reding the file ::"
					+ filename);
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * Populate the category and file Id
	 * @param document
	 * @param filename
	 */
	private static void populateFileIdCat(Document document,String filename){
		
		String pattern = Pattern.quote(String.valueOf(File.separatorChar));
		String [] arrFolders = filename.split(pattern);
		if(arrFolders!=null && arrFolders.length>0){
			int length=arrFolders.length;
			document.setField(FieldNames.FILEID, arrFolders[length-1]);
			document.setField(FieldNames.CATEGORY,arrFolders[length-2]);
			System.out.println("\nCategory ::"+arrFolders[length-2]);
		}
	}

}
