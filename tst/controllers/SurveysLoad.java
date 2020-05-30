package controllers;

import entities.Survey;
import entities.SurveyList;
import questionTypes.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SurveysLoad {
    private static final String SURVEYS_FN = "surveys";

    private void go() {
        SurveyList surveys = new SurveyList();
        List<Question> questions = new ArrayList<>();

        questions.add(new TrueFalse("San Antonio is the capital of Texas.", false));

        questions.add(new MultipleChoice("Where did Texas win it's independence?", 3,
            new ArrayList<>(List.of("Alamo", "Goliad", "San Jacinto")), 3));

        questions.add(new ShortAnswer("Who was the Mexican President/general that invaded Texas in 1836?",
            "Santa Anna"));

        questions.add(new Essay("What was the underlying cause of both the Texas Revolution and the U.S. Civil War?"));

        questions.add(new ValidDate("When did Texas declare its independence from Mexico?",
            LocalDate.of(1836, 3, 2)));

        questions.add(new Matching("Match the commander to the Texas battle:", 3,
            List.of("Sam Houston", "William B. Travis", "James Fannin"),
            List.of("Alamo", "Goliad", "San Jacinto"),
            Map.of(1, "C", 2, "A", 3, "B")));

        Survey survey = new Survey("survey1", questions);
        save(survey.getName(), survey);

        surveys.addSurvey(survey.getName());
        save(SURVEYS_FN, surveys);
    }

    private void save(String fileName, Object object) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main( String[] args ) {
        new SurveysLoad().go();
    }
}
