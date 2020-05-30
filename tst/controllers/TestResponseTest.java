package controllers;

import entities.TestResponse;
import entities.TestResponseList;
import org.junit.jupiter.api.Test;
import questionControllers.QuestionOps;
import questionTypes.*;
import responses.MatchingResp;
import responses.QuestionResp;
import responses.SimpleResp;
import responses.TestAnswer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class TestResponseTest {
    public static final String TEST_RESPONSES_FN = "testResponces";
    QuestionOps controller;
    QuestionType type;
    @Test
    void takeTestTest() {
        List<TestResponseList> tests = ( List<TestResponseList> ) load(TEST_RESPONSES_FN);
        for ( TestResponseList list : tests ) {
            System.out.println("Test: " + list.getTest());
            for ( String responseName : list.getTestResponses() ) {
                TestResponse response = ( TestResponse ) load(responseName);
                System.out.print("Taken by: " + response.getTakerName());
                if (response.getGrade() > 0)
                    System.out.printf("\t\tGrade: %.0f\n", response.getGrade());
                List<Question> questions = response.getQuestions();
                List<TestAnswer> answers = response.getCorrectAnswers();
                List<QuestionResp> responces = response.getResponses();
                for ( int i = 0; i < questions.size(); i++ ) {
                    Question question = questions.get(i);
                    String answer = answers.get(i).getAnswer();
                    type = Answer.getQuestionType(question);
                    controller = question.fetchController();
                    System.out.print(controller.displayQuestion(true, question));
                    System.out.print("Response given: ");
                    boolean credit;
                    if ( type == QuestionType.MATCHING ) {
                        Map<Integer, String> map = (( MatchingResp ) responces.get(i)).getResponse();
                        System.out.println(map);
                        credit = Answer.compareAnswer(type, answer, map);
                    } else if ( type == QuestionType.VALID_DATE ) {
                        LocalDate date = (( ValidDate ) question).getAnswer();
                        System.out.print((( ValidDate ) question).getDate());
                        credit = Answer.compareAnswer(type, answer, date);
                    } else {
                        String resp2 = (( SimpleResp ) responces.get(i)).getResponse();
                        System.out.println(resp2);
                        credit = Answer.compareAnswer(type, answer, resp2);
                    }
                    System.out.println(credit ? "correct" : "wrong");
                    System.out.println();
                }
                System.out.println();
            }
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

}