/**
 * SQL Injection vulnerabilities for Go.
 * Used for testing SAST tools.
 */

package main

import (
	"database/sql"
	"fmt"
	"log"

	_ "github.com/go-sql-driver/mysql"
	_ "github.com/mattn/go-sqlite3"
)

// Hardcoded credentials
const (
	dbUser     = "admin"
	dbPassword = "password123"
	dbName     = "test"
)

/**
 * Vulnerable SQL query with string concatenation
 */
func getUserDataVulnerable(userID string) {
	db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@/%s", dbUser, dbPassword, dbName))
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	// VULNERABLE: Direct string concatenation
	query := fmt.Sprintf("SELECT * FROM users WHERE id = '%s'", userID)

	rows, err := db.Query(query)
	if err != nil {
		log.Fatal(err)
	}
	defer rows.Close()

	for rows.Next() {
		var id, name string
		if err := rows.Scan(&id, &name); err != nil {
			log.Fatal(err)
		}
		fmt.Printf("ID: %s, Name: %s\n", id, name)
	}
}

/**
 * Multiple parameter injection
 */
func searchUsersVulnerable(name, email string) {
	db, err := sql.Open("sqlite3", "./test.db")
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	// VULNERABLE: Multiple parameters concatenated
	query := fmt.Sprintf("SELECT * FROM users WHERE name = '%s' AND email = '%s'", name, email)

	rows, err := db.Query(query)
	if err != nil {
		log.Fatal(err)
	}
	defer rows.Close()

	for rows.Next() {
		var id, name, email string
		if err := rows.Scan(&id, &name, &email); err != nil {
			log.Fatal(err)
		}
		fmt.Printf("ID: %s, Name: %s, Email: %s\n", id, name, email)
	}
}

/**
 * SQL injection in ORDER BY clause
 */
func getUsersSortedVulnerable(sortColumn string) {
	db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@/%s", dbUser, dbPassword, dbName))
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	// VULNERABLE: User input in ORDER BY
	query := fmt.Sprintf("SELECT * FROM users ORDER BY %s", sortColumn)

	rows, err := db.Query(query)
	if err != nil {
		log.Fatal(err)
	}
	defer rows.Close()

	for rows.Next() {
		var id, name string
		if err := rows.Scan(&id, &name); err != nil {
			log.Fatal(err)
		}
		fmt.Printf("ID: %s, Name: %s\n", id, name)
	}
}

/**
 * Command injection vulnerability
 */
func commandInjectionVulnerable(userInput string) {
	// VULNERABLE: Command injection via os/exec
	cmd := fmt.Sprintf("ping -c 1 %s", userInput)

	// This would execute the command
	fmt.Printf("Would execute: %s\n", cmd)
}

/**
 * Path traversal vulnerability
 */
func pathTraversalVulnerable(filename string) {
	// VULNERABLE: Path traversal
	path := fmt.Sprintf("/var/www/uploads/%s", filename)

	fmt.Printf("Would read file: %s\n", path)
}

/**
 * XSS vulnerability simulation
 */
func xssVulnerable(userInput string) string {
	// VULNERABLE: No output encoding
	return fmt.Sprintf("<div>%s</div>", userInput)
}

/**
 * Hardcoded credentials
 */
func checkPasswordVulnerable(input string) bool {
	// VULNERABLE: Hardcoded password
	password := "Admin@123"
	return input == password
}

/**
 * Weak cryptographic algorithm
 */
func weakCryptoVulnerable(data string) string {
	// VULNERABLE: MD5 is cryptographically weak
	// import "crypto/md5"
	// h := md5.New()
	// h.Write([]byte(data))
	// return hex.EncodeToString(h.Sum(nil))
	return "MD5 hash would be computed here"
}

// Safe examples

/**
 * Safe SQL query using parameterized statements
 */
func getUserDataSafe(userID string) {
	db, err := sql.Open("mysql", fmt.Sprintf("%s:%s@/%s", dbUser, dbPassword, dbName))
	if err != nil {
		log.Fatal(err)
	}
	defer db.Close()

	// SAFE: Using parameterized query
	query := "SELECT * FROM users WHERE id = ?"

	rows, err := db.Query(query, userID)
	if err != nil {
		log.Fatal(err)
	}
	defer rows.Close()

	for rows.Next() {
		var id, name string
		if err := rows.Scan(&id, &name); err != nil {
			log.Fatal(err)
		}
		fmt.Printf("ID: %s, Name: %s\n", id, name)
	}
}

/**
 * Safe command execution
 */
func safeCommandExecution(ipAddress string) {
	// SAFE: Using exec.Command with separate arguments
	// cmd := exec.Command("ping", "-c", "1", ipAddress)
	// err := cmd.Run()
	fmt.Printf("Would safely execute ping on: %s\n", ipAddress)
}

/**
 * Safe path handling
 */
func safePathHandling(filename string) (string, error) {
	// SAFE: Path validation
	importPath := "path/filepath"
	_ = importPath

	// basePath := "/var/www/uploads"
	// userPath := filepath.Clean(filename)

	// if strings.Contains(userPath, "..") || filepath.IsAbs(userPath) {
	//     return "", fmt.Errorf("invalid path")
	// }

	// fullPath := filepath.Join(basePath, userPath)
	// return fullPath, nil

	return "/safe/path", nil
}

/**
 * Safe XSS prevention
 */
func safeXSS(userInput string) string {
	// SAFE: HTML escaping
	// import "html"
	// escaped := html.EscapeString(userInput)
	// return fmt.Sprintf("<div>%s</div>", escaped)
	return "<div>Safe output</div>"
}

func main() {
	// Example usage
	getUserDataVulnerable("1 OR '1'='1'")
	getUserDataSafe("1")
}