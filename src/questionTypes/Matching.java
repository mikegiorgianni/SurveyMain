package questionTypes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Matching extends Question implements Serializable {
    private int numInListA;
    private int numInListB;
    private List<String> listA;
    private List<String> listB;
    private Map<String, String> matches;

    public Matching( String question, int numInListA, int numInListB,
                     List<String> listA, List<String> listB,
                     Map<String, String> matches ) {
        super(question);
        this.numInListA = numInListA;
        this.numInListB = numInListB;
        this.listA = listA;
        this.listB = listB;
        this.matches = matches;
    }

    public int getNumInListA() {
        return numInListA;
    }

    public int getNumInListB() {
        return numInListB;
    }

    public List<String> getListA() {
        return listA;
    }

    public List<String> getListB() {
        return listB;
    }

    public Map<String, String> getMatches() {
        return matches;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
            String.format("%-10s %-5d %-5d\t%s\n",
            "questionTypes.Matching", numInListA, numInListB, getQuestion()));
        String[] keys = matches.keySet().toArray(new String[0]);
        String[] vals = matches.values().toArray(new String[0]);
        for ( int i = 0; i < Math.max(numInListA, numInListB); i++ ) {
            char no = ( char ) ('A' + i);
            String stringA = i < numInListA ? (i+1) + ". " + listA.get(i) : " ";
            String stringB = i < numInListB ? no    + ". " + listB.get(i) : " ";
            String stringC = i < matches.size() ? keys[i] + ":" + vals[i] : " ";
            string.append(String.format("\t%-40s %-40s %-40s\n", stringA, stringB, stringC));
        }
        return string.toString();
    }
}
