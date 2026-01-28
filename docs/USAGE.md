# Usage Guide

This guide explains how to use the All-Vulnerable Framework to test SAST and SBOM tools.

## Prerequisites

- Git
- SAST tools you want to test (Checkmarx, Fortify, SonarQube, etc.)
- SBOM tools (Syft, Trivy, cdxgen, etc.) for dependency analysis
- Language-specific runtimes (Java, Python, Node.js, etc.) as needed

## Getting Started

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd all-vulnerable
   ```

2. Explore the directory structure:
   ```bash
   tree src/
   ```

## Testing SAST Tools

### General Approach

1. **Select a language directory** you want to test
2. **Run your SAST tool** against that directory
3. **Review the findings** and compare with expected vulnerabilities
4. **Repeat** for other languages and tools

### Example Commands

#### For Java projects:
```bash
# Checkmarx example (command varies by installation)
cx scan -s src/java -C "Java Vulnerabilities"

# SonarQube example
sonar-scanner -Dsonar.projectKey=all-vulnerable-java -Dsonar.sources=src/java
```

#### For Python projects:
```bash
# Bandit
bandit -r src/python/

# Safety (for dependencies)
safety check -r src/python/requirements.txt
```

#### For JavaScript projects:
```bash
# npm audit (for dependencies)
cd src/javascript && npm audit

# ESLint with security plugins
eslint src/javascript/
```

#### For Infrastructure as Code:
```bash
# Checkov
checkov -d src/infrastructure/

# Terrascan
terrascan scan -d src/infrastructure/
```

### For SBOM Tools:
```bash
# Generate SBOM with Syft
syft dir:src/sbom/ -o spdx-json > sbom.spdx.json

# Generate CycloneDX BOM with cdxgen
cdxgen -o bom.cdx.json src/sbom/

# Scan SBOM with Trivy
trivy sbom sbom.spdx.json

# Convert between SBOM formats
trivy convert --format cyclonedx sbom.spdx.json > bom.cdx.json
```

## Testing SBOM Tools

### General Approach

1. **Navigate to SBOM directory**: `cd src/sbom/`
2. **Generate SBOMs** using different tools (Syft, cdxgen, Trivy)
3. **Scan generated SBOMs** for vulnerabilities
4. **Validate SBOM quality** and standards compliance
5. **Compare results** across different SBOM tools

### Key SBOM Testing Areas

1. **Dependency Vulnerability Detection**: CVEs in packages
2. **License Compliance**: GPL, AGPL, proprietary license detection
3. **SBOM Quality**: Completeness, accuracy, standards compliance
4. **Configuration Testing**: Different SBOM tool configurations

## Expected Results

Each file contains comments indicating vulnerabilities. The `SAST_TOOL_COVERAGE.md` document lists which tools should detect each vulnerability type.

### Common Detection Scenarios

#### SAST Tools:
1. **SQL Injection**: Look for string concatenation in SQL queries
2. **Command Injection**: Look for `exec()`, `system()`, or similar functions with user input
3. **XSS**: Look for unescaped user input in HTML output
4. **Path Traversal**: Look for file operations with user-controlled paths
5. **Hardcoded Credentials**: Look for passwords, API keys in source code
6. **Weak Cryptography**: Look for MD5, SHA-1, DES, ECB mode

#### SBOM Tools:
7. **Dependency CVEs**: Look for outdated packages with known vulnerabilities
8. **License Issues**: Look for GPL, AGPL, or missing licenses
9. **Supply Chain Attacks**: Look for typosquatting and malicious packages
10. **SBOM Quality**: Look for incomplete metadata or dependency graphs
11. **Configuration Issues**: Look for insecure SBOM generation settings

## Comparing Tools

### Comparing SAST Tools:
1. **Create a spreadsheet** listing all vulnerabilities in the framework
2. **Run each SAST tool** and record which vulnerabilities are detected
3. **Calculate detection rates** for each tool
4. **Note false positives** (incorrect findings)
5. **Note false negatives** (missed vulnerabilities)

### Comparing SBOM Tools:
1. **Generate SBOMs** with each tool (Syft, cdxgen, Trivy)
2. **Compare SBOM outputs** for completeness and accuracy
3. **Scan SBOMs** with vulnerability scanners
4. **Measure performance** (generation time, file size)
5. **Evaluate standards compliance** (SPDX, CycloneDX)

## Advanced Testing

### Custom Test Scenarios

1. **Add new vulnerabilities** to test specific tool capabilities
2. **Create complex multi-file vulnerabilities** to test data flow analysis
3. **Add obfuscated code** to test advanced analysis capabilities

### Performance Testing

#### SAST Tools:
1. **Measure scan time** for each language directory
2. **Test with large codebases** by duplicating vulnerable patterns
3. **Check resource usage** (CPU, memory) during scans

#### SBOM Tools:
4. **Measure SBOM generation time** for different project sizes
5. **Compare SBOM file sizes** across different formats
6. **Test SBOM scanning performance** with large dependency trees

## Security Considerations

⚠️ **IMPORTANT**: This framework contains deliberately vulnerable code.

- **Never deploy** this code in production environments
- **Use isolated environments** for testing
- **Do not expose** test systems to the internet
- **Clean up** test environments after use

## Contributing

### Adding New Vulnerabilities

1. Choose an appropriate language directory
2. Create a new file or add to existing files
3. Clearly mark vulnerabilities with comments
4. Include both vulnerable and safe examples
5. Update documentation

### Vulnerability Guidelines

- Use realistic code patterns
- Include comments explaining the vulnerability
- Follow language conventions and style
- Test that the code compiles/runs (for demonstration)
- Include safe alternatives for comparison

## Troubleshooting

### Common Issues

#### SAST Tools:
1. **SAST tool not detecting vulnerabilities**:
   - Check tool configuration
   - Verify the tool supports the language
   - Check if vulnerability patterns match tool's rules

2. **False positives**:
   - Review tool documentation for rule configuration
   - Adjust severity thresholds if possible
   - Report false positives to tool vendors

3. **Build/compile errors**:
   - Some code is intentionally incomplete for demonstration
   - Focus on static analysis, not execution

#### SBOM Tools:
4. **SBOM generation errors**:
   - Check file permissions and access
   - Verify supported package managers
   - Check network connectivity for remote resources

5. **Incomplete SBOMs**:
   - Check scanning scope and configuration
   - Verify all relevant directories are included
   - Check for excluded files or patterns

6. **SBOM validation failures**:
   - Check SBOM format and version
   - Verify required fields are present
   - Check for schema compliance issues

## Support

For issues, questions, or contributions:

1. Check existing documentation
2. Review the `SAST_TOOL_COVERAGE.md` guide
3. Submit issues or pull requests on the repository

## License

This framework is licensed under MIT License. See LICENSE file for details.