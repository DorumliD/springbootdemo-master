package net.proselyte.springbootdemo.service;
import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActivationService {

    private final UserRepository userRepository;

    public UserActivationService(UserRepository userRepository, SmsService smsService) {
        this.userRepository = userRepository;
        this.smsService = smsService;
    }
    private final SmsService smsService;

    @Scheduled(fixedDelay = 8000) // Проверка раз в сутки 86400000
    public void updateUsersActivationStatus() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            user.updateIsActive();
            userRepository.save(user);
            // Проверяем, истек ли срок лицензии и смс не был отправлен
            if (!user.isActive() && !user.isSmsSent()) {
                smsService.sendSms(user);
            }
        }
    }

}
