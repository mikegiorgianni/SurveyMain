package responses;

import questionTypes.Question;

import java.util.Map;

public class MatchingResp extends QuestionResp {
    Map<Integer, String> response;

    public MatchingResp( Question question, Map<Integer, String> response ) {
        super(question);
        this.response = response;
    }

    public Map<Integer, String> getResponse() {
        return response;
    }

    public void setResponse( Map<Integer, String> response ) {
        this.response = response;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Integer[] keys = response.keySet().toArray(new Integer[0]);
        String[] vals = response.values().toArray(new String[0]);
        for ( int i = 0; i < response.size(); i++ ) {
            char no = ( char ) ('A' + i);
            sb.append(keys[i]).append(":").append(vals[i]);
//            String stringA = (i + 1) + ". " + listA.get(i);
//            String stringB = no + ". " + listB.get(i);
//            String stringc = keys[i] + ":" + vals[i];
//            sb.append(String.format("\t%-40s %-40s %-40s\n", stringA, stringB, stringC));
        }
        return sb.toString();
    }
}
