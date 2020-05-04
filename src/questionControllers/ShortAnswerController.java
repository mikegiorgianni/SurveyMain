package questionControllers;

import questionTypes.QuestionOps;
import questionTypes.ShortAnswer;

public class ShortAnswerController implements QuestionOps<ShortAnswer> {
    @Override
    public ShortAnswer inputQuestion() {
        return null;
    }

    @Override
    public void changeQuestion( ShortAnswer question ) {

    }

    @Override
    public String askQuestion() {
        return null;
    }
}
