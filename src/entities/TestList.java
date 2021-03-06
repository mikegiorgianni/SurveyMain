package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestList implements Serializable {
    private final List<String> tests;

    public TestList() {
        this.tests = new ArrayList<>();
    }

    public void addTest(String test) {
        tests.add(test);
    }

    public List<String> getTests() {
        return tests;
    }

    public boolean contains(String testName) {
        return tests.contains(testName);
    }

    public int size() { return tests.size(); }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for ( String test : tests ) {
            s.append(test).append(", ");
        }
        return s.substring(0, s.length()-2);
    }
}
