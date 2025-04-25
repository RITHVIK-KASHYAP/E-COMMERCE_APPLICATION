# E-COMMERCE_APPLICATION
Object Oriented Analysis and Design PROJECT (6TH SEM PESU)

## Description
A simple yet scalable eCommerce platform built in Java following the MVC architecture. Users can browse products, add items to their cart, place orders, and track order statuses. The backend manages inventory, order processing, and payment integration.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [Design Patterns & Principles](#design-patterns--principles)
- [Individual Contributions](#individual-contributions)
- [License](#license)

## Features
- User registration and login
- Product browsing and search by category
- Shopping cart management
- Order placement and status tracking
- Inventory management with automatic stock updates
- Payment integration via a factory-based processor
- Order notifications using the Observer pattern

## Project Structure
```
src/
├── model/
│   ├── Product.java
│   ├── User.java
│   ├── Order.java
│   ├── Cart.java
│   └── repository/
│       ├── ProductRepository.java
│       ├── UserRepository.java
│       └── OrderRepository.java
├── view/
│   ├── ProductView.java
│   ├── CartView.java
│   └── OrderView.java
├── controller/
│   ├── ProductController.java
│   ├── UserController.java
│   └── OrderController.java
└── util/
    ├── factory/
    │   └── PaymentProcessorFactory.java
    ├── observer/
    │   ├── Observer.java
    │   ├── Subject.java
    │   └── OrderTracker.java
    └── singleton/
        └── DatabaseConnection.java
```

## Technologies Used
- Java 11
- MySQL 8
- JDBC

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/ecommerce-platform.git
   cd ecommerce-platform
   ```
2. Configure the database connection in `util/singleton/DatabaseConnection.java`.
3. Build the project using your preferred IDE (e.g., IntelliJ, Eclipse) or via CLI:
   ```bash
   javac -d out src/**/*.java
   ```

## Database Setup
Execute the SQL script in `db/schema.sql` to create and populate the database:
```sql
CREATE DATABASE IF NOT EXISTS ecommerce;
USE ecommerce;
-- Tables: users, products, orders, order_items, cart_items, categories
-- Views, procedures, triggers for stock updates and order history
-- (See full script in db/schema.sql)
```

## Running the Application
Run the main controller or entry point after building:
```bash
java -cp out controller.Main
```

## Design Patterns & Principles
- **Singleton Pattern** (DatabaseConnection) — ensures a single DB connection instance
- **Factory Pattern** (PaymentProcessorFactory) — abstracts payment processor creation
- **Observer Pattern** (OrderTracker) — notifies on order status changes

Principles:
- **Single Responsibility Principle** — classes have one responsibility
- **Open/Closed Principle** — easy to add new payment methods without modifying existing code
- **Dependency Inversion Principle** — high-level modules depend on abstractions

## Individual Contributions
| Member    | Pattern Implemented | Design Principle    | Responsibilities                                                  |
|-----------|---------------------|---------------------|-------------------------------------------------------------------|
| Rithvik Kashyap B S (SRN: PES2UG22CS450) | Singleton           | Single Responsibility | Database schema design, DatabaseConnection singleton, User & Product repositories |
| Rohan Mullay (SRN: PES2UG22CS455) | Factory             | Open/Closed         | PaymentProcessorFactory, OrderController logic, payment integration |
| Rayaan Sataar (SRN: PES2UG22CS437) | Observer            | Dependency Inversion | OrderTracker observer, view layer (ProductView, CartView, OrderView) |

## License
This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

