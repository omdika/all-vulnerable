# Usage Guide

This guide explains how to use the All-Vulnerable Framework to test SAST tools.

## Prerequisites

- Git
- SAST tools you want to test (Checkmarx, Fortify, SonarQube, etc.)
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

## Expected Results

Each file contains comments indicating vulnerabilities. The `SAST_TOOL_COVERAGE.md` document lists which tools should detect each vulnerability type.

### Common Detection Scenarios

1. **SQL Injection**: Look for string concatenation in SQL queries
2. **Command Injection**: Look for `exec()`, `system()`, or similar functions with user input
3. **XSS**: Look for unescaped user input in HTML output
4. **Path Traversal**: Look for file operations with user-controlled paths
5. **Hardcoded Credentials**: Look for passwords, API keys in source code
6. **Weak Cryptography**: Look for MD5, SHA-1, DES, ECB mode

## Comparing Tools

To compare SAST tool effectiveness:

1. **Create a spreadsheet** listing all vulnerabilities in the framework
2. **Run each tool** and record which vulnerabilities are detected
3. **Calculate detection rates** for each tool
4. **Note false positives** (incorrect findings)
5. **Note false negatives** (missed vulnerabilities)

## Advanced Testing

### Custom Test Scenarios

1. **Add new vulnerabilities** to test specific tool capabilities
2. **Create complex multi-file vulnerabilities** to test data flow analysis
3. **Add obfuscated code** to test advanced analysis capabilities

### Performance Testing

1. **Measure scan time** for each language directory
2. **Test with large codebases** by duplicating vulnerable patterns
3. **Check resource usage** (CPU, memory) during scans

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

## Support

For issues, questions, or contributions:

1. Check existing documentation
2. Review the `SAST_TOOL_COVERAGE.md` guide
3. Submit issues or pull requests on the repository

## License

This framework is licensed under MIT License. See LICENSE file for details.