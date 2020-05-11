package questionControllers;

import questionTypes.ValidDate;
import responses.QuestionResp;
import responses.SimpleResp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static questionControllers.SurveyOrTest.SURVEY;
import static questionControllers.SurveyOrTest.TEST;

public class ValidDateController extends QuestionOps<ValidDate> {
    private final Scanner kb = new Scanner(System.in);
    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    DateTimeFormatter ds = DateTimeFormatter.ofPattern("MMddyyyy");

    @Override
    public ValidDate inputQuestion(SurveyOrTest st) {
        String question = promptAccept("Enter question: ");
        if (st == TEST) {
            System.out.print("Enter valid date (mmddyyyy): ");
            LocalDate answer = getValidDate(null);
            return new ValidDate(question, answer);
        }
        return new ValidDate(question);
    }

    @Override
    public void changeQuestion( SurveyOrTest st, ValidDate question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if (!resp.isEmpty()) question.setQuestion(resp);
        if (st == TEST) {
            question.setAnswer(getValidDate(question.getAnswer()));
        }
    }

    @Override
    public QuestionResp askQuestion( ValidDate question ) {
        System.out.println(question.getQuestion() + "\n");
        System.out.print("Enter valid date (mmddyyyy): ");
        return new SimpleResp(question, getValidDate(null).toString());
    }

    @Override
    public String displayQuestion(SurveyOrTest st, ValidDate question) {
        if (st == SURVEY) return null;
        return "Valid date: " + question.getQuestion() + " : " +
            question.getAnswer().format(df) +"\n";
    }

    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }

    public LocalDate getValidDate(LocalDate date) {
        LocalDate answer;
        String resp;

        while (true) {
            resp = date == null ?
                promptAccept("Enter date (mmddyyyy: "):
                promptAccept(date.format(df) + ": ") ;
            try {
                if (resp.isEmpty() && date != null) resp = date.format(ds);
                if ( resp.length() != 8 )
                    throw new IllegalArgumentException("Invalid response length");
                int mm = Integer.parseInt(resp.substring(0, 2));
                int dd = Integer.parseInt(resp.substring(2, 4));
                int yyyy = Integer.parseInt(resp.substring(4));
                if ( mm < 1 || mm > 12 ) throw new IllegalArgumentException("Invalid month");
                if ( dd < 1 || dd > 31 ) throw new IllegalArgumentException("Invalid day");
                answer = LocalDate.of(yyyy, mm, dd);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date - " + resp);
            }
        }
        return answer;
    }
}