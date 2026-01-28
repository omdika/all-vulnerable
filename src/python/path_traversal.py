#!/usr/bin/env python3
"""
Path Traversal vulnerabilities for Python.
Used for testing SAST tools.
"""

import os
from flask import Flask, request, send_file

app = Flask(__name__)

def vulnerable_file_read(filename):
    """Vulnerable file read with user input."""
    # VULNERABLE: Direct path concatenation
    filepath = f"/var/www/uploads/{filename}"

    with open(filepath, 'r') as f:
        return f.read()

def vulnerable_file_write(filename, content):
    """Vulnerable file write."""
    # VULNERABLE: User controls file path
    filepath = os.path.join("/tmp/userfiles", filename)

    with open(filepath, 'w') as f:
        f.write(content)

def vulnerable_flask_route():
    """Flask route with path traversal."""
    @app.route('/download')
    def download():
        # VULNERABLE: User input in send_file
        filename = request.args.get('file')
        return send_file(f"/var/www/files/{filename}")

def vulnerable_os_path_join(filename):
    """os.path.join can still be vulnerable with absolute paths."""
    # VULNERABLE: If filename starts with /, join returns filename
    filepath = os.path.join("/var/www/data", filename)

    with open(filepath, 'r') as f:
        return f.read()

def vulnerable_open_without_validation(user_input):
    """Direct open without any checks."""
    # VULNERABLE: Direct user input
    with open(user_input, 'r') as f:
        return f.read()

def safe_file_read(filename):
    """Safe file read with path validation."""
    import posixpath

    # SAFE: Validate and normalize path
    base_path = "/var/www/uploads"
    user_path = posixpath.normpath(filename)

    if user_path.startswith('..') or user_path.startswith('/'):
        raise SecurityError("Invalid path")

    full_path = posixpath.join(base_path, user_path)
    full_path = posixpath.normpath(full_path)

    if not full_path.startswith(base_path):
        raise SecurityError("Path traversal attempt")

    with open(full_path, 'r') as f:
        return f.read()

def safe_flask_download():
    """Safe Flask route with validation."""
    @app.route('/download-safe')
    def download_safe():
        filename = request.args.get('file')

        # SAFE: Validate filename
        if '..' in filename or filename.startswith('/'):
            return "Invalid filename", 400

        filepath = os.path.join("/var/www/files", filename)

        # Additional check
        if not os.path.commonprefix([os.path.realpath(filepath), "/var/www/files"]) == "/var/www/files":
            return "Invalid path", 400

        return send_file(filepath)

class SecurityError(Exception):
    pass