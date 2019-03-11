package uk.gov.hmcts.reform.dg.template.management.repository;

import java.io.InputStream;
import java.util.Optional;

public interface TemplateBinaryRepository {

    Optional<InputStream> getTemplateById(String id);

}
