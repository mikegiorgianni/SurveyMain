package entities;

import java.io.Serializable;
import java.util.List;

public class TestResponseList implements Serializable {
    private String test;
    private List<String> testResponses;

    public TestResponseList( String test, List<String> testResponses ) {
        this.test = test;
        this.testResponses = testResponses;
    }

    public String getTest() {
        return test;
    }

    public List<String> getTestResponses() {
        return testResponses;
    }

    public void addTestRespounses(String responseName) {
        testResponses.add(responseName);
    }

    @Override
    public String toString() {
        return test + ":" + String.join("|", testResponses);
    }

}
