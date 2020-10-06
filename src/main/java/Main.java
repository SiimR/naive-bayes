import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        String scamString = "Hello My name is George Mike I am a lawyer by profession. I wish to offer you the next of kin to my client. You will inherit the sum of ($3.5 Million) dollars my client left in the bank before his death. My client is a citizen of your country who died in auto crash with his wife and only son. I will be entitled with 50% of the total fund while 50% will be for you. Please contact my private email here for more details.................georgemike1065@gmail.com Many thanks in advance, Mr.George Mike, Note: If you received this message in your SPAM/JUNK folder, it is because of the restrictions implemented by your Internet Service Provider, I urge you to treat it genuinely. The information contained in this e-mail is private & confidential and may also be legally privileged.";
//        if (args.length == 0) {
//            System.out.println("No argument given!");
//            System.out.println("Run: java -jar naive-bayes.jar 'ARG1'");
//            System.out.println("Where ARG1 is a sentence you want to check.");
//            return;
//        }
        TestDataManager manager = new TestDataManager();
        NaiveBayes naiveBayes = new NaiveBayes(manager.getVocabulary(), manager.getVocabularyScam(), manager.getVocabularyHam());
        BigDecimal scam = naiveBayes.getScamProbability(scamString);
        BigDecimal ham = naiveBayes.getHamProbability(scamString);
        System.out.println();
        System.out.println("Sentence: ");
        System.out.println(scamString);
        System.out.println();
        if (scam.compareTo(ham) > 0) System.out.println("Sentence is Scam!");
        else System.out.println("Sentence is Ham!");

    }


}
