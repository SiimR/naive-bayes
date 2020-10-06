import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument given!");
            System.out.println("Run: java -jar naive-bayes.jar 'ARG1'");
            System.out.println("Where ARG1 is a sentence you want to check.");
            return;
        }
        TestDataManager manager = new TestDataManager();
        NaiveBayes naiveBayes = new NaiveBayes(manager.getVocabulary(), manager.getVocabularyScam(), manager.getVocabularyHam());
        BigDecimal scam = naiveBayes.getScamProbability(args[0]);
        BigDecimal ham = naiveBayes.getHamProbability(args[0]);
        System.out.println();
        System.out.println("Sentence: ");
        System.out.println(args[0]);
        System.out.println();
        if (scam.compareTo(ham) > 0) System.out.println("Sentence is Scam!");
        else System.out.println("Sentence is Ham!");

    }


}
