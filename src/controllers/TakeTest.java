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
        TestList tests = ( TestList ) load(TESTS_FN);
        String testName = promptAccept("Enter test name: ");
        if (testName.isEmpty() || !tests.contains(testName)) {
            System.out.println("Test not found.");
            return;
        }
        Test test = ( Test ) load(testName);
        String name = promptAccept("Enter taker name: ");
        List<QuestionResp> responses = new ArrayList<>();
        for ( Question question : test.getQuestions()) {
            controller = question.fetchController();
            responses.add(controller.askQuestion(question));
        }
        TestResponse response = new TestResponse(test, name, responses, test.getCorrectAnswers(), 0);
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
