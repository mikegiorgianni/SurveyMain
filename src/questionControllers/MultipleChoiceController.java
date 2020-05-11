package questionControllers;

import questionTypes.MultipleChoice;
import responses.QuestionResp;
import responses.SimpleResp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static questionControllers.SurveyOrTest.SURVEY;
import static questionControllers.SurveyOrTest.TEST;

public class MultipleChoiceController extends QuestionOps<MultipleChoice> {
    private final Scanner kb = new Scanner(System.in);

   @Override
    public MultipleChoice inputQuestion(SurveyOrTest st) {
        String question = promptAccept("Enter question: ");
        int numOfChoices = promptNumber("Enter number of choices: ", 0);
        List<String> answers = new ArrayList<>();
        for ( int i = 0; i < numOfChoices; i++ ) {
            answers.add(promptAccept("Enter a choice: "));
        }
        if ( st == TEST) {
            int correctAnswer = promptNumber("Enter correct answer: ", 0);
            return new MultipleChoice(question, numOfChoices, answers, correctAnswer);
        }
        return new MultipleChoice(question, numOfChoices, answers);
    }

    @Override
    public void changeQuestion( SurveyOrTest st, MultipleChoice question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if ( !resp.isEmpty() ) question.setQuestion(resp);
        question.setNumOfChoices(promptNumber("Enter number of choices: ",
            question.getNumOfChoices()));
        for ( int i = 0; i < question.getNumOfChoices(); i++ ) {
            question.getChoices().set(i, promptAccept("Enter a choice: "));
        }
        if (st == TEST) {
            question.setCorrectAnswer(promptNumber("Enter correct answer: ",
                question.getCorrectAnswer()));
        }
    }

    @Override
    public QuestionResp askQuestion( MultipleChoice question ) {
        System.out.println(question.getQuestion());
        for ( int i = 0; i < question.getNumOfChoices(); i++ ) {
            System.out.printf("%d. %s", i+1, question.getChoices().get(i));
        }
        return new SimpleResp(question, promptAccept("Enter response: "));
    }

    @Override
    public String displayQuestion(SurveyOrTest st, MultipleChoice question) {
        if (st == SURVEY) return null;
        StringBuilder sb = new StringBuilder()
            .append("Multiple choice: ")
            .append(question.getQuestion()).append(" ")
            .append("choices: ").append(question.getNumOfChoices()).append(" ")
            .append("answer: ").append(question.getCorrectAnswer()).append("\n");
        List<String> choices = question.getChoices();
        for ( int i = 0; i < question.getNumOfChoices(); i++ ) {
            sb.append("\t").append(i + 1).append(". ").append(choices.get(i)).append("\n");
        }
        return sb.toString();

    }
    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }

    private int promptNumber( String prompt, int no) {
       String resp;
        while (true) {
            resp = promptAccept(prompt);
            if (resp.isEmpty()) return no;
            try {
                return Integer.parseInt(resp);
            } catch (NumberFormatException e) {
                System.out.println("Not a number.");
            }
        }
    }
}
