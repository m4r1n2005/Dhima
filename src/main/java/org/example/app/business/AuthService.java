package org.example.app.business;

public interface AuthService extends TokenGenerator,
        TokenValidator, TokenRevoker {

}