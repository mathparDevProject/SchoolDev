package mathpar.web.learning.school.services;

import mathpar.web.learning.school.entities.School;
import mathpar.web.learning.school.entities.UserProfile;
import mathpar.web.learning.school.repositories.UserProfileRepository;
import mathpar.web.learning.school.services.interfaces.MailService;
import mathpar.web.learning.school.utils.SecurityUtils;
import mathpar.web.learning.school.utils.enums.Role;
import mathpar.web.learning.school.utils.exceptions.InvalidAccountException;
import mathpar.web.learning.school.utils.exceptions.MalformedDataException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserProfileService {
    private final AccountService accountService;
    private final UserProfileRepository userProfileRepository;
    private final MailService mailService;

    public UserProfileService(AccountService accountService, UserProfileRepository userProfileRepository, MailService mailService) {
        this.accountService = accountService;
        this.userProfileRepository = userProfileRepository;
        this.mailService = mailService;
    }

    public long getSchoolId(long profileId){
        return userProfileRepository.getSchoolId(profileId);
    }

    public List<UserProfile> getProfiles(long accountId){
        return userProfileRepository.findAllByAccountId(accountId);
    }

    public UserProfile createProfile(long accountId, School school, Role role){
        return userProfileRepository.save(UserProfile.of(accountId, school, role));
    }

    public UserProfile createProfile(String email, School school, Role role){
        var accountId = accountService.getAccountId(email).orElseGet(()->accountService.createTemporaryAccount(email));
        return createProfile(accountId, school, role);
    }

    public Role getUserType(long userId){
        return userProfileRepository.getUserType(userId);
    }

    public UserProfile getProfile(long profileId){
        return userProfileRepository.findById(profileId).orElseThrow(MalformedDataException::new);
    }

    public UserProfile getDirector(School school){
        return userProfileRepository.findBySchoolAndRole(school, Role.Director).get(0);
    }

    /**
     * This method send email request to apply for position in the school.
     * If account registered on email doesn't exist OR account registered on email doesn't have
      */
    public void requestProfile(String email, Role position) throws InvalidAccountException {
        var accountId = accountService.getAccountId(email);
        if(accountId.isEmpty()) throw new InvalidAccountException("Account doesn't exist");
        var profiles = userProfileRepository.findAllByAccountId(accountId.get());
        if(profiles.stream().noneMatch(profile -> profile.getRole().equals(Role.Director)))
            throw new InvalidAccountException("Account doesn't have director profiles");
        var account = accountService.getAccount(SecurityUtils.getUserAuthentication().getPrincipal());
        if(account.isEmpty()) throw new RuntimeException("Authenticated user's account wasn't found");
        mailService.sendSimpleMail(email, "New profile request", String.format("%s requested to create a new profile of role %s in your school. To do so, go to your school page and create a %s with an account email %s", account.get().getFullName(), position, position, account.get().getEmail()));
    }

    @Transactional
    public void removeAllSchoolProfiles(long schoolId){
        userProfileRepository.deleteBySchool(new School(schoolId));
    }

}
