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
- `JWT_PRIVATE_KEY`: Private key for JWT token signing in PEM format (required)
  - Format: PEM-encoded RSA private key with headers
  - Example: 
    ```
    -----BEGIN PRIVATE KEY-----
    MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC...
    -----END PRIVATE KEY-----
    ```
  - Can be provided as a single line or multiline string

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

## Health Check Endpoints

The application exposes Spring Boot Actuator health endpoints for Kubernetes health probes:

### Available Endpoints

- **`/actuator/health`**: Overall application health status
- **`/actuator/health/liveness`**: Liveness probe endpoint for Kubernetes
- **`/actuator/health/readiness`**: Readiness probe endpoint for Kubernetes

### Health Check Responses

**Healthy Response (HTTP 200):**
```json
{
  "status": "UP"
}
```

**Unhealthy Response (HTTP 503):**
```json
{
  "status": "DOWN"
}
```

### Kubernetes Configuration Example

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: authentication-service
spec:
  containers:
  - name: auth-service
    image: authentication-service:latest
    ports:
    - containerPort: 8080
    livenessProbe:
      httpGet:
        path: /actuator/health/liveness
        port: 8080
      initialDelaySeconds: 30
      periodSeconds: 10
      timeoutSeconds: 5
      failureThreshold: 3
    readinessProbe:
      httpGet:
        path: /actuator/health/readiness
        port: 8080
      initialDelaySeconds: 10
      periodSeconds: 5
      timeoutSeconds: 3
      failureThreshold: 3
```

### Notes

- Health endpoints are publicly accessible (no authentication required)
- Detailed health information is only shown to authenticated users
- Liveness probe checks if the application is running
- Readiness probe checks if the application is ready to accept traffic
