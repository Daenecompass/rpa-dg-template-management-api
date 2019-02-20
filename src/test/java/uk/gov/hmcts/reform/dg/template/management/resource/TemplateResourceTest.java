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
import uk.gov.hmcts.reform.dg.template.management.repository.MockTemplateBinaryRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.MockTemplateListRepository;

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
                new MockTemplateListRepository(),
                new MockTemplateBinaryRepository()
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
            .andExpect(jsonPath("$[0].name").value("template1.docx"))
            .andExpect(jsonPath("$[1].name").value("template2.docx"))
            .andExpect(jsonPath("$[2].name").value("template3.docx"));
    }

    @Test
    public void template() throws Exception {
        BDDMockito.given(authTokenGenerator.generate()).willReturn("s2s");
        BDDMockito.given(userResolver.getTokenDetails("jwt")).willReturn(new User("id", null));

        MvcResult result = mvc.perform(get("/api/templates/templateName"))
            .andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("customXml/itemProps1"));
    }
}
