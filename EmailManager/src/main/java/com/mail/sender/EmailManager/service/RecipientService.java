package com.mail.sender.EmailManager.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipientService {

    private static final Logger logger = LoggerFactory.getLogger(RecipientService.class);
    private static final String RECIPIENTS_FILE = "email_recipients.txt";
    private Set<String> recipients = new HashSet<>();

    @PostConstruct
    public void init() {
        try {
            loadRecipientsFromFile();
        } catch (Exception e) {
            logger.error("Error initializing recipient service", e);
        }
    }

    public List<String> getAllRecipients() {
        return new ArrayList<>(recipients);
    }

    public void addRecipient(String email) {
        if (email != null && !email.trim().isEmpty()) {
            recipients.add(email.trim());
            try {
                saveRecipientsToFile();
            } catch (Exception e) {
                logger.error("Error saving recipient", e);
            }
        }
    }

    public void removeRecipient(String email) {
        if (email != null) {
            recipients.remove(email.trim());
            try {
                saveRecipientsToFile();
            } catch (Exception e) {
                logger.error("Error removing recipient", e);
            }
        }
    }

    private void loadRecipientsFromFile() {
        Path path = Paths.get(RECIPIENTS_FILE);

        if (Files.exists(path)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(RECIPIENTS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        recipients.add(line.trim());
                    }
                }
                logger.info("Loaded {} recipients from file", recipients.size());
            } catch (IOException e) {
                logger.error("Error loading recipients from file", e);
            }
        } else {
            try {
                // Create the file if it doesn't exist
                Files.createFile(path);
                logger.info("Created new recipients file");
            } catch (IOException e) {
                logger.error("Error creating recipients file", e);
            }
        }
    }

    private void saveRecipientsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECIPIENTS_FILE))) {
            for (String recipient : recipients) {
                writer.write(recipient);
                writer.newLine();
            }
            logger.info("Saved {} recipients to file", recipients.size());
        } catch (IOException e) {
            logger.error("Error saving recipients to file", e);
            throw new RuntimeException("Failed to save recipients", e);
        }
    }
}