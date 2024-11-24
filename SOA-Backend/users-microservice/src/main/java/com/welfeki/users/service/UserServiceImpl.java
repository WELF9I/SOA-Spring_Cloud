package com.welfeki.users.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.welfeki.users.entities.Role;
import com.welfeki.users.entities.User;
import com.welfeki.users.repos.RoleRepository;
import com.welfeki.users.repos.UserRepository;
import com.welfeki.users.service.exceptions.EmailAlreadyExistsException;
import com.welfeki.users.service.exceptions.ExpiredTokenException;
import com.welfeki.users.service.exceptions.InvalidTokenException;
import com.welfeki.users.service.register.RegistrationRequest;
import com.welfeki.users.service.register.VerificationToken;
import com.welfeki.users.service.register.VerificationTokenRepository;
import com.welfeki.users.util.EmailSender;

@Transactional
@Service
public class UserServiceImpl  implements com.welfeki.users.service.UserService {

	@Autowired
	UserRepository userRep;
	
	@Autowired
	RoleRepository roleRep;
	
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	VerificationTokenRepository verificationTokenRepo;
	
	@Autowired
	EmailSender emailSender;
	
	@Override
	public User saveUser(User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRep.save(user);
	}

	@Override
	public User addRoleToUser(String username, String rolename) {
		User usr = userRep.findByUsername(username);
		Role r = roleRep.findByRole(rolename);
		
		usr.getRoles().add(r);
		return usr;
	}

	
	@Override
	public Role addRole(Role role) {
		return roleRep.save(role);
	}

	@Override
	public User findUserByUsername(String username) {	
		return userRep.findByUsername(username);
	}

	@Override
	public User registerUser(RegistrationRequest request) {

		Optional<User>  optionalUser = userRep.findByEmail(request.getEmail());
		if(optionalUser.isPresent())
			throw new EmailAlreadyExistsException("Email déjà existant!");
		
		User newUser = new User();
		newUser.setUsername(request.getUsername());
		newUser.setEmail(request.getEmail());
		
		newUser.setPassword( bCryptPasswordEncoder.encode( request.getPassword() )  );
		newUser.setEnabled(false);
		
		userRep.save(newUser);
		
		Role r = roleRep.findByRole("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(r);
		newUser.setRoles(roles);
		
		//génére le code secret
		 String code = this.generateCode();

		 VerificationToken token = new VerificationToken(code, newUser);
		 verificationTokenRepo.save(token);
		 
		 //envoyer le code par email à l'utilisateur
		  sendEmailUser(newUser,token.getToken());
		

		return userRep.save(newUser);
	}

	private String generateCode() {
		 Random random = new Random();
		 Integer code = 100000 + random.nextInt(900000);

		 return code.toString();

	}

	@Override
	public void sendEmailUser(User u, String code) {
		String emailBody = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
        </head>
        <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; line-height: 1.6; background-color: #f6f9fc;">
            <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; margin-top: 40px; margin-bottom: 40px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                <!-- Header -->
                <div style="background-color: #4F46E5; padding: 40px 20px; text-align: center;">
                    <h1 style="color: #ffffff; margin: 0; font-size: 24px;">Confirmation de votre compte</h1>
                </div>
                
                <!-- Content -->
                <div style="padding: 40px 20px;">
                    <p style="margin-top: 0; color: #374151; font-size: 16px;">Bonjour <strong>%s</strong>,</p>
                    
                    <p style="color: #374151; font-size: 16px;">Merci d'avoir créé votre compte. Pour finaliser votre inscription, veuillez utiliser le code de validation ci-dessous :</p>
                    
                    <div style="background-color: #F3F4F6; border-radius: 6px; padding: 20px; margin: 30px 0; text-align: center;">
                        <span style="font-family: monospace; font-size: 32px; font-weight: bold; color: #4F46E5; letter-spacing: 4px;">%s</span>
                    </div>
                    
                    <p style="color: #374151; font-size: 16px;">Ce code est valable pendant 10 minutes. Si vous n'avez pas demandé cette vérification, veuillez ignorer cet email.</p>
                </div>
                
                <!-- Footer -->
                <div style="background-color: #F9FAFB; padding: 20px; text-align: center; border-top: 1px solid #E5E7EB;">
                    <p style="margin: 0; color: #6B7280; font-size: 14px;">Cet email a été envoyé automatiquement, merci de ne pas y répondre.</p>
                    <div style="margin-top: 20px;">
                        <p style="margin: 0; color: #6B7280; font-size: 14px;">&copy; %d YourCompany. Tous droits réservés.</p>
                    </div>
                </div>
            </div>
        </body>
        </html>
        """.formatted(u.getUsername(), code, LocalDate.now().getYear());

		emailSender.sendEmail(u.getEmail(),emailBody);
	}

	@Override
	public User validateToken(String code) {
		VerificationToken token = verificationTokenRepo.findByToken(code);
		
		if(token == null){
			throw new InvalidTokenException("Invalid Token !!!!!!!");
		}

		User user = token.getUser();
		
		Calendar calendar = Calendar.getInstance();
		
		if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
			verificationTokenRepo.delete(token);
			throw new ExpiredTokenException("expired Token");
		}
		
		user.setEnabled(true);
		userRep.save(user);
		return user;
	}

}
