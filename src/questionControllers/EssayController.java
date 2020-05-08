package questionControllers;

import questionTypes.Essay;
import responses.QuestionResp;
import responses.SimpleResp;

import java.util.Scanner;

public class EssayController extends QuestionOps<Essay> {
    private final Scanner kb = new Scanner(System.in);

    @Override
    public Essay inputQuestion() {
        String question = promptAccept("Enter question: ");
        String answer = promptAccept("Enter answer: ");
        return new Essay(question, answer);
    }

    @Override
    public void changeQuestion( Essay question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if (!resp.isEmpty()) question.setQuestion(resp);
        resp = promptAccept(question.getAnswer() + ": ");
        if (!resp.isEmpty()) question.setAnswer(resp);
    }

    @Override
    public QuestionResp askQuestion( Essay question ) {
        return new SimpleResp(question, promptAccept(question.getQuestion()+"\n"));
    }

    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }
}
