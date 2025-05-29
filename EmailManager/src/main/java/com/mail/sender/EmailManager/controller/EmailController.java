
package com.mail.sender.EmailManager.controller;

import com.mail.sender.EmailManager.service.RecipientService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmailController {
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final JavaMailSender mailSender;
    private final RecipientService recipientService;

    @Value("${spring.mail.username}")
    private String fromMailId;

    @Autowired
    public EmailController(JavaMailSender mailSender, RecipientService recipientService) {
        this.mailSender = mailSender;
        this.recipientService = recipientService;
    }

    @GetMapping({"/", "/email-form"})
    public String showEmailForm(Model model) {
        model.addAttribute("savedRecipients", recipientService.getAllRecipients());
        return "EmailForm";
    }

    @PostMapping("/save-recipient")
    @ResponseBody
    public ResponseEntity<String> saveRecipient(@RequestParam("email") String email) {
        try {
            recipientService.addRecipient(email);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            logger.error("Error saving recipient", e);
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @PostMapping("/remove-recipient")
    public String removeRecipient(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        try {
            recipientService.removeRecipient(email);
            redirectAttributes.addFlashAttribute("success", "Recipient removed successfully!");
        } catch (Exception e) {
            logger.error("Error removing recipient", e);
            redirectAttributes.addFlashAttribute("error", "Failed to remove recipient: " + e.getMessage());
        }
        return "redirect:/email-form";
    }

    @PostMapping("/send-email")
    public String sendEmail(
            @RequestParam(value = "toEmails", required = false) List<String> toEmails,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment,
            @RequestParam(value = "selectedRecipients", required = false) List<String> selectedRecipients,
            RedirectAttributes redirectAttributes) {

        try {
            List<String> allRecipients = new ArrayList<>();

            // Add manually entered emails if any
            if (toEmails != null) {
                for (String email : toEmails) {
                    if (email != null && !email.trim().isEmpty()) {
                        allRecipients.add(email.trim());
                    }
                }
            }

            // Add selected saved recipients if any
            if (selectedRecipients != null) {
                allRecipients.addAll(selectedRecipients);
            }

            // If no recipients were selected or entered, use all saved recipients
            if (allRecipients.isEmpty()) {
                allRecipients = recipientService.getAllRecipients();
                if (allRecipients.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "No recipients selected or available!");
                    return "redirect:/email-form";
                }
            }

            logger.info("Sending email to {} recipients", allRecipients.size());

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, attachment != null && !attachment.isEmpty());

            helper.setFrom(fromMailId);
            helper.setTo(allRecipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body);

            if (attachment != null && !attachment.isEmpty()) {
                helper.addAttachment(attachment.getOriginalFilename(), attachment);
            }

            mailSender.send(message);

            // Save any new recipients
            for (String email : allRecipients) {
                if (email != null && !email.trim().isEmpty()) {
                    recipientService.addRecipient(email.trim());
                }
            }

            redirectAttributes.addFlashAttribute("success", "Email sent successfully to " + allRecipients.size() + " recipients!");
        } catch (Exception e) {
            logger.error("Failed to send email", e);
            redirectAttributes.addFlashAttribute("error", "Failed to send email: " + e.getMessage() +
                    ". Please check your SMTP settings in application.properties.");
        }

        return "redirect:/email-form";
    }
}