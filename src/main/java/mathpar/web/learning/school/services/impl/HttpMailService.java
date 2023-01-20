package mathpar.web.learning.school.services.impl;

import mathpar.web.learning.school.services.interfaces.MailService;
import org.springframework.stereotype.Service;

@Service
public class HttpMailService implements MailService {
    @Override
    public void sendSimpleMail(String to, String title, String message) {
        System.out.printf("Sending email to %s with title %s and message %s", to, title, message);
    }
}
