package uk.gov.hmcts.reform.dg.template.management.repository;

import java.io.InputStream;
import java.util.Base64;

public class LocalTemplateBinaryRepository implements TemplateBinaryRepository {

    @Override
    public InputStream getTemplateById(String id) {
        String filename = new String(Base64.getDecoder().decode(id));

        return Thread
            .currentThread()
            .getContextClassLoader()
            .getResourceAsStream("templates/" + filename);
    }

}
