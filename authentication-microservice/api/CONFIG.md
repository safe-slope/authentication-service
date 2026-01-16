# Application Configuration

This application uses Spring Boot profiles to manage environment-specific configurations.

## Available Profiles

- **development** (default): For local development with verbose logging
- **staging**: For staging environment with moderate logging
- **production**: For production environment with minimal logging

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
- `JWT_PRIVATE_KEY`: Private key for JWT token signing (required)

### Development (optional with defaults)
- `DB_URL`: Database connection URL (default: jdbc:postgresql://localhost:5432/authdb_dev)
- `DB_USERNAME`: Database username (default: auth_user)
- `DB_PASSWORD`: Database password (default: auth_password)

### Staging (required)
- `DB_URL`: Database connection URL (required)
- `DB_USERNAME`: Database username (required)
- `DB_PASSWORD`: Database password (required)

### Production (required)
- `DB_URL`: Database connection URL (required)
- `DB_USERNAME`: Database username (required)
- `DB_PASSWORD`: Database password (required)
- `SERVER_PORT`: Server port (optional, default: 8080)

## Configuration Files

- `application.properties`: Base configuration with defaults
- `application-development.properties`: Development-specific overrides
- `application-staging.properties`: Staging-specific overrides
- `application-production.properties`: Production-specific overrides

## Key Differences Between Environments

| Configuration | Development | Staging | Production |
|--------------|-------------|---------|------------|
| Logging Level | DEBUG | INFO | WARN |
| Show SQL | true | false | false |
| JPA DDL Auto | update | validate | validate |
| Error Details | visible | hidden | hidden |
| Actuator Endpoints | health,info,metrics,env | health,info,metrics | health,info |

## Security Notes

- Never commit sensitive credentials to version control
- Always use environment variables for sensitive data
- JWT private keys should be securely generated and stored
- Production database credentials must be set via secure secrets management
