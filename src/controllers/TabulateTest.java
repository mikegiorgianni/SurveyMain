package controllers;

import questionControllers.QuestionOps;
import responses.TestResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Scanner;

public class TabulateTest {
    QuestionOps controller;
    Scanner kb;

    public static final String TESTS_FN = "tests";

    public void go() {
        kb = new Scanner(System.in);
        controller = new QuestionOps();
        List<String> tests = ( List<String> ) load(TESTS_FN);
        String testName = promptAccept("Enter test name: ");
        if (!tests.contains(testName)) {
            System.out.println("Test not found.");
            System.exit(9);
        }
        Test test = ( Test ) load(testName);
        List<TestResponse> responses = test.getResponses();
        System.out.printf("%-30s %-5s\n","Test Taker", "Grade");
        // for each test taken
        responses.forEach(resp -> System.out.printf("%-30s %5.1f\n",
            resp.getTakerName(), resp.getGrade() * 100));
    }

    private String promptAccept(String prompt) {
        System.out.print(prompt);
        return kb.nextLine();
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

