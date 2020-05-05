package questionControllers;

import questionTypes.Matching;
import questionTypes.QuestionOps;

import java.util.*;

public class MatchingController implements QuestionOps<Matching> {
    private final Scanner kb = new Scanner(System.in);
    List<String> listA, listB;
    Map<Integer, String> matches;

    @Override
    public Matching inputQuestion() {
        listA = new ArrayList<>();
        listB = new ArrayList<>();
        matches = new TreeMap<>();
        String question = promptAccept("Enter question: ");
        int numInList = promptNumber("Enter num of matches: ", 0);
        System.out.println("Enter left and right list items in random sequence.");
        for ( int i = 0; i < numInList; i++ ) {
            listA.add(promptAccept("Enter item in left list: "+(i+1)));
            listB.add(promptAccept("Enter item in right list: "+(char)(i+'0')));
        }
        for ( int i = 0; i < numInList; i++ ) {
            matches.put((i+1), promptAccept("Matching item in right list(A-Z) for left item "+(i+1))+": ");
        }
        return new Matching(question, numInList, listA, listB, matches);

    }
    @Override
    public void changeQuestion( Matching question ) {
        System.out.println("Press to retain current value.");
        String resp;
        resp = promptAccept(question.getQuestion() + ": ");
        if ( !resp.isEmpty() ) question.setQuestion(resp);
        question.setNumInList(promptNumber("Number of choices: " + question.getQuestion(),
            question.getNumInList()));
        for ( int i = 0; i < question.getNumInList(); i++ ) {
            resp = promptAccept("Left: " + (i + 1) + question.getListA().get(i));
            if (!resp.isEmpty()) question.getListA().set(i, resp);
            resp = promptAccept("Right: " + (i + 1) + question.getListB().get(i));
            if (!resp.isEmpty()) question.getListB().set(i, resp);
        }
        for ( int i = 0; i < question.getNumInList(); i++ ) {
            resp = promptAccept("Right (A-Z) for left item " + (i + 1) + ": ");
            if (!resp.isEmpty()) question.getMatches().put((i+1), resp);
        }
    }

    @Override
    public String askQuestion( Matching question ) {
        System.out.println(question.getQuestion());
        for ( int i = 0; i < question.getNumInList(); i++ ) {
            System.out.println(question.getListA().get(i) + " : " +
                question.getListB().get(i));
        }
        for ( int i = 0; i < question.getNumInList(); i++ ) {
            matches.put((i+1), promptAccept("Matching item in right list(A-Z) for left item "+(i+1))+": ");
        }
        return null;
    }
    @Override
    public String promptAccept( String prompt ) {
        System.out.print(prompt);
        return kb.nextLine();
    }

    private int promptNumber( String prompt, int no) {
        String resp;
        while (true) {
            resp = promptAccept(prompt);
            if (resp.isEmpty()) return no;
            try {
                return Integer.parseInt(resp);
            } catch (NumberFormatException e) {
                System.out.println("Not a number.");
            }
        }
    }
}
