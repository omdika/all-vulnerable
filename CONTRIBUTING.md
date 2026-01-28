# Contributing to All-Vulnerable Framework

Thank you for your interest in contributing to the All-Vulnerable Framework! This project relies on contributions to maintain comprehensive vulnerability coverage for SAST tool testing.

## Purpose

This framework is designed for:
- Testing Static Application Security Testing (SAST) tools
- Security education and training
- Research on vulnerability detection
- Comparing security tool capabilities

## Code of Conduct

Please be respectful and constructive in all interactions. Remember that this project is for educational purposes only.

## How to Contribute

### 1. Reporting Issues

If you find bugs or have suggestions:
1. Check if the issue already exists
2. Create a new issue with clear description
3. Include:
   - Language/framework affected
   - Vulnerability type
   - Expected behavior
   - Actual behavior

### 2. Adding New Vulnerabilities

#### Guidelines for Vulnerability Examples

1. **Educational Value**: Vulnerabilities should teach something about security
2. **Realistic Patterns**: Use code patterns found in real applications
3. **Clear Marking**: Clearly comment vulnerable sections with `// VULNERABLE:`
4. **Safe Alternatives**: Include safe examples for comparison
5. **Documentation**: Update relevant documentation files

#### Vulnerability Categories Needed

We particularly welcome contributions for:
- New vulnerability types not covered
- Additional language support
- Complex multi-step vulnerabilities
- Framework-specific vulnerabilities (Spring, Django, React, etc.)
- API security vulnerabilities
- Cloud configuration vulnerabilities

### 3. Improving Documentation

- Fix typos or unclear explanations
- Add examples or usage scenarios
- Translate documentation (if appropriate)
- Add tool-specific testing instructions

### 4. Code Quality

- Follow language-specific style guides
- Keep code simple and readable
- Include comments explaining the vulnerability
- Test that code compiles/parses (but doesn't need to run)

## Development Process

### 1. Fork the Repository

1. Fork the repository on GitHub
2. Clone your fork locally
3. Create a feature branch

### 2. Make Changes

1. Add or modify vulnerability examples
2. Update documentation as needed
3. Test your changes
4. Ensure security warnings remain prominent

### 3. Submit Pull Request

1. Push changes to your fork
2. Create a pull request with:
   - Description of changes
   - Purpose of new vulnerabilities
   - SAST tools that should detect them
   - Any testing performed

### 4. Review Process

1. Maintainers will review for:
   - Educational value
   - Code quality
   - Security appropriateness
   - Documentation updates
2. Address any feedback
3. PR will be merged when approved

## Vulnerability Contribution Guidelines

### Do's

✅ **DO** add realistic vulnerability examples
✅ **DO** include comments explaining the vulnerability
✅ **DO** provide safe alternatives when possible
✅ **DO** follow existing code structure and patterns
✅ **DO** update relevant documentation
✅ **DO** test that code is syntactically correct

### Don'ts

❌ **DON'T** add malicious payloads or exploits
❌ **DON'T** include real credentials or sensitive data
❌ **DON'T** create vulnerabilities that could escape test environments
❌ **DON'T** violate responsible disclosure for real vulnerabilities
❌ **DON'T** add vulnerabilities without educational value

### Language-Specific Guidelines

#### Java
- Use standard Java conventions
- Include package declarations
- Comment vulnerable sections clearly

#### Python
- Follow PEP 8 style guide
- Use docstrings for functions
- Mark vulnerabilities with comments

#### JavaScript
- Use modern ES6+ syntax where appropriate
- Include module.exports for Node.js
- Comment security issues clearly

#### C/C++
- Include necessary headers
- Comment memory vulnerabilities thoroughly
- Show both vulnerable and safe patterns

## Testing Contributions

Before submitting:
1. Verify code compiles/parses (if applicable)
2. Check that vulnerabilities are clearly marked
3. Ensure no real credentials are included
4. Update SAST_TOOL_COVERAGE.md if adding new vulnerability types

## Security Considerations

- Never include real exploits or attack tools
- Use example credentials only (e.g., "password123")
- Ensure code cannot accidentally harm systems
- Maintain the educational purpose

## Recognition

Contributors will be:
- Listed in CONTRIBUTORS.md
- Acknowledged in release notes
- Credited for significant contributions

## Questions?

Open an issue or discussion for:
- Clarification on contribution guidelines
- Ideas for new vulnerability types
- Questions about existing code
- Suggestions for improvement

Thank you for helping make SAST tool testing better for everyone!