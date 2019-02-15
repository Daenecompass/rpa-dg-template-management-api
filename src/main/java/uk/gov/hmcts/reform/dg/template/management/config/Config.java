package uk.gov.hmcts.reform.dg.template.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.dg.template.management.repository.MockTemplateBinaryRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.MockTemplateListRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.TemplateBinaryRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.TemplateListRepository;

@Configuration
public class Config {

    @Bean
    public TemplateListRepository getTemplateRepository() {
        return new MockTemplateListRepository();
    }

    @Bean
    public TemplateBinaryRepository getTemplateBinaryRepository() {
        return new MockTemplateBinaryRepository();
    }

}
