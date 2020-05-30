package controllers;

import entities.SurveyResponse;
import entities.SurveyResponseList;
import entities.TestResponse;
import entities.TestResponseList;
import questionTypes.*;
import responses.MatchingResp;
import responses.QuestionResp;
import responses.SimpleResp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Tabulator {
    static Scanner kb = new Scanner(System.in);

    public static final String TEST_RESPONSES_FN = "testResponses";
    public static final String SURVEY_RESPONSES_FN = "surveyResponses";

    private Question question;
    private QuestionType type;
    private List<Question> questions;
    private List<TestResponse> testResponses;
    private List<SurveyResponse> surveyResponses;
    private List<List<QuestionResp>> questionResponses;

    public void tabulateTest() {
        List<TestResponseList> testResponseList = ( List<TestResponseList> ) load(TEST_RESPONSES_FN);
        if ( testResponseList.isEmpty() ) {
            System.out.println("No tests to tabulate.");
            promptAccept("Press Enter to continue.");
            return;
        }
        int testNo;
        if ( testResponseList.size() == 1 ) {
            testNo = 1;
            System.out.println(testResponseList.get(0).getTest() + " chosen by default.");
        } else {
            System.out.println("Select an existing test to tabulate:");
            for ( int i = 0; i < testResponseList.size(); i++ ) {
                System.out.printf("%d) %s\n", i + 1, testResponseList.get(i).getTest());
            }
            testNo = promptNumber("> ", 0);
            if ( testNo < 1 || testNo > testResponseList.size() ) {
                System.out.println("Invalid response.");
                promptAccept("Press Enter to continue.");
                return;
            }
        }
        List<String> responseNames = testResponseList.get(testNo - 1).getTestResponses();
        testResponses = new ArrayList<>();
        questionResponses = new ArrayList<>();
        for ( String testResponseName : responseNames ) {  // load all test response files for test
            testResponses.add(( TestResponse ) load(testResponseName));
        }
        for ( TestResponse response : testResponses ) {    // load all question responses for test
            questionResponses.add(response.getResponses());
        }
        questions = testResponses.get(0).getQuestions();
        tabulateQuestions();
    }

    public void tabulateSurvey() {
        List<SurveyResponseList> surveyResponseList = ( List<SurveyResponseList> ) load(SURVEY_RESPONSES_FN);
        if ( surveyResponseList.isEmpty() ) {
            System.out.println("No surveys to tabulate.");
            promptAccept("Press Enter to continue.");
            return;
        }
        int surveyNo;
        if ( surveyResponseList.size() == 1 ) {
            surveyNo = 1;
            System.out.println(surveyResponseList.get(0).getSurvey() + " chosen by default.");
        } else {
            System.out.println("Select an existing survey to tabulate:");
            for ( int i = 0; i < surveyResponseList.size(); i++ ) {
                System.out.printf("%d) %s\n", i + 1, surveyResponseList.get(i).getSurvey());
            }
            surveyNo = promptNumber("> ", 0);
            if ( surveyNo < 1 || surveyNo > surveyResponseList.size() ) {
                System.out.println("Invalid response.");
                promptAccept("Press Enter to continue.");
                return;
            }
        }
        List<String> responseNames = surveyResponseList.get(surveyNo - 1).getSurveyResponses();
        surveyResponses = new ArrayList<>();
        questionResponses = new ArrayList<>();
        for ( String surveyResponseName : responseNames ) {  // load all survey response files for survey
            surveyResponses.add(( SurveyResponse ) load(surveyResponseName));
        }
        for ( SurveyResponse response : surveyResponses ) {    // load all question responses for survey
            questionResponses.add(response.getResponses());
        }
        questions = surveyResponses.get(0).getQuestions();
        tabulateQuestions();
    }


    private void tabulateQuestions() {
        for ( int i = 0; i < questions.size(); i++ ) {
            question = questions.get(i);
            type = Answer.getQuestionType(question);
            switch (type) {
                case ESSAY: { tabulateEssay(i); break; }                        // done
                case MATCHING: { tabulateMatching(i); break; }                  // done
                case MULTIPLE_CHOICE: { tablulateMultipleChoice(i); break; }    // done
                case SHORT_ANSWER: { tabulateShortAnswer(i); break; }           // done
                case TRUE_FALSE: { tabulateTrueFalse(i); break; }               // done
                case VALID_DATE: { tabulateValidDate(i); break; }               // done
            }
        }
        System.out.println();
    }

    private void tabulateEssay( int questionNo ) {
        List<String> resps = new ArrayList<>();
        System.out.println("Question:");
        System.out.println(questions.get(questionNo).getQuestion());
        System.out.println();
        System.out.println("Responses:");
        for ( List<QuestionResp> questionResponse : questionResponses ) {
            String resp = (( SimpleResp ) questionResponse.get(questionNo)).getResponse();
            System.out.println(resp + "\n");
            resps.add(resp);
        }
        System.out.println("\nTabulation");
        resps.forEach(x -> System.out.println(x + "\n"));
        System.out.println();
        System.out.println();
    }

    private void tabulateMatching( int questionNo ) {
        Map<String, Integer> tabs = new TreeMap<>();
        System.out.println("Question:");
        String q = questions.get(questionNo).getQuestion();
        int pos = q.lastIndexOf(":");
        System.out.println(q.substring(0, pos).trim());              // question
        System.out.println();
        int numInList = (( Matching ) questions.get(questionNo)).getNumInList();
        List<String> listA = (( Matching ) questions.get(questionNo)).getListA();
        List<String> listB = (( Matching ) questions.get(questionNo)).getListA();
        for ( int i = 0; i < numInList; i++ ) {
            System.out.printf("%d %-40s %c %-40s\n",
                (i + 1), listA.get(i), ( char ) (i + 'A'), listB.get(i));   // list of matches
        }
        System.out.println();
        System.out.println("Responses:");
        for ( List<QuestionResp> questionResponse : questionResponses ) {
            Map<Integer, String> resp = (( MatchingResp ) questionResponse.get(questionNo)).getResponse();
            resp.forEach(( k, v ) -> System.out.printf("%s %s\n", k, v));
            System.out.println();
            Integer count = tabs.putIfAbsent(resp.toString(), 0);
            count = count == null ? 1 : count + 1;
            tabs.replace(resp.toString(), count);
        }
        System.out.println("Tabulation");
        tabs.forEach(( k, v ) -> System.out.printf("%d\n%s\n", v, formatMap(k)));
        System.out.println();
    }

    String formatMap( String answer ) {
        StringBuilder sb = new StringBuilder();
        if ( answer.startsWith("{") ) answer = answer.substring(1, answer.length() - 1);
        String[] s = answer.split(",");
        for ( String value : s ) {
            String[] t = value.trim().split("=");
            sb.append(String.format("%s %s\n", t[0].trim(), t[1].trim()));
        }
        return sb.toString();
    }

    private void tablulateMultipleChoice( int questionNo ) {
        Map<String, Integer> tabs = new TreeMap<>();
        System.out.println("Question:");
        System.out.println(questions.get(questionNo).getQuestion());
        List<String> choices = (( MultipleChoice ) questions.get(questionNo)).getChoices();
        for ( int i = 0; i < choices.size(); i++ ) {
            String choice = choices.get(i);
            System.out.printf("%d) %s     ", (i + 1), choice);
        }
        System.out.println();
        System.out.println();
        System.out.println("Responses:");
        for ( List<QuestionResp> questionResponse : questionResponses ) {
            String resp = (( SimpleResp ) questionResponse.get(questionNo)).getResponse();
            System.out.println(resp);
            Integer count = tabs.putIfAbsent(resp, 0);
            count = count == null ? 1 : count + 1;
            tabs.replace(resp, count);
        }
        System.out.println("\nTabulation");
        tabs.forEach(( k, v ) -> System.out.printf("%s\t%d\n", k, v));
        System.out.println();
        System.out.println();
    }

    private void tabulateShortAnswer( int questionNo ) {
        Map<String, Integer> tabs = new TreeMap<>();
        System.out.println("Question:");
        System.out.println(questions.get(questionNo).getQuestion());
        System.out.println();
        System.out.println("Responses:");
        for ( List<QuestionResp> questionResponse : questionResponses ) {
            String resp = (( SimpleResp ) questionResponse.get(questionNo)).getResponse();
            System.out.println(resp);
            Integer count = tabs.putIfAbsent(resp, 0);
            count = count == null ? 1 : count + 1;
            tabs.replace(resp, count);
        }
        System.out.println("\nTabulation");
        tabs.forEach(( k, v ) -> System.out.printf("%s\t%d\n", k, v));
        System.out.println();
        System.out.println();
    }

    private void tabulateTrueFalse( int questionNo ) {
        Map<String, Integer> tabs = new TreeMap<>();
        System.out.println("Question:");
        System.out.println(questions.get(questionNo).getQuestion() + "\tTrue / False");
        System.out.println();
        System.out.println("Responses:");
        for ( List<QuestionResp> questionResponse : questionResponses ) {
            String resp = (( SimpleResp ) questionResponse.get(questionNo)).getResponse();
            resp = (resp.equalsIgnoreCase("T") ? "True" : "False").toUpperCase();
            System.out.println(resp);
            Integer count = tabs.putIfAbsent(resp, 0);
            count = count == null ? 1 : count + 1;
            tabs.replace(resp, count);
        }
        System.out.println("\nTabulation");
        tabs.forEach(( k, v ) -> System.out.printf("%s\t%d\n", k, v));
        System.out.println();
        System.out.println();
    }

    private void tabulateValidDate( int questionNo ) {
        Map<String, Integer> tabs = new TreeMap<>();
        System.out.println("Question:");
        System.out.println(questions.get(questionNo).getQuestion());
        System.out.println();
        System.out.println("Responses:");
        for ( List<QuestionResp> questionResponse : questionResponses ) {
            String resp = (( SimpleResp ) questionResponse.get(questionNo)).getResponse();
            DateTimeFormatter stringToDateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateToStringFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate date = LocalDate.parse(resp, stringToDateformatter);
            resp = date.format(dateToStringFormatter);
            System.out.println(resp);
            Integer count = tabs.putIfAbsent(resp, 0);
            count = count == null ? 1 : count + 1;
            tabs.replace(resp, count);
        }
        System.out.println("\nTabulation");
        tabs.forEach(( k, v ) -> System.out.printf("%s\t%d\n", k, v));
        System.out.println();
        System.out.println();
    }

    private String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }

    private int promptNumber( String prompt, int no ) {
        String resp;
        while (true) {
            resp = promptAccept(prompt);
            if ( resp.isEmpty() ) return no;
            try {
                return Integer.parseInt(resp);
            } catch (NumberFormatException e) {
                System.out.println("Not a number.");
            }
        }
    }

    private Object load( String fileName ) {
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
        new Tabulator().go();
    }

    private void go() {
        String tOrS = promptAccept("S(urvey) or T(est) ");
        if ( tOrS.toUpperCase().startsWith("S") )
            tabulateSurvey();
        else
            tabulateTest();
    }
}
