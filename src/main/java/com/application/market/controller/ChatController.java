package com.application.market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ChatController {

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> handleChatMessage(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String reply = generateAIResponse(message);

        Map<String, String> response = new HashMap<>();
        response.put("reply", reply);

        return ResponseEntity.ok(response);
    }

    private String generateAIResponse(String message) {
        // Simple AI-like responses for the eco marketplace
        message = message.toLowerCase();

        if (message.contains("what is the platform") || message.contains("platform")) {
            return "ReFreshAI is an innovative eco-marketplace that connects buyers and sellers of recyclable materials worldwide. " +
                    "Our platform uses AI to help you find the best deals, recommend products based on your interests, " +
                    "and promote sustainable trading practices. We support 14 different categories of recyclable materials!";
        }

        if (message.contains("what products can i sell") || message.contains("sell")) {
            return "You can sell various recyclable materials including: Metals, Battery Recycling materials, " +
                    "Compost & Food Waste, Computer & Electronics, Glass & Fiberglass, Chemicals, Paper/Cardboard, " +
                    "Plastic, Textiles & Leather, Tire & Rubber, Wood, Used Commercial Goods, Used Clothes, " +
                    "and Used Equipment. Our AI helps you set competitive prices based on market data!";
        }

        if (message.contains("where") || message.contains("why")) {
            return "ReFreshAI operates globally with a focus on Moldova and surrounding regions. " +
                    "Why choose us? We use AI-powered recommendations, provide smart pricing suggestions, " +
                    "offer real-time chat support, and help reduce environmental waste by facilitating " +
                    "the circular economy. Every transaction contributes to a more sustainable future!";
        }

        if (message.contains("price") || message.contains("cost")) {
            return "Our AI analyzes market trends to suggest optimal pricing for your recyclable materials. " +
                    "Prices vary by category, quality, and location. Use our smart pricing feature when listing " +
                    "products to get AI-generated price recommendations based on similar items in your area!";
        }

        if (message.contains("ai") || message.contains("artificial intelligence")) {
            return "Our AI features include: Smart product recommendations based on your browsing history, " +
                    "AI-powered price suggestions, automated product description generation, intelligent search, " +
                    "and personalized content. The AI learns from your preferences to improve your experience!";
        }

        if (message.contains("help") || message.contains("support")) {
            return "I'm here to help! You can ask me about: How to sell products, pricing strategies, " +
                    "platform features, recyclable material categories, shipping options, or any other questions " +
                    "about our eco-marketplace. What would you like to know?";
        }

        if (message.contains("environment") || message.contains("eco") || message.contains("green")) {
            return "Great question! ReFreshAI promotes environmental sustainability by facilitating the reuse " +
                    "and recycling of materials. Every transaction on our platform helps reduce waste, conserve " +
                    "natural resources, and support the circular economy. Together, we're building a greener future!";
        }

        // Default response
        return "Thank you for your question! I'm here to help you navigate our AI-powered eco-marketplace. " +
                "You can ask me about selling products, pricing, platform features, or environmental benefits. " +
                "How can I assist you today?";
    }
}