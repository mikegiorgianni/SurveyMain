package questionControllers;

import org.junit.jupiter.api.Test;

import java.util.Scanner;


class SurveyQuestionTest {
    Scanner kb = new Scanner(System.in);

    @Test
    void inputQuestion() {
        MatchingController mc = new MatchingController();
        mc.inputQuestion();
    }

    @Test
    void changeQuestion() {
    }

    @Test
    void askQuestion() {
    }
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