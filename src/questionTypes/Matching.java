package questionTypes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Matching extends Question implements Serializable {
    private int numInList;
    private List<String> listA;
    private List<String> listB;
    private Map<Integer, String> matches;

    public Matching( String question, int numInList,
                     List<String> listA, List<String> listB,
                     Map<Integer, String> matches ) {
        super(question);
        this.numInList = numInList;
        this.listA = listA;
        this.listB = listB;
        this.matches = matches;
    }

    public Matching( String question, int numInList,
                     List<String> listA, List<String> listB ) {
        super(question);
        this.numInList = numInList;
        this.listA = listA;
        this.listB = listB;
    }

    public int getNumInList() {
        return numInList;
    }

    public void setNumInList( int numInList ) {
        this.numInList = numInList;
    }

    public List<String> getListA() {
        return listA;
    }

    public void setListA( List<String> listA ) {
        this.listA = listA;
    }

    public List<String> getListB() {
        return listB;
    }

    public void setListB( List<String> listB ) {
        this.listB = listB;
    }

    public Map<Integer, String> getMatches() {
        return matches;
    }

    public void setMatches( Map<Integer, String> matches ) {
        this.matches = matches;
    }

    public String formatMatches() {
        StringBuilder string = new StringBuilder();
        Integer[] keys = matches.keySet().toArray(new Integer[0]);
        String[] vals = matches.values().toArray(new String[0]);
        for ( int i = 0; i < numInList; i++ ) {
            char no = ( char ) ('A' + i);
            String stringC = i < matches.size() ? keys[i] + ":" + vals[i] : " ";
            string.append(String.format("\t%-40s\n", stringC));
        }
        return string.toString();
    }
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
            String.format("%-10s %s\t%-5d\n",
            "Matching", getQuestion(), numInList));
        for ( int i = 0; i < numInList; i++ ) {
            char no = ( char ) ('A' + i);
            String stringA = (i+1) + ". " + listA.get(i);
            String stringB = no    + ". " + listB.get(i);
            string.append(String.format("\t%-40s %-40s\n", stringA, stringB));
        }
        return string.toString();
    }
}
