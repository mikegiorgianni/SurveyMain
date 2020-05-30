package entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSerializedFiles {

    private void go() {
        List<String> files = new ArrayList<>();
        File[] fileList = new File(".").listFiles();
        if ( fileList != null ) {
            for ( File file : fileList ) {
                if ( file.isFile()
                    && !file.getName().equals("stuff")
                    && !file.getName().startsWith(".")
                    && !file.getName().endsWith(".pdf")
                    && !file.getName().endsWith(".iml")
                    && !file.getName().endsWith(".md") )
                    files.add(file.getName());
            }
            Collections.sort(files);
            files.forEach(System.out :: println);
            for ( String file : files ) {
                if ( file.equals("surveys") ) testSurveyList(file);
                else if ( file.equals("tests") ) testTestList(file);
                else if ( file.equals("surveyResponses") ) testSurveyResponseList(file);
                else if ( file.equals("testResponses") ) testTestResponseList(file);
                else if ( file.startsWith("survey") )
                    if ( file.contains("_") ) testSurveyResponse(file);
                    else testSurvey(file);
                else if ( file.contains("_") ) testTestResponse(file);
                else testTest(file);
            }
        }
    }

    private void testSurveyList(String file) {
        try {
            SurveyList surveyList = ( SurveyList ) load(file);
        } catch (ClassCastException e) {
            System.out.println(file + " has a serialization issue.");
        }
    }

    private void testTestList(String file) {
        try {
            TestList testList = ( TestList ) load(file);
        } catch (ClassCastException e) {
            System.out.println(file + " has a serialization issue.");
        }
    }

    private void testSurveyResponseList(String file) {
        try {
            SurveyResponseList surveyResponseList = ( SurveyResponseList ) load(file);
        } catch (ClassCastException e) {
            System.out.println(file + " has a serialization issue.");
        }
    }

    private void testTestResponseList(String file) {
        try {
            TestResponseList testResponseList = ( TestResponseList ) load(file);
        } catch (ClassCastException e) {
            System.out.println(file + " has a serialization issue.");
        }
    }

    private void testSurveyResponse( String file ) {
        try {
            SurveyResponse surveyResponse = ( SurveyResponse ) load(file);
        } catch (ClassCastException e) {
            System.out.println(file + " has a serialization issue.");
        }
    }

    private void testSurvey( String file ) {
        try {
            Survey survey = ( Survey ) load(file);
        } catch (ClassCastException e) {
            System.out.println(file + " has a serialization issue.");
        }
    }

    private void testTestResponse( String file ) {
        try {
            TestResponse testResponse = ( TestResponse ) load(file);
        } catch (ClassCastException e) {
            System.out.println(file + " has a serialization issue.");
        }
    }

    private void testTest( String file ) {
        try {
            Test test = ( Test ) load(file);
        } catch (ClassCastException e) {
            System.out.println(file + " has a serialization issue.");
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

    public static void main( String[] args ) throws IOException {
        new TestSerializedFiles().go();
    }
}
