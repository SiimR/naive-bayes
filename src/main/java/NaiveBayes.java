import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class NaiveBayes {
    private List<String> vocabulary;
    private HashMap<String, Integer> vocabularyScam;
    private HashMap<String, Integer> vocabularyHam;

    public NaiveBayes(List<String> vocabulary, HashMap<String, Integer> vocabularyScam, HashMap<String, Integer> vocabularyHam) {
        this.vocabulary = vocabulary;
        this.vocabularyScam = vocabularyScam;
        this.vocabularyHam = vocabularyHam;
    }

    public BigDecimal getScamProbability(String scamSentence) {
        return sentenceProbability(vocabularyScam, scamSentence);
    }

    public BigDecimal getHamProbability(String hamSentence) {
        return sentenceProbability(vocabularyHam, hamSentence);
    }

    private BigDecimal sentenceProbability(HashMap<String, Integer> hashMapVocabulary, String sentence) {
        BigDecimal finalProbability = new BigDecimal(1.0);
        String[] words = formatSentence(sentence);

        for (String word : formatSentence(sentence)) {
            BigDecimal probability = classificationProbability(hashMapVocabulary, word);
            if (probability != null) finalProbability = finalProbability.multiply(probability);
        }
        return finalProbability;
    }

    private String[] formatSentence(String sentence) {
        return sentence.replaceAll("[^a-zA-Z0-9]", " ").split(" ");
    }

    private BigDecimal classificationProbability(HashMap<String, Integer> hashMapVocabulary, String word) {
        Integer numberOfTimes = hashMapVocabulary.get(word);
        if (numberOfTimes == null) return null;
        return BigDecimal.valueOf((numberOfTimes + 1) / (getTotalWordsInVocabulary(hashMapVocabulary) + hashMapVocabulary.size()));
    }

    private double getTotalWordsInVocabulary(HashMap<String, Integer> hashMapVocabulary) {
        return hashMapVocabulary.values().stream().mapToInt(i -> i).sum();
    }
}
