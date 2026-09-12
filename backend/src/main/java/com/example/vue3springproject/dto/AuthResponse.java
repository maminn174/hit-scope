package com.example.vue3springproject.dto;

public class AuthResponse {

    private Long id;
    private String login;

    public AuthResponse(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }


}
