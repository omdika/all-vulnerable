# SBOM (Software Bill of Materials) Vulnerabilities

This directory contains examples of vulnerabilities and misconfigurations detectable by SBOM tools:

## Supported SBOM Tools

1. **Syft** - SBOM generation tool
2. **Trivy-SBOM** - Vulnerability scanning for SBOMs
3. **cdxgen-cbom** - CycloneDX SBOM generation
4. **cdxgen-mlbom** - Multi-language BOM generation

## Vulnerability Types for SBOM Tools

### 1. Dependency Vulnerabilities
- **Outdated packages with known CVEs**: Packages with publicly disclosed vulnerabilities
- **License compliance issues**: GPL, AGPL, or proprietary licenses in commercial projects
- **Missing license information**: Packages without clear licensing
- **Deprecated/abandoned packages**: Packages no longer maintained

### 2. Configuration Vulnerabilities
- **Incomplete SBOM generation**: Missing packages or relationships
- **Insecure SBOM storage**: World-readable SBOM files
- **Missing metadata**: No version, author, or creation info
- **Incorrect package identification**: Wrong package URLs (purls) or CPEs

### 3. Supply Chain Vulnerabilities
- **Typosquatting packages**: Malicious packages with similar names
- **Unpinned dependencies**: Git references without commit hashes
- **Untrusted sources**: Packages from unofficial repositories
- **Local file dependencies**: Unversioned local packages

## Files in This Directory

### Dependency Files
- `package-sbom.json` - Node.js package.json with SBOM-detectable vulnerabilities
- `requirements-sbom.txt` - Python requirements with license and CVE issues
- `pom-sbom.xml` - Maven POM with license compliance issues
- `go.mod.sbom` - Go module with replace/exclude vulnerabilities

### Configuration Files
- `Dockerfile-sbom` - Dockerfile with SBOM-relevant issues
- `syft-config.yaml` - Syft configuration with security issues
- `vulnerable-sbom.spdx.json` - Example SBOM with vulnerabilities

## Testing SBOM Tools

### 1. Generate SBOMs
```bash
# Generate SBOM with Syft
syft dir:src/sbom/ -o spdx-json > sbom.json

# Generate SBOM with cdxgen
cdxgen -o bom.cdx.json src/sbom/

# Generate SBOM with Trivy
trivy fs --format spdx-json src/sbom/ > trivy-sbom.json
```

### 2. Scan SBOMs for Vulnerabilities
```bash
# Scan SBOM with Trivy
trivy sbom sbom.json

# Convert and scan
trivy convert --format cyclonedx sbom.json | trivy sbom -
```

### 3. Validate SBOMs
```bash
# Validate SPDX SBOM
spdx-json-validator vulnerable-sbom.spdx.json

# Check for missing fields
# (Tool-specific validation)
```

## Expected Findings

### Syft Should Detect:
- Missing package metadata
- Incomplete dependency graphs
- License information gaps
- Package identification issues

### Trivy-SBOM Should Detect:
- CVEs in dependencies listed in SBOM
- License compliance issues
- Deprecated packages
- Configuration vulnerabilities

### cdxgen Should Detect:
- Multi-language dependency issues
- CycloneDX specification violations
- Missing component metadata
- Incomplete BOM generation

## SBOM Quality Issues

1. **Incomplete SBOMs**: Missing transitive dependencies
2. **Inaccurate SBOMs**: Wrong versions or package identifiers
3. **Insecure SBOMs**: Contains secrets or sensitive information
4. **Non-compliant SBOMs**: Doesn't follow SPDX or CycloneDX standards

## Security Considerations

⚠️ **SBOMs Can Contain Sensitive Information**:
- Package names and versions reveal technology stack
- Dependency graphs can expose architecture
- Metadata might contain internal information
- Always secure SBOM files like source code

## Best Practices

1. **Complete SBOMs**: Include all dependencies, direct and transitive
2. **Accurate Metadata**: Correct package URLs, versions, and licenses
3. **Regular Updates**: Regenerate SBOMs when dependencies change
4. **Secure Storage**: Store SBOMs with access controls
5. **Validation**: Validate SBOMs against standards
6. **Integration**: Integrate SBOM generation into CI/CD pipelines

## Integration with SAST Tools

SBOM tools complement traditional SAST by:
1. Providing dependency context for code vulnerabilities
2. Enabling software supply chain security
3. Supporting license compliance monitoring
4. Facilitating vulnerability management across dependencies

## References

- [SPDX Specification](https://spdx.dev/specifications/)
- [CycloneDX Specification](https://cyclonedx.org/specification/overview/)
- [Syft Documentation](https://github.com/anchore/syft)
- [Trivy Documentation](https://github.com/aquasecurity/trivy)
- [cdxgen Documentation](https://github.com/CycloneDX/cdxgen)