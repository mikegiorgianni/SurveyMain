package questionTypes;

public interface QuestionOps<T> {
    T inputQuestion();
    void changeQuestion(T question);
    String askQuestion();
}
