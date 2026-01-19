package io.github.safeslope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.security.PrivateKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {JwtKeyProvider.class})
@TestPropertySource(properties = {
    "spring.security.jwt.private-key=MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpBocfpw5BtXoxY614V15FSPnOl2D/GEynJ+ef2SMj//mBjFz4SrX9cjwsLpudB9ta+Uuf5Ch+JFskmWU6pDPtyucz55AvHJibPs3sAlYnBFTAa8vYszdwfEqu1JypdHizc7BGGy9U8IlqNWFQ1u+GET6tby9zAPYr9Dtw9BBLiriMw6w3JktONUuJ55x06j+WzUPyrRAInP5iaOSCfWDeeitLoEle0MJbT2+byo0r8hkKCcc7B+Wx+kRkfe1M/y/y4W4HyikWckBzGwV246OCLklKyPxEe//JxKfgHaXlLz6/YfO2hqHKYm2IWtF8uZbnggPywdyt6+lC9C3C/cgrAgMBAAECggEAB+NAbloLEJvvGNua88ZipX8TUUW/3AepRBUDpRz/8SUijUjHPzr/+n+DOViaIHOGaVzANQpJAsygMuQJqk6RXHkfMvfibaBlqrun5mnxf/P0HzVTQkiSv2gSys0shmfjXhR5rGaGXe4KVkTUHBlQ/Nj/K3Qd1b28vy5a5m7X99PCQ8DRHx4sDXiqJnItHVD7arOqduzopAlrboMfKEiDl7cSrw5B09vQyaq64CIkzS2eqnEQ+457QCaE3VN8hHKHqc3yvXnGtSqU9JtDPt9mDV0vzGHiePOY89Qg3296rM7vZJaXQLF/wqbQ/vmWzI/e15NbqLjfm50Iq0Lbfclc+QKBgQDis7WDOE/P+RqbrEzdVODHU2VKE+emQBA5fTdW2635b7Sl/g7sMrCff1gea/Dw4uGMlKNHjgwSXCngGmCophcJe/aCF9cJeDhFIRHFnT74mKq6TFTIrna3nxrZmkQZTWxlI/WSIBjZU9AU9p4NMCl/4DptBqk+/zFXltau8qozfwKBgQC+3qD9jsJZqCLWjmtgxoUDjFW22ysfw6JTc6uRcLU+EBm5IkwCdB1Kt/UKIdd3rdbG4UqlH61v85c4MIbcseyTz9+hrsiOyLmJ/rsNgPkMndoRnGe902YjMFk43eK/YMySKY6aLUJp1c7zVKq7sVD/cR9rRuZy1xrc3wUhO5DRVQKBgQC4uo2O9qT6qFbJQXaE6HQdmFQyL9QUgaF25xTf/zRdzGuc01AfohKQdXk9mOGFuKroHfqLBTHLCDTLO1NGOckN3mh5i6mbOs5HbnIH4GF+uxBrrGVt/dQZPlnntTAGmjNYmxuxTdxRw10MqAUUZAl8/R8+1i21escfQ3d+qZiNSwKBgCckgN9+51x7nqlhi6PNEnonzWwyhiV9eUyj7AtnkcOoiZ3t6qiWJ2urThVF22ovvFwM18IFPQw4aEqdqQqZcmrdtn7x2S9u+p3SH4olaO2tcYsrCBtlvwx4XdPLrTSSJWHUI+p6XaRWtJoyQtE7C+FW0Zqub5QdiOTC18p47LoBAoGAa7qcXIf0sr04jpV+jVWVvFqAkCN8wOYMUI+2WO0f8vEWyv/yC4ZJ6M6GN1SOveIqGyZ6xGRbBZstA9zIbV3gHa1hJHR7zap36GbXBwvJrxCOjXew6EXl5zxd+oNh7+u9YMkNoQtOJwuo7OB1uXdKcGHG3GBeATlkns0qFK8dgQI="
})
class JwtKeyProviderTest {

    @Autowired
    private JwtKeyProvider jwtKeyProvider;

    @Test
    void testPrivateKeyInitialization() {
        // This test verifies that the private key can be initialized successfully
        PrivateKey privateKey = jwtKeyProvider.privateKey();
        
        assertNotNull(privateKey, "Private key should not be null");
        assertEquals("RSA", privateKey.getAlgorithm(), "Private key should use RSA algorithm");
    }

    @Test
    void testPrivateKeyIsCached() {
        // This test verifies that the private key is cached after first initialization
        PrivateKey privateKey1 = jwtKeyProvider.privateKey();
        PrivateKey privateKey2 = jwtKeyProvider.privateKey();
        
        assertSame(privateKey1, privateKey2, "Private key should be cached and return the same instance");
    }
}
