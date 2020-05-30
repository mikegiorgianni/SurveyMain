package questionControllers;

import org.junit.jupiter.api.Test;
import questionTypes.ValidDate;

import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static questionTypes.QuestionType.VALID_DATE;

public class ValidDateControllerTest {
    Scanner kb = new Scanner(System.in);

    @Test
    void inputQuestionTest() {
        ValidDate vd = new ValidDate("When did Texas declare its independence from Mexico?",
            ( LocalDate ) VALID_DATE.getController().inputQuestion(SurveyOrTest.TEST));
        assertEquals(LocalDate.of(1836, 3, 2), vd.getAnswer());
    }

    @Test
    void getValidDateTest() {
        LocalDate date = new ValidDateController().getValidDate(null);
        System.out.println(date);
    }

}
