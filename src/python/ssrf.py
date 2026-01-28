#!/usr/bin/env python3
"""
Server-Side Request Forgery (SSRF) vulnerabilities.
Used for testing SAST tools.
"""

import requests
import urllib.request
import urllib.parse

def vulnerable_requests(url):
    """Vulnerable SSRF using requests library."""
    # VULNERABLE: User controls URL
    response = requests.get(url)
    return response.text

def vulnerable_urllib(url):
    """Vulnerable SSRF using urllib."""
    # VULNERABLE: User controls URL
    with urllib.request.urlopen(url) as response:
        return response.read().decode('utf-8')

def vulnerable_http_client(url):
    """Vulnerable SSRF using http.client."""
    import http.client
    from urllib.parse import urlparse

    # VULNERABLE: User controls URL
    parsed = urlparse(url)
    conn = http.client.HTTPConnection(parsed.netloc)
    conn.request("GET", parsed.path)
    response = conn.getresponse()
    return response.read().decode('utf-8')

def ssrf_with_internal_network(url_path):
    """SSRF targeting internal network."""
    # VULNERABLE: Constructing URL with user input
    base_url = "http://internal-api.local"
    url = f"{base_url}{url_path}"

    response = requests.get(url)
    return response.json()

def ssrf_with_protocol_switching(user_input):
    """SSRF with protocol switching."""
    # VULNERABLE: User controls protocol and host
    url = f"{user_input}"

    # Could be file://, gopher://, dict://, etc.
    try:
        response = requests.get(url)
        return response.text
    except:
        return "Error"

def ssrf_in_webhook(url, data):
    """SSRF in webhook configuration."""
    # VULNERABLE: User controls webhook URL
    response = requests.post(url, json=data)
    return response.status_code

# Safe examples
def safe_requests(allowed_domains):
    """Safe SSRF prevention with allowlist."""
    def make_request(url):
        from urllib.parse import urlparse

        parsed = urlparse(url)
        domain = parsed.netloc

        if domain not in allowed_domains:
            raise ValueError(f"Domain not allowed: {domain}")

        # Remove sensitive parts from URL
        if parsed.scheme not in ['http', 'https']:
            raise ValueError(f"Protocol not allowed: {parsed.scheme}")

        # Block internal IPs
        if domain.startswith('10.') or domain.startswith('192.168.'):
            raise ValueError("Internal IP not allowed")

        response = requests.get(url, timeout=5)
        return response.text

    return make_request

def safe_url_fetch(url, allowed_hosts):
    """Safe URL fetching with validation."""
    from urllib.parse import urlparse

    parsed = urlparse(url)

    # Validate scheme
    if parsed.scheme not in ['http', 'https']:
        raise ValueError("Only HTTP and HTTPS allowed")

    # Validate host
    if parsed.hostname not in allowed_hosts:
        raise ValueError("Host not allowed")

    # Block internal addresses
    if parsed.hostname in ['localhost', '127.0.0.1', '::1']:
        raise ValueError("Localhost not allowed")

    # Block private IP ranges
    if parsed.hostname:
        parts = parsed.hostname.split('.')
        if len(parts) == 4:
            first_octet = int(parts[0])
            second_octet = int(parts[1])

            # Check for private IPs
            if first_octet == 10:
                raise ValueError("Private IP range 10.0.0.0/8")
            elif first_octet == 172 and 16 <= second_octet <= 31:
                raise ValueError("Private IP range 172.16.0.0/12")
            elif first_octet == 192 and second_octet == 168:
                raise ValueError("Private IP range 192.168.0.0/16")

    # Make request
    response = requests.get(url, timeout=5)
    return response.text

def use_dedicated_api_client():
    """Use dedicated API client instead of generic HTTP client."""
    # SAFE: Using specific API client with fixed endpoints
    class SecureAPIClient:
        def __init__(self, base_url):
            self.base_url = base_url
            self.session = requests.Session()

        def get_data(self, endpoint):
            # Only allow specific endpoints
            allowed_endpoints = ['/api/users', '/api/products']
            if endpoint not in allowed_endpoints:
                raise ValueError("Endpoint not allowed")

            url = self.base_url + endpoint
            response = self.session.get(url)
            return response.json()

    return SecureAPIClient("https://api.example.com")

# Example usage
if __name__ == "__main__":
    # Vulnerable usage (would be exploited)
    # result = vulnerable_requests("http://localhost:8080/admin")

    # Safe usage
    allowed = ['api.example.com', 'cdn.example.com']
    safe_client = safe_requests(allowed)
    # result = safe_client("https://api.example.com/data")

    print("SSRF examples loaded")