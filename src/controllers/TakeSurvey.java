package controllers;

import entities.*;
import questionControllers.QuestionOps;
import questionTypes.Answer;
import questionTypes.Question;
import questionTypes.QuestionType;
import responses.QuestionResp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TakeSurvey {
    public static final String SURVEYS_FN = "surveys";
    public static final String SURVEY_RESPONSES_FN = "surveyResponses";
    QuestionOps controller;
    QuestionType type;
    Scanner kb;


    void go() {
        kb = new Scanner(System.in);
        controller = new QuestionOps();
        SurveyList surveys = ( SurveyList ) load(SURVEYS_FN);
        String surveyName = promptAccept("Enter survey name: ");
        if (!surveys.contains(surveyName)) {
            System.out.println("Survey not found.");
            System.exit(9);
        }
        Survey survey = ( Survey ) load(surveyName);
        String name = promptAccept("Enter responder name: ");
        List<Question> questions = survey.getQuestions();
        List<QuestionResp> responses = new ArrayList<>();

        for ( Question question : questions) {
            controller = question.fetchController();
            type = Answer.getQuestionType(question);

            responses.add(controller.askQuestion(question));
        }
        SurveyResponse response = new SurveyResponse(survey, name, questions, responses);
        String responseName = surveyName + "_" + name;
        save(responseName, response);

        List<SurveyResponseList> surveyResponses = ( List<SurveyResponseList> ) load(SURVEY_RESPONSES_FN);
        if ( surveyResponses == null ) surveyResponses = new ArrayList<>(); // new file
        int i;
        for ( i = 0; i < surveyResponses.size(); i++ ) {
            SurveyResponseList surveyResponce = surveyResponses.get(i);
            if ( surveyResponce.getSurvey().equals(surveyName) ) {
                surveyResponce.addSurveyResponse(responseName);       // add response to existing test
                break;
            }
        }
        if (i == surveyResponses.size()) {                            // did not find test
            SurveyResponseList newRespList = new SurveyResponseList(surveyName);
            newRespList.addSurveyResponse(responseName);            // add response to a new test
            surveyResponses.add(newRespList);                        // add new test to list
        }
        save(SURVEY_RESPONSES_FN, surveyResponses);
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
