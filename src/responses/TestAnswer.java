package responses;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

public class TestAnswer implements Serializable {
    private final String answer;

    public TestAnswer( String answer ) {
        this.answer = answer;
    }

    public TestAnswer( Map map) {
        this.answer = ( String ) map.keySet().stream()
            .map(key -> key + "=" + map.get(key))
            .collect(Collectors.joining(", ", "{", "}"));
    }

    public String getAnswer() {
        return answer;
    }
}
