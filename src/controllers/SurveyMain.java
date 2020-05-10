package controllers;

import questionControllers.QuestionOps;
import questionControllers.SurveyOrTest;
import questionTypes.*;

import java.io.*;
import java.util.Scanner;

import static questionTypes.QuestionType.*;

public class SurveyMain {
    public static final String SURVEYS_FN = "surveys";
    private Scanner kb;
    private SurveyList surveys;
    //private Random rand = new Random(System.currentTimeMillis());
    private Survey survey;
    QuestionOps controller;

    private void go() {
        kb = new Scanner(System.in);
        controller = new QuestionOps();
        surveys = ( SurveyList ) load(SURVEYS_FN);
        if ( surveys == null ) surveys = new SurveyList();
        else { System.out.println("Surveys:");System.out.println(surveys); }
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
        if (survey == null) {
            System.out.println("You must load a survey before displaying one");
        } else {
            System.out.println(survey.toString());
        }
    }

    private void loadSurvey() {
        surveys.getSurveys().forEach(System.out :: println);
        String surveyName = promptAccept("Enter survey name: ");
        if(surveys.contains(surveyName)) {
            survey = ( Survey ) load(surveyName);
        } else survey = null;
    }

    private void saveSurvey() {
        save(survey.getName(), survey);
        surveys.addSurvey(survey.getName());
    }

    private void takeSurvey() {
        new TakeSurvey().go();
    }

    private void modifySurvey() {

    }

    private void quit() {
        save(SURVEYS_FN, surveys);
        System.exit(0);
    }

    private void createSurvey() {
        String surveyName = promptAccept("Enter survey name: ");
        survey = new Survey(surveyName);
        char opt = 0;
        while(opt != '7') {
            opt = displayQuestionMenu();
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
                    break;
                default:
                    System.out.println("Invalid response");
            }
        }
    }

    private void multipleChoice() {
        survey.addQuestion(( MultipleChoice ) MULTIPLE_CHOICE.getController().inputQuestion(SurveyOrTest.SURVEY));
    }

    private void shortAnswer() {
        survey.addQuestion(( ShortAnswer ) SHORT_ANSWER.getController().inputQuestion(SurveyOrTest.SURVEY));
    }

    private void essay() {
        survey.addQuestion(( Essay ) ESSAY.getController().inputQuestion(SurveyOrTest.SURVEY));
    }

    private void validDate() {
        survey.addQuestion((ValidDate) VALID_DATE.getController().inputQuestion(SurveyOrTest.SURVEY));
    }

    private void matching() {
         survey.addQuestion(( Matching ) MATCHING.getController().inputQuestion(SurveyOrTest.SURVEY));
    }

    private void trueFalse() {
        survey.addQuestion(( TrueFalse ) TRUE_FALSE.getController().inputQuestion(SurveyOrTest.SURVEY));
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
        System.out.println("\n" +
        "1) Create a new controllers.Survey\n" +
        "2) Display an existing controllers.Survey\n" +
        "3) Load an existing controllers.Survey\n" +
        "4) Save the current controllers.Survey\n" +
        "5) Take the current controllers.Survey\n" +
        "6) Modifying the current controllers.Survey\n" +
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
