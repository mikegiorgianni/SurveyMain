import questionControllers.*;
import questionTypes.*;

import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static questionTypes.QuestionType.*;

public class SurveyMain {
    public static final String SURVEYS_FN = "surveys";
    private Scanner kb;
    private SurveyList surveys;
    private Random rand = new Random(System.currentTimeMillis());
    private List<QuestionOps> controllers;
    private Survey survey;


    private void go() {
        kb = new Scanner(System.in);
        surveys = new SurveyList();
        controllers = new ArrayList<>();
        controllers.add(new TrueFalseController());
        controllers.add(new ShortAnswerController());
        controllers.add(new EssayController());
        controllers.add(new ValidDateController());
        controllers.add(new MultipleChoiceController());
        controllers.add(new MatchingController());
        surveys = ( SurveyList ) load(SURVEYS_FN);
        System.out.println(surveys);
        while(true) {
            char opt = displaySurveyMenu();
            switch (opt) {
                case '1': createSurvey(); break;
                case '2': displaySurvey(); break;
                case '3': loadSurvey();break;
                case '4': saveSurvey();break;
                case '5': takeSurvey();break;
                case '6': modifySurvey();break;
                case '7': quit();break;
                default:
                System.out.println("Invalid response");
            }
        }
    }

    private void displaySurvey() {
    }

    private void loadSurvey() {

    }

    private void saveSurvey() {

    }

    private void takeSurvey() {
    }

    private void modifySurvey() {
    }

    private void quit() {
        save(SURVEYS_FN, surveys);
        System.exit(0);
    }

    private void prevMenu() {
        save(SURVEYS_FN, surveys);
        displaySurveyMenu();
    }

    private void createSurvey() {
        String surveyName = promptAccept("Enter survey name: ");
        survey = new Survey(surveyName);
        while(true) {
            char opt = displayQuestionMenu();
            switch (opt) {
                case '1':
                    trueFalse();
                    break;
                case '2':
                    multipleChoice();
                    break;
                case '3':
                    shortAnswer();
                    break;
                case '4':
                    essay();
                    break;
                case '5':
                    validDate();
                    break;
                case '6':
                    matching();
                    break;
                case '7':
                    prevMenu();
                    break;
                default:
                    System.out.println("Invalid response");
            }
        }
    }

    private void multipleChoice() {
        MultipleChoice mcQuestion = (MultipleChoice) controllers.get(MULTIPLE_CHOICE.ordinal()).inputQuestion();
        survey.addQuestion(mcQuestion);
    }

    private void shortAnswer() {
        ShortAnswer shortAnswer = (ShortAnswer) controllers.get(SHORT_ANSWER.ordinal()).inputQuestion();
        survey.addQuestion(shortAnswer);
    }

    private void essay() {
        Essay essay = (Essay) controllers.get(ESSAY.ordinal()).inputQuestion();
        survey.addQuestion(essay);
    }

    private void validDate() {
        ValidDate validDate = (ValidDate) controllers.get(VALID_DATE.ordinal()).inputQuestion();
        survey.addQuestion(validDate);
    }

    private void matching() {
        Matching matching = (Matching) controllers.get(MATCHING.ordinal()).inputQuestion();
        survey.addQuestion(matching);
    }

    private void trueFalse() {
        TrueFalse tfQuestion = (TrueFalse) controllers.get(TRUE_FALSE.ordinal()).inputQuestion();
        survey.addQuestion(tfQuestion);
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

    private char displaySurveyMenu() {
        System.out.print("\n" +
        "1) Create a new Survey\n" +
        "2) Display an existing Survey\n" +
        "3) Load an existing Survey\n" +
        "4) Save the current Survey\n" +
        "5) Take the current Survey\n" +
        "6) Modifying the current Survey\n" +
        "7) Quit");
        return kb.nextLine().charAt(0);
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

    private void insertSurvey(String survey) { surveys.addSurvey(survey); }

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
        new SurveyMain().go();
    }
}
