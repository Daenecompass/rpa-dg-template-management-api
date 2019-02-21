package uk.gov.hmcts.reform.dg.template.management.repository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class LocalTemplateListRepository implements TemplateListRepository {

    @Override
    public List<Template> getTemplates() throws IOException {
        try {
            URL templateDirectory = Thread.currentThread().getContextClassLoader().getResource("templates/");
            File templateFile = new File(templateDirectory.toURI());
            Path templateDirectoryPath = Paths.get(templateFile.getAbsolutePath());

            return Files
                .walk(templateDirectoryPath)
                .filter(Files::isRegularFile)
                .sorted()
                .map(f -> new Template(f.getFileName().toString()))
                .collect(Collectors.toList());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

}
