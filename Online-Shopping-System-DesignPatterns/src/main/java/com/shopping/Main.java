package com.shopping;

import com.shopping.cart.AddCommand;
import com.shopping.cart.RemoveCommand;
import com.shopping.cart.ShoppingCart;
import com.shopping.models.CartItem;
import com.shopping.models.Order;
import com.shopping.models.Payment;
import com.shopping.models.Product;
import com.shopping.models.User;
import com.shopping.orders.OrderBuilder;
import com.shopping.orders.OrderValidator;
import com.shopping.orders.PaymentValidator;
import com.shopping.orders.StockValidator;
import com.shopping.payment.PaymentFactory;
import com.shopping.payment.PaymentMethod;
import com.shopping.products.ProductFactory;
import com.shopping.products.ProductManager;
import com.shopping.users.AuthManager;
import com.shopping.users.UserFactory;
import com.shopping.util.Input;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final ProductManager productManager = ProductManager.getInstance();
    private static final ShoppingCart cart = ShoppingCart.getInstance();
    private static final AuthManager auth = AuthManager.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        seedIfEmpty();

        System.out.println("===================================");
        System.out.println("   ONLINE SHOPPING SYSTEM (CLI)    ");
        System.out.println("   Design Patterns Demo Project    ");
        System.out.println("===================================");

        while (true) {
            if (!auth.isLoggedIn()) {
                if (!loginMenu(sc)) break; // exit
            } else {
                User current = auth.getCurrentUser();
                if ("Admin".equalsIgnoreCase(current.getRole())) {
                    adminMenu(sc);
                } else {
                    customerMenu(sc);
                }
            }
        }

        System.out.println("Goodbye!");
        sc.close();
    }

    private static void seedIfEmpty() {
        if (!productManager.isEmpty()) return;

        ProductFactory pf = new ProductFactory();
        productManager.addProduct(pf.create(ProductFactory.ProductType.ELECTRONICS, "P100", "Headphones", 1200, 10, "Sony"));
        productManager.addProduct(pf.create(ProductFactory.ProductType.CLOTHING, "P200", "Hoodie", 800, 5, "L"));
        productManager.addProduct(pf.create(ProductFactory.ProductType.BOOK, "P300", "Clean Code", 650, 3, "Robert C. Martin"));
    }

    private static boolean loginMenu(Scanner sc) {
        System.out.println("\n--- Login Menu ---");
        System.out.println("1) Login as Customer");
        System.out.println("2) Login as Admin");
        System.out.println("0) Exit");

        int choice = Input.readInt(sc, "Choose: ", 0, 2);
        if (choice == 0) return false;

        String id = Input.readNonBlank(sc, "Enter user id: ");
        String name = Input.readNonBlank(sc, "Enter name: ");
        String email = Input.readNonBlank(sc, "Enter email: ");

        UserFactory uf = new UserFactory();
        User user = (choice == 1)
                ? uf.create(UserFactory.UserType.CUSTOMER, id, name, email)
                : uf.create(UserFactory.UserType.ADMIN, id, name, email);

        auth.login(user);
        System.out.println("Logged in: " + user);
        return true;
    }

    private static void adminMenu(Scanner sc) {
        while (auth.isLoggedIn() && "Admin".equalsIgnoreCase(auth.getCurrentUser().getRole())) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1) List products");
            System.out.println("2) Add new product (Factory Method)");
            System.out.println("3) Increase stock for a product");
            System.out.println("4) Logout");

            int choice = Input.readInt(sc, "Choose: ", 1, 4);

            switch (choice) {
                case 1 -> listProducts();
                case 2 -> adminAddProduct(sc);
                case 3 -> adminIncreaseStock(sc);
                case 4 -> { cart.clear(); auth.logout(); System.out.println("Logged out."); }
            }
        }
    }

    private static void adminAddProduct(Scanner sc) {
        ProductFactory pf = new ProductFactory();

        System.out.println("\nChoose Product Type:");
        System.out.println("1) Electronics");
        System.out.println("2) Clothing");
        System.out.println("3) Book");
        int t = Input.readInt(sc, "Type: ", 1, 3);

        String id = Input.readNonBlank(sc, "Product id (unique): ");
        String name = Input.readNonBlank(sc, "Name: ");
        double price = Input.readDoubleMin(sc, "Price: ", 0);
        int stock = Input.readIntMin(sc, "Stock: ", 0);

        String extraLabel = (t == 1) ? "Brand" : (t == 2) ? "Size" : "Author";
        String extra = Input.readNonBlank(sc, extraLabel + ": ");

        ProductFactory.ProductType type = switch (t) {
            case 1 -> ProductFactory.ProductType.ELECTRONICS;
            case 2 -> ProductFactory.ProductType.CLOTHING;
            default -> ProductFactory.ProductType.BOOK;
        };

        try {
            Product p = pf.create(type, id, name, price, stock, extra);
            boolean ok = productManager.addProduct(p);
            if (!ok) System.out.println("A product with this id already exists.");
            else System.out.println("Added: " + p);
        } catch (Exception ex) {
            System.out.println("Failed to add product: " + ex.getMessage());
        }
    }

    private static void adminIncreaseStock(Scanner sc) {
        listProducts();
        String id = Input.readNonBlank(sc, "Enter product id: ");
        Product p = productManager.findById(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        int qty = Input.readIntMin(sc, "Increase by: ", 1);
        p.increaseStock(qty);
        System.out.println("Updated: " + p);
    }

    private static void customerMenu(Scanner sc) {
        while (auth.isLoggedIn() && !"Admin".equalsIgnoreCase(auth.getCurrentUser().getRole())) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1) List products");
            System.out.println("2) Add to cart (Command)");
            System.out.println("3) Remove from cart (Command)");
            System.out.println("4) View cart");
            System.out.println("5) Checkout (Builder + Chain of Responsibility)");
            System.out.println("6) Logout");

            int choice = Input.readInt(sc, "Choose: ", 1, 6);

            switch (choice) {
                case 1 -> listProducts();
                case 2 -> customerAddToCart(sc);
                case 3 -> customerRemoveFromCart(sc);
                case 4 -> viewCart();
                case 5 -> checkout(sc);
                case 6 -> { cart.clear(); auth.logout(); System.out.println("Logged out."); }
            }
        }
    }

    private static void listProducts() {
        System.out.println("\n== Products ==");
        List<Product> products = productManager.listAll();
        if (products.isEmpty()) {
            System.out.println("(no products)");
            return;
        }
        products.forEach(System.out::println);
    }

    private static void customerAddToCart(Scanner sc) {
        listProducts();
        String id = Input.readNonBlank(sc, "Enter product id to add: ");
        Product p = productManager.findById(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        if (p.getStock() <= 0) {
            System.out.println("Out of stock.");
            return;
        }
        int qty = Input.readIntMin(sc, "Quantity: ", 1);
        // Stock is validated at checkout (Chain), but we can early-check too
        if (!p.hasStock(qty)) {
            System.out.println("Not enough stock. Available: " + p.getStock());
            return;
        }
        new AddCommand(cart, p, qty).execute();
        System.out.println("Added to cart.");
    }

    private static void customerRemoveFromCart(Scanner sc) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        viewCart();
        String id = Input.readNonBlank(sc, "Enter product id to remove: ");
        CartItem item = cart.findItem(id.trim());
        if (item == null) {
            System.out.println("This product is not in your cart.");
            return;
        }
        int max = item.getQuantity();
        int qty = Input.readInt(sc, "Quantity to remove (1-" + max + "): ", 1, max);
        new RemoveCommand(cart, item.getProduct(), qty).execute();
        System.out.println("Removed from cart.");
    }

    private static void viewCart() {
        System.out.println("\n== Cart ==");
        if (cart.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        cart.getItems().forEach(System.out::println);
        System.out.println("Total = " + String.format("%.2f", cart.getTotal()));
    }

    private static void checkout(Scanner sc) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        viewCart();
        String address = Input.readNonBlank(sc, "Delivery address: ");

        Payment payment = doPayment(sc, cart.getTotal());

        // Build Order (Builder)
        User user = auth.getCurrentUser();
        Order order = new OrderBuilder()
                .user(user)
                .items(cart.getItems())
                .address(address)
                .payment(payment)
                .build();

        // Validate (Chain)
        OrderValidator validator = new StockValidator();
        validator.linkWith(new PaymentValidator());

        boolean ok = validator.validate(order);
        if (!ok) {
            System.out.println("Checkout failed. Please fix the issue and try again.");
            return;
        }

        // Deduct stock + clear cart
        order.getItems().forEach(i -> i.getProduct().decreaseStock(i.getQuantity()));
        cart.clear();

        System.out.println("\n✅ Order placed successfully!");
        System.out.println(order);
        System.out.println("Payment: " + order.getPayment());
        System.out.println("Ship to: " + order.getAddress());
    }

    private static Payment doPayment(Scanner sc, double amount) {
        PaymentFactory pf = new PaymentFactory();

        System.out.println("\nChoose Payment Method:");
        System.out.println("1) Credit Card");
        System.out.println("2) PayPal");
        System.out.println("3) Legacy Gateway (Adapter)");

        int t = Input.readInt(sc, "Method: ", 1, 3);

        PaymentFactory.PaymentType type = switch (t) {
            case 1 -> PaymentFactory.PaymentType.CREDIT_CARD;
            case 2 -> PaymentFactory.PaymentType.PAYPAL;
            default -> PaymentFactory.PaymentType.LEGACY_GATEWAY;
        };

        String label = switch (type) {
            case CREDIT_CARD -> "Enter card (e.g. ****-1234): ";
            case PAYPAL -> "Enter PayPal email: ";
            case LEGACY_GATEWAY -> "Enter legacy token: ";
        };

        String credential = Input.readNonBlank(sc, label);
        PaymentMethod method = pf.create(type, credential);

        Payment payment = method.pay(amount);
        System.out.println("Payment result: " + payment);
        return payment;
    }
}
