#!/usr/bin/env python3
"""
Web vulnerabilities for Python (Flask examples).
Used for testing SAST tools.
"""

from flask import Flask, request, make_response, redirect
import jinja2

app = Flask(__name__)

@app.route('/xss')
def reflected_xss():
    """Reflected XSS vulnerability."""
    # VULNERABLE: Direct output of user input
    name = request.args.get('name', '')
    return f"<h1>Hello {name}</h1>"

@app.route('/stored-xss')
def stored_xss():
    """Stored XSS simulation."""
    comment = request.args.get('comment', '')

    # VULNERABLE: No output encoding
    return f"""
    <html>
        <body>
            <div class="comment">{comment}</div>
        </body>
    </html>
    """

@app.route('/template-injection')
def template_injection():
    """Jinja2 template injection."""
    template = request.args.get('template', '')

    # VULNERABLE: User-controlled template
    env = jinja2.Environment()
    template_obj = env.from_string(template)
    return template_obj.render()

@app.route('/open-redirect')
def open_redirect():
    """Open redirect vulnerability."""
    url = request.args.get('url', '/')

    # VULNERABLE: No validation of redirect URL
    return redirect(url)

@app.route('/response-splitting')
def response_splitting():
    """HTTP response splitting."""
    user_agent = request.headers.get('User-Agent', '')

    # VULNERABLE: User input in headers
    response = make_response("OK")
    response.headers['X-User-Agent'] = user_agent
    return response

@app.route('/cookies')
def insecure_cookies():
    """Insecure cookie handling."""
    user_id = request.args.get('user_id', '')

    # VULNERABLE: Missing HttpOnly and Secure flags
    response = make_response("Cookie set")
    response.set_cookie('session_id', user_id, max_age=3600)
    return response

@app.route('/ssrf')
def ssrf():
    """Server-Side Request Forgery."""
    import requests

    url = request.args.get('url', '')

    # VULNERABLE: SSRF - user controls URL
    response = requests.get(url)
    return response.text

@app.route('/xxe')
def xxe():
    """XML External Entity attack."""
    from xml.etree import ElementTree

    xml_data = request.get_data(as_text=True)

    # VULNERABLE: Parsing XML with external entities enabled
    # Note: xml.etree.ElementTree is safe by default, but other parsers aren't
    return "XXE endpoint - use a vulnerable XML parser"

# Safe examples
@app.route('/safe-xss')
def safe_xss():
    """Safe XSS prevention."""
    from markupsafe import escape

    name = request.args.get('name', '')

    # SAFE: Using escape function
    return f"<h1>Hello {escape(name)}</h1>"

@app.route('/safe-redirect')
def safe_redirect():
    """Safe redirect with validation."""
    url = request.args.get('url', '/')

    # SAFE: Validate redirect URL
    allowed_domains = ['example.com', 'mysite.com']

    from urllib.parse import urlparse
    parsed = urlparse(url)

    if parsed.netloc in allowed_domains:
        return redirect(url)
    else:
        return redirect('/')

@app.route('/safe-cookies')
def safe_cookies():
    """Secure cookie handling."""
    user_id = request.args.get('user_id', '')

    # SAFE: HttpOnly and Secure flags (in production)
    response = make_response("Secure cookie set")
    response.set_cookie(
        'session_id',
        user_id,
        max_age=3600,
        httponly=True,
        secure=True,  # In production
        samesite='Strict'
    )
    return response