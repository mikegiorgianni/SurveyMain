package entities;

import questionTypes.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable {
    private final String name;
    private List<Question> questions;

    public Test( String name ) {
        this.name = name;
        questions = new ArrayList<>();
    }

    public Test( String name, List<Question> questions /*, List<TestAnswer> correctAnswers*/ ) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public void addQuestion(Question question) { questions.add(question); }

    public boolean equals(Test test) {
        return this.name.compareTo(test.getName()) == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Test: ").append(name)
            .append(" questions: ").append(questions.size())
            .append("\n");
        for ( Question question : questions ) {
            sb.append(question.getQuestion());
        }
        return sb.toString();
    }
}
