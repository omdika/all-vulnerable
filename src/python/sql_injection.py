#!/usr/bin/env python3
"""
SQL Injection vulnerabilities for Python.
Used for testing SAST tools like Bandit, Checkmarx, etc.
"""

import sqlite3
import MySQLdb

# Hardcoded credentials - detectable by SAST tools
DB_USER = "admin"
DB_PASSWORD = "password123"

def vulnerable_query(user_id):
    """Vulnerable SQL query with string concatenation."""
    conn = sqlite3.connect('test.db')
    cursor = conn.cursor()

    # VULNERABLE: Direct string concatenation
    query = f"SELECT * FROM users WHERE id = '{user_id}'"
    cursor.execute(query)

    return cursor.fetchall()

def vulnerable_multiple_params(name, email):
    """SQL injection with multiple parameters."""
    conn = MySQLdb.connect(
        host="localhost",
        user=DB_USER,
        passwd=DB_PASSWORD,
        db="test"
    )
    cursor = conn.cursor()

    # VULNERABLE: Multiple parameters concatenated
    query = f"SELECT * FROM users WHERE name = '{name}' AND email = '{email}'"
    cursor.execute(query)

    return cursor.fetchall()

def vulnerable_order_by(sort_column):
    """SQL injection in ORDER BY clause."""
    conn = sqlite3.connect('test.db')
    cursor = conn.cursor()

    # VULNERABLE: User input in ORDER BY
    query = f"SELECT * FROM users ORDER BY {sort_column}"
    cursor.execute(query)

    return cursor.fetchall()

def vulnerable_second_order(user_id, order_by):
    """Multiple injection points."""
    conn = sqlite3.connect('test.db')
    cursor = conn.cursor()

    # VULNERABLE: Two injection points
    query = f"SELECT * FROM users WHERE id = '{user_id}' ORDER BY {order_by}"
    cursor.execute(query)

    return cursor.fetchall()

def safe_query(user_id):
    """Safe SQL query using parameterized statements."""
    conn = sqlite3.connect('test.db')
    cursor = conn.cursor()

    # SAFE: Using parameterized query
    query = "SELECT * FROM users WHERE id = ?"
    cursor.execute(query, (user_id,))

    return cursor.fetchall()

def exec_sqlite3(user_input):
    """Using sqlite3 module directly with vulnerable string."""
    conn = sqlite3.connect('test.db')
    cursor = conn.cursor()

    # VULNERABLE: Using executescript
    cursor.executescript(f"SELECT * FROM users WHERE id = '{user_input}'")

    return cursor.fetchall()