package controllers;

import questionTypes.Question;
import responses.TestResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable {
    private final String name;
    private List<Question> questions;
//    private List<TestAnswer> correctAnswers;
    private List<TestResponse> responses;

    public Test( String name ) {
        this.name = name;
        questions = new ArrayList<>();
//        correctAnswers = new ArrayList<>();
        responses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public void addQuestion(Question question) { questions.add(question); }

//    public List<TestAnswer> getCorrectAnswers() {
//        return correctAnswers;
//    }
//
//    public void setCorrectAnswers( List<TestAnswer> correctAnswers ) {
//        this.correctAnswers = correctAnswers;
//    }
//
//    public void addCorrectAnswers( TestAnswer correctAnswer ) {
//        this.correctAnswers.add(correctAnswer);
//    }

    public List<TestResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<TestResponse> responses) { this.responses = responses; }

    public void addResponse( TestResponse response) { responses.add(response); }

    public boolean equals(Test test) {
        return this.name.compareTo(test.getName()) == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Test: ").append(name)
            .append(" questions: ").append(questions.size())
            .append(" responses: ").append(responses.size())
            .append("\n");
        for ( Question question : questions ) {
            sb.append(question.getQuestion());
        }
        return sb.toString();
    }
}
