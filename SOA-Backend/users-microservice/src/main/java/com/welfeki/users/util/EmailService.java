package com.welfeki.users.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

	private final JavaMailSender mailSender;
	private final static Logger LOGGER = Logger.getLogger(EmailService.class.getName());
	private final static int MAX_RETRIES = 3;
	private final static long RETRY_DELAY_MS = 1000;

	@Override
	public void sendEmail(String to, String email) {
		int attempts = 0;
		while (attempts < MAX_RETRIES) {
			try {
				sendEmailWithRetry(to, email);
				return;
			} catch (Exception e) {
				attempts++;
				if (attempts == MAX_RETRIES) {
					LOGGER.log(Level.SEVERE, "Failed to send email after " + MAX_RETRIES + " attempts", e);
					throw new IllegalStateException("Failed to send email after multiple attempts", e);
				}
				try {
					Thread.sleep(RETRY_DELAY_MS);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					throw new IllegalStateException("Email sending interrupted", ie);
				}
			}
		}
	}

	private void sendEmailWithRetry(String to, String email) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

		helper.setText(email, true);
		helper.setTo(to);
		helper.setSubject("Confirm your email");
		helper.setFrom("welfkimedamine@gmail.com"); // Update this

		mailSender.send(mimeMessage);
		LOGGER.info("Email sent successfully to: " + to);
	}
}