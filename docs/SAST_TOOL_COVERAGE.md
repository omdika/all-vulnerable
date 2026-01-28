# SAST Tool Coverage Guide

This document outlines the vulnerabilities included in this framework and which SAST tools should detect them.

## Vulnerability Categories

### 1. Injection Flaws

#### SQL Injection
- **Location**: All language directories
- **Examples**:
  - String concatenation in SQL queries
  - ORDER BY injection
  - Multiple parameter injection
- **SAST Tools**: Checkmarx, Fortify, CodeQL, GitLab SAST, SonarQube, Kiuwan

#### Command Injection
- **Location**: `java/CommandInjection.java`, `python/command_injection.py`, `javascript/command_injection.js`
- **Examples**:
  - `Runtime.exec()` with user input
  - `subprocess.call()` with `shell=True`
  - `eval()` and `exec()` functions
- **SAST Tools**: Checkmarx, Fortify, Bandit (Python), CodeQL, SonarQube

#### NoSQL Injection
- **Location**: `javascript/sql_injection.js` (MongoDB example)
- **SAST Tools**: Checkmarx, Fortify, CodeQL

### 2. Broken Authentication

#### Hardcoded Credentials
- **Location**: All language files with hardcoded passwords
- **Examples**:
  - `DB_PASSWORD = "password123"`
  - Hardcoded API keys
  - Encryption keys in source code
- **SAST Tools**: Checkmarx, Fortify, GitLab SAST, Bandit, SonarQube

#### Weak Session Management
- **Location**: `java/WebVulnerabilities.java`, `javascript/web_vulnerabilities.js`
- **Examples**:
  - Missing HttpOnly flag on cookies
  - Session fixation
  - Weak session IDs
- **SAST Tools**: Checkmarx, Fortify, SonarQube

### 3. Sensitive Data Exposure

#### Weak Cryptography
- **Location**: `java/WeakCryptography.java`, `python/crypto_weaknesses.py`
- **Examples**:
  - MD5 and SHA-1 usage
  - DES encryption
  - ECB mode
  - Weak random number generation
- **SAST Tools**: Checkmarx, Fortify, Bandit, SonarQube

#### Information in Error Messages
- **Location**: (To be added) Stack trace exposure
- **SAST Tools**: Checkmarx, Fortify, SonarQube

### 4. XML External Entities (XXE)

#### XXE Vulnerabilities
- **Location**: `python/web_vulnerabilities.py` (placeholder)
- **Examples**:
  - XML parsing with external entities
- **SAST Tools**: Checkmarx, Fortify, CodeQL, SonarQube

### 5. Broken Access Control

#### Path Traversal
- **Location**: `java/PathTraversal.java`, `python/path_traversal.py`, `javascript/command_injection.js`
- **Examples**:
  - File operations with user-controlled paths
  - Lack of path validation
- **SAST Tools**: Checkmarx, Fortify, Bandit, CodeQL, SonarQube

#### Insecure Direct Object References
- **Location**: (Implicit in various examples)
- **SAST Tools**: Checkmarx, Fortify, SonarQube

### 6. Security Misconfiguration

#### Infrastructure Vulnerabilities
- **Location**: `src/infrastructure/`
- **Examples**:
  - Dockerfile with latest tags, root user
  - Terraform with public resources, hardcoded credentials
  - Kubernetes with privileged containers
- **SAST Tools**: Checkov, GitLab SAST

#### Dependency Vulnerabilities
- **Location**: `package.json`, `requirements.txt`
- **Examples**:
  - Outdated packages with known vulnerabilities
  - Unpinned versions
  - Malicious packages
- **SAST Tools**: GitLab SAST, Black Duck, SonarQube

### 7. Cross-Site Scripting (XSS)

#### XSS Vulnerabilities
- **Location**: `java/WebVulnerabilities.java`, `python/web_vulnerabilities.py`, `javascript/web_vulnerabilities.js`
- **Examples**:
  - Reflected XSS
  - Stored XSS
  - DOM-based XSS
- **SAST Tools**: Checkmarx, Fortify, CodeQL, GitLab SAST, SonarQube

### 8. Insecure Deserialization

#### Deserialization Vulnerabilities
- **Location**: `java/InsecureDeserialization.java`, `python/command_injection.py` (pickle), `ruby/sql_injection.rb` (Marshal/YAML)
- **Examples**:
  - Java `ObjectInputStream` with untrusted data
  - Python `pickle.loads()`
  - Ruby `Marshal.load()` and `YAML.load()`
- **SAST Tools**: Checkmarx, Fortify, Bandit, CodeQL, SonarQube

### 9. Using Components with Known Vulnerabilities

#### Dependency Management
- **Location**: `package.json`, `requirements.txt`
- **Examples**:
  - Old versions of Express, Django, etc.
  - Packages with known CVEs
- **SAST Tools**: GitLab SAST, Black Duck, OWASP Dependency Check, SonarQube

### 10. Insufficient Logging & Monitoring

#### Logging Issues
- **Location**: (To be added) Lack of security logging
- **SAST Tools**: Checkmarx, Fortify, SonarQube

## Language-Specific Coverage

### Java
- **Files**: `src/java/*.java`
- **Vulnerabilities**: SQLi, Command Injection, XSS, Path Traversal, Insecure Deserialization, Weak Crypto, Race Conditions
- **SAST Tools**: Checkmarx, Fortify, Coverity, SonarQube, Kiuwan

### Python
- **Files**: `src/python/*.py`
- **Vulnerabilities**: SQLi, Command Injection, XSS, Path Traversal, Template Injection, Pickle deserialization, Weak Crypto
- **SAST Tools**: Bandit, Checkmarx, Fortify, CodeQL, GitLab SAST, SonarQube

### JavaScript/Node.js
- **Files**: `src/javascript/*.js`
- **Vulnerabilities**: SQLi, Command Injection, XSS, Prototype Pollution, ReDoS, SSRF, JSON Injection
- **SAST Tools**: Checkmarx, Fortify, CodeQL, GitLab SAST, SonarQube

### C/C++
- **Files**: `src/cpp/*.cpp`
- **Vulnerabilities**: Buffer Overflow, Format String, Integer Overflow, Use-After-Free, Memory Leaks
- **SAST Tools**: Coverity, Fortify, Checkmarx, CodeQL

### Go
- **Files**: `src/go/*.go`
- **Vulnerabilities**: SQLi, Command Injection, Path Traversal, XSS
- **SAST Tools**: Checkmarx, Fortify, CodeQL, SonarQube

### PHP
- **Files**: `src/php/*.php`
- **Vulnerabilities**: SQLi, Command Injection, XSS, File Inclusion, Unserialize
- **SAST Tools**: Checkmarx, Fortify, CodeQL, SonarQube

### Ruby
- **Files**: `src/ruby/*.rb`
- **Vulnerabilities**: SQLi, Command Injection, ERB Injection, YAML/Marshal deserialization
- **SAST Tools**: Checkmarx, Fortify, CodeQL, SonarQube

### C#/.NET
- **Files**: `src/csharp/*.cs`
- **Vulnerabilities**: SQLi, Command Injection, XSS, Path Traversal, Insecure Deserialization
- **SAST Tools**: Checkmarx, Fortify, Coverity, SonarQube

## Infrastructure as Code

### Docker
- **Files**: `src/infrastructure/Dockerfile.vulnerable`
- **Vulnerabilities**: Latest tags, root user, hardcoded secrets, exposed ports
- **SAST Tools**: Checkov, GitLab SAST

### Terraform
- **Files**: `src/infrastructure/terraform.vulnerable.tf`
- **Vulnerabilities**: Hardcoded credentials, public resources, wildcard permissions
- **SAST Tools**: Checkov, GitLab SAST

### Kubernetes
- **Files**: `src/infrastructure/kubernetes.vulnerable.yaml`
- **Vulnerabilities**: Privileged containers, root user, hardcoded secrets
- **SAST Tools**: Checkov, GitLab SAST

## Testing Recommendations

1. **Run SAST Tools** against each language directory
2. **Compare Results** across different tools
3. **Verify Detection Rates** for each vulnerability type
4. **Check False Positives/Negatives**
5. **Test Infrastructure Scanning** with Checkov or similar

## Notes

- Some vulnerabilities may be detected by multiple tools
- Detection capabilities vary by tool and configuration
- This framework is for testing and education only
- Always run in isolated environments