package com.leonardo.bookstoremanager.authentication.builder;


import com.leonardo.bookstoremanager.dto.JwtRequest;
import lombok.Builder;

@Builder
public class JwtRequestBuilder {

    @Builder.Default
    private String userName = "leo";

    @Builder.Default
    private String password = "654321";

    public JwtRequest buildJwtRequest(){
        return new JwtRequest(userName, password);
    }
}
