package questionControllers;


import responses.QuestionResp;

public class QuestionOps<T> {
    public T inputQuestion(SurveyOrTest st) {
        return null;
    }
    public void changeQuestion(SurveyOrTest st, T question) {}
    public QuestionResp askQuestion( T question) {
        return null;
    }
    public String displayQuestion(boolean withAnswers, T question) { return null; }
    public String promptAccept(String prompt) {
        return null;
    }
}
