# Changelog

All notable changes to the All-Vulnerable Framework will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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