package uk.gov.hmcts.reform.dg.template.management.testutil;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.idam.client.IdamClient;

@Service
public class IdamHelper {

    private static final String USERNAME = "test@test.com";
    private static final String PASSWORD = "password";

    @Autowired
    private IdamClient client;

    @Value("${idam.api.url}")
    private String idamUrl;

    @Value("${idam.api.secret}")
    private String secret;

    @Value("${idam.api.redirect_uri}")
    private String redirect;

    public String getIdamToken() {
        System.out.println(idamUrl);
        System.out.println(secret);
        System.out.println(redirect);

        createUser();

        return client.authenticateUser(USERNAME, PASSWORD);
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
            .post(idamUrl + "/testing-support/accounts");
    }

}
