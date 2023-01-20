package mathpar.web.learning.school.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Getter
@Slf4j
@NoArgsConstructor
public class MathparProperties {
    private String databaseUsername;
    private String databasePassword;
    private String databaseUrl;

    private String accountPrefix;

    public void loadPropertiesFromManager(String secretmanagerUrlPrefix) {
        RestTemplate restTemplate = new RestTemplate();
        var namespaceProperties = restTemplate.getForObject(secretmanagerUrlPrefix+"/getNamespaceProperties?namespace=school", SchoolProperties.class);
        if(namespaceProperties==null) throw new RuntimeException("Can't load authentication properties");

        log.info("NAMESPACE RESPONSE: " + namespaceProperties);
        log.info("SECRET MANAGER URL: " + secretmanagerUrlPrefix);
        this.databasePassword = namespaceProperties.databasePassword;
        log.info("USERNAME: " + databasePassword);
        this.databaseUsername = namespaceProperties.databaseUsername;
        log.info("PASSWORD: " + databaseUsername);
        this.databaseUrl = namespaceProperties.databaseUrl;
        log.info("URL: " + databaseUrl);

        this.accountPrefix = namespaceProperties.accountUrl;
    }

    @Data
    public static class SchoolProperties {
        @JsonProperty("DatabaseUrl")
        private String databaseUrl;
        @JsonProperty("DatabaseUsername")
        private String databaseUsername;
        @JsonProperty("DatabasePassword")
        private String databasePassword;

        @JsonProperty("AccountUrl")
        private String accountUrl;
    }
}
