package uk.gov.hmcts.reform.dg.template.management.smoke;

import io.restassured.RestAssured;
import org.junit.Test;
import uk.gov.hmcts.reform.dg.template.management.testutil.Env;

public class SmokeTest {

    @Test
    public void testHealthEndpoint() {

        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.given()
            .request("GET", Env.getTestUrl() + "/health")
            .then()
            .statusCode(200);


    }
}
