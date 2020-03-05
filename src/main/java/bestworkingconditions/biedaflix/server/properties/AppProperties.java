package bestworkingconditions.biedaflix.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "biedaflix.config")
public class AppProperties {
    private String apiDomain;
    private String domain;
    private Boolean requireUserAcceptance;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getApiDomain() {
        return apiDomain;
    }

    public void setApiDomain(String apiDomain) {
        this.apiDomain = apiDomain;
    }

    public Boolean getRequireUserAcceptance() {
        return requireUserAcceptance;
    }

    public void setRequireUserAcceptance(Boolean requireUserAcceptance) {
        this.requireUserAcceptance = requireUserAcceptance;
    }
}
