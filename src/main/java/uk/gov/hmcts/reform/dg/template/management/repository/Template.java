package uk.gov.hmcts.reform.dg.template.management.repository;

import java.util.Base64;

/**
 * Template stored in Docmosis.
 */
public class Template {

    public final String id;
    public final String name;

    public Template(String name) {
        this.id = Base64.getEncoder().encodeToString(name.getBytes());
        this.name = name;
    }

}
