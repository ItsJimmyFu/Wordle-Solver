package Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Loader {
    public HashSet<String> wordList;
    public HashSet<String> solutionWordList;

    public Loader(int wordLength) throws Exception{
        String solutionWordListFilePath;
        String allWordListFilePath;

        //Check the desired word length is within word range and load the words from the corresponding file path
        switch (wordLength) {
            case 2 -> {
                solutionWordListFilePath = "src/resources/WordList/2-letter-words.txt";
                allWordListFilePath = "";
            }
            case 3 -> {
                solutionWordListFilePath = "src/resources/WordList/3-letter-words.txt";
                allWordListFilePath = "";
            }
            case 4 -> {
                solutionWordListFilePath = "src/resources/WordList/4-letter-words.txt";
                allWordListFilePath = "";
            }
            case 5 -> {
                allWordListFilePath = "src/resources/WordList/5-letter-words.txt";
                solutionWordListFilePath = "src/resources/WordList/wordle-list.txt";
            }
            default -> throw new Exception("Not Valid Word Length");
        }

        this.wordList = generateWords(allWordListFilePath);
        this.solutionWordList = generateWords(solutionWordListFilePath);
    }

    // Read the text file and load the words into wordList
    private HashSet<String> generateWords(String filePath){
        HashSet<String> wordList = new HashSet<>();

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
        return wordList;
    }

    //Get the list of possible words
    public HashSet<String> getWordList() {
        return wordList;
    }

    //Get the list of possible solution words
    public HashSet<String> getSolutionWordList() {
        return solutionWordList;
    }
}
