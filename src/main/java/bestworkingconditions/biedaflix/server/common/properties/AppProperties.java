package bestworkingconditions.biedaflix.server.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "biedaflix.config")
public class AppProperties {
    private String apiDomain;
    private String domain;
    private String ownerUsername;
    private String ownerRoleName;
    private Boolean requireUserAcceptance;

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getOwnerRoleName() {
        return ownerRoleName;
    }

    public void setOwnerRoleName(String ownerRoleName) {
        this.ownerRoleName = ownerRoleName;
    }

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
