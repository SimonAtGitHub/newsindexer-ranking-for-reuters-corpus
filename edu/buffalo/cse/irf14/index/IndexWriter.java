/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.common.CommonConstants;
import edu.buffalo.cse.irf14.common.CommonUtil;
import edu.buffalo.cse.irf14.common.StringUtil;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo
 * Class responsible for writing indexes to disk
 */
public class IndexWriter {
	
	private String indexDir=null;
	/**
	 * Default constructor
	 * @param indexDir : The root directory to be sued for indexing
	 */
	public IndexWriter(String indexDir) {
		//TODO : YOU MUST IMPLEMENT THIS
		this.indexDir=indexDir;
	}
	
	/**
	 * Method to add the given Document to the index
	 * This method should take care of reading the filed values, passing
	 * them through corresponding analyzers and then indexing the results
	 * for each indexable field within the document. 
	 * @param d : The Document to be added
	 * @throws IndexerException : In case any error occurs
	 */
	public void addDocument(Document d) throws IndexerException {
		//TODO : YOU MUST IMPLEMENT THIS
        TokenStream stream = null;
		try {
			
			/**
			 * create document dictionary
			 */
			String fileId = StringUtil.convertStrArrToString(d.getField(FieldNames.FILEID));
			//document id to be put in the postings list
			Integer docId = DocumentDictionary.getInstance().nextVal();
			//create document dictionary
			DocumentDictionary docDictionary = DocumentDictionary.getInstance();
			docDictionary.getMap().put(fileId, docId);
			
			/*FileOutputStream fileOut  = new FileOutputStream("E://ser//test2.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(docDictionary);
			out.close();
			fileOut.close();*/
			
			for(FieldNames fieldName:FieldNames.values()){
				//analyze the tokenstream and apply filter chaining except for FileId
				if(!fieldName.equals(FieldNames.FILEID)){
					stream=analyzeStream(fieldName,d);
					if(stream!=null){
					   createDictionaryAndIndex(fieldName,stream,fileId,docId);
					}
				}
			}
			
			
			
			//String streamTest = convertTokenStreamToString(stream);
			//System.out.println("Content ::: "+streamTest);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Analyze the tokenstream of each document attributes adn apply chained filters to it
	 * @param fieldName
	 * @param d
	 * @return
	 * @throws TokenizerException
	 */
	private TokenStream analyzeStream(FieldNames fieldName,Document d) throws TokenizerException{
		Tokenizer tknizer = new Tokenizer();
		AnalyzerFactory fact = AnalyzerFactory.getInstance();
		TokenStream stream = null;
		String[] termArr=null;
		try{
			switch(fieldName){
			    case FILEID:
			    	termArr = d.getField(FieldNames.FILEID);
			    	break;
			    case CATEGORY:
			    	termArr = d.getField(FieldNames.CATEGORY);
			    	break;
			    case TITLE:
			    	termArr = d.getField(FieldNames.TITLE);
			    	break;
			    case AUTHOR:
			    	termArr = d.getField(FieldNames.AUTHOR);
			    	break;
			    case AUTHORORG:
			    	termArr = d.getField(FieldNames.AUTHORORG);
			    	break;
			    case PLACE:
			    	termArr = d.getField(FieldNames.PLACE);
			    	break;
			    case CONTENT:
			    	termArr = d.getField(FieldNames.CONTENT);
			    	break;
			    case NEWSDATE:
			    	termArr = d.getField(FieldNames.NEWSDATE);
			    	break;
			    default:
			    	break;
			}
			String str=StringUtil.convertStrArrToString(termArr);
			if(str!=null){
				stream = tknizer.consume(str);
				Analyzer analyzer ;
				analyzer = fact.getAnalyzerForField(fieldName,
						stream);
		
				while (analyzer.increment()) {
		
				}
				
				stream.reset();
			}
			return stream;
		}catch(TokenizerException e){
			throw new TokenizerException("Error while analyzing");
		}
	}
	
	
	

	/**
	 * Method that indicates that all open resources must be closed
	 * and cleaned and that the entire indexing operation has been completed.
	 * @throws IndexerException : In case any error occurs
	 */
	public void close() throws IndexerException {
		writeToDisk(DocumentDictionary.getInstance().getMap(),CommonConstants.DOCUMENT_DICTIONARY_FILENAME);
		writeToDisk(TermDictionary.getInstance().getMap(),CommonConstants.TERM_DICTIONARY_FILENAME);
		writeToDisk(AuthorDictionary.getInstance().getMap(),CommonConstants.AUTHOR_DICTIONARY_FILENAME);
		writeToDisk(PlaceDictionary.getInstance().getMap(),CommonConstants.PLACE_DICTIONARY_FILENAME);
		writeToDisk(CategoryDictionary.getInstance().getMap(),CommonConstants.CATEGORY_DICTIONARY_FILENAME);
	    writeToDisk(TermIndex.getInstance().getMap(),CommonConstants.TERM_INDEX_FILENAME);
	    writeToDisk(AuthorIndex.getInstance().getMap(),CommonConstants.AUTHOR_INDEX_FILENAME);
	    writeToDisk(PlaceIndex.getInstance().getMap(),CommonConstants.PLACE_INDEX_FILENAME);
	    writeToDisk(CategoryIndex.getInstance().getMap(),CommonConstants.CATEGORY_INDEX_FILENAME);
	}
 
	/**
	 * Method that is used to write all the dictionaries and indexes to disk
	 * @throws IOException 
	 */
	public void writeToDisk(Object object,String fileName) {
		//TODO
		FileOutputStream fileOut=null;
		try {
			fileOut= new FileOutputStream(indexDir+File.separatorChar +fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 
	/**
	 * Method that is used to write all the dictionaries and indexes to disk
	 * @throws IOException 
	 */
	public void writeToDiskUTF(Object object,String fileName) {
			//TODO
			FileOutputStream fileOut=null;
			try {
				fileOut= new FileOutputStream(indexDir+File.separatorChar +fileName);
				DataOutputStream out = new DataOutputStream(fileOut);
				out.writeUTF(object.toString());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
	/**
	 * Method that creates the term dictionary and term index
	 * @param d
	 * @param docId
	 */
	private void createDictionaryAndIndex(FieldNames fieldName,TokenStream stream,String fileId,Integer docId){
		
		try{				
				  while(stream.hasNext()){
						Token token=stream.next();
						String term=token.toString();
						//get the dictionary
						NewsDictionary dictionary = CommonUtil.getDictionaryByType(fieldName);
						//get the index
						NewsIndex index = CommonUtil.getIndexByType(fieldName);
						//enter the term in the dictionary and index if term is not in the dictionary
						// if it is not in the dictionary , it is also not in the postings list
						if(dictionary.getMap().get(term)==null){
							//enter the term in the dictionary
							Integer id = dictionary.nextVal();
							dictionary.getMap().put(term, id);
							
							//enter the term in the postings list
							Posting posting= new Posting();
							posting.setDocId(docId);
							posting.setFileId(fileId);
							posting.setFrequency(1);
							List<Posting> postings = new ArrayList<Posting>();
							postings.add(posting);
							index.getMap().put(id, postings);
							
						}
						//if the term is already present in the dictionary
						else{
	                        boolean hasPosting=false;
	                        //get the id from the dictionary
	                        Integer termId = dictionary.getMap().get(term);
	                        //get the postings list from the index
							List<Posting> postings=index.getMap().get(termId);
							
							//prepare the new posting object
							Posting posting= new Posting();
							posting.setDocId(docId);
							posting.setFileId(fileId);
							if(postings!=null){
								for(Posting postingItem:postings){
									//if the term is already mapped to a doc in the postings list just increase its frequency
									if(postingItem.equals(posting)){
										 Integer frequency = postingItem.getFrequency();
										 postingItem.setFrequency(++frequency);
										 hasPosting = true;
										 break;
									}
								}
								//if the term is not mapped to the doc in the postings list , create a new posting for the term
								//and map it to the term
								if(!hasPosting){
									//enter the term in the postings list
									Posting posting1= new Posting();
									posting1.setDocId(docId);
									posting1.setFileId(fileId);
									posting1.setFrequency(1);
									//add it to the same postings list
									postings.add(posting1);
								}
							}
						}
						
						
			}
			stream.reset();	  

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
