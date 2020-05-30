package questionTypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import static questionTypes.QuestionType.*;

public class Answer {
    // Compare answer for True/False, Multiple Choice, and Short Answer questions
    public static boolean compareAnswer( QuestionType type, String answer, String response ) {
        if (type == TRUE_FALSE) {
            response = response.equalsIgnoreCase("T") ? "TRUE" : "FALSE";
        }
        return answer.equalsIgnoreCase(response);
    }

    // Compare answer for Matching questions
    public static boolean compareAnswer( QuestionType type, String answer, Map<Integer, String> map) {
        if (answer.startsWith("{")) answer = answer.substring(1, answer.length()-1);
        String[] s = answer.split(",");
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        for ( String s2 : s) {
            String[] s3 = s2.split("=");
            linkedHashMap.put(Integer.valueOf(s3[0].trim()), s3[1]);
        }
        return ( linkedHashMap.equals(map));
    }

    // Compare for Valid Date questions
    public static boolean compareAnswer( QuestionType type, String answer, LocalDate date) {
        LocalDate ans = LocalDate.parse(answer.trim(), DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            return ans.equals(date);
    }

    // Determine question type
    public static QuestionType getQuestionType(Question question) {
        return
            (question instanceof Essay)?                        ESSAY :
                (question instanceof Matching)?                 MATCHING :
                    (question instanceof MultipleChoice)?       MULTIPLE_CHOICE :
                        (question instanceof ShortAnswer)?      SHORT_ANSWER :
                            (question instanceof TrueFalse)?    TRUE_FALSE :
                                                                VALID_DATE;
    }
}
