package carpool_portal.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import carpool_portal.dto.RegisterDto;
import carpool_portal.entity.User;
import carpool_portal.repository.UserRepository;
import carpool_portal.service.UserService;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class UserController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/register")
    public String showRegisterPage(
            Model model
    ) {

        model.addAttribute(
                "registerDto",
                new RegisterDto()
        );

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(

            @Valid
            @ModelAttribute("registerDto")
            RegisterDto registerDto,

            BindingResult result,

            Model model
    ) {

        if (result.hasErrors()) {

            return "register";
        }

        if (!registerDto.getPassword()
                .equals(
                        registerDto.getConfirmPassword()
                )) {

            model.addAttribute(
                    "passwordError",
                    "Passwords do not match"
            );

            return "register";
        }

        if (userRepository.existsByEmail(
                registerDto.getEmail()
        )) {

            model.addAttribute(
                    "emailError",
                    "Email already exists"
            );

            return "register";
        }

        User user =
                modelMapper.map(
                        registerDto,
                        User.class
                );

        userService.registerUser(user);

        model.addAttribute(
                "success",
                "Registration successful! Please login."
        );

        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }  
        
        @GetMapping("/dashboard")
        public String dashboard(Authentication authentication,
                                Model model) {

            String email = authentication.getName();

            User user = userService.findByEmail(email);

            model.addAttribute("name", user.getName());

            return "dashboard";  
    }
    

}
