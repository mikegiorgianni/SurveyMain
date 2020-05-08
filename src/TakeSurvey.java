import questionControllers.QuestionOps;
import questionTypes.Question;
import questionTypes.Survey;
import responses.QuestionResp;
import responses.SurveyResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TakeSurvey {
    QuestionOps controller;
    Scanner kb;

    public static final String SURVEYS_FN = "surveys";

    private void go() {
        kb = new Scanner(System.in);
        controller = new QuestionOps();
        List<String> surveys = ( List<String> ) load(SURVEYS_FN);
        String surveyName = promptAccept("Enter survey name: ");
        if (!surveys.contains(surveyName)) {
            System.out.println("questionTypes.Survey not found.");
            System.exit(9);
        }
        Survey survey = ( Survey ) load(surveyName);
        String name = promptAccept("Enter responder name: ");
        List<QuestionResp> responses = new ArrayList<>();
        for ( Question question : survey.getQuestions()) {
            responses.add(controller.askQuestion(question));
        }
        SurveyResponse response = new SurveyResponse(survey, name, responses);
        save(surveyName + "_" + name, response);
        survey = ( Survey ) load(surveyName);
        survey.addResponse(response);
        save(surveyName, survey);
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
        new TakeSurvey().go();
    }
}
