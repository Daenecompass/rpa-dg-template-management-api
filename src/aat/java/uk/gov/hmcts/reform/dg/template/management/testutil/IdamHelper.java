package uk.gov.hmcts.reform.dg.template.management.testutil;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.springframework.http.MediaType;

public class IdamHelper {

    private static final String USERNAME = "test@test.com";
    private static final String PASSWORD = "password";

    private String idamToken = null;

    public String getIdamToken() {
        if (idamToken == null) {
            createUser();

            idamToken = getToken(getAuthCode());
        }

        return idamToken;
    }

    private void createUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", USERNAME);
        jsonObject.put("password", PASSWORD);
        jsonObject.put("forename", "test");
        jsonObject.put("surname", "test");

        RestAssured
            .given()
            .header("Content-Type", "application/json")
            .body(jsonObject.toString())
            .post(Env.getIdamURL() + "/testing-support/accounts");

    }

    private String getAuthCode() {
        return RestAssured
            .given()
            .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .formParam("username", USERNAME)
            .formParam("password", PASSWORD)
            .post(Env.getIdamURL() + "/oauth2/authorize")
            .body()
            .print();
    }

    private String getToken(String authCode) {
        return RestAssured
            .given()
            .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .formParam("authCode", authCode)
            .post(Env.getIdamURL() + "/oauth2/token")
            .body()
            .print();
    }

}
