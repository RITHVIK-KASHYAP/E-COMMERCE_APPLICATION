package controller;

import model.User;
import model.Repository.UserRepository;

public class UserController {
private UserRepository userRepository;

public UserController() {
this.userRepository = new UserRepository();
}

public User login(String username, String password) {
User user = userRepository.authenticate(username, password);

if (user != null) {
    System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
    return user;
} else {
    System.out.println("Invalid username or password.");
    return null;
}
}

public User register(String username, String password, String email, String address, String phone) {
// Check if username already exists
if (userRepository.findByUsername(username) != null) {
    System.out.println("Username already exists. Please choose another one.");
    return null;
}

// Create new user
User newUser = new User(0, username, password, email, address, phone);
boolean success = userRepository.save(newUser);

if (success) {
    System.out.println("Registration successful!");
    return userRepository.findByUsername(username);
} else {
    System.out.println("Registration failed.");
    return null;
}
}

public boolean updateUserProfile(User user) {
boolean success = userRepository.update(user);

if (success) {
    System.out.println("Profile updated successfully!");
} else {
    System.out.println("Failed to update profile.");
}

return success;
}

public User getUserById(int userId) {
return userRepository.findById(userId);
}
}
