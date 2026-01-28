# SCA (Software Composition Analysis) Vulnerabilities

This directory contains examples of vulnerabilities and misconfigurations detectable by SCA tools:

## Supported SCA Tools

1. **Black Duck** - Open source security and license compliance
2. **Checkmarx SCA** - Dependency vulnerability scanning
3. **GitHub Dependabot** - Automated dependency updates and security alerts
4. **GitLab Dependency Scanning** - Integrated dependency scanning in CI/CD
5. **DustiLock** - Supply chain security and dependency confusion detection
6. **OWASP Dependency-Check** - Dependency vulnerability analysis
7. **Trivy-SCA** - Vulnerability scanning for dependencies

## Vulnerability Types for SCA Tools

### 1. Dependency Vulnerabilities
- **Known CVEs**: Packages with publicly disclosed vulnerabilities
- **License compliance issues**: GPL, AGPL, or proprietary licenses in commercial projects
- **Deprecated/abandoned packages**: Packages no longer maintained
- **Outdated packages**: Old versions with security fixes available

### 2. Supply Chain Vulnerabilities
- **Typosquatting packages**: Malicious packages with similar names to popular packages
- **Dependency confusion**: Private package names published publicly
- **Malicious packages**: Packages with backdoors or malicious code
- **Untrusted sources**: Packages from unofficial repositories or Git URLs without commit hashes

### 3. Configuration Vulnerabilities
- **Unpinned versions**: Using wildcards (*, latest) or version ranges
- **Local dependencies**: File:// or local path dependencies
- **Development dependencies in production**: Test and dev tools in production requirements
- **Missing or incorrect license declarations**: No license or non-SPDX compliant licenses

## Files in This Directory

### Tool-Specific Files
- `blackduck-vulnerable.json` - Node.js package.json with license compliance issues and CVEs for Black Duck
- `checkmarx-sca-vulnerable.json` - Node.js package.json with risky patterns for Checkmarx SCA
- `dependabot-vulnerable.json` - Node.js package.json with CVEs from GitHub Advisory Database
- `gitlab-dependency-vulnerable.json` - Node.js package.json for GitLab Dependency Scanning
- `gitlab-ci-example.yml` - GitLab CI configuration with dependency scanning misconfigurations
- `dustilock-vulnerable.json` - Node.js package.json with supply chain vulnerabilities for DustiLock
- `dependency-check-vulnerable-pom.xml` - Maven POM with CVEs for OWASP Dependency-Check
- `dependency-check-vulnerable.json` - Node.js package.json for OWASP Dependency-Check
- `trivy-sca-vulnerable.json` - Node.js package.json for Trivy SCA

### Multi-Language Files
- `requirements-sca.txt` - Python requirements with SCA-detectable vulnerabilities

## Testing SCA Tools

### 1. Black Duck
```bash
# Scan for vulnerabilities and license issues
blackduck-cli scan src/sca/blackduck-vulnerable.json
```

### 2. Checkmarx SCA
```bash
# Scan dependencies for vulnerabilities
checkmarx-scanner --source src/sca/checkmarx-sca-vulnerable.json
```

### 3. GitHub Dependabot
```bash
# Dependabot runs automatically on GitHub
# Place dependabot-vulnerable.json in a GitHub repo to test
```

### 4. GitLab Dependency Scanning
```bash
# GitLab CI will automatically scan dependencies
# Use gitlab-ci-example.yml as a template
```

### 5. DustiLock
```bash
# Scan for dependency confusion and typosquatting
dustilock scan src/sca/dustilock-vulnerable.json
```

### 6. OWASP Dependency-Check
```bash
# Scan Java dependencies
dependency-check --project test --scan src/sca/dependency-check-vulnerable-pom.xml

# Scan Node.js dependencies
dependency-check --project test --scan src/sca/dependency-check-vulnerable.json
```

### 7. Trivy-SCA
```bash
# Scan dependencies with Trivy
trivy fs --scanners vuln,license,secret src/sca/
```

## Expected Findings

### Black Duck Should Detect:
- License compliance issues (GPL, AGPL, proprietary)
- Known CVEs in dependencies
- Out-of-support packages
- License conflicts and ambiguities

### Checkmarx SCA Should Detect:
- Direct Git references without commit hashes
- Local file dependencies
- Unpinned versions (wildcards, latest)
- Packages with known vulnerabilities
- Typosquatting packages

### GitHub Dependabot Should Detect:
- CVEs from GitHub Advisory Database
- Outdated dependencies with available fixes
- Dependency confusion risks
- Transitive vulnerabilities

### GitLab Dependency Scanning Should Detect:
- CVEs in dependencies
- License issues
- Unpinned versions
- Packages from untrusted sources

### DustiLock Should Detect:
- Typosquatting packages
- Dependency confusion (private package names)
- Packages with known malicious code
- Suspicious post-install scripts

### OWASP Dependency-Check Should Detect:
- CVEs from NVD database
- Vulnerable transitive dependencies
- Outdated package versions
- System scope dependencies

### Trivy-SCA Should Detect:
- CVEs from multiple sources (OSV, NVD, etc.)
- License issues
- Unpinned versions
- Deprecated packages

## SCA Quality Issues

1. **False Positives**: Legitimate packages flagged due to name similarity
2. **False Negatives**: Missed vulnerabilities due to incomplete databases
3. **License Misidentification**: Incorrect license detection
4. **Transitive Dependency Blindness**: Missing vulnerabilities in nested dependencies
5. **Version Pin Confusion**: Misinterpreting version specifiers

## Security Considerations

⚠️ **SCA Tools Have Limitations**:
- Cannot detect zero-day vulnerabilities
- May miss custom or private package vulnerabilities
- License detection may be inaccurate
- Supply chain attacks can evade detection
- Always use multiple tools for defense in depth

## Best Practices

1. **Pin All Dependencies**: Use exact versions, not ranges or wildcards
2. **Use Trusted Sources**: Only use official package registries
3. **Regular Updates**: Update dependencies regularly with vulnerability scanning
4. **License Compliance**: Monitor and enforce license policies
5. **Supply Chain Security**: Use tools like DustiLock to detect dependency confusion
6. **CI/CD Integration**: Integrate SCA into your build pipeline
7. **Multiple Tools**: Use complementary SCA tools for better coverage

## Integration with SAST Tools

SCA tools complement traditional SAST by:
1. Providing dependency context for code vulnerabilities
2. Enabling software supply chain security
3. Supporting license compliance monitoring
4. Facilitating vulnerability management across dependencies
5. Detecting risks in third-party code

## References

- [Black Duck Documentation](https://www.blackducksoftware.com/)
- [Checkmarx SCA Documentation](https://checkmarx.com/)
- [GitHub Dependabot Documentation](https://docs.github.com/en/code-security/dependabot)
- [GitLab Dependency Scanning](https://docs.gitlab.com/ee/user/application_security/dependency_scanning/)
- [DustiLock Documentation](https://github.com/Checkmarx/dustilock)
- [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/)
- [Trivy Documentation](https://github.com/aquasecurity/trivy)