package com.application.market.config;

import com.application.market.entity.Category;
import com.application.market.entity.Product;
import com.application.market.entity.Role;
import com.application.market.entity.User;
import com.application.market.repository.CategoryRepository;
import com.application.market.repository.ProductRepository;
import com.application.market.repository.RoleRepository;
import com.application.market.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CategoryRepository categoryRepository, ProductRepository productRepository,
                           UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        System.out.println("DataInitializer: Starting initialization...");

        // Initialize categories first
        initializeCategories();

        // Initialize default user
        insertDefaultUser();

        // Initialize sample products
        initializeProducts();

        System.out.println("DataInitializer: Initialization completed successfully!");
    }

    private void initializeCategories() {
        List<String> categories = List.of(
                "Metals", "Battery Recycling", "Compost & Food Waste",
                "Computer & Electronics", "Glass & Fiberglass",
                "Chemicals", "Paper/Cardboard", "Plastic",
                "Textiles & Leather", "Tire & Rubber", "Wood",
                "Used Commercial Goods", "Used Clothes", "Used Equipment"
        );

        categories.forEach(categoryName -> {
            if (!categoryRepository.existsByCategoryName(categoryName)) {
                Category category = new Category();
                category.setCategoryName(categoryName);
                categoryRepository.save(category);
                System.out.println("Created category: " + categoryName);
            }
        });
    }

    private void insertDefaultUser() {
        // Check if user already exists - use existsByEmail to avoid duplicate issues
        if (userRepository.existsByEmail("john.doe@example.com")) {
            System.out.println("Default user already exists, skipping creation.");
            return;
        }

        // Fetch the role from the database
        Role userRole = roleRepository.findByName("ROLE_USER");

        if (userRole != null) {
            // Create the new user with the hashed password
            User user = new User();
            user.setUsername("johndoe");
            user.setName("John");
            user.setSurname("Doe");
            user.setEmail("john.doe@example.com");
            user.setPhoneNumber("1234567890");
            user.setPassword(passwordEncoder.encode("password123"));
            user.setRegisterDate(LocalDateTime.now());
            user.setUserType("USER");

            // Save the user
            User savedUser = userRepository.save(user);

            // Assign the role to the user
            savedUser.getRoles().add(userRole);
            userRepository.save(savedUser);

            System.out.println("Created default user: " + savedUser.getEmail());
        } else {
            System.out.println("Role 'ROLE_USER' not found in the database.");
        }
    }

    private void initializeProducts() {
        // Only create products if we have a user and categories
        Optional<User> user1 = userRepository.findById(1L);
        if (!user1.isPresent()) {
            System.out.println("No user found, skipping product creation.");
            return;
        }

        insertSampleProducts();
        insertChemicalProducts();
        insertCompostProducts();
        insertElectronicsProducts();
        insertGlassProducts();
        insertMetalProducts();
        insertPaperCardboardProducts();
        insertPlasticProducts();
        insertTextilesAndLeatherProducts();
        insertTireAndRubberProducts();
        insertUsedClothesProducts();
        insertUsedCommercialGoodsProducts();
        insertWoodProducts();
    }

    private void insertWoodProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(11L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Oak Wood Planks - 2x6 inches, 10ft",
                            "High-quality oak wood planks, 2x6 inches, 10ft long. Ideal for flooring or cabinetry projects.",
                            45.00, "Cantemir, strada 1, casa 2", 20, loadImage("src/main/resources/imagesdb/Wood1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Cherry Wood Lumber - 4x4 inches, 8ft",
                            "Beautiful cherry wood lumber, 4x4 inches, 8ft long. Perfect for high-end furniture making.",
                            60.00, "Cantemir, strada 1, casa 3", 15, loadImage("src/main/resources/imagesdb/Wood2.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Pine Wood Boards - 1x12 inches, 6ft",
                            "Versatile pine wood boards, 1x12 inches, 6ft long. Suitable for shelving, trim, and various woodworking projects.",
                            25.00, "Cantemir, strada 1, casa 4", 25, loadImage("src/main/resources/imagesdb/Wood3.jpg"),
                            LocalDateTime.now(), "Available")
            );

            // Save products if they don't already exist
            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertUsedCommercialGoodsProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(12L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Office Desk - 60x30 inches",
                            "Spacious office desk with a sleek design. Measures 60x30 inches, includes drawer and storage compartments.",
                            150.00, "Basarabeasca, strada 2, casa 1", 5, loadImage("src/main/resources/imagesdb/Used Commercial Goods1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Conference Table - Seats 8",
                            "Large conference table that seats up to 8 people. Made from high-quality wood with a polished finish.",
                            300.00, "Basarabeasca, strada 2, casa 2", 2, loadImage("src/main/resources/imagesdb/Used Commercial Goods2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertUsedClothesProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(13L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Vintage Denim Jacket - Size M",
                            "Classic vintage denim jacket in size medium. Slightly distressed, perfect for layering over casual outfits.",
                            25.00, "Soroca, strada 3, casa 1", 10, loadImage("src/main/resources/imagesdb/Used Clothes.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Floral Print Maxi Dress - Size S",
                            "Beautiful floral print maxi dress in size small. Ideal for summer outings and casual wear.",
                            30.00, "Soroca, strada 3, casa 2", 15, loadImage("src/main/resources/imagesdb/Used Clothes2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertTireAndRubberProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(10L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "All-Season Car Tire - 205/55R16",
                            "Durable all-season tire for cars. Size: 205/55R16, designed for optimal performance in all weather conditions.",
                            89.99, "Glodeni, strada 4, casa 1", 100, loadImage("src/main/resources/imagesdb/Tire & Rubber1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Rubber Floor Mat - 3ft x 5ft, Anti-Slip",
                            "Heavy-duty rubber floor mat, 3ft x 5ft, with anti-slip design. Ideal for industrial and home use.",
                            49.99, "Glodeni, strada 4, casa 2", 60, loadImage("src/main/resources/imagesdb/Tire & Rubber2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertTextilesAndLeatherProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(9L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Leather Wallet - Genuine Leather, Brown",
                            "Handcrafted genuine leather wallet in brown. Slim design with multiple card slots and a cash compartment.",
                            34.99, "Nisporeni, strada 5, casa 1", 50, loadImage("src/main/resources/imagesdb/Textiles & Leather1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Cotton Bedsheet Set - Queen Size, White",
                            "100% cotton bedsheet set, queen size, includes one fitted sheet, one flat sheet, and two pillowcases. White color.",
                            49.99, "Nisporeni, strada 5, casa 2", 100, loadImage("src/main/resources/imagesdb/Textiles & Leather2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertPlasticProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(8L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Plastic Storage Bin - 12\"x12\"x15\"",
                            "Durable plastic storage bin with a lid, 12\"x12\"x15\", suitable for organizing and storing various household items.",
                            9.99, "Ungheni, strada 6, casa 1", 120, loadImage("src/main/resources/imagesdb/Plastic1.jpeg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Polyethylene Tarp - 10ft x 20ft",
                            "Heavy-duty polyethylene tarp, 10ft x 20ft, waterproof and UV-resistant. Ideal for covering materials and equipment.",
                            24.99, "Ungheni, strada 6, casa 2", 60, loadImage("src/main/resources/imagesdb/Plastic2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertPaperCardboardProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(7L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Recycled Cardboard Sheets - 24\"x36\", 10 Pack",
                            "Eco-friendly recycled cardboard sheets, 24\"x36\", perfect for packaging, crafts, and storage. 10 sheets per pack.",
                            14.99, "Fălești, strada 7, casa 1", 100, loadImage("src/main/resources/imagesdb/PaperCardboard1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "A4 Recycled Printer Paper - 500 Sheets",
                            "A4 size recycled printer paper, 80gsm, suitable for inkjet and laser printing. 500 sheets per ream.",
                            9.99, "Fălești, strada 7, casa 2", 200, loadImage("src/main/resources/imagesdb/PaperCardboard2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertSampleProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(2L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Duracell AA Batteries - 10 Pack",
                            "Duracell AA alkaline batteries with long-lasting power for household and portable devices. Pack of 10 batteries.",
                            12.99, "Cahul, strada 8, casa 1", 50, loadImage("src/main/resources/imagesdb/Battery1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Energizer AAA Rechargeable Batteries - 4 Pack",
                            "Energizer AAA rechargeable batteries, suitable for high-drain devices. Pack of 4 batteries.",
                            16.50, "Cahul, strada 8, casa 2", 100, loadImage("src/main/resources/imagesdb/Battery2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertChemicalProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(6L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Acetone - 1 Liter",
                            "High-purity acetone for cleaning and solvent purposes. Suitable for industrial and household use. 1-liter bottle.",
                            14.99, "Strășeni, strada 9, casa 1", 25, loadImage("src/main/resources/imagesdb/Chemicals1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Isopropyl Alcohol 99% - 500ml",
                            "99% pure isopropyl alcohol for cleaning electronics and medical devices. 500ml bottle.",
                            7.49, "Strășeni, strada 9, casa 2", 60, loadImage("src/main/resources/imagesdb/Chemicals2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertCompostProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(3L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Organic Compost - 20 lbs Bag",
                            "Premium organic compost ideal for gardens, lawns, and flower beds. Rich in nutrients to improve soil health.",
                            9.99, "Drochia, strada 10, casa 1", 200, loadImage("src/main/resources/imagesdb/compost Food Waste1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Compost Bin - 30 Gallon Capacity",
                            "Durable compost bin with a 30-gallon capacity, perfect for food waste and organic material recycling at home.",
                            29.99, "Drochia, strada 10, casa 2", 50, loadImage("src/main/resources/imagesdb/compost Food Waste2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertElectronicsProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(4L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Dell XPS 13 Laptop - 13.3\" Display",
                            "Dell XPS 13 with a 13.3\" Full HD display, Intel i7 processor, 16GB RAM, and 512GB SSD. Perfect for productivity and travel.",
                            1299.99, "Orhei, strada 11, casa 1", 10, loadImage("src/main/resources/imagesdb/Computer & Electronics 1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Apple MacBook Pro 16\" - M1 Chip",
                            "Apple MacBook Pro with the M1 chip, 16\" Retina display, 16GB RAM, and 1TB SSD. Ideal for creative professionals.",
                            2499.99, "Orhei, strada 11, casa 2", 8, loadImage("src/main/resources/imagesdb/Computer & Electronics 2.png"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private void insertGlassProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(5L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Tempered Glass Panel - 6mm, 24\"x36\"",
                            "High-quality tempered glass panel, 6mm thickness, suitable for windows, doors, and furniture applications.",
                            79.99, "Briceni, strada 12, casa 1", 50, loadImage("src/main/resources/imagesdb/Glass & Fiberglass 1.jpeg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Fiberglass Insulation Roll - 15\"x50ft",
                            "Fiberglass insulation roll, 15-inch width, 50 feet length, suitable for residential and commercial building insulation.",
                            59.99, "Briceni, strada 12, casa 2", 30, loadImage("src/main/resources/imagesdb/Glass & Fiberglass 2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }

    private byte[] loadImage(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            if (Files.exists(path)) {
                return Files.readAllBytes(path);
            } else {
                System.out.println("Image not found: " + imagePath);
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + imagePath + " - " + e.getMessage());
            return null;
        }
    }

    private void insertMetalProducts() {
        Optional<User> user1 = userRepository.findById(1L);
        Optional<Category> category = categoryRepository.findById(1L);

        if (user1.isPresent() && category.isPresent()) {
            List<Product> products = List.of(
                    new Product(user1.get(), category.get(), "Stainless Steel Sheet - 304 Grade, 24\"x36\"",
                            "Premium 304 grade stainless steel sheet, 24\"x36\", 1mm thick. Ideal for industrial, construction, and home projects.",
                            89.99, "Edineț, strada 13, casa 1", 20, loadImage("src/main/resources/imagesdb/metals1.jpg"),
                            LocalDateTime.now(), "Available"),

                    new Product(user1.get(), category.get(), "Aluminum Rod - 1\" Diameter, 4ft Long",
                            "High-quality aluminum rod, 1-inch diameter, 4 feet long. Lightweight and corrosion-resistant, ideal for structural applications.",
                            29.99, "Edineț, strada 13, casa 2", 50, loadImage("src/main/resources/imagesdb/metals2.jpg"),
                            LocalDateTime.now(), "Available")
            );

            products.forEach(product -> {
                if (!productRepository.existsByTitleAndCategoryId(product.getTitle(), product.getCategory().getId())) {
                    productRepository.save(product);
                    System.out.println("Created product: " + product.getTitle());
                }
            });
        }
    }
}