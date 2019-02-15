package uk.gov.hmcts.reform.dg.template.management.repository;

import java.io.InputStream;

public interface TemplateBinaryRepository {

    InputStream getTemplateById(String id);

}
