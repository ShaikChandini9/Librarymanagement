package com.springboot.librarymanagement.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.springboot.librarymanagement.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // Minimum 32 characters (256 bits)
    private static final String SECRET_KEY = "MySuperSecretJwtKeyThatIsAtLeast32Chars!";
    private static final long JWT_EXPIRATION = 86400000L;  // 1 day

    public String generateToken(User user) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .claim("role", user.getRole().toString())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .build();

        // Ensure key length is valid
        JWSSigner signer = new MACSigner(SECRET_KEY.getBytes());

        JWSObject jwsObject = new JWSObject(
                new JWSHeader(JWSAlgorithm.HS256),
                new Payload(claimsSet.toJSONObject())
        );

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }
}
