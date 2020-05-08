package questionControllers;

import questionTypes.ShortAnswer;
import responses.QuestionResp;
import responses.SimpleResp;

import java.util.Scanner;

public class ShortAnswerController extends QuestionOps<ShortAnswer> {
    private final Scanner kb = new Scanner(System.in);

    @Override
    public ShortAnswer inputQuestion() {
        String question = promptAccept("Enter question: ");
        String answer = promptAccept("Enter answer: ");
        return new ShortAnswer(question, answer);
    }

    @Override
    public void changeQuestion( ShortAnswer question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if (!resp.isEmpty()) question.setQuestion(resp);
        resp = promptAccept(question.getAnswer() + ": ");
        if (!resp.isEmpty()) question.setAnswer(resp);
    }

    @Override
    public QuestionResp askQuestion( ShortAnswer question ) {
        return new SimpleResp(question, promptAccept(question.getQuestion()+"\n"));
    }

    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }

}
