package mathpar.web.learning.school.services;

import mathpar.web.learning.school.utils.MathparProperties;
import mathpar.web.learning.school.utils.dto.account.payloads.CreateTemporaryAccountPayload;
import mathpar.web.learning.school.utils.dto.account.responses.Account;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AccountService {
    private final String prefix;
    private final static RestTemplate restTemplate = new RestTemplate();

    public AccountService(MathparProperties properties) {
        this.prefix = properties.getAccountPrefix();
    }

    public Optional<Long> getAccountId(String email){
        try {
            return Optional.ofNullable(restTemplate.getForObject(prefix + "/accountId?email={email}", Long.class, email));
        }catch (HttpClientErrorException.BadRequest ex){
            return Optional.empty();
        }
    }

    public Long createTemporaryAccount(String email){
        return restTemplate.postForObject(prefix+"/temporaryAccount", new CreateTemporaryAccountPayload(email), Long.class);
    }

    public boolean isTokenValid(String token){
        return Optional.ofNullable(restTemplate.getForObject(prefix+"/isTokenValid?token={token}", Boolean.class, token)).orElse(false);
    }

    public Optional<Account> getAccount(long id){
        return Optional.ofNullable(restTemplate.getForObject(prefix+"/account?id={id}", Account.class, id));
    }

    @Deprecated
    public boolean isEmailAvailable(String email){
        return Optional.ofNullable(restTemplate.getForObject(prefix+"/principalAvailable?principal={principal}", Boolean.class, email)).orElse(false);
    }
}
