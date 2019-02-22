package uk.gov.hmcts.reform.dg.template.management.repository;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.touk.throwing.ThrowingFunction.unchecked;

public class LocalTemplateListRepository implements TemplateListRepository {

    @Override
    public List<Template> getTemplates() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:templates/*.*");

        return Arrays.stream(resources)
            .map(unchecked(Resource::getFilename))
            .sorted()
            .map(Template::new)
            .collect(Collectors.toList());
    }

}
