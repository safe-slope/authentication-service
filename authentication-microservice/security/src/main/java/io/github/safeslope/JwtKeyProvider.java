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
    private String privateKey;

    public PrivateKey privateKey() {
        try {
            // Remove PEM headers/footers and whitespace if present
            // Support both PKCS#8 and traditional RSA formats
            String key = privateKey
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            // Decode the PEM-formatted key (which uses base64 internally)
            byte[] decoded;
            try {
                decoded = Base64.getDecoder().decode(key);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException(
                    "Invalid private key format. Expected PEM format " +
                    "(e.g., '-----BEGIN PRIVATE KEY-----...') or base64-encoded string.", e);
            }

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Invalid private key", e);
        }
    }
}

