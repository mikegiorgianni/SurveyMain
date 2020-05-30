package controllers;

import entities.Test;
import entities.TestList;
import questionControllers.QuestionOps;
import questionTypes.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import static questionControllers.SurveyOrTest.TEST;
import static questionTypes.QuestionType.*;

public class TestMain {
    public static final String TESTS_FN = "tests";
    private Scanner kb;
    private TestList tests;
    private Test test;
    QuestionOps controller;
    List<Question> questions;

    public void go() {
        kb = new Scanner(System.in);
        controller = new QuestionOps();
        tests = ( TestList ) load(TESTS_FN);
        if ( tests == null ) tests = new TestList();
        else if (tests.size() == 1) {
            test = ( Test ) load(tests.getTests().get(0));
            System.out.println(test.getName() + " loaded by default.");
        } else if (tests.size() > 0){
            System.out.println("Tests:");System.out.println(tests);
        }
        int opt = 0;
        while(opt != 10) {
            opt = displayTestMenu();
            switch (opt) {
                case 1: createTest(); break;
                case 2: displayTest(false); break;
                case 3: displayTest(true); break;
                case 4: loadTest();break;
                case 5: saveTest();break;
                case 6: takeTest();break;
                case 7: modifyTest();break;
                case 8: tabulateTest();break;
                case 9: gradeTest();break;
                case 10: quit();break;
                default: System.out.println("Invalid response");
            }
        }
    }

    private int displayTestMenu() {
        System.out.println("\n" +
            "1) Create a new Test\n" +
            "2) Display an existing Test w/o correct answers\n" +
            "3) Display an existing Test with correct answers\n" +
            "4) Load an existing Test\n" +
            "5) Save the current Test\n" +
            "6) Take a Test\n" +
            "7) Modify the current Test\n" +
            "8) Tabulate a Test\n" +
            "9) Grade a Test\n" +
            "10) Return to previous menu");
        if (kb.hasNextInt()) {
            int n = kb.nextInt();
            kb.nextLine();
            return n;
        }
        else return 0;
    }

    private void createTest() {
        String testName = promptAccept("Enter test name: ");
        if(tests.contains(testName)) {
            System.out.println("That test name is already used.");
            return;
        }
        test = new Test(testName);
        char opt = 0;
        while(opt != '7') {
            opt = displayQuestionMenu();
            switch (opt) {
                case '1': trueFalse(); break;
                case '2': multipleChoice(); break;
                case '3': shortAnswer(); break;
                case '4': essay(); break;
                case '5': validDate(); break;
                case '6': matching(); break;
                case '7': saveTest(); break;
                default: System.out.println("Invalid response");
            }
        }
    }

    private void modifyTest() {
        if (test == null) {
            System.out.println("You must load a test before displaying one");
        } else {
            questions = test.getQuestions();
            displayTest(true);
            int i = promptNumber("Question number to modify: ", 0) - 1;
            System.out.println("Question: " +(i+1));
            controller = questions.get(i).fetchController();
            controller.changeQuestion(TEST, questions.get(i));
            test.setQuestions(questions);
        }
    }

    private void loadTest() {
        tests.getTests().forEach(System.out :: println);
        String testName = promptAccept("Enter test name: ");
        if(tests.contains(testName)) {
            test = ( Test ) load(testName);
        } else test = null;
    }

    private void saveTest() {
        save(test.getName(), test);
        if(!tests.contains(test.getName())) {
            tests.addTest(test.getName());
            save(TESTS_FN, tests);
        }
    }

    private void takeTest() {
        new TakeTest().go();
    }

    private void displayTest( boolean withAnswers ) {
        if (test == null) {
            System.out.println("You must load a test before displaying one");
        } else {
            System.out.println(test.getName());
            List<Question> testQuestions = test.getQuestions();
            for ( int i = 0; i < testQuestions.size(); i++ ) {
                Question question = testQuestions.get(i);
                controller = question.fetchController();
                String s = controller.displayQuestion(withAnswers, question);
                if ( s != null ) {
                    System.out.println( (i +1)+ ")  " + s );
                }
            }
        }
    }

    private void tabulateTest() { new Tabulator().tabulateTest(); }

    private void gradeTest() { new GradeTest().go();}

    private void quit() {
        save(TESTS_FN, tests);
    }

    private char displayQuestionMenu() {
        System.out.println("\n" +
            "1) Add a new T/F question\n" +
            "2) Add a new multiple choice question\n" +
            "3) Add a new short answer question\n" +
            "4) Add a new essay question\n" +
            "5) Add a new date question\n" +
            "6) Add a new matching question\n" +
            "7) Return to previous menu");
        return kb.nextLine().charAt(0);
    }

    private void trueFalse() {
        test.addQuestion(
            ( TrueFalse ) TRUE_FALSE.getController().inputQuestion(TEST));
    }

    private void multipleChoice() {
        test.addQuestion(( MultipleChoice )
            MULTIPLE_CHOICE.getController().inputQuestion(TEST));
    }

    private void shortAnswer() {
        test.addQuestion(( ShortAnswer )
            SHORT_ANSWER.getController().inputQuestion(TEST));
    }

    private void essay() {
        test.addQuestion(( Essay )
            ESSAY.getController().inputQuestion(TEST));
    }

    private void validDate() {
        test.addQuestion((ValidDate)
            VALID_DATE.getController().inputQuestion(TEST));
    }

    private void matching() {
        Matching matching = ( Matching ) MATCHING.getController().inputQuestion(TEST);
        test.addQuestion(new Question(matching.getQuestion()));
//        test.addCorrectAnswers(new TestAnswer(matching.getMatches()));
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
        new TestMain().go();
    }
}
