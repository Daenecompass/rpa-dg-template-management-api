package uk.gov.hmcts.reform.dg.template.management.appinsights;

import java.util.Map;

public interface EventRepository {

    void trackEvent(String name, Map<String, String> properties);
}
