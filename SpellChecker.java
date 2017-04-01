import java.io.*;
import java.util.ArrayList;


public class SpellChecker {
    private Hashtable<String, String> table = new Hashtable<>();
    final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    
    public void readDictionary(String path) throws IOException {
        
        String nc;
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((nc = bufferedReader.readLine()) != null){
            table.insert(nc, "");
        }

        fileReader.close();
    }

    
   public String readInputText(String path) throws IOException {
        ArrayList<String> mispelled = new ArrayList<>();
        String word;
        StringBuilder result = new StringBuilder();

        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        int i = 1; 
        while ((word = bufferedReader.readLine()) != null){
            if (word.length() == 0)
                continue;

            word = word.toLowerCase();
            if (table.get(word) == null) {
                result.append(word).append(", ");
                result.append(i).append(": ");
                result.append(Suggestions(word));
                result.append("\n");
            }
            i++;
        }

        fileReader.close();
        return result.toString();
    }

    
    private String Suggestions(String word){
        StringBuilder result = new StringBuilder();
        Hashtable<String, Boolean> corrections = new Hashtable<>();
        char[] wordChars = word.toCharArray();
        String correctWord;

        
        for(int i = 0 ; i < wordChars.length ; i++) {
            char origChar = wordChars[i];
            
            for(char c: ALPHABET){
                wordChars[i] = c;
                correctWord = new String(wordChars);
                if(table.get(correctWord) != null &&
                        corrections.get(correctWord) == null &&
                        word.compareTo(correctWord) != 0){

                    result.append(correctWord).append(", ");
                    corrections.insert(correctWord, true);
                }
            }
            wordChars[i] = origChar;
        }
        
        if (result.length() > 1)
            result.delete(result.length() - 2, result.length() - 1);

        return result.toString();
    }

    
   
    
    public int getNoOfColl(){
        return table.getNoOfColl();
    }

    
    public double getAverageChainLength(){
        return table.getAverageChainLength();
    }

    public int getMaxChainLength(){
        return table.getMaxChainLength();
    }
    
     public double getLoadFactor(){
        return table.getLoadFactor();
    }


}
