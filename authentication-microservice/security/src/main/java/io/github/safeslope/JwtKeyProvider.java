package io.github.safeslope;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtKeyProvider {

    @Value("${spring.security.jwt.private-key}")
    private String privateKeyBase64;

    @Value("${spring.security.jwt.public-key}")
    private String publicKeyBase64;


    public PrivateKey privateKey() {
        if (privateKeyBase64 == null || privateKeyBase64.isBlank()) {
            throw new IllegalStateException("JWT private key is not configured");
        }

        // MUST be single-line base64 PKCS#8 DER
        if (privateKeyBase64.contains("BEGIN") || privateKeyBase64.contains("END")) {
            throw new IllegalStateException(
                    "Invalid private key format: PEM is not supported. " +
                            "Provide a single-line Base64-encoded PKCS#8 DER key."
            );
        }

        if (privateKeyBase64.matches(".*\\s+.*")) {
            throw new IllegalStateException(
                    "Invalid private key format: key must be a single-line Base64 string."
            );
        }

        final byte[] derBytes;
        try {
            derBytes = Base64.getDecoder().decode(privateKeyBase64);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "Invalid private key format: not valid Base64", e
            );
        }

        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(derBytes);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Invalid private key: expected PKCS#8 DER RSA key", e
            );
        }
    }

    public PublicKey publicKey() {
        if (publicKeyBase64 == null || publicKeyBase64.isBlank()) {
            throw new IllegalStateException("JWT public key is not configured");
        }

        // MUST be single-line base64 SubjectPublicKeyInfo DER
        if (publicKeyBase64.contains("BEGIN") || publicKeyBase64.contains("END")) {
            throw new IllegalStateException(
                    "Invalid public key format: PEM is not supported. " +
                            "Provide a single-line Base64-encoded X.509 DER public key."
            );
        }

        if (publicKeyBase64.matches(".*\\s+.*")) {
            throw new IllegalStateException(
                    "Invalid public key format: key must be a single-line Base64 string."
            );
        }

        final byte[] derBytes;
        try {
            derBytes = Base64.getDecoder().decode(publicKeyBase64);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(
                    "Invalid public key format: not valid Base64", e
            );
        }

        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(derBytes);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Invalid public key: expected X.509 DER RSA key", e
            );
        }
    }
}
