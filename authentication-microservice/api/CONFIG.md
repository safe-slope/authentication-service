# Application Configuration

This application uses Spring Boot profiles to manage environment-specific configurations.

## Available Profiles

- **development** (default): For local development with verbose logging
- **staging**: For staging environment with moderate logging
- **production**: For production environment with minimal logging
- **test**: For running tests with H2 in-memory database

## Running with Different Profiles

You can set the active profile using the `SPRING_PROFILES_ACTIVE` environment variable:

```bash
# Development (default)
java -jar api.jar

# Staging
export SPRING_PROFILES_ACTIVE=staging
java -jar api.jar

# Production
export SPRING_PROFILES_ACTIVE=production
java -jar api.jar
```

Or use the command line parameter:
```bash
java -jar api.jar --spring.profiles.active=production
```

## Required Environment Variables

### All Environments
- `JWT_PRIVATE_KEY`: Private key for JWT token signing (required for production/staging)

### Development (optional with defaults)
- `DB_URL`: Database connection URL (default: jdbc:postgresql://localhost:5432/authdb_dev)
- `DB_USERNAME`: Database username (default: auth_user)
- `DB_PASSWORD`: Database password (default: auth_password)
- `JWT_PRIVATE_KEY`: Optional - uses a default test key if not provided

### Staging (required)
- `DB_URL`: Database connection URL (required)
- `DB_USERNAME`: Database username (required)
- `DB_PASSWORD`: Database password (required)

### Production (required)
- `DB_URL`: Database connection URL (required)
- `DB_USERNAME`: Database username (required)
- `DB_PASSWORD`: Database password (required)
- `SERVER_PORT`: Server port (optional, default: 8080)

### Test Profile
- Uses H2 in-memory database
- Uses a default test JWT key (no configuration needed)

## Generating JWT Keys

To generate a new RSA private key for JWT signing:

```bash
# Generate RSA private key (2048 bits)
openssl genrsa -out private_key.pem 2048

# Convert to PKCS8 format (required by Java)
openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt

# Encode to Base64 (for environment variable)
base64 -w 0 private_key.der
```

Then set the output as the `JWT_PRIVATE_KEY` environment variable:

```bash
export JWT_PRIVATE_KEY="<base64-encoded-key-from-above>"
```

**Important**: 
- Never commit JWT keys to version control
- Use different keys for each environment
- Store production keys in secure secrets management systems (e.g., AWS Secrets Manager, Azure Key Vault)

## Configuration Files

- `application.properties`: Base configuration with defaults
- `application-development.properties`: Development-specific overrides (includes default test key)
- `application-staging.properties`: Staging-specific overrides
- `application-production.properties`: Production-specific overrides
- `application-test.properties`: Test-specific configuration (includes default test key)

## Key Differences Between Environments

| Configuration | Development | Staging | Production |
|--------------|-------------|---------|------------|
| Logging Level | DEBUG | INFO | WARN |
| Show SQL | true | false | false |
| JPA DDL Auto | update | validate | validate |
| Error Details | visible | hidden | hidden |
| Actuator Endpoints | health,info,metrics,env | health,info,metrics | health,info |
| JWT Key | Default (can override) | Required | Required |

## Security Notes

- Never commit sensitive credentials to version control
- Always use environment variables for sensitive data
- JWT private keys should be securely generated and stored
- Production database credentials must be set via secure secrets management
- Development and test profiles include default JWT keys for convenience - **NEVER use these in production**
