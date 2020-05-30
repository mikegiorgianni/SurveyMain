package controllers;

import entities.Test;
import entities.TestList;
import entities.TestResponse;
import entities.TestResponseList;
import questionControllers.QuestionOps;
import questionTypes.*;
import responses.QuestionResp;
import responses.TestAnswer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static questionTypes.QuestionType.*;

public class TakeTest {
    public static final String TESTS_FN = "tests";
    public static final String TEST_RESPONSES_FN = "testResponses";
    QuestionOps controller;
    QuestionType type;
    Scanner kb;


    void go() {
        kb = new Scanner(System.in);
        TestList tests = ( TestList ) load(TESTS_FN);
        String testName = promptAccept("Enter test name: ");
        if (testName.isEmpty() || !tests.contains(testName)) {
            System.out.println("Test not found.");
            return;
        }
        Test test = ( Test ) load(testName);
        if (test == null) {
            System.out.println("Test not found.");
            return;
        }
        String name;
        do name = promptAccept("Enter taker name: ");
        while (name.isEmpty());

        List<Question> questions = test.getQuestions();
        List<QuestionResp> responses = new ArrayList<>();
        List<TestAnswer> correctAnswers = new ArrayList<>();

        for ( Question question : questions ) {
            controller = question.fetchController();
            type = Answer.getQuestionType(question);

            // fetch correct answer
            if ( type == ESSAY ) correctAnswers.add(new TestAnswer("n/a"));

            else if ( type == MATCHING ) correctAnswers.add(
                new TestAnswer((( Matching ) question).getMatches()));

            else if ( type == MULTIPLE_CHOICE ) correctAnswers.add(
                new TestAnswer(String.valueOf((( MultipleChoice ) question).getCorrectAnswer())));

            else if ( type == SHORT_ANSWER ) correctAnswers.add(
                new TestAnswer((( ShortAnswer ) question).getAnswer()));

            else if ( type == TRUE_FALSE ) correctAnswers.add(
                new TestAnswer(String.valueOf((( TrueFalse ) question).isTrue())));

            else   // type == VALID_DATE
                correctAnswers.add(new TestAnswer((( ValidDate ) question).getDate()));

            responses.add(controller.askQuestion(question));    // fetch taker response
        }
        TestResponse response = new TestResponse(
            test, name, questions, responses, correctAnswers, 0);
        String responseName = testName + "_" + name;
        save(responseName, response);
        List<TestResponseList> testResponses = ( List<TestResponseList> ) load(TEST_RESPONSES_FN);
        if ( testResponses == null ) testResponses = new ArrayList<>(); // new file
        int i;
        for ( i = 0; i < testResponses.size(); i++ ) {
            TestResponseList testResponce = testResponses.get(i);
            if ( testResponce.getTest().equals(testName) ) {
                testResponce.addTestRespounses(responseName);       // add response to existing test
                break;
            }
        }
        if (i == testResponses.size()) {                            // did not find test
            TestResponseList newRespList = new TestResponseList(testName, new ArrayList<>());
            newRespList.addTestRespounses(responseName);            // add response to a new test
            testResponses.add(newRespList);                         // add new test to list
        }
        save(TEST_RESPONSES_FN, testResponses);
    }

    private String promptAccept(String prompt) {
        System.out.print(prompt);
        return kb.nextLine();
    }

    private void save(String fileName, Object surveys) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(surveys);
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
        new TakeTest().go();
    }
}
