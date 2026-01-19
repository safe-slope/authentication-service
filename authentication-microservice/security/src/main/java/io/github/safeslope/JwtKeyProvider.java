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

    private volatile PrivateKey cachedPrivateKey;

    public PrivateKey privateKey() {
        if (cachedPrivateKey == null) {
            synchronized (this) {
                if (cachedPrivateKey == null) {
                    cachedPrivateKey = initializePrivateKey();
                }
            }
        }
        return cachedPrivateKey;
    }

    private PrivateKey initializePrivateKey() {
        try {
            if (privateKeyBase64 == null || privateKeyBase64.trim().isEmpty()) {
                throw new IllegalStateException(
                    "JWT private key is not configured. Please set the JWT_PRIVATE_KEY environment variable. " +
                    "See CONFIG.md for instructions on generating and configuring JWT keys."
                );
            }

            byte[] decoded = Base64.getDecoder().decode(privateKeyBase64.trim());

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                "Invalid JWT private key format. The key must be a valid Base64-encoded PKCS8 private key. " +
                "See CONFIG.md for instructions on generating JWT keys.", e
            );
        } catch (Exception e) {
            throw new IllegalStateException(
                "Failed to initialize JWT private key: " + e.getMessage() + ". " +
                "See CONFIG.md for instructions on generating and configuring JWT keys.", e
            );
        }
    }
}

