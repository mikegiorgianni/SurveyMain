package questionControllers;

import questionTypes.TrueFalse;
import responses.QuestionResp;
import responses.SimpleResp;

import java.util.Scanner;

public class TrueFalseController extends QuestionOps<TrueFalse> {
    private final Scanner kb = new Scanner(System.in);

    @Override
    public TrueFalse inputQuestion() {
        String question = promptAccept("Enter question: ");
        boolean answer = promptAccept("Enter answer: ").toUpperCase().charAt(0) == 'T';
        return new TrueFalse(question, answer);
    }

    @Override
    public void changeQuestion( TrueFalse question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if (!resp.isEmpty()) question.setQuestion(resp);
        question.setAnswer(promptAccept(question.isTrue() + ": ").toUpperCase().charAt(0) == 'T');
    }

    @Override
    public QuestionResp askQuestion( TrueFalse question ) {
        System.out.print(question.getQuestion());
        return new SimpleResp(question, promptAccept(" (Enter T/F:) ").toUpperCase());
    }

    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }
}
