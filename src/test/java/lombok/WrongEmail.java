package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrongEmail {

    private String wrongEmail;

    public WrongEmail(String wrongEmail) {
        this.wrongEmail = wrongEmail;
    }
}
