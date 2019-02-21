package uk.gov.hmcts.reform.dg.template.management.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dg.template.management.repository.Template;
import uk.gov.hmcts.reform.dg.template.management.repository.TemplateListRepository;
import uk.gov.hmcts.reform.dg.template.management.repository.TemplateBinaryRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TemplateResource {
    private final Logger log = LoggerFactory.getLogger(TemplateResource.class);
    private final TemplateListRepository listRepository;
    private final TemplateBinaryRepository binaryRepository;

    public TemplateResource(TemplateListRepository listRepository, TemplateBinaryRepository binaryRepository) {
        this.listRepository = listRepository;
        this.binaryRepository = binaryRepository;
    }

    @ApiOperation(value = "Get a list of available templates")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = List.class),
        @ApiResponse(code = 401, message = "Unauthorised"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
    })
    @GetMapping("/templates")
    public ResponseEntity<List<Template>> templates() throws IOException {
        log.debug("GET /templates");

        final List<Template> results = listRepository.getTemplates();

        return ResponseEntity.ok().body(results);
    }

    @ApiOperation(value = "Get a binary template")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = InputStream.class),
        @ApiResponse(code = 401, message = "Unauthorised"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
    })
    @GetMapping("/templates/{id}")
    public void template(@PathVariable String id, HttpServletResponse response) throws IOException {
        log.debug("GET /templates/{}", id);

        final InputStream results = binaryRepository.getTemplateById(id);

        response.setStatus(HttpServletResponse.SC_OK);
        IOUtils.copy(results, response.getOutputStream());
    }
}
