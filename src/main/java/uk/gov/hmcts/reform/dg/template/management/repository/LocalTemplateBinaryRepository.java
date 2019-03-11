package uk.gov.hmcts.reform.dg.template.management.repository;

import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

public class LocalTemplateBinaryRepository implements TemplateBinaryRepository {

    @Override
    public Optional<InputStream> getTemplateById(String id) {
        String filename = new String(Base64.getDecoder().decode(id));

        return Optional.ofNullable(
            Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("templates/" + filename)
        );
    }

}
