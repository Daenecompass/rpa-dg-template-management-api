package uk.gov.hmcts.reform.dg.template.management.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.gov.hmcts.reform.auth.checker.core.SubjectResolver;
import uk.gov.hmcts.reform.auth.checker.core.user.User;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.dg.template.management.Application;
import uk.gov.hmcts.reform.dg.template.management.repository.LocalTemplateBinaryRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.LocalTemplateListRepository;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TemplateResourceTest {

    @MockBean
    private AuthTokenGenerator authTokenGenerator;

    @MockBean
    private SubjectResolver<User> userResolver;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(
            new TemplateResource(
                new LocalTemplateListRepository(),
                new LocalTemplateBinaryRepository()
            )
        ).build();
    }

    @Test
    public void templates() throws Exception {
        BDDMockito.given(authTokenGenerator.generate()).willReturn("s2s");
        BDDMockito.given(userResolver.getTokenDetails("jwt")).willReturn(new User("id", null));

        mvc.perform(get("/api/templates"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$[1].name").value("FL-FRM-APP-ENG-00002.docx"))
            .andExpect(jsonPath("$[2].name").value("FL-FRM-GOR-ENG-00007.docx"))
            .andExpect(jsonPath("$[3].name").value("PostponementRequestGenericTest.docx"));
    }

    @Test
    public void template() throws Exception {
        BDDMockito.given(authTokenGenerator.generate()).willReturn("s2s");
        BDDMockito.given(userResolver.getTokenDetails("jwt")).willReturn(new User("id", null));
        String id = Base64.getEncoder().encodeToString("TB-IAC-APP-ENG-00003 Template Tornado.docx".getBytes());

        MvcResult result = mvc.perform(get("/api/templates/" + id))
            .andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("customXml/itemProps1"));
    }
}
