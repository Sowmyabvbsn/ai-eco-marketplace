package com.application.market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AIController {

    @PostMapping("/generate-description")
    public ResponseEntity<Map<String, String>> generateDescription(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String existingDescription = request.get("description");

        Map<String, String> response = new HashMap<>();

        try {
            String generatedDescription = generateProductDescription(title, existingDescription);
            response.put("generatedDescription", generatedDescription);
        } catch (Exception e) {
            response.put("error", "Failed to generate description: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    private String generateProductDescription(String title, String existingDescription) {
        if (title == null || title.trim().isEmpty()) {
            return "Please provide a product title to generate a description.";
        }

        title = title.toLowerCase();

        // AI-like description generation based on product title
        StringBuilder description = new StringBuilder();

        // Determine category and generate appropriate description
        if (title.contains("battery") || title.contains("batteries")) {
            description.append("High-quality battery suitable for various electronic devices. ")
                    .append("Environmentally friendly disposal and recycling options available. ")
                    .append("Long-lasting performance with reliable power output. ")
                    .append("Perfect for both household and commercial applications.");
        }
        else if (title.contains("metal") || title.contains("steel") || title.contains("aluminum") || title.contains("copper")) {
            description.append("Premium grade metal material ideal for construction and industrial applications. ")
                    .append("Excellent durability and corrosion resistance. ")
                    .append("Sustainably sourced and processed for environmental responsibility. ")
                    .append("Suitable for recycling and reuse in various projects.");
        }
        else if (title.contains("plastic") || title.contains("polymer")) {
            description.append("High-quality plastic material perfect for recycling and reuse. ")
                    .append("Durable construction with excellent chemical resistance. ")
                    .append("Environmentally conscious choice for sustainable projects. ")
                    .append("Suitable for various industrial and commercial applications.");
        }
        else if (title.contains("paper") || title.contains("cardboard")) {
            description.append("Premium paper/cardboard material ideal for packaging and crafts. ")
                    .append("Made from recycled content to support environmental sustainability. ")
                    .append("Excellent quality with consistent thickness and durability. ")
                    .append("Perfect for creative projects and commercial packaging needs.");
        }
        else if (title.contains("glass") || title.contains("fiberglass")) {
            description.append("High-quality glass material suitable for various applications. ")
                    .append("Excellent transparency and durability characteristics. ")
                    .append("Environmentally friendly and fully recyclable. ")
                    .append("Ideal for construction, decoration, and industrial use.");
        }
        else if (title.contains("textile") || title.contains("fabric") || title.contains("leather")) {
            description.append("Quality textile material perfect for fashion and upholstery projects. ")
                    .append("Durable construction with excellent wear resistance. ")
                    .append("Sustainable choice supporting circular fashion economy. ")
                    .append("Suitable for creative projects and commercial applications.");
        }
        else if (title.contains("wood") || title.contains("lumber")) {
            description.append("Premium wood material sourced from sustainable forests. ")
                    .append("Excellent grain pattern and natural durability. ")
                    .append("Perfect for furniture making, construction, and craft projects. ")
                    .append("Environmentally responsible choice for eco-conscious builders.");
        }
        else if (title.contains("tire") || title.contains("rubber")) {
            description.append("High-quality rubber material suitable for various applications. ")
                    .append("Excellent durability and weather resistance. ")
                    .append("Perfect for recycling into new products and applications. ")
                    .append("Environmentally responsible disposal and reuse option.");
        }
        else if (title.contains("electronic") || title.contains("computer") || title.contains("laptop")) {
            description.append("Quality electronic equipment in good working condition. ")
                    .append("Thoroughly tested for functionality and performance. ")
                    .append("Environmentally responsible alternative to buying new. ")
                    .append("Perfect for students, professionals, or hobbyists.");
        }
        else if (title.contains("chemical")) {
            description.append("Industrial-grade chemical suitable for various applications. ")
                    .append("Properly stored and handled according to safety standards. ")
                    .append("Ideal for laboratory, industrial, or commercial use. ")
                    .append("Please handle with appropriate safety precautions.");
        }
        else if (title.contains("compost") || title.contains("organic")) {
            description.append("Premium organic compost material rich in nutrients. ")
                    .append("Perfect for gardening and agricultural applications. ")
                    .append("Sustainably produced from organic waste materials. ")
                    .append("Helps improve soil health and plant growth naturally.");
        }
        else {
            // Generic description for unrecognized items
            description.append("Quality recyclable material in excellent condition. ")
                    .append("Carefully stored and maintained for optimal reuse potential. ")
                    .append("Environmentally responsible choice supporting sustainability. ")
                    .append("Perfect for various creative and practical applications.");
        }

        // Add eco-friendly message
        description.append("\n\nBy choosing this recyclable material, you're contributing to environmental ")
                .append("sustainability and supporting the circular economy. Every purchase helps reduce ")
                .append("waste and conserve natural resources for future generations.");

        return description.toString();
    }
}