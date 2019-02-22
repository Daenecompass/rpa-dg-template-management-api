package uk.gov.hmcts.reform.dg.template.management.functional;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import uk.gov.hmcts.reform.dg.template.management.testutil.Env;
import uk.gov.hmcts.reform.dg.template.management.testutil.TestUtil;

import java.util.Base64;

public class TemplateScenarios {

    private final TestUtil util = new TestUtil();

    @Test
    public void testGetTemplates() {
        Response response = util.authRequest()
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .request("GET", Env.getTestUrl() + "/api/templates");

        JsonPath body = response.getBody().jsonPath();
        Base64.Encoder enc = Base64.getEncoder();

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("FL-FRM-APP-ENG-00002.docx", body.getString("[1].name"));
        Assert.assertEquals(enc.encodeToString("FL-FRM-APP-ENG-00002.docx".getBytes()), body.getString("[1].id"));
        Assert.assertEquals("FL-FRM-GOR-ENG-00007.docx", body.getString("[2].name"));
        Assert.assertEquals(enc.encodeToString("FL-FRM-GOR-ENG-00007.docx".getBytes()), body.getString("[2].id"));
        Assert.assertEquals("PostponementRequestGenericTest.docx", body.getString("[3].name"));
        Assert.assertEquals(enc.encodeToString("PostponementRequestGenericTest.docx".getBytes()), body.getString("[3].id"));
    }

    @Test
    public void testGetTemplate() {
        String id = Base64.getEncoder().encodeToString("TB-IAC-APP-ENG-00003 Template Tornado.docx".getBytes());

        Response response = util.authRequest()
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .request("GET", Env.getTestUrl() + "/api/templates/" + id);

        String body = response.getBody().asString();

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(body.contains("customXml/itemProps1"));
    }
}
