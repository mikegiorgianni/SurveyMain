package questionControllers;

import questionTypes.QuestionOps;
import questionTypes.ValidDate;

import java.time.LocalDate;
import java.util.Scanner;

public class ValidDateController implements QuestionOps<ValidDate> {
    private final Scanner kb = new Scanner(System.in);

    @Override
    public ValidDate inputQuestion() {
        String question = promptAccept("Enter question: ");
        System.out.print("Enter valid date (mmddyyyy): ");
        LocalDate answer = getValidDate(null);
        return new ValidDate(question, answer);
    }

    @Override
    public void changeQuestion( ValidDate question ) {
        System.out.println("Press to retain current value.");
        String resp = promptAccept(question.getQuestion() + ": ");
        if (!resp.isEmpty()) question.setQuestion(resp);
        question.setAnswer(getValidDate(question.getDate().toString()));
    }

    @Override
    public String askQuestion( ValidDate question ) {
        System.out.println(question.getQuestion() + "\n");
        System.out.print("Enter valid date (mmddyyyy): ");
        return getValidDate(null).toString();
    }

    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }

    private LocalDate getValidDate(String date) {
        LocalDate answer;
        if (date != null) {
            String resp = promptAccept(date + ": ");
            if (!resp.isEmpty()) date = resp;
        }
        while (true) {
            try {
                if ( date == null || date.length() != 8 )
                    throw new IllegalArgumentException("Invalid response length");
                int mm = Integer.parseInt(date.substring(0, 2));
                int dd = Integer.parseInt(date.substring(2, 4));
                int yyyy = Integer.parseInt(date.substring(4));
                if ( mm < 1 || mm > 12 ) throw new IllegalArgumentException("Invalid month");
                if ( dd < 1 || dd > 31 ) throw new IllegalArgumentException("Invalid day");
                answer = LocalDate.of(yyyy, mm, dd);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date");
            }
        }
        return answer;
    }
}