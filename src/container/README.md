# Container Security Vulnerabilities

This directory contains examples of vulnerabilities and misconfigurations detectable by container security tools:

## Supported Container Security Tools

1. **Checkov Container** - Infrastructure as Code scanning for containers (Dockerfile, Kubernetes, etc.)
2. **Grype** - Vulnerability scanner for container images and filesystems
3. **Trivy-Container** - Comprehensive vulnerability scanning for containers
4. **KICS Container** - Infrastructure as Code security scanning with container focus

## Vulnerability Types for Container Security Tools

### 1. Image Vulnerabilities (Grype, Trivy-Container)
- **Packages with known CVEs**: Outdated system packages with security vulnerabilities
- **Vulnerable dependencies**: Python pip packages, npm packages with CVEs
- **Unpatched software**: Old versions of critical software (OpenSSL, glibc, etc.)
- **Malicious packages**: Packages from untrusted repositories

### 2. Configuration Vulnerabilities (Checkov Container, KICS Container)
- **Privilege escalation**: Running containers as root, privileged mode
- **Exposed secrets**: Hardcoded credentials in environment variables or files
- **Network exposure**: Exposing unnecessary ports, using host networking
- **Resource mismanagement**: No resource limits, no health checks
- **Security feature disabling**: Disabling seccomp, AppArmor, or no-new-privileges
- **Volume vulnerabilities**: Mounting sensitive host directories

### 3. Build-Time Vulnerabilities
- **Base image issues**: Using latest tags, outdated base images
- **Build context problems**: Copying all files without .dockerignore
- **Unnecessary packages**: Installing debug tools, unnecessary software
- **Permission issues**: World-writable files, incorrect file ownership

### 4. Runtime Vulnerabilities
- **Orchestration misconfigurations**: docker-compose, Kubernetes misconfigurations
- **Networking issues**: Lack of network policies, exposed services
- **Storage issues**: Unencrypted volumes, improper backup configuration
- **Monitoring gaps**: Lack of logging, monitoring, and auditing

## Files in This Directory

### Configuration Files
- `Dockerfile-container-vulnerable` - Dockerfile with comprehensive container vulnerabilities
- `docker-compose-vulnerable.yml` - docker-compose file with orchestration vulnerabilities

### Testing Files
- `.dockerignore-missing` - Example of missing .dockerignore (vulnerability context)
- `secrets/` - Directory containing example secret files (for volume mounting tests)

## Testing Container Security Tools

### 1. Checkov Container
```bash
# Scan Dockerfile
checkov --file src/container/Dockerfile-container-vulnerable

# Scan docker-compose file
checkov --file src/container/docker-compose-vulnerable.yml
```

### 2. Grype
```bash
# Scan a built container image
grype myimage:latest

# Scan a Dockerfile (requires building first)
docker build -f src/container/Dockerfile-container-vulnerable -t test-container .
grype test-container:latest

# Scan a directory
grype dir:src/container/
```

### 3. Trivy-Container
```bash
# Scan container image
trivy image myimage:latest

# Scan Dockerfile
trivy config src/container/Dockerfile-container-vulnerable

# Scan with specific scanners
trivy image --scanners vuln,secret,config myimage:latest
```

### 4. KICS Container
```bash
# Scan container configurations
kics scan -p src/container/

# Scan specific files
kics scan -p src/container/Dockerfile-container-vulnerable
```

## Expected Findings

### Grype Should Detect:
- CVEs in system packages (OpenSSL, glibc, bash, etc.)
- Vulnerable Python packages (Django, Flask, requests, etc.)
- Vulnerable npm packages (lodash, minimist, hoek, etc.)
- Outdated software versions

### Trivy-Container Should Detect:
- CVEs in container images
- Misconfigurations in Dockerfile
- Exposed secrets in images
- License compliance issues
- IAC misconfigurations

### Checkov Container Should Detect:
- Running containers as root
- Privileged mode enabled
- Exposed ports and services
- Missing resource limits
- Security feature disabling
- Hardcoded secrets

### KICS Container Should Detect:
- Dockerfile misconfigurations
- docker-compose security issues
- Container orchestration vulnerabilities
- Infrastructure as code security gaps

## Container Security Best Practices

### 1. Image Security
- Use minimal base images (Alpine, Distroless)
- Pin versions for all packages and dependencies
- Regularly update and patch containers
- Scan images in CI/CD pipeline

### 2. Configuration Security
- Run containers as non-root users
- Disable privileged mode and unnecessary capabilities
- Implement resource limits and quotas
- Use read-only filesystems where possible
- Enable security features (seccomp, AppArmor)

### 3. Secret Management
- Never hardcode secrets in Dockerfiles
- Use secret management tools (Docker Secrets, Kubernetes Secrets, Vault)
- Rotate secrets regularly
- Limit secret access with least privilege

### 4. Network Security
- Use network policies to restrict traffic
- Avoid host networking when possible
- Encrypt network traffic
- Implement service mesh for advanced security

### 5. Runtime Security
- Implement health checks and readiness probes
- Configure proper logging and monitoring
- Use runtime security tools (Falco, Sysdig)
- Regularly audit container configurations

## Integration with Other Security Tools

Container security tools complement:
- **SAST Tools**: Code-level vulnerabilities in application code
- **SCA Tools**: Dependency vulnerabilities in application dependencies
- **IaC Tools**: Infrastructure configuration vulnerabilities
- **Runtime Security**: Behavioral analysis and threat detection

## References

- [Checkov Documentation](https://www.checkov.io/)
- [Grype Documentation](https://github.com/anchore/grype)
- [Trivy Documentation](https://github.com/aquasecurity/trivy)
- [KICS Documentation](https://kics.io/)
- [Docker Security Best Practices](https://docs.docker.com/develop/security/)
- [OWASP Container Security](https://owasp.org/www-project-container-security/)