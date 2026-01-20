package io.github.safeslope;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

@Component
public class JwtKeyProvider {

    @Value("${spring.security.jwt.private-key}")
    private String privateKey;

    private static final Pattern PEM_PATTERN = Pattern.compile(
        "-----BEGIN (RSA )?PRIVATE KEY-----([\\s\\S]*?)-----END (RSA )?PRIVATE KEY-----"
    );

    public PrivateKey privateKey() {
        String key = privateKey.trim();
        
        // Extract base64 content from PEM format if present
        var matcher = PEM_PATTERN.matcher(key);
        if (matcher.find()) {
            key = matcher.group(2).replaceAll("\\s+", "");
        } else {
            // If no PEM headers, assume it's already base64 content
            key = key.replaceAll("\\s+", "");
        }

        // Decode the base64-encoded key content
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(key);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                "Invalid private key format. Expected PEM format " +
                "(e.g., '-----BEGIN PRIVATE KEY-----...') or base64-encoded string.", e);
        }

        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid private key", e);
        }
    }
}

