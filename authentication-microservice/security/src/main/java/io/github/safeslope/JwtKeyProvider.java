package io.github.safeslope;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtKeyProvider {

    @Value("${spring.security.jwt.private-key}")
    private String privateKeyBase64;

    public PrivateKey privateKey() {
        try {
            byte[] decoded = Base64.getDecoder().decode(privateKeyBase64);

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid private key", e);
        }
    }
}

