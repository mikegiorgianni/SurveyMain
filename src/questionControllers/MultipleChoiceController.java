package questionControllers;

import questionTypes.MultipleChoice;
import questionTypes.QuestionOps;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultipleChoiceController implements QuestionOps<MultipleChoice> {
    private final Scanner kb = new Scanner(System.in);

   @Override
    public MultipleChoice inputQuestion() {
        String question = promptAccept("Enter question: ");
        int numOfChoices = promptNumber("Enter number of choices: ", 0);
        int correctAnswer = promptNumber("Enter correct answer: ", 0);
        List<String> answers = new ArrayList<>();
        for ( int i = 0; i < numOfChoices; i++ ) {
            answers.add(promptAccept("Enter a choice: "));
        }
        return new MultipleChoice(question, numOfChoices, correctAnswer, answers);
    }

    @Override
    public void changeQuestion( MultipleChoice question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if ( !resp.isEmpty() ) question.setQuestion(resp);
        question.setNumOfChoices(promptNumber("Enter number of choices: ",
            question.getNumOfChoices()));
        question.setCorrectAnswer(promptNumber("Enter correct answer: ",
            question.getCorrectAnswer()));
        for ( int i = 0; i < question.getNumOfChoices(); i++ ) {
            question.getChoices().set(i, promptAccept("Enter a choice: "));
        }
    }

    @Override
    public String askQuestion( MultipleChoice question ) {
        System.out.println(question.getQuestion());
        for ( int i = 0; i < question.getNumOfChoices(); i++ ) {
            System.out.printf("%d. %s", i+1, question.getChoices().get(i));
        }
        return promptAccept("Enter most correct response: ");
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
