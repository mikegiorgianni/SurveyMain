package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SurveyResponseList implements Serializable {
    private String survey;
    private List<String> surveyResponses;

    public SurveyResponseList( String survey ) {
        this.survey = survey;
        this.surveyResponses = new ArrayList<>();
    }

    public String getSurvey() {
        return survey;
    }

    public List<String> getSurveyResponses() {
        return surveyResponses;
    }

    public void addSurveyResponse(String responseName) { surveyResponses.add(responseName); }

    @Override
    public String toString() {
        return survey + ":" + String.join("|", surveyResponses);
    }

}
