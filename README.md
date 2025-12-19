# 🛒 Online Shopping System

A Java-based e-commerce application demonstrating **7 Design Patterns** for educational purposes.

---

## 👥 Team Members

| Name | ID | Email |
|------|-----|-------|
| Rahma Ali Bauomi | 192100170 | 192100170@ecu.edu.eg |
| Reham Mohamed Asem | 192100089 | 192100089@ecu.edu.eg |
| Merna Ahmed Mohamed | 192100144 | 192100144@ecu.edu.eg |
| Fatemah Ahmed Farouk | 192100160 | 192100160@ecu.edu.eg |
| Roaa Elemam Mohamed | 192100083 | 192100083@ecu.edu.eg |
| Salma Ahmed Kamel | 192200136 | 192200136@ecu.edu.eg |

---

## 📋 About The Project

An online shopping system that allows users to:
- Browse products
- Manage shopping cart
- Process payments
- Complete orders

Built to demonstrate **Creational, Structural, and Behavioral Design Patterns** in Java.

---

## 🎨 Design Patterns Used

| Module | Owner | Patterns | Purpose |
|--------|-------|----------|---------|
| **Products** | Fatemah Ahmed | Factory Method, Singleton, Composite | Create and manage product catalog |
| **Shopping Cart** | Merna Ahmed | Singleton, Command | Manage cart operations with undo |
| **Payment** | Reham Mohamed | Factory Method, Adapter | Process multiple payment methods |
| **Users** | Rahma Ali | Factory Method, Singleton | User authentication and roles |
| **Orders** | Roaa Elemam | Builder, Chain of Responsibility | Build and validate orders |
| **Integration** | Salma Ahmed | - | Project setup and integration |

**Total: 7 Design Patterns** (Factory Method, Singleton, Builder, Command, Chain of Responsibility, Adapter, Composite)

---

## 🏗️ Project Structure
```
src/com/shopping/
├── Main.java                 [Salma]
├── models/                   [Salma - Shared]
├── products/                 [Fatemah]
├── cart/                     [Merna]
├── payment/                  [Reham]
├── users/                    [Rahma]
└── orders/                   [Roaa]
```

---

## 🚀 How to Run
```bash
# Clone repository
git clone https://github.com/1-3-Rahma/Online_Shopping_DesignPatterns.git

# Navigate to project
cd online-shopping-system

# Compile
javac src/com/shopping/**/*.java

# Run
java -cp src com.shopping.Main
```

---

## 🎓 Academic Info

**Course**: SET412: Design Patterns  
**Supervised by**: Dr. Hossam Hawash  
**Institution**: Egyptian Chinese University (ECU)  
**Academic Year**: 2025/2026

---

## **Project Structure**
```
online-shopping-system/
│
├── src/
│   └── com/
│       └── shopping/
│           │
│           ├── Main.java                               [Salma]
│           │
│           ├── models/                                 [Salma]
│           │   ├── Product.java
│           │   ├── User.java
│           │   ├── CartItem.java
│           │   ├── Order.java
│           │   └── Payment.java
│           │
│           ├── products/                               [Fatemah]
│           │   ├── ProductFactory.java                 (Factory Method)
│           │   ├── Electronics.java
│           │   ├── Clothing.java
│           │   ├── Book.java
│           │   └── ProductManager.java                 (Singleton)
│           │
│           ├── cart/                                   [Merna]
│           │   ├── ShoppingCart.java                   (Singleton)
│           │   ├── CartCommand.java
│           │   ├── AddCommand.java                     (Command)
│           │   └── RemoveCommand.java                  (Command)
│           │
│           ├── payment/                                [Reham]
│           │   ├── PaymentMethod.java
│           │   ├── CreditCard.java
│           │   ├── PayPal.java
│           │   ├── PaymentFactory.java                 (Factory Method)
│           │   └── PaymentAdapter.java                 (Adapter)
│           │
│           ├── users/                                  [Rahma]
│           │   ├── UserFactory.java                    (Factory Method)
│           │   ├── Customer.java
│           │   ├── Admin.java
│           │   └── AuthManager.java                    (Singleton)
│           │
│           └── orders/                                 [Roaa]
│               ├── OrderBuilder.java                   (Builder)
│               ├── OrderValidator.java                 (Chain of Responsibility)
│               ├── StockValidator.java
│               └── PaymentValidator.java
│
├── docs/
│   ├── README.md
│ 
│
├── .gitignore
└── pom.xml                                             [Salma]
