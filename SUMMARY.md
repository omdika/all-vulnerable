# All-Vulnerable Framework - Summary

## Overview

A comprehensive framework containing deliberately vulnerable code examples for testing and evaluating Static Application Security Testing (SAST) tools.

## Quick Start

```bash
# Clone the repository
git clone https://github.com/example/all-vulnerable.git
cd all-vulnerable

# Test a SAST tool against Java code
# (Replace with your SAST tool command)
your-sast-tool scan src/java/

# Test against Python code
bandit -r src/python/

# Test infrastructure as code
checkov -d src/infrastructure/
```

## Key Features

### 📚 Multi-Language Coverage
- **Java**: SQLi, Command Injection, XSS, Path Traversal, Insecure Deserialization
- **Python**: SQLi, Command Injection, XSS, Template Injection, Pickle deserialization
- **JavaScript**: SQLi, Command Injection, XSS, Prototype Pollution, SSRF
- **C/C++**: Buffer Overflow, Format String, Memory Corruption
- **Go**: SQLi, Command Injection, Path Traversal
- **PHP**: SQLi, Command Injection, XSS, File Inclusion
- **Ruby**: SQLi, Command Injection, ERB Injection
- **C#**: SQLi, Command Injection, XSS, Insecure Deserialization

### 🏗️ Infrastructure as Code
- **Docker**: Latest tags, root user, hardcoded secrets
- **Terraform**: Public resources, hardcoded credentials, wildcard permissions
- **Kubernetes**: Privileged containers, root user, exposed secrets

### 📦 Dependency Management
- **package.json**: Outdated packages, unpinned versions
- **requirements.txt**: Vulnerable dependencies, unpinned versions
- **pom.xml**: Old library versions with known vulnerabilities

## Supported SAST Tools

| Tool | Primary Languages | Infrastructure | Dependencies |
|------|-------------------|----------------|--------------|
| Checkmarx | Java, Python, JS, C#, etc. | Limited | Limited |
| Fortify | Java, .NET, C++, etc. | Limited | Limited |
| CodeQL | Java, Python, JS, C++, etc. | No | Limited |
| GitLab SAST | Multiple languages | Yes | Yes |
| Opengrep | Pattern matching | No | No |
| Bandit | Python only | No | No |
| Coverity | C/C++, Java | No | Yes (Black Duck) |
| DevSkim | Multiple languages | No | No |
| Checkov | Terraform, Kubernetes, Docker | Yes | No |
| Kiuwan | Java, .NET, etc. | Limited | Limited |
| SonarQube | Multiple languages | Limited | Yes |

## Vulnerability Catalog

### OWASP Top 10 Coverage

1. **Injection**: SQLi, Command Injection, NoSQLi, LDAPi
2. **Broken Authentication**: Hardcoded credentials, weak session management
3. **Sensitive Data Exposure**: Weak crypto, information leakage
4. **XXE**: XML External Entities
5. **Broken Access Control**: Path traversal, insecure direct object references
6. **Security Misconfiguration**: Infrastructure vulnerabilities
7. **XSS**: Reflected, Stored, DOM-based
8. **Insecure Deserialization**: Java, Python, Ruby, C#
9. **Using Vulnerable Components**: Dependency vulnerabilities
10. **Insufficient Logging**: (Placeholder)

### Additional Vulnerabilities
- Buffer overflow (C/C++)
- Format string (C/C++)
- Race conditions (Java)
- Prototype pollution (JavaScript)
- ReDoS (JavaScript)
- SSRF (JavaScript, Python)
- Template injection (Python, Ruby)

## Documentation Structure

- `README.md` - Project overview and getting started
- `SAST_TOOL_COVERAGE.md` - Detailed vulnerability mapping
- `USAGE.md` - Testing instructions and examples
- `DISCLAIMER.md` - Critical security warnings
- `CONTRIBUTING.md` - How to contribute
- `CHANGELOG.md` - Version history
- `CONTRIBUTORS.md` - Contributor list

## Security Warning

⚠️ **CRITICAL**: This framework contains intentionally vulnerable code.
- **DO NOT** deploy in production
- **DO NOT** use real credentials
- **USE** isolated test environments only
- **FOR** educational and testing purposes only

## Use Cases

1. **SAST Tool Evaluation**: Compare detection capabilities
2. **Security Training**: Learn vulnerability patterns
3. **Research**: Study static analysis techniques
4. **Tool Development**: Test new detection rules
5. **Education**: Teach secure coding practices

## Getting Help

- Review documentation in `docs/` directory
- Check `SAST_TOOL_COVERAGE.md` for tool-specific guidance
- Submit issues for questions or problems

## License

MIT License - See [LICENSE](LICENSE) file for details.

---

**Remember**: This framework is for making software more secure by testing security tools, not for attacking systems.