package controllers;

import questionTypes.Question;
import responses.SurveyResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Survey implements Serializable {
    private final String name;
    private List<Question> questions;
    private List<SurveyResponse> responses;

    public Survey(String name) {
        this.name = name;
        questions = new ArrayList<>();
        responses = new ArrayList<>();
    }

    public String getName() { return name; }

    public boolean equals(Survey survey) {
        return this.name.compareTo(survey.getName()) == 0;
    }

    public void addQuestion(Question question) { questions.add(question); }

    public List<Question> getQuestions() { return questions; }

    public void addResponse( SurveyResponse response) { responses.add(response); }

    public List<SurveyResponse> getResponses() { return responses; }

    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public void setResponses(List<SurveyResponse> responses) { this.responses = responses; }


    @Override
    public String toString() {
        StringBuilder q = new StringBuilder();
        for ( Question question : questions ) {
            q.append(question.toString());
        }
        return "Survey: " + name + " ->\n" + q.substring(0, q.length()-1);
    }
}
