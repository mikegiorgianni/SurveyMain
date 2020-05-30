package questionTypes;

import questionControllers.*;

public enum QuestionType {

    TRUE_FALSE(new TrueFalseController()), SHORT_ANSWER(new ShortAnswerController()),
    ESSAY(new EssayController()), VALID_DATE(new ValidDateController()),
    MULTIPLE_CHOICE(new MultipleChoiceController()), MATCHING(new MatchingController());

    private final QuestionOps controller;

    QuestionType( QuestionOps controller ) {
        this.controller = controller;
    }

    public QuestionOps getController() {
        return controller;
    }
}
