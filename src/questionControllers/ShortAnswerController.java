package questionControllers;

import questionTypes.QuestionOps;
import questionTypes.ShortAnswer;

public class ShortAnswerController implements QuestionOps<ShortAnswer> {
    @Override
    public ShortAnswer inputQuestion() {
        return new ShortAnswer("Am I there?", "Don't know.");
    }

    @Override
    public void changeQuestion( ShortAnswer question ) {

    }

    @Override
    public String askQuestion() {
        return null;
    }
}
