import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class SurveyMain {
    public static final String SURVEYS_FN = "surveys";
    private Scanner kb;
    private SurveyList surveys;
    private Random rand = new Random(System.currentTimeMillis());


    private void go() {
        kb = new Scanner(System.in);
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

    private void quit() {
        save(SURVEYS_FN, surveys);
        System.exit(0);
    }

    private void prevMenu() {
        save(SURVEYS_FN, surveys);
        displaySurveyMenu();
    }

    private createSurvey() {
        kb = new Scanner(System.in);
        int num = kb.nextLine();
        System.out.println("The option you have chosen is: " + num + "\n");
        while(true) {
            char opt = displayQuestionMenu();
            switch (opt) {
                case '1'
                    TrueFalse(); break;
                case '2':
                    MultipleChoice() break;
                case '3':
                    ShortAnswer();break;
                case '4':
                    Essay();break;
                case '5':
                    ValidDate();break;
                case '6':
                    Matching();break;
                case '7':
                    prevMenu();break;
                default:
                    System.out.println("Invalid response");
            }
    }



    private char displayQuestionMenu() {
        System.out.println("\n" +
        "1) Add a new T/F question\n" +
        "2) Add a new multiple choice question\n" +
        "3) Add a new short answer question\n" +
        "4) Add a new essay question\n" +
        "5) Add a new date question\n" +
        "6) Add a new matching question\n" +
        "7) Return to previous menu"););
        return kb.nextLine().charAt(0)
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
