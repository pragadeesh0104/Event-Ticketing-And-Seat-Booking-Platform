package com.eventbooking.dto.response;

import com.eventbooking.enums.AccountStatus;
import com.eventbooking.enums.Role;

public class AuthResponse {
    private String token;
    private String tokenType;
    private Long userId;
    private String fullName;
    private String email;
    private Role role;
    private AccountStatus status;

    public AuthResponse() {}

    public AuthResponse(String token, String tokenType, Long userId, String fullName, String email, Role role, AccountStatus status) {
        this.token = token;
        this.tokenType = tokenType;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String tokenType;
        private Long userId;
        private String fullName;
        private String email;
        private Role role;
        private AccountStatus status;

        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public AuthResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public AuthResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder role(Role role) { this.role = role; return this; }
        public AuthResponseBuilder status(AccountStatus status) { this.status = status; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, tokenType, userId, fullName, email, role, status);
        }
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
}
