package questionControllers;

import questionTypes.ShortAnswer;
import responses.QuestionResp;
import responses.SimpleResp;

import java.util.Scanner;

import static questionControllers.SurveyOrTest.TEST;

public class ShortAnswerController extends QuestionOps<ShortAnswer> {
    private final Scanner kb = new Scanner(System.in);

    @Override
    public ShortAnswer inputQuestion(SurveyOrTest st) {
        String question = promptAccept("Enter question: ");
        if (st == TEST) {
            String answer = promptAccept("Enter answer: ");
            return new ShortAnswer(question, answer);
        }
        return new ShortAnswer(question);
    }

    @Override
    public void changeQuestion( SurveyOrTest st, ShortAnswer question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if (!resp.isEmpty()) question.setQuestion(resp);
        if (st == TEST) {
            resp = promptAccept(question.getAnswer() + ": ");
            if ( !resp.isEmpty() ) question.setAnswer(resp);
        }
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
