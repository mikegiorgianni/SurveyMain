package questionControllers;

import questionTypes.TrueFalse;
import responses.QuestionResp;
import responses.SimpleResp;

import java.util.Scanner;

import static questionControllers.SurveyOrTest.TEST;

public class TrueFalseController extends QuestionOps<TrueFalse> {
    private final Scanner kb = new Scanner(System.in);

    @Override
    public TrueFalse inputQuestion(SurveyOrTest st) {
        String question = promptAccept("Enter question: ");
        if (st == TEST) {
            boolean answer = promptAccept("Enter answer: ").toUpperCase().charAt(0) == 'T';
            return new TrueFalse(question, answer);
        }
        return new TrueFalse(question);
    }

    @Override
    public void changeQuestion( SurveyOrTest st, TrueFalse question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if (!resp.isEmpty()) question.setQuestion(resp);
        if (st == TEST ) {
            String s = question.isTrue()?"true":"false";
            String t = promptAccept(s + ": ");
            if (t == null || t.isEmpty()) t = s;
            question.setAnswer(t.toUpperCase().charAt(0) == 'T');
        }
    }

    @Override
    public QuestionResp askQuestion( TrueFalse question ) {
        System.out.print(question.getQuestion());
        return new SimpleResp(question, promptAccept(" (Enter T/F:) ").toUpperCase());
    }

    @Override
    public String displayQuestion(boolean withAnswers, TrueFalse question) {
        StringBuilder sb = new StringBuilder("True/False: " + question.getQuestion());
        if (withAnswers) sb.append(" : ").append((question.isTrue() ? "True" : "False"));
        return sb.append("\n").toString();
    }
    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }
}