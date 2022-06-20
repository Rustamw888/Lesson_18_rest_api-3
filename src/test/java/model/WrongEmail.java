package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrongEmail {

    public String getWrongEmail() {
        return wrongEmail;
    }

    public void setWrongEmail(String wrongEmail) {
        this.wrongEmail = wrongEmail;
    }

    private String wrongEmail;

}
