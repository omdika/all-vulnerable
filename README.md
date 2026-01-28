# All-Vulnerable Framework

A comprehensive framework containing deliberately vulnerable code examples for testing and evaluating Static Application Security Testing (SAST) tools.

## Purpose

This framework is designed for educational and testing purposes only. It contains intentionally vulnerable code snippets to help:
- Security researchers test SAST tool capabilities
- Developers learn about common security vulnerabilities
- Organizations evaluate SAST tool effectiveness
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
└── sbom/           - SBOM and dependency vulnerabilities
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

### Example Commands:
```bash
# Generate SBOM with Syft
syft dir:src/sbom/ -o spdx-json > sbom.json

# Scan SBOM with Trivy
trivy sbom sbom.json

# Generate CycloneDX BOM with cdxgen
cdxgen -o bom.cdx.json src/sbom/
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