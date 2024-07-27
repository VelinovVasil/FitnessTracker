package com.fitnesstracker.fitnesstracker.utils;

import com.fitnesstracker.fitnesstracker.models.dto.UserDTO;
import com.fitnesstracker.fitnesstracker.services.EmailService;
import com.fitnesstracker.fitnesstracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final EmailService emailService;

    private final UserService userService;


    @Scheduled(cron = "0 0 9 * * ?") // 9 AM
    public void sendMorningEmails() {
        List<UserDTO> users = this.userService.getAllUsers();

        for (UserDTO user : users) {
            String email = user.getEmail();
            String htmlContent = "<html><body>"
                    + "<h1>Good Morning, " + user.getUsername() + "!</h1>"
                    + "<p>Here's your daily reminder to hit your physical activity goals. Have a great day ahead!</p>"
                    + "<p>Best regards,<br>The Fitness Tracker Team</p>"
                    + "</body></html>";

            try {
                emailService.sendHtmlMessage(email, "Morning Greeting", htmlContent);
            } catch (Exception e) {

                System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            }
        }
    }
}
