package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SurveyList implements Serializable {
    private final List<String> surveys;

    public SurveyList() {
        surveys = new ArrayList<>();
    }

    public void addSurvey(String survey) {
        surveys.add(survey);
    }

    public List<String> getSurveys() {
        return surveys;
    }

    public boolean contains(String surveyName) {
        return surveys.contains(surveyName);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for ( String survey : surveys ) {
           s.append(survey).append(", ");
        }
        return s.substring(0, s.length()-2);
    }
}
