package com.application.market.controller;

import com.application.market.entity.Product;
import com.application.market.entity.Profile;
import com.application.market.entity.User;
import com.application.market.service.ProductService;
import com.application.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller("userProfileController")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/profile/{username}")
    public String viewUserProfile(@PathVariable String username, Model model, Authentication auth) {
        User user = userService.findByUsername(username);

        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            return "404";
        }

        Profile profile = userService.getUserInfo(user.getEmail());
        List<Product> userProducts = productService.getProductsByUser(user);

        model.addAttribute("profile", profile);
        model.addAttribute("user", user);
        model.addAttribute("products", userProducts);
        model.addAttribute("isOwnProfile", auth != null && auth.getName().equals(user.getEmail()));

        return "user-profile";
    }
}