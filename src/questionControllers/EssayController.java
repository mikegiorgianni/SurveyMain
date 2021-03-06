package questionControllers;

import questionTypes.Essay;
import responses.QuestionResp;
import responses.SimpleResp;

import java.util.Scanner;

public class EssayController extends QuestionOps<Essay> {
    private final Scanner kb = new Scanner(System.in);

    @Override
    public Essay inputQuestion(SurveyOrTest st) {
        String question = promptAccept("Enter question: ");
        return new Essay(question);
    }

    @Override
    public void changeQuestion( SurveyOrTest st, Essay question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if (!resp.isEmpty()) question.setQuestion(resp);
    }

    @Override
    public QuestionResp askQuestion( Essay question ) {
        return new SimpleResp(question, promptAccept(question.getQuestion()+"\n"));
    }

    @Override
    public String displayQuestion(boolean withAnswers, Essay question) {
        return "Essay: " + question.getQuestion() + "\n";
    }
    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }
}
