package entities;

import questionTypes.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Survey implements Serializable {
    private final String name;
    private List<Question> questions;

    public Survey(String name) {
        this.name = name;
        questions = new ArrayList<>();
    }

    public Survey(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() { return name; }

    public boolean equals(Survey survey) {
        return this.name.compareTo(survey.getName()) == 0;
    }

    public void addQuestion(Question question) { questions.add(question); }

    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public List<Question> getQuestions() { return questions; }

    @Override
    public String toString() {
        StringBuilder q = new StringBuilder();
        for ( Question question : questions ) {
            q.append(question.toString());
        }
        return "Survey: " + name + " ->\n" + q.substring(0, q.length()-1);
    }
}
