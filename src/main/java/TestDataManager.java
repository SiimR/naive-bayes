import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestDataManager {
    private static final String SCAM_FILE = "scam.properties";
    private static final String HAM_FILE = "ham.properties";
    private HashMap<String, Integer> vocabularyScam = new HashMap<>();
    private HashMap<String, Integer> vocabularyHam = new HashMap<>();
    private List<String> vocabulary = new ArrayList<>();

    public TestDataManager() {
        readVocabularyFile(SCAM_FILE, vocabularyScam);
        readVocabularyFile(HAM_FILE, vocabularyHam);

    }

    public HashMap<String, Integer> getVocabularyScam() {
        return vocabularyScam;
    }

    public HashMap<String, Integer> getVocabularyHam() {
        return vocabularyHam;
    }

    public List<String> getVocabulary() {
        return vocabulary;
    }

    private void generateTestDataFiles() {
        try (CSVReader reader = new CSVReader(new FileReader("emails.csv"))){


            String [] nextLine;
            boolean firstLine = true;
            while ((nextLine = reader.readNext()) != null) {
                String[] innerLine = Arrays.copyOfRange(nextLine, 1, nextLine.length - 2);

                if (firstLine) {

                    firstLine = false;
                    for(String word : innerLine) {
                        vocabularyScam.put(word, 0);
                        vocabularyHam.put(word, 0);
                        vocabulary.add(word);
                    }
                    continue;
                }

                if (isScam(nextLine)) {
                    // Scam
                    updateVocabulary(vocabularyScam, innerLine);
                } else {
                    // Ham
                    updateVocabulary(vocabularyHam, innerLine);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Test data fail not found!");
        }
    }

    private void updateVocabulary(HashMap<String, Integer> hashMapVocabulary, String[] line) {
        int wordIndex = 0;
        for (String number : line) {
            hashMapVocabulary.put(vocabulary.get(wordIndex),
                    hashMapVocabulary.get(vocabulary.get(wordIndex)) + Integer.parseInt(number));
            wordIndex++;
        }
    }

    private void readVocabularyFile(String fileName, HashMap<String, Integer> vocabulary) {
        Properties properties = new Properties();
        try (FileInputStream scamStream = new FileInputStream(fileName)) {
            properties.load(scamStream);
            for (String key : properties.stringPropertyNames()) {
                vocabulary.put(key, Integer.parseInt(properties.get(key).toString()));
            }
        } catch (IOException e) {
            System.out.println("No vocabulary file found!");
            generateTestDataFiles();
            saveToVocabularyFile(SCAM_FILE, vocabularyScam);
            saveToVocabularyFile(HAM_FILE, vocabularyHam);
        }
    }

    private void saveToVocabularyFile(String filename, HashMap<String, Integer> vocabulary) {
        Properties properties = new Properties();

        for (Map.Entry<String,Integer> entry : vocabulary.entrySet()) {
            properties.put(entry.getKey(), entry.getValue().toString());
        }

        try {
            properties.store(new FileOutputStream(filename), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isScam(String[] line) {
        return Integer.parseInt(line[line.length - 1]) == 1;
    }
}
