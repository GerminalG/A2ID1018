// SynonymHandler

/****************************************************************

SynonymHandler handles information about synonyms for various
words.

The synonym data can be read from a file and handled in various
ways. These data consists of several lines, where each line begins
with a word, and this word is followed with a number of synonyms.

The synonym line for a given word can be obtained. It is possible
to add a synonym line, and to remove the synonym line for a given
word. Also a synonym for a particular word can be added, or
removed. The synonym data can be sorted. Lastly, the data can be
written to a given file.

Author: Fadil Galjic

****************************************************************/

import java.io.*;    // FileReader, BufferedReader, PrintWriter,
                     // IOException
import java.util.ArrayList;
import java.util.List;

class SynonymHandler
{
	// readSynonymData reads the synonym data from a given file
	// and returns the data as an array
    public static String[] readSynonymData (String synonymFile)
                                         throws IOException
    {
        BufferedReader reader = new BufferedReader(
	        new FileReader(synonymFile));
        int numberOfLines = 0;
        String synonymLine = reader.readLine();
        while (synonymLine != null)
        {
			numberOfLines++;
			synonymLine = reader.readLine();
		}
		reader.close();

		String[] synonymData = new String[numberOfLines];
        reader = new BufferedReader(new FileReader(synonymFile));
		for (int i = 0; i < numberOfLines; i++)
		    synonymData[i] = reader.readLine();
		reader.close();

		return synonymData;
    }

    // writeSynonymData writes a given synonym data to a given
    // file
    public static void writeSynonymData (String[] synonymData,
        String synonymFile) throws IOException
    {
        PrintWriter writer = new PrintWriter(synonymFile);
        for (String synonymLine : synonymData)
            writer.println(synonymLine);
        writer.close();

    }

    // synonymLineIndex accepts synonym data, and returns the
    // index of the synonym line corresponding to a given word.
    // If the given word is not present, an exception of
    // the type IllegalArgumentException is thrown.
	private static int synonymLineIndex (String[] synonymData,
        String word) throws IllegalArgumentException
    {
		int delimiterIndex = 0;
		String w = "";
		int i = 0;
		boolean wordFound = false;
		while (!wordFound  &&  i < synonymData.length)
		{
		    delimiterIndex = synonymData[i].indexOf('|');
		    w = synonymData[i].substring(0, delimiterIndex).trim();
		    if (w.equalsIgnoreCase(word))
				wordFound = true;
			else
				i++;
	    }

	    if (!wordFound)
	        throw new IllegalArgumentException(
		        word + " not present");

	    return i;
	}

    // getSynonymLine accepts synonym data, and returns
    // the synonym line corresponding to a given word.
    // If the given word is not present, an exception of
    // the type IllegalArgumentException is thrown.
    public static String getSynonymLine (String[] synonymData,
        String word) throws IllegalArgumentException
    {
		int index = synonymLineIndex(synonymData, word);

	    return synonymData[index];
	}

    // addSynonymLine accepts synonym data, and adds a given
    // synonym line to the data.
	public static String[] addSynonymLine (String[] synonymData,
	    String synonymLine)
	{
		String[] synData = new String[synonymData.length + 1];
		for (int i = 0; i < synonymData.length; i++)
		    synData[i] = synonymData[i];
		synData[synData.length - 1] = synonymLine;

	    return synData;
	}

    // removeSynonymLine accepts synonym data, and removes
    // the synonym line corresponding to a given word.
    // If the given word is not present, an exception of
    // the type IllegalArgumentException is thrown.
	public static String[] removeSynonymLine (String[] synonymData,
	    String word) throws IllegalArgumentException
    {
        boolean wordFound = false;

        //String[] removeData = new String[synonymData.length - 1];
        //allowing elements to be added and removed from the list as needed
        ArrayList<String> removeData = new ArrayList<>();
        for (int i = 0; i < synonymData.length; i++){
            if (synonymData[i].startsWith(word +" | ")){
                wordFound = true;
                continue;
            }
            removeData.add(synonymData[i]);
        
        }

        if (!wordFound){
            throw new IllegalArgumentException(word + "not present");
        
        }
        //converts the removeData list back to an array and returns it.
        return removeData.toArray(new String[0]);


            
        


	
		// add code here
	}

    // getSynonyms returns synonyms in a given synonym line. 
	private static String[] getSynonyms (String synonymLine)
	{
        
        String regex = ", ";
        String[] synonyms = synonymLine.split(regex);
        return synonyms;
        // add code here
	}

    // addSynonym accepts synonym data, and adds a given
    // synonym for a given word.
    // If the given word is not present, an exception of
    // the type IllegalArgumentException is thrown.
	public static void addSynonym (String[] synonymData,
	    String word, String synonym) throws IllegalArgumentException
	{
        boolean wordFound = false;
        int index = synonymLineIndex(synonymData, word);
        String newSynonymLine = word + " | " + getSynonymLine(synonymData, word) + ", " + synonym;
        synonymData[index] = newSynonymLine;

        if (!wordFound){
            throw new IllegalArgumentException(word + "not present");
        
        }

        // add code here
	}

    // removeSynonym accepts synonym data, and removes a given
    // synonym for a given word.
    // If the given word or the given synonym is not present, an
    // exception of the type IllegalArgumentException is thrown.
    // If there is only one synonym for the given word, an
    // exception of the type IllegalStateException is thrown.
	public static void removeSynonym (String[] synonymData,
	    String word, String synonym)
	    throws IllegalArgumentException, IllegalStateException
	{
        boolean wordFound = false;
        String []synonymsArray = getSynonyms(getSynonymLine(synonymData, word));
        
        if (synonymsArray.length <= 1) 
        {
            throw new IllegalStateException("Cannot remove the only synonym for the word");
        }
        List<String> removedSynonyms = new ArrayList<>();
        for (int i = 0; i < synonymsArray.length; i++){
            if (synonymsArray[i].equals(synonym))
            {
                wordFound = true;
                continue;                             
            }
            removedSynonyms.add(synonymsArray[i]);
            
        }
        

                


        if (!wordFound){
            throw new IllegalArgumentException(word + "not present");
        
        }
    
        // add code here
	}

    // sortIgnoreCase sorts an array of strings, using
    // the selection sort algorithm
    private static void sortIgnoreCase (String[] strings)
    {
        int n = strings.length;
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            String minStr = strings[i];
            for (int j = i + 1; j < n; j++) {
                /*compareTo() will return a -ve value, 
            if string1 (strings[j]) is smaller than string2 (minStr)*/
                if (strings[j].compareToIgnoreCase(minStr) < 0) {
                    //update
                    minStr = strings[j];
                }
            }
             // Swapping the minimum element 
        if (minIndex != i)
        {
            String temp = strings[minIndex];
            strings[minIndex] = strings[i];
            strings[i] = temp;

        }
        }
       
        
    
        // add code here
	}

    // sortSynonymLine accepts a synonym line, and sorts
    // the synonyms in this line
    private static String sortSynonymLine (String synonymLine)
    {
        String[] synonyms = synonymLine.split(", ");
        sortIgnoreCase(synonyms);
        return String.join(", ", synonyms);

	    // add code here
	}

    // sortSynonymData accepts synonym data, and sorts its
    // synonym lines and the synonyms in these lines
	public static void sortSynonymData (String[] synonymData)
	{
        for (int i = 0; i < synonymData.length; i++) 
        {
            synonymData[i] = sortSynonymLine(synonymData[i]);
        }
        sortIgnoreCase(synonymData);

        // add code here
	}
}