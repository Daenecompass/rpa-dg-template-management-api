package uk.gov.hmcts.reform.dg.template.management.repository;

import java.io.InputStream;

public class MockTemplateBinaryRepository implements TemplateBinaryRepository {

    @Override
    public InputStream getTemplateById(String id) {
        return ClassLoader.getSystemResourceAsStream("logback-spring.xml");
    }

}
