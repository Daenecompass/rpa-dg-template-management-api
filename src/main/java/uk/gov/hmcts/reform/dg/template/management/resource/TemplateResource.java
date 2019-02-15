package uk.gov.hmcts.reform.dg.template.management.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dg.template.management.repository.Template;
import uk.gov.hmcts.reform.dg.template.management.repository.TemplateRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TemplateResource {
    private final Logger log = LoggerFactory.getLogger(TemplateResource.class);
    private final TemplateRepository repository;

    public TemplateResource(TemplateRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Get a list of available templates")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = List.class),
        @ApiResponse(code = 401, message = "Unauthorised"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
    })
    @GetMapping("/templates")
    public ResponseEntity<List<Template>> templates() {
        log.debug("GET /templates");

        List<Template> results = repository.getTemplates();

        return  ResponseEntity.ok().body(results);
    }
}
