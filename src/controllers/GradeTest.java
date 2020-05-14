package controllers;

import responses.QuestionResp;
import responses.SimpleResp;
import responses.TestAnswer;
import responses.TestResponse;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class GradeTest {
    Scanner kb;

    public static final String TESTS_FN = "tests";

    public void go() {
        kb = new Scanner(System.in);
        TestList tests = ( TestList ) load(TESTS_FN);
        String testName = promptAccept("Enter test name: ");
        if (testName.isEmpty() || !tests.contains(testName)) {
            System.out.println("Test not found.");
            return;
        }
        Test test = ( Test ) load(testName);
        List<TestResponse> responses = test.getResponses();
        for ( int i = 0; i < responses.size(); i++ ) { // for each test taken
            TestResponse response = responses.get(i);
            List<QuestionResp> answers = response.getResponses(); // answers given
            List<TestAnswer> correctAnswers = response.getCorrectAnswers(); // correct answers
            int credit = 0;
            for ( int j = 0; j < answers.size(); j++ ) {        // for each question
                String answer = ( (SimpleResp) answers.get(j)).getResponse();
                if (answer.equalsIgnoreCase(correctAnswers.get(j).getAnswer()))  credit++;
            }
            response.setGrade(credit * 1.0f / answers.size());
            responses.set(i, response);
        }
        save(TESTS_FN, responses);
    }

    private String promptAccept(String prompt) {
        System.out.print(prompt);
        return kb.nextLine();
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
