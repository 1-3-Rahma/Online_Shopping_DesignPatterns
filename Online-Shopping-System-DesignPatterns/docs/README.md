# Online Shopping System (Dynamic CLI)

Run `com.shopping.Main`.

Patterns used:
- Factory Method: ProductFactory, UserFactory, PaymentFactory
- Singleton: ProductManager, ShoppingCart, AuthManager
- Command: AddCommand, RemoveCommand
- Adapter: PaymentAdapter (LegacyGateway)
- Builder: OrderBuilder
- Chain of Responsibility: StockValidator -> PaymentValidator
