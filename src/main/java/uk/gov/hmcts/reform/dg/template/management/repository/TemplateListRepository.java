package uk.gov.hmcts.reform.dg.template.management.repository;

import java.io.IOException;
import java.util.List;


public interface TemplateListRepository {

    List<Template> getTemplates() throws IOException;

}
