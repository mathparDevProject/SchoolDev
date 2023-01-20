package mathpar.web.learning.school.controllers.api;

import lombok.Data;
import mathpar.web.learning.school.utils.PublicApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PublicApi
@RestController
public class HealthController {
    @Value("${mathpar.project.version}")
    private String version;

    @GetMapping("/status")
    public HealthStatus getHealth(){
        return new HealthStatus(version);
    }

    @Data
    private static class HealthStatus{
        private boolean applicationHealthy;
        private boolean databaseHealthy;
        private String version;

        public HealthStatus(String version) {
            this.version = version;
            this.applicationHealthy = true;
            this.databaseHealthy = true;
        }
    }
}
