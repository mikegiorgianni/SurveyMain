package responses;

import entities.TestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

class TestResponseTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void testResponseTest() {
        TestResponse testResponse = ( TestResponse ) load("testbf_Bill");
        System.out.println(testResponse.getTakerName());
        System.out.println(testResponse.getResponses().size());
        for ( QuestionResp questionResp : testResponse.getResponses() ) {
            System.out.println(questionResp.getQuestion());
        }
        System.out.println(testResponse.getCorrectAnswers().size());
//        testResponse.getCorrectAnswers().forEach(System.out::println);
    }

    private Object load( String fileName) {
        Object object = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            object = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return object;
    }

}