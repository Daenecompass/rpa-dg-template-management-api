package uk.gov.hmcts.reform.dg.template.management.functional;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.dg.template.management.Application;
import uk.gov.hmcts.reform.dg.template.management.testutil.Env;
import uk.gov.hmcts.reform.dg.template.management.testutil.IdamHelper;
import uk.gov.hmcts.reform.dg.template.management.testutil.S2sHelper;

import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, properties = "SpringBootTest")
@TestPropertySource(locations = "classpath:application-aat.yaml")
@ActiveProfiles("aat")
public class TemplateScenarios {

    @Autowired
    private IdamHelper idamHelper;

    @Autowired
    private S2sHelper s2sHelper;

    private String idamAuth;
    private String s2sAuth;

    @Before
    public void setup() {
        idamAuth = idamHelper.getIdamToken();
        s2sAuth = s2sHelper.getS2sToken();
    }

    @Test
    public void testGetTemplates() {
        Response response = RestAssured.given()
            .header("Authorization", idamAuth)
            .header("ServiceAuthorization", s2sAuth)
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

        Response response = RestAssured.given()
            .header("Authorization", idamAuth)
            .header("ServiceAuthorization", s2sAuth)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .request("GET", Env.getTestUrl() + "/api/templates/" + id);

        String body = response.getBody().asString();

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(body.contains("customXml/itemProps1"));
    }

    @Test
    public void testGetTemplate404() {
        String id = Base64.getEncoder().encodeToString("does not exist.docx".getBytes());

        Response response = RestAssured.given()
            .header("Authorization", idamAuth)
            .header("ServiceAuthorization", s2sAuth)
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .request("GET", Env.getTestUrl() + "/api/templates/" + id);

        Assert.assertEquals(404, response.getStatusCode());
    }
}
