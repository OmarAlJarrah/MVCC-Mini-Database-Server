package com.atypon.authorization;

import java.util.UUID;

public class UserFactory {
  public static final String ADMIN = "Admin";
  public static final String CLIENT = "Client";

  private UserFactory() {}

  public static UserInterface makeUser(UserType type) {
    UserInterface newUser;
    if (ADMIN.equals(type.getType())) {
      newUser = new User(UUID.randomUUID().toString(), ADMIN);
    } else {
      newUser = new User(UUID.randomUUID().toString(), CLIENT);
    }

    return newUser;
  }
}
