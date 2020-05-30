package questionControllers;

import org.junit.jupiter.api.Test;
import questionTypes.Matching;
import responses.MatchingResp;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static questionTypes.QuestionType.MATCHING;

public class MatchingControllerTest {
    Scanner kb = new Scanner(System.in);

    @Test
    void inputQuestionTest() {
        Matching m = new Matching("Match the following:", 3,
            List.of("Houston", "Fannin", "Travis"),
            List.of("Alamo", "Goliad", "San Jacinto"),
            ( Map<Integer, String> ) MATCHING.getController().inputQuestion(SurveyOrTest.TEST));
        assertEquals(Map.of(1, "C", 2, "B", 3, "A"),m.getMatches());
    }

    @Test
    void changeQuestionTest() {

        Matching m = new Matching("Match the following:", 3,
            List.of("Houston", "Fannin", "Travis"),
            List.of("Alamo", "Goliad", "San Jacinto"),
            Map.of(1, "C", 2, "B", 3, "A"));
        MATCHING.getController().changeQuestion(SurveyOrTest.TEST, m);
        assertEquals(3, m.getNumInList());
    }

    @Test
    void askQuestionTest() {
        MatchingResp matchingResp = ( MatchingResp ) MATCHING.getController().askQuestion(
            new Matching("Match the following:", 3,
            List.of("Houston", "Fannin", "Travis"),
            List.of("Alamo", "Goliad", "San Jacinto"),
            Map.of(1, "C", 2, "B", 3, "A")));
//        assertEquals(3, matchingResp.getMatches().size());
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