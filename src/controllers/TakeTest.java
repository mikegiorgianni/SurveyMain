package controllers;

import questionControllers.QuestionOps;
import questionTypes.Question;
import responses.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TakeTest {
    QuestionOps controller;
    Scanner kb;

    public static final String TESTS_FN = "tests";

    void go() {
        kb = new Scanner(System.in);
        controller = new QuestionOps();
        List<String> tests = ( List<String> ) load(TESTS_FN);
        String testName = promptAccept("Enter test name: ");
        if (!tests.contains(testName)) {
            System.out.println("Test not found.");
            System.exit(9);
        }
        Test test = ( Test ) load(testName);
        String name = promptAccept("Enter taker name: ");
        List<QuestionResp> responses = new ArrayList<>();
        List<TestAnswer> correctAnswers = new ArrayList<>();
        for ( Question question : test.getQuestions()) {
            QuestionResp questionResp = controller.askQuestion(question);
            responses.add(questionResp);
            if (questionResp instanceof MatchingResp ) {
                correctAnswers.add(new TestAnswer((( MatchingResp ) questionResp).getResponse()));
            } else {
                correctAnswers.add(new TestAnswer((( SimpleResp ) questionResp).getResponse()));
            }
        }
        TestResponse response = new TestResponse(test, name, responses, correctAnswers, 0);
        save(testName + "_" + name, response);
        test = ( Test ) load(testName);
        test.addResponse(response);
        save(testName, test);
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
