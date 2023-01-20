package mathpar.web.learning.school.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import mathpar.web.learning.school.utils.Constants;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class MathparCalculatorService {
    public String urlPrefix;
    private final static RestTemplate restTemplate = new RestTemplate();

    /**
     * This method checks if the solution provided as first argument correct against the solution provided as the second parameter.
     * Both solutions need to be in LaTeX format
     * @param userSolution the solution which need to be compared (in LaTeX). In scope of school this is the student's solution
     * @param databaseSolution the solution to which we compare (in LaTeX). In scope of school this is the solution provided by the task's creator.
     * @return true if the userSolution is equals to databaseSolution. False otherwise.
     */
    public boolean isSolvedCorrectly(String userSolution, String databaseSolution){
        try {
            var result = restTemplate.postForObject(urlPrefix + "/check", new CheckSolutionPayload(userSolution, databaseSolution), CheckSolutionResponse.class);
            if (result == null || result.error != null) throw new InternalComputationException(result==null?"Empty response returned":result.error);
            return result.result.equals(Constants.MATHPAR_CORRECT_ANSWER_KEY);
        } catch (HttpClientErrorException ex){
            throw new InternalComputationException(String.format("Request failed with status %s, message: %s", ex.getStatusCode().value(), ex.getMessage()));
        } catch (Exception ex){
            throw new RuntimeException(String.format("Unexpected exception occurred during checking solution %s compared to %s", userSolution, databaseSolution), ex);
        }
    }

    @Data
    @AllArgsConstructor
    private static class CheckSolutionPayload{
        private String userAnswer;
        private String dbSolutionAnswer;
    }

    @Data
    private static class CheckSolutionResponse{
          private String task;
          private int sectionId;
          private String status;
          private String result;
          private String latex;
          private String warning;
          private String error;
          private String stacktrace;
          private String warningMsg;
          private String errorMsg;
          private String filenames;
    }

    private static class InternalComputationException extends RuntimeException {
        public InternalComputationException(String message) {
            super(message);
        }
    }
}
