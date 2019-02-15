package uk.gov.hmcts.reform.dg.template.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.dg.template.management.repository.MockTemplateRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.TemplateRepository;

@Configuration
public class Config {

    @Bean
    public TemplateRepository getTemplateRepository() {
        return new MockTemplateRepository();
    }

}
