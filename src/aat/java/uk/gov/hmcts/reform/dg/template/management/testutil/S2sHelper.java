package uk.gov.hmcts.reform.dg.template.management.testutil;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class S2sHelper {

    public String getS2sToken() {
        String otp = String.valueOf(new GoogleAuthenticator().getTotpPassword(Env.getS2SToken()));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("microservice", Env.getS2SServiceName());
        jsonObject.put("oneTimePassword", otp);

        return "Bearer " + RestAssured
            .given()
            .header("Content-Type", "application/json")
            .body(jsonObject.toString())
            .post(Env.getS2SUrl() + "/lease")
            .getBody()
            .asString();
    }
}
