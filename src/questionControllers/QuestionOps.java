package questionControllers;

public interface QuestionOps<T> {
    T inputQuestion();
    void changeQuestion(T question);
    String askQuestion(T question);
    String promptAccept(String prompt);
}
