<?php
/**
 * SQL Injection vulnerabilities for PHP.
 * Used for testing SAST tools.
 */

// Hardcoded credentials
define('DB_USER', 'admin');
define('DB_PASSWORD', 'password123');
define('DB_NAME', 'test');

/**
 * Vulnerable SQL query with string concatenation
 */
function getUserDataVulnerable($userId) {
    $conn = new mysqli('localhost', DB_USER, DB_PASSWORD, DB_NAME);

    // VULNERABLE: Direct string concatenation
    $query = "SELECT * FROM users WHERE id = '$userId'";

    $result = $conn->query($query);

    $users = [];
    while ($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

    $conn->close();
    return $users;
}

/**
 * Multiple parameter injection
 */
function searchUsersVulnerable($name, $email) {
    $conn = new mysqli('localhost', DB_USER, DB_PASSWORD, DB_NAME);

    // VULNERABLE: Multiple parameters concatenated
    $query = "SELECT * FROM users WHERE name = '$name' AND email = '$email'";

    $result = $conn->query($query);

    $users = [];
    while ($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

    $conn->close();
    return $users;
}

/**
 * SQL injection in ORDER BY clause
 */
function getUsersSortedVulnerable($sortColumn) {
    $conn = new mysqli('localhost', DB_USER, DB_PASSWORD, DB_NAME);

    // VULNERABLE: User input in ORDER BY
    $query = "SELECT * FROM users ORDER BY $sortColumn";

    $result = $conn->query($query);

    $users = [];
    while ($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

    $conn->close();
    return $users;
}

/**
 * Command injection vulnerability
 */
function commandInjectionVulnerable($userInput) {
    // VULNERABLE: Command injection
    $command = "ping -c 1 " . $userInput;

    // VULNERABLE: Using shell_exec
    $output = shell_exec($command);

    return $output;
}

/**
 * Path traversal vulnerability
 */
function pathTraversalVulnerable($filename) {
    // VULNERABLE: Path traversal
    $path = "/var/www/uploads/" . $filename;

    $content = file_get_contents($path);

    return $content;
}

/**
 * XSS vulnerability
 */
function xssVulnerable($userInput) {
    // VULNERABLE: No output encoding
    echo "<div>" . $userInput . "</div>";
}

/**
 * File inclusion vulnerability
 */
function fileInclusionVulnerable($page) {
    // VULNERABLE: File inclusion
    include($page . '.php');
}

/**
 * Unserialize vulnerability
 */
function unserializeVulnerable($data) {
    // VULNERABLE: Unserializing user input
    $object = unserialize($data);

    return $object;
}

/**
 * Weak session management
 */
function weakSessionManagement() {
    // VULNERABLE: Weak session ID
    session_id('weak_id');

    // VULNERABLE: Session fixation
    session_start();
}

/**
 * Hardcoded credentials
 */
function checkPasswordVulnerable($input) {
    // VULNERABLE: Hardcoded password
    $password = "Admin@123";

    return $input === $password;
}

/**
 * Weak cryptographic algorithm
 */
function weakCryptoVulnerable($data) {
    // VULNERABLE: MD5 is cryptographically weak
    return md5($data);
}

// Safe examples

/**
 * Safe SQL query using prepared statements
 */
function getUserDataSafe($userId) {
    $conn = new mysqli('localhost', DB_USER, DB_PASSWORD, DB_NAME);

    // SAFE: Using prepared statement
    $stmt = $conn->prepare("SELECT * FROM users WHERE id = ?");
    $stmt->bind_param("s", $userId);
    $stmt->execute();

    $result = $stmt->get_result();

    $users = [];
    while ($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

    $stmt->close();
    $conn->close();

    return $users;
}

/**
 * Safe command execution
 */
function safeCommandExecution($ipAddress) {
    // SAFE: Input validation
    if (preg_match('/^[0-9.]+$/', $ipAddress)) {
        $command = "ping -c 1 " . escapeshellarg($ipAddress);
        $output = shell_exec($command);

        return $output;
    }

    return null;
}

/**
 * Safe path handling
 */
function safePathHandling($filename) {
    // SAFE: Path validation
    $basePath = "/var/www/uploads/";
    $userPath = realpath($basePath . $filename);

    if (strpos($userPath, $basePath) === 0) {
        $content = file_get_contents($userPath);
        return $content;
    }

    throw new Exception("Invalid path");
}

/**
 * Safe XSS prevention
 */
function safeXSS($userInput) {
    // SAFE: HTML escaping
    echo "<div>" . htmlspecialchars($userInput, ENT_QUOTES, 'UTF-8') . "</div>";
}

// Example usage
if (isset($_GET['user_id'])) {
    $users = getUserDataVulnerable($_GET['user_id']);
    print_r($users);
}

?>