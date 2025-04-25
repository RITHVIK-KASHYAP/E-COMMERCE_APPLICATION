package controller;

import model.Product;
import model.Repository.ProductRepository;
import view.ProductView;

import java.util.List;

public class ProductController {
private ProductRepository productRepository;
private ProductView productView;

public ProductController() {
this.productRepository = new ProductRepository();
this.productView = new ProductView();
}

public void showProductDetails(int productId) {
Product product = productRepository.findById(productId);
if (product != null) {
    productView.displayProductDetails(product);
} else {
    System.out.println("Product not found!");
}
}

public void showAllProducts() {
List<Product> products = productRepository.findAll();
productView.displayProductList(products);
}

public void showProductsByCategory(String category) {
List<Product> products = productRepository.findByCategory(category);
productView.displayProductList(products);
}

public void addProduct() {
Product newProduct = productView.getNewProductInput();
boolean success = productRepository.save(newProduct);

if (success) {
    System.out.println("Product added successfully!");
} else {
    System.out.println("Failed to add product.");
}
}

public void updateProduct(int productId) {
Product product = productRepository.findById(productId);

if (product == null) {
    System.out.println("Product not found!");
    return;
}

Product updatedProduct = productView.getUpdateProductInput(product);
boolean success = productRepository.update(updatedProduct);

if (success) {
    System.out.println("Product updated successfully!");
} else {
    System.out.println("Failed to update product.");
}
}

public void deleteProduct(int productId) {
boolean success = productRepository.delete(productId);

if (success) {
    System.out.println("Product deleted successfully!");
} else {
    System.out.println("Failed to delete product.");
}
}

public Product getProductById(int productId) {
return productRepository.findById(productId);
}
}