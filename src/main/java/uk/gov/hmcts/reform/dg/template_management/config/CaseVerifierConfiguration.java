package uk.gov.hmcts.reform.dg.template_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.dg.template_management.service.caseupdater.JsonNodesVerifier;

@Configuration
public class CaseVerifierConfiguration {

    @Bean
    JsonNodesVerifier exampleCaseVerifier() {
        return new JsonNodesVerifier(
                "/case_details/jurisdiction", "PUBLICLAW",
                "/case_details/case_type_id", "CCD_BUNDLE_MVP_TYPE");
    }

}
