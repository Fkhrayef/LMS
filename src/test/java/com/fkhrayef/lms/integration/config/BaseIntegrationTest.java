package com.fkhrayef.lms.integration.config;

import com.fkhrayef.lms.model.AppRole;
import com.fkhrayef.lms.model.Role;
import com.fkhrayef.lms.model.User;
import com.fkhrayef.lms.repo.RoleRepo;
import com.fkhrayef.lms.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class BaseIntegrationTest {
    @Autowired
    protected RoleRepo roleRepo;

    @Autowired
    protected UserRepo userRepo;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected void setupTestUsers() {
        // Create roles if they don't exist
        Role userRole = roleRepo.findRoleByRoleName(AppRole.ROLE_USER)
                .orElseGet(() -> roleRepo.save(new Role(AppRole.ROLE_USER)));

        Role adminRole = roleRepo.findRoleByRoleName(AppRole.ROLE_ADMIN)
                .orElseGet(() -> roleRepo.save(new Role(AppRole.ROLE_ADMIN)));

        // Create test user if it doesn't exist
        if (!userRepo.existsByUserName("testuser")) {
            User testUser = new User("testuser", "test@example.com",
                    passwordEncoder.encode("password123"));
            setupUserDefaults(testUser);
            testUser.setRole(userRole);
            userRepo.save(testUser);
        }

        // Create admin user if it doesn't exist
        if (!userRepo.existsByUserName("admin")) {
            User adminUser = new User("admin", "admin@example.com",
                    passwordEncoder.encode("password123"));
            setupUserDefaults(adminUser);
            adminUser.setRole(adminRole);
            userRepo.save(adminUser);
        }
    }

    private void setupUserDefaults(User user) {
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
        user.setAccountExpiryDate(LocalDate.now().plusYears(1));
        user.setTwoFactorEnabled(false);
        user.setSignUpMethod("email");
    }
}