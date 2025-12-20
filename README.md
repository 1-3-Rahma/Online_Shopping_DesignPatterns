#  Online Shopping System

A Java-based desktop e-commerce application demonstrating **7 Design Patterns** using a modern Swing UI.


 **Team Members**

| Name                 | ID        | Email                                               |
| -------------------- | --------- | --------------------------------------------------- |
| Rahma Ali Bauomi     | 192100170 | [192100170@ecu.edu.eg](mailto:192100170@ecu.edu.eg) |
| Reham Mohamed Asem   | 192100089 | [192100089@ecu.edu.eg](mailto:192100089@ecu.edu.eg) |
| Merna Ahmed Mohamed  | 192100144 | [192100144@ecu.edu.eg](mailto:192100144@ecu.edu.eg) |
| Fatemah Ahmed Farouk | 192100160 | [192100160@ecu.edu.eg](mailto:192100160@ecu.edu.eg) |
| Roaa Elemam Mohamed  | 192100083 | [192100083@ecu.edu.eg](mailto:192100083@ecu.edu.eg) |
| Salma Ahmed Kamel    | 192200136 | [192200136@ecu.edu.eg](mailto:192200136@ecu.edu.eg) |

---

 **About The Project**

This project is a desktop-based Online Shopping System implemented in Java using Swing.

It allows users to interact with the system dynamically through a graphical interface instead of static data. The application supports **Admin** and **Customer** roles and simulates real-world e-commerce workflows.

The main goal of the project is to demonstrate the correct application of **Creational, Structural, and Behavioral Design Patterns** in a realistic software system.

---

 **Project Objectives**

* Apply Object-Oriented Design Principles in a real application
* Demonstrate 7 Design Patterns covered in the course
* Build a dynamic, user-driven system (no static inputs)
* Provide a modern, user-friendly UI
* Simulate real-world shopping operations:

  * Product management
  * Cart operations
  * Checkout and order history

---

 **Design Patterns Used**

| Module           | Owner         | Patterns                             | Purpose                             |
| ---------------- | ------------- | ------------------------------------ | ----------------------------------- |
| Products         | Fatemah Ahmed | Factory Method, Singleton, Composite | Create, filter, and manage products |
| Shopping Cart    | Merna Ahmed   | Singleton, Command                   | Manage cart actions with undo/redo  |
| Payment          | Reham Mohamed | Factory Method, Adapter              | Handle multiple payment methods     |
| Users            | Rahma Ali     | Factory Method, Singleton            | user roles                          |
| Orders           | Roaa Elemam   | Builder, Chain of Responsibility     | Build and validate orders           |
| Integration & UI | Salma Ahmed   | Bridge                               | Theme switching & UI integration    |

**Total Design Patterns Used:** 7 Patterns — Factory Method, Singleton, Builder, Command, Chain of Responsibility, Adapter, Composite

---

 **Admin Features**

* View all products in a sortable table
* Add new products with dynamic category/type
* Edit selected product (Name, Price, Details)
* Delete selected product with confirmation dialogs
* **Pattern Angle:** Product management uses a Singleton `ProductManager`, ensuring that edits and deletions are reflected globally across the system.

---

 **Customer Features**

* Browse products with:

  * Search
  * Category filter
  * Sorting options
* Add products to shopping cart
* Undo / Redo cart operations
* Checkout using different payment methods
* **Order History (My Orders):**

  * View past orders (Order ID, Date, Total, Payment method)
  * View order items
* **Pattern Angle:** Orders are created using the Builder Pattern, then stored and displayed to the user, adding real-world functionality.

---

 **Tools and Technologies Used**

* Java SE
* Java Swing (GUI)
* FlatLaf (Modern UI styling)
* Maven (Build & dependency management)
* NetBeans IDE
* Git & GitHub (Version control)

---

 **How to Run the Project**

🔹 **Using NetBeans**

1. Extract the project ZIP
2. Open NetBeans
3. Click File → Open Project
4. Select the project folder
5. Wait for Maven dependencies to load
6. Ensure the Main Class is set to: `com.shopping.Main`
7. Click **Run Project**
8. Maximize **The Window**

---

**Project Structure**

```
online-shopping-system/
│
├── .gitignore
├── pom.xml
│
├── docs/
│   └── README.md
│
└── src/
    └── main/
        └── java/
            └── com/
                └── shopping/
                    │
                    ├── Main.java
                    ├── util/
                    │   ├── Ids.java
                    │   ├── Validators.java   
                    │   ├── HashUtil.java      

                    ├── models/
                    │   ├── CartItem.java
                    │   ├── Order.java
                    │   ├── Payment.java
                    │   ├── Product.java
                    │   └── User.java
                    ├── controllers/
                    │   ├── AuthController.java
                    │   ├── CartController.java
                    │   ├── OrderController.java
                    │   ├── PaymentController.java
                    │   └── ProductController.java
                    ├── cart/
                    │   ├── AddCommand.java
                    │   ├── CartCommand.java
                    │   ├── RemoveCommand.java
                    │   └── ShoppingCart.java
                    ├── products/
                    │   ├── Book.java
                    │   ├── Clothing.java
                    │   ├── Electronics.java
                    │   ├── GenericProduct.java
                    │   ├── ProductFactory.java
                    │   └── ProductManager.java
                    │   └── criteria/
                    │       ├── AndCriteria.java
                    │       ├── CategoryCriteria.java
                    │       ├── CriteriaFactory.java
                    │       ├── NameContainsCriteria.java
                    │       ├── OrCriteria.java
                    │       ├── PriceRangeCriteria.java
                    │       ├── ProductCriteria.java
                    │       └── SortFactory.java
                    ├── payment/
                    │   ├── CreditCard.java
                    │   ├── LegacyGateway.java
                    │   ├── PayPal.java
                    │   ├── PaymentAdapter.java
                    │   ├── PaymentFactory.java
                    │   └── PaymentMethod.java
                    ├── users/
                    │   ├── Admin.java
                    │   ├── AuthManager.java
                    │   ├── Customer.java
                    │   └── UserFactory.java
                    ├── orders/
                    │   ├── OrderBuilder.java
                    │   ├── OrderValidator.java
                    │   ├── PaymentValidator.java
                    │   └── StockValidator.java
                    ├── persistence/
                    │   ├── JsonDatabase.java  
                    │   ├── UserRepository.java     
                    │   ├── OrderRepository.java  
                    └── ui/
                        ├── AdminFrame.java
                        ├── CustomerFrame.java
                        ├── LoginFrame.java
                        ├── components/Ui.java
                        ├── models/
                        │   ├── CartTableModel.java
                        │   ├── OrderTableModel.java
                        │   └── ProductTableModel.java
                        └── theme/
                            ├── DarkTheme.java
                            ├── FlatLafThemeApplier.java
                            ├── LightTheme.java
                            ├── Theme.java
                            ├── ThemeApplier.java
                            └── ThemeManager.java
```

---

 **Academic Information**

* **Course:** SET412 – Design Patterns
* **Supervised by:** Dr. Hossam Hawash
* **Institution:** Egyptian Chinese University (ECU)
* **Academic Year:** 2025/2026

---

