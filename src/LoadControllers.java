import questionControllers.*;

import static questionTypes.QuestionType.*;

public class LoadControllers {
    public static QuestionOps[] load() {
        QuestionOps[] controllers = new QuestionOps[6];
        controllers[TRUE_FALSE.ordinal()] = new TrueFalseController();
        controllers[SHORT_ANSWER.ordinal()] = new ShortAnswerController();
        controllers[ESSAY.ordinal()] = new EssayController();
        controllers[VALID_DATE.ordinal()] = new ValidDateController();
        controllers[MULTIPLE_CHOICE.ordinal()] = new MultipleChoiceController();
        controllers[MATCHING.ordinal()] = new MatchingController();
        return controllers;
    }
}
