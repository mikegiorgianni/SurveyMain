package questionTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidDate extends Question implements Serializable {
    LocalDate answer;

    public ValidDate( String question, LocalDate answer ) {
        super(question);
        this.answer = answer;
    }

    public LocalDate getAnswer() {
        return answer;
    }

    public void setAnswer( LocalDate answer ) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "questionTypes.ValidDate: " + super.getQuestion() + " : " +
            answer.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + "\n";
    }
}
