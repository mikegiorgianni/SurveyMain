package questionTypes;

import java.io.Serializable;

public class Essay extends Question implements Serializable {

    public Essay( String question ) { super(question); }

    @Override
    public String toString() {
        return "Essay: " + super.getQuestion()  + "\n";
    }
}
