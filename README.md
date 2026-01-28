# All-Vulnerable Framework

A comprehensive framework containing deliberately vulnerable code examples for testing and evaluating security tools including Static Application Security Testing (SAST), Software Composition Analysis (SCA), Software Bill of Materials (SBOM), and Infrastructure as Code (IaC) security tools.

## Purpose

This framework is designed for educational and testing purposes only. It contains intentionally vulnerable code snippets to help:
- Security researchers test SAST, SCA, SBOM, and IaC tool capabilities
- Developers learn about common security vulnerabilities
- Organizations evaluate security tool effectiveness
- Security training and awareness programs

## Supported Security Tools

This framework includes vulnerabilities detectable by:

### SAST (Static Application Security Testing) Tools:
- Checkmarx SAST
- Fortify Static Code Analyzer
- GitHub CodeQL
- GitLab SAST
- Opengrep
- Bandit (Python)
- Coverity Static Analysis / Black Duck
- DevSkim
- Checkov SAST
- Kiuwan
- SonarQube

### SBOM (Software Bill of Materials) Tools:
- Syft - SBOM generation and analysis
- Trivy-SBOM - Vulnerability scanning for SBOMs
- cdxgen-cbom - CycloneDX SBOM generation
- cdxgen-mlbom - Multi-language BOM generation

### SCA (Software Composition Analysis) Tools:
- Black Duck - Open source security and license compliance
- Checkmarx SCA - Dependency vulnerability scanning
- GitHub Dependabot - Automated dependency updates and security alerts
- GitLab Dependency Scanning - Integrated dependency scanning in CI/CD
- DustiLock - Supply chain security and dependency confusion detection
- OWASP Dependency-Check - Dependency vulnerability analysis
- Trivy-SCA - Vulnerability scanning for dependencies

### IaC (Infrastructure as Code) Security Tools:
- Checkov - Terraform, Kubernetes, CloudFormation, ARM templates
- Terrascan - Terraform, Kubernetes, CloudFormation, Dockerfile
- TFLint - Terraform linting and security checking
- Hadolint - Dockerfile security linter
- KICS (Keeping Infrastructure as Code Secure) - Multi-IaC scanning
- GitLab SAST IaC - Integrated IaC scanning in CI/CD
- Snyk IaC - Infrastructure as Code security scanning

## Security Warning

⚠️ **WARNING**: This framework contains deliberately vulnerable code.
- DO NOT deploy this code in production environments
- DO NOT use this code for any purpose other than security testing and education
- This code is for RESEARCH and EDUCATIONAL purposes only
- Always run this code in isolated, controlled environments

## Structure

```
src/
├── java/           - Java vulnerabilities
├── python/         - Python vulnerabilities
├── javascript/     - JavaScript/Node.js vulnerabilities
├── cpp/            - C/C++ vulnerabilities
├── go/             - Go vulnerabilities
├── php/            - PHP vulnerabilities
├── ruby/           - Ruby vulnerabilities
├── csharp/         - C#/.NET vulnerabilities
├── infrastructure/ - Infrastructure as Code vulnerabilities
├── sbom/           - SBOM and dependency vulnerabilities
└── sca/            - SCA (Software Composition Analysis) vulnerabilities
tests/              - Test cases for vulnerabilities
docs/               - Documentation
```

## Vulnerability Categories

### Application Security Vulnerabilities:
- Injection flaws (SQL, NoSQL, OS command, LDAP)
- Broken Authentication and Session Management
- Sensitive Data Exposure
- XML External Entities (XXE)
- Broken Access Control
- Security Misconfiguration
- Cross-Site Scripting (XSS)
- Insecure Deserialization
- Using Components with Known Vulnerabilities
- Insufficient Logging & Monitoring

### SBOM and Dependency Vulnerabilities:
- Outdated packages with known CVEs
- License compliance issues (GPL, AGPL, proprietary)
- Missing or incorrect license information
- Typosquatting and supply chain attacks
- Unpinned dependencies and version conflicts
- Incomplete or inaccurate SBOMs
- SBOM configuration and storage issues

### SCA (Software Composition Analysis) Vulnerabilities:
- License compliance and legal risks (Black Duck)
- Risky dependency patterns (Checkmarx SCA)
- Automated security updates (GitHub Dependabot)
- CI/CD integrated scanning (GitLab Dependency Scanning)
- Supply chain security and dependency confusion (DustiLock)
- CVE detection across multiple languages (OWASP Dependency-Check)
- Comprehensive vulnerability scanning (Trivy-SCA)

### IaC (Infrastructure as Code) Vulnerabilities:
- Hardcoded secrets and credentials in configuration files
- Publicly accessible resources and open security groups
- Overly permissive IAM roles and policies
- Missing encryption for data at rest and in transit
- Use of deprecated or insecure API versions
- Resource misconfiguration and security best practices violations
- Container security issues (root user, latest tags, exposed ports)

## Usage

### For SAST Tools:
1. Clone this repository
2. Navigate to the language directory of interest
3. Run your SAST tool against the code
4. Review the findings

### For SBOM Tools:
1. Navigate to `src/sbom/` directory
2. Generate SBOMs using Syft, cdxgen, or Trivy
3. Scan SBOMs for vulnerabilities and compliance issues
4. Validate SBOM quality and completeness

### For SCA Tools:
1. Navigate to `src/sca/` directory
2. Test each SCA tool against its specific vulnerable file
3. Review detection of license issues, CVEs, and supply chain risks
4. Compare tool capabilities and coverage

### For IaC Tools:
1. Navigate to `src/infrastructure/` directory
2. Test IaC security tools against Terraform, Kubernetes, Docker, and CloudFormation files
3. Review detection of misconfigurations, hardcoded secrets, and security violations
4. Compare tool coverage across different IaC languages

### Example Commands:
```bash
# Generate SBOM with Syft
syft dir:src/sbom/ -o spdx-json > sbom.json

# Scan SBOM with Trivy
trivy sbom sbom.json

# Generate CycloneDX BOM with cdxgen
cdxgen -o bom.cdx.json src/sbom/

# Scan dependencies with OWASP Dependency-Check
dependency-check --project test --scan src/sca/dependency-check-vulnerable.json

# Scan with Trivy SCA
trivy fs --scanners vuln,license,secret src/sca/

# Scan IaC with Checkov
checkov -d src/infrastructure/

# Scan Terraform with TFLint
tflint src/infrastructure/terraform.vulnerable.tf

# Scan Dockerfile with Hadolint
hadolint src/infrastructure/Dockerfile.vulnerable

# Scan multi-IaC with KICS
kics scan -p src/infrastructure/
```

## Contributing

Contributions are welcome! Please ensure that any added vulnerabilities:
1. Are clearly documented
2. Include comments explaining the vulnerability
3. Follow the existing structure
4. Are for educational purposes only

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Disclaimer

This software is provided "as is", without warranty of any kind. The authors are not responsible for any misuse of this code.