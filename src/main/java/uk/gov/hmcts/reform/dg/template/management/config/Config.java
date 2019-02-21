package uk.gov.hmcts.reform.dg.template.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.dg.template.management.repository.LocalTemplateBinaryRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.LocalTemplateListRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.TemplateBinaryRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.TemplateListRepository;

@Configuration
public class Config {

    @Bean
    public TemplateListRepository getTemplateRepository() {
        return new LocalTemplateListRepository();
    }

    @Bean
    public TemplateBinaryRepository getTemplateBinaryRepository() {
        return new LocalTemplateBinaryRepository();
    }

}
