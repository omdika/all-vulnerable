# Changelog

All notable changes to the All-Vulnerable Framework will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2026-01-28

### Added
- **SBOM (Software Bill of Materials) tool support**:
  - Added support for Syft, Trivy-SBOM, cdxgen-cbom, cdxgen-mlbom
  - New `src/sbom/` directory with SBOM-specific vulnerabilities
  - Dependency files with license compliance issues and known CVEs
  - SBOM configuration files with security issues
  - Example vulnerable SBOM in SPDX format

- **New vulnerability types for SBOM tools**:
  - Package dependencies with specific CVEs (CVE-2017-16016, CVE-2018-3728, etc.)
  - License compliance issues (GPL, AGPL, proprietary licenses)
  - Missing or incorrect license information
  - Typosquatting and supply chain attack examples
  - Unpinned dependencies and version conflicts
  - Incomplete or inaccurate SBOMs
  - SBOM configuration and storage vulnerabilities

- **Updated documentation**:
  - Added SBOM tools to README.md supported tools list
  - Updated SAST_TOOL_COVERAGE.md with SBOM tool coverage
  - Enhanced USAGE.md with SBOM testing instructions
  - Added README-SBOM.md specific to SBOM vulnerabilities

- **New files in `src/sbom/`**:
  - `package-sbom.json` - Node.js package with SBOM-detectable vulnerabilities
  - `requirements-sbom.txt` - Python requirements with license/CVE issues
  - `pom-sbom.xml` - Maven POM with license compliance issues
  - `go.mod.sbom` - Go module with replace/exclude vulnerabilities
  - `Dockerfile-sbom` - Dockerfile with SBOM-relevant issues
  - `syft-config.yaml` - Syft configuration with security issues
  - `vulnerable-sbom.spdx.json` - Example SBOM with vulnerabilities
  - `README-SBOM.md` - SBOM-specific documentation

### Updated
- Enhanced existing dependency files with more SBOM-detectable issues
- Updated documentation structure to include SBOM tools
- Improved vulnerability categorization for dependency analysis

### Notes
- SBOM tools complement traditional SAST by focusing on supply chain security
- Framework now covers both code vulnerabilities and dependency vulnerabilities
- Supports software supply chain security testing and compliance checking

## [1.0.0] - 2026-01-28

### Added
- Initial release of All-Vulnerable Framework
- Comprehensive vulnerability examples in 8 programming languages:
  - Java: SQLi, Command Injection, XSS, Path Traversal, Insecure Deserialization, Weak Crypto, Race Conditions, Buffer Issues
  - Python: SQLi, Command Injection, XSS, Path Traversal, Template Injection, Pickle deserialization, Weak Crypto
  - JavaScript/Node.js: SQLi, Command Injection, XSS, Prototype Pollution, ReDoS, SSRF, JSON Injection
  - C/C++: Buffer Overflow, Format String, Integer Overflow, Use-After-Free, Memory Leaks
  - Go: SQLi, Command Injection, Path Traversal, XSS
  - PHP: SQLi, Command Injection, XSS, File Inclusion, Unserialize
  - Ruby: SQLi, Command Injection, ERB Injection, YAML/Marshal deserialization
  - C#/.NET: SQLi, Command Injection, XSS, Path Traversal, Insecure Deserialization

- Infrastructure as Code vulnerabilities:
  - Docker: Latest tags, root user, hardcoded secrets, exposed ports
  - Terraform: Hardcoded credentials, public resources, wildcard permissions
  - Kubernetes: Privileged containers, root user, hardcoded secrets

- Dependency vulnerability examples:
  - package.json with outdated/vulnerable packages
  - requirements.txt with unpinned dependencies
  - pom.xml with old library versions

- Documentation:
  - README.md with project overview
  - SAST_TOOL_COVERAGE.md detailing vulnerability coverage
  - USAGE.md with testing instructions
  - DISCLAIMER.md with security warnings
  - CONTRIBUTING.md with contribution guidelines

### Security
- All code is intentionally vulnerable for testing purposes
- Comprehensive security warnings and disclaimers
- Educational use only design

### Notes
- This is the initial release for SAST tool testing
- Framework designed for security researchers and tool evaluators
- Not for production use under any circumstances