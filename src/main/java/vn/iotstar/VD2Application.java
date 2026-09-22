package vn.iotstar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;



import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;

@SpringBootApplication
public class VD2Application {

    public static void main(String[] args) {

        SpringApplication.run(VD2Application.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository,
                            UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        return args -> {

            Role role = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName("ROLE_USER");
                        return roleRepository.save(newRole);
                    });

            if (userRepository.findByUsername("user01").isEmpty()) {

                User user = new User();

                user.setUsername("user01");
                user.setEmail("user01@gmail.com");
                user.setPassword(passwordEncoder.encode("123456"));

                // QUAN TRỌNG
                user.setFullName("Nguyễn Hữu Trung");

                user.setImages("/images/user.png");
                user.setEnabled(true);
                user.setRole(role);

                userRepository.save(user);
            }
        };
    }
}