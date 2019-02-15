package uk.gov.hmcts.reform.dg.template.management.functional;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import uk.gov.hmcts.reform.dg.template.management.testutil.Env;
import uk.gov.hmcts.reform.dg.template.management.testutil.TestUtil;

public class TemplateScenarios {

    private final TestUtil util = new TestUtil();

    @Test
    public void testGetTemplates() {
        Response response = util.authRequest()
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .request("GET", Env.getTestUrl() + "/api/templates");

        JsonPath body = response.getBody().jsonPath();

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("template1.docx", body.getString("[0].name"));
        Assert.assertEquals("template2.docx", body.getString("[1].name"));
        Assert.assertEquals("template3.docx", body.getString("[2].name"));
        Assert.assertEquals("template4.docx", body.getString("[3].name"));
    }

    @Test
    public void testGetTemplate() {
        Response response = util.authRequest()
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .request("GET", Env.getTestUrl() + "/api/templates/anything");

        String body = response.getBody().asString();

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(body.contains("appender"));
    }
}
