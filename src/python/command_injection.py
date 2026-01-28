#!/usr/bin/env python3
"""
Command Injection vulnerabilities for Python.
Used for testing SAST tools like Bandit.
"""

import os
import subprocess
import sys

def vulnerable_os_system(user_input):
    """Vulnerable command execution using os.system()."""
    # VULNERABLE: Direct command concatenation
    command = f"ping -c 1 {user_input}"
    os.system(command)

def vulnerable_subprocess_call(user_input):
    """Command injection with subprocess.call()."""
    # VULNERABLE: shell=True with user input
    subprocess.call(f"nslookup {user_input}", shell=True)

def vulnerable_subprocess_popen(filename):
    """Command injection with subprocess.Popen."""
    # VULNERABLE: User input in command arguments
    subprocess.Popen(["cat", filename])

def vulnerable_eval(user_input):
    """Code injection using eval()."""
    # VULNERABLE: Evaluating user input
    result = eval(user_input)
    return result

def vulnerable_exec(user_input):
    """Code injection using exec()."""
    # VULNERABLE: Executing user input
    exec(user_input)

def vulnerable_pickle(pickle_data):
    """Insecure deserialization with pickle."""
    import pickle
    # VULNERABLE: Loading untrusted pickle data
    return pickle.loads(pickle_data)

def vulnerable_yaml(yaml_data):
    """Insecure YAML loading."""
    import yaml
    # VULNERABLE: yaml.load instead of yaml.safe_load
    return yaml.load(yaml_data, Loader=yaml.Loader)

def vulnerable_template(user_input):
    """Template injection."""
    from string import Template
    # VULNERABLE: User input in template
    template = Template(f"Hello $name, you entered: {user_input}")
    return template.substitute(name="User")

def safe_command_execution(ip_address):
    """Safe command execution with input validation."""
    import re

    # SAFE: Validate input before use
    if re.match(r'^[0-9.]+$', ip_address):
        subprocess.call(["ping", "-c", "1", ip_address])

def safe_subprocess(user_input):
    """Safe subprocess without shell."""
    # SAFE: Using list argument without shell=True
    subprocess.run(["echo", user_input])

def use_ast_literal_eval(user_input):
    """Safe alternative to eval."""
    import ast
    # SAFE: Using ast.literal_eval for simple literals
    return ast.literal_eval(user_input)