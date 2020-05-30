package controllers;

import entities.TestResponse;
import entities.TestResponseList;
import questionTypes.Answer;
import questionTypes.Question;
import questionTypes.QuestionType;
import questionTypes.ValidDate;
import responses.MatchingResp;
import responses.QuestionResp;
import responses.SimpleResp;
import responses.TestAnswer;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GradeTest {
    Scanner kb;

    public static final String TEST_RESPONSES_FN = "testResponses";
    private static final String[] words = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public void go() {
        kb = new Scanner(System.in);
        List<TestResponseList> testResponseList = ( List<TestResponseList> ) load(TEST_RESPONSES_FN);
        if (testResponseList.isEmpty()) {
            System.out.println("No tests to grade.");
            promptAccept("Press Enter to continue.");
            return;
        }
        System.out.println("Select an existing test to grade:");
        for ( int i = 0; i < testResponseList.size(); i++ ) {
            System.out.printf("%d) %s\n", i + 1, testResponseList.get(i).getTest());
        }
        int testNo = promptNumber("> ", 0);
        if (testNo < 1 || testNo > testResponseList.size()) {
            System.out.println("Invalid rresponse.");
            promptAccept("Press Enter to continue.");
            return;
        }
        List<String> responseNames = testResponseList.get(testNo - 1).getTestResponses();
        System.out.println("Select an existing response set:");
        for ( int j = 0; j < responseNames.size(); j++ ) {
            System.out.printf("%d) %s\n", j + 1, responseNames.get(j));
        }
        int responseNo = promptNumber("> ", 0);
        if (responseNo < 1 || responseNo > responseNames.size()) {
            System.out.println("Invalid rresponse.");
            promptAccept("Press Enter to continue.");
            return;
        }
        TestResponse testResponse = ( TestResponse ) load(responseNames.get(responseNo - 1));
        if (testResponse == null) {
            System.out.println("Test response not found.");
            promptAccept("Press Enter to continue.");
            return;
        }
        if (testResponse.getResponses().size() == 0) {
            System.out.println("Responses are missing.");
            promptAccept("Press Enter to continue.");
            return;
        }
        if (testResponse.getCorrectAnswers().size() == 0) {
            System.out.println("correctAnswers are missing.");
            promptAccept("Press Enter to continue.");
            return;
        }
        List<Question> questions = testResponse.getQuestions();
        List<QuestionResp> responses = testResponse.getResponses();     // answers given
        List<TestAnswer> answers = testResponse.getCorrectAnswers();    // correct answers
        int credit = 0;
        int essays = 0;
        boolean correct;
        QuestionType type;
        for ( int i = 0; i < questions.size(); i++ ) {
            Question question = questions.get(i);
            String answer = answers.get(i).getAnswer();
            type = Answer.getQuestionType(question);
            if ( type == QuestionType.MATCHING ) {              // Matching question
                Map<Integer, String> map = (( MatchingResp ) responses.get(i)).getResponse();
                correct = Answer.compareAnswer(type, answer, map);
            } else if ( type == QuestionType.VALID_DATE ) {     // Valid Date question
                LocalDate date = (( ValidDate ) question).getAnswer();
                correct = Answer.compareAnswer(type, answer, date);
            } else if (type == QuestionType.ESSAY) {            // Essay question
                essays++;
                correct = true;
            } else {                                            // all other types
                String resp2 = (( SimpleResp ) responses.get(i)).getResponse();
                correct = Answer.compareAnswer(type, answer, resp2);
            }
            if (correct) credit++;
        }

        float grade = credit * 1.0f / answers.size() * 100.0f;
        float grade2 = (1.0f - essays * 1.0f / answers.size()) * 100.0f;
        System.out.printf("You received an %.0f on the test. The test was worth 100 points, but " +
            "only %.0f of\nthose points could be auto graded because there was %s essay question.\n",
            grade, (grade2), words[essays]);
        testResponse.setGrade(grade);
        save(responseNames.get(responseNo - 1), testResponse);
    }

    private String promptAccept(String prompt) {
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

    private void save(String fileName, Object object) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Object load(String fileName) {
        Object object = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            object = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return object;
    }

    public static void main( String[] args ) {
        new GradeTest().go();
    }
}
