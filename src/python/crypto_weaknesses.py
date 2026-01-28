#!/usr/bin/env python3
"""
Cryptographic weaknesses for Python.
Used for testing SAST tools.
"""

import hashlib
import base64
import os
import random
from Crypto.Cipher import AES, DES  # pycryptodome

# Hardcoded secrets - detectable by SAST tools
SECRET_KEY = b"1234567890123456"  # 16 bytes for AES
WEAK_KEY = b"12345678"  # 8 bytes for DES
HARDCODED_PASSWORD = "Admin@123"

def weak_hash_md5(data):
    """Weak MD5 hash function."""
    # VULNERABLE: Using MD5
    return hashlib.md5(data.encode()).hexdigest()

def weak_hash_sha1(data):
    """Weak SHA-1 hash function."""
    # VULNERABLE: Using SHA-1
    return hashlib.sha1(data.encode()).hexdigest()

def weak_encryption_des(data):
    """Weak DES encryption."""
    # VULNERABLE: Using DES
    cipher = DES.new(WEAK_KEY, DES.MODE_ECB)
    padded_data = data + ' ' * (8 - len(data) % 8)
    encrypted = cipher.encrypt(padded_data.encode())
    return base64.b64encode(encrypted).decode()

def weak_encryption_ecb(data):
    """AES in ECB mode (weak)."""
    # VULNERABLE: Using ECB mode
    cipher = AES.new(SECRET_KEY, AES.MODE_ECB)
    padded_data = data + ' ' * (16 - len(data) % 16)
    encrypted = cipher.encrypt(padded_data.encode())
    return base64.b64encode(encrypted).decode()

def weak_random():
    """Weak random number generation."""
    # VULNERABLE: Using random for cryptographic purposes
    return random.randint(0, 1000000)

def predictable_random_seed():
    """Predictable random seed."""
    # VULNERABLE: Setting predictable seed
    random.seed(12345)
    return random.random()

def hardcoded_password_check(input_password):
    """Hardcoded password check."""
    # VULNERABLE: Hardcoded password
    return input_password == HARDCODED_PASSWORD

def weak_pbkdf2(password):
    """Weak PBKDF2 parameters."""
    import hashlib
    # VULNERABLE: Too few iterations
    dk = hashlib.pbkdf2_hmac('sha256', password.encode(), b'salt', 1000)
    return dk.hex()

def unsafe_rsa_key_size():
    """Unsafe RSA key size."""
    from Crypto.PublicKey import RSA
    # VULNERABLE: 1024-bit RSA key
    key = RSA.generate(1024)
    return key.export_key()

def unsafe_signature_verification(data, signature):
    """Unsafe signature verification."""
    # VULNERABLE: Comparing signatures without constant-time compare
    expected = hashlib.sha256(data.encode()).hexdigest()
    return signature == expected

# Safe examples
def safe_hash(data):
    """Safe hash function."""
    # SAFE: Using SHA-256 or better
    return hashlib.sha256(data.encode()).hexdigest()

def safe_encryption(data):
    """Safe encryption with AES-GCM."""
    # SAFE: Using AES-GCM
    from Crypto.Cipher import AES
    from Crypto.Random import get_random_bytes

    key = get_random_bytes(32)  # 256-bit key
    cipher = AES.new(key, AES.MODE_GCM)
    ciphertext, tag = cipher.encrypt_and_digest(data.encode())

    return base64.b64encode(cipher.nonce + tag + ciphertext).decode()

def safe_random():
    """Cryptographically secure random."""
    # SAFE: Using os.urandom or secrets
    return os.urandom(16)

def safe_password_check(input_password, stored_hash, salt):
    """Safe password checking."""
    import hashlib
    import hmac

    # SAFE: Constant-time comparison
    input_hash = hashlib.pbkdf2_hmac('sha256', input_password.encode(), salt, 100000)
    return hmac.compare_digest(input_hash, stored_hash)

def safe_pbkdf2(password):
    """Safe PBKDF2 parameters."""
    import hashlib
    # SAFE: Sufficient iterations
    salt = os.urandom(16)
    dk = hashlib.pbkdf2_hmac('sha256', password.encode(), salt, 100000)
    return salt + dk