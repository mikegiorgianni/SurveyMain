package questionTypes;

import questionControllers.*;

public enum QuestionType {
    TRUE_FALSE(TrueFalseController.class), SHORT_ANSWER(ShortAnswerController.class), ESSAY(EssayController.class),
    VALID_DATE(ValidDateController.class), MULTIPLE_CHOICE(MultipleChoiceController.class), MATCHING(MatchingController.class);

    private final Class<QuestionType> controller;

    QuestionType( Class controller ) {
        this.controller = controller;
    }

    public Class getController() {
        return controller;
    }
}
