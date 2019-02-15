package uk.gov.hmcts.reform.dg.template.management.repository;

import com.google.common.collect.Lists;

import java.util.List;

public class MockTemplateRepository implements TemplateRepository {

    @Override
    public List<Template> getTemplates() {
        return Lists.newArrayList(
            new Template("template1.docx"),
            new Template("template2.docx"),
            new Template("template3.docx"),
            new Template("template4.docx")
        );
    }
}
