package Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Loader {
    private int wordLength;
    public HashSet<String> wordList;

    public Loader(int wordLength) throws Exception{
        this.wordLength = wordLength;
        String filePath;

        //Check the desired word length is within word range and load the words from the corresponding file path
        switch (this.wordLength) {
            case 2:
                filePath = "src/resources/WordList/2-letter-words.txt";
                break;
            case 3:
                filePath = "src/resources/WordList/3-letter-words.txt";
                break;
            case 4:
                filePath = "src/resources/WordList/4-letter-words.txt";
                break;
            case 5:
                filePath = "src/resources/WordList/5-letter-words.txt";
                break;
            default:
                throw new Exception("Not Valid Word Length");
        }

        generateWords(filePath);
    }

    // Read the text file and load the words into wordList
    private void generateWords(String filePath){
        wordList = new HashSet<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            while (line != null) {
                wordList.add(line);
                // read next line from the file
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get the list of words
    public HashSet<String> getWordList() {
        return wordList;
    }
}
