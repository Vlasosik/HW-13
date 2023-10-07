package org.example.part;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
}

@Data
class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;
}

@Data
class Geo {
    private String lat;
    private String lng;
}

@Data
class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}

@Data
class Posts {
    Long userId;
    Long id;
    String title;
    String body;
}

@Data
class Comments {
    Long postId;
    Long id;
    String name;
    String email;
    String body;
}

@Data
class UserTask {
    Long userId;
    Long id;
    String title;
    boolean completed;
}
