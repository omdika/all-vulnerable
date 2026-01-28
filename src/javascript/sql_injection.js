/**
 * SQL Injection vulnerabilities for JavaScript/Node.js.
 * Used for testing SAST tools.
 */

const mysql = require('mysql');

// Hardcoded credentials
const DB_CONFIG = {
    host: 'localhost',
    user: 'admin',
    password: 'password123',
    database: 'test'
};

/**
 * Vulnerable SQL query with string concatenation
 */
function getUserData(userId, callback) {
    const connection = mysql.createConnection(DB_CONFIG);
    connection.connect();

    // VULNERABLE: Direct string concatenation
    const query = `SELECT * FROM users WHERE id = '${userId}'`;

    connection.query(query, (error, results) => {
        callback(error, results);
        connection.end();
    });
}

/**
 * Multiple parameter injection
 */
function searchUsers(name, email, callback) {
    const connection = mysql.createConnection(DB_CONFIG);
    connection.connect();

    // VULNERABLE: Multiple parameters concatenated
    const query = `SELECT * FROM users WHERE name = '${name}' AND email = '${email}'`;

    connection.query(query, (error, results) => {
        callback(error, results);
        connection.end();
    });
}

/**
 * SQL injection in ORDER BY clause
 */
function getUsersSorted(sortColumn, callback) {
    const connection = mysql.createConnection(DB_CONFIG);
    connection.connect();

    // VULNERABLE: User input in ORDER BY
    const query = `SELECT * FROM users ORDER BY ${sortColumn}`;

    connection.query(query, (error, results) => {
        callback(error, results);
        connection.end();
    });
}

/**
 * NoSQL injection (MongoDB example)
 */
function nosqlInjection(userId) {
    const MongoClient = require('mongodb').MongoClient;

    // VULNERABLE: User input in query object
    const query = {
        $where: `this.userId === '${userId}'`
    };

    // Simulated MongoDB query
    return query;
}

/**
 * Safe SQL query using parameterized statements
 */
function getUserDataSafe(userId, callback) {
    const connection = mysql.createConnection(DB_CONFIG);
    connection.connect();

    // SAFE: Using parameterized query
    const query = 'SELECT * FROM users WHERE id = ?';

    connection.query(query, [userId], (error, results) => {
        callback(error, results);
        connection.end();
    });
}

/**
 * SQLite3 vulnerable example
 */
function sqlite3Injection(userId) {
    const sqlite3 = require('sqlite3').verbose();
    const db = new sqlite3.Database(':memory:');

    // VULNERABLE: String concatenation
    db.all(`SELECT * FROM users WHERE id = '${userId}'`, (err, rows) => {
        return rows;
    });
}

/**
 * Sequelize ORM with raw query (vulnerable)
 */
function sequelizeRawQuery(userId) {
    const { Sequelize, DataTypes } = require('sequelize');
    const sequelize = new Sequelize('database', 'username', 'password', {
        host: 'localhost',
        dialect: 'mysql'
    });

    // VULNERABLE: Raw query with string concatenation
    const query = `SELECT * FROM users WHERE id = '${userId}'`;

    return sequelize.query(query, {
        type: Sequelize.QueryTypes.SELECT
    });
}

module.exports = {
    getUserData,
    searchUsers,
    getUsersSorted,
    nosqlInjection,
    getUserDataSafe,
    sqlite3Injection,
    sequelizeRawQuery
};