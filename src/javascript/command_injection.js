/**
 * Command Injection vulnerabilities for JavaScript/Node.js.
 * Used for testing SAST tools.
 */

const { exec, spawn, execFile } = require('child_process');
const fs = require('fs');

/**
 * Vulnerable command execution using exec()
 */
function executeCommand(userInput, callback) {
    // VULNERABLE: Direct command concatenation
    const command = `ping -c 1 ${userInput}`;

    exec(command, (error, stdout, stderr) => {
        callback(error, stdout, stderr);
    });
}

/**
 * Command injection with shell: true
 */
function executeWithShell(userInput) {
    // VULNERABLE: shell: true with user input
    const command = `nslookup ${userInput}`;

    exec(command, { shell: true }, (error, stdout, stderr) => {
        console.log(stdout);
    });
}

/**
 * Vulnerable spawn with shell
 */
function spawnWithShell(userInput) {
    // VULNERABLE: spawn with shell and user input
    const child = spawn('echo', [userInput], { shell: true });

    child.stdout.on('data', (data) => {
        console.log(data.toString());
    });
}

/**
 * eval() injection
 */
function evalInjection(userInput) {
    // VULNERABLE: Evaluating user input
    return eval(userInput);
}

/**
 * Function constructor injection
 */
function functionConstructorInjection(userInput) {
    // VULNERABLE: Using Function constructor
    const func = new Function('return ' + userInput);
    return func();
}

/**
 * setTimeout with string evaluation
 */
function setTimeoutInjection(userInput) {
    // VULNERABLE: setTimeout with string
    setTimeout(userInput, 1000);
}

/**
 * setInterval with string evaluation
 */
function setIntervalInjection(userInput) {
    // VULNERABLE: setInterval with string
    setInterval(userInput, 1000);
}

/**
 * fs module injection
 */
function fsInjection(filename) {
    // VULNERABLE: User input in fs operations
    fs.readFile(filename, 'utf8', (err, data) => {
        console.log(data);
    });
}

/**
 * Path traversal with fs
 */
function pathTraversal(filename) {
    // VULNERABLE: Path traversal
    const filepath = `/var/www/uploads/${filename}`;
    fs.readFile(filepath, 'utf8', (err, data) => {
        console.log(data);
    });
}

/**
 * Safe command execution
 */
function safeCommandExecution(ipAddress, callback) {
    // SAFE: Input validation
    const ipRegex = /^[0-9.]+$/;

    if (!ipRegex.test(ipAddress)) {
        callback(new Error('Invalid IP address'));
        return;
    }

    // SAFE: Using array arguments without shell
    execFile('ping', ['-c', '1', ipAddress], (error, stdout, stderr) => {
        callback(error, stdout, stderr);
    });
}

/**
 * Safe eval alternative
 */
function safeEvalAlternative(userInput) {
    // SAFE: Using JSON.parse for JSON data
    try {
        return JSON.parse(userInput);
    } catch (e) {
        throw new Error('Invalid JSON');
    }
}

/**
 * Safe file read with path validation
 */
function safeFileRead(filename) {
    // SAFE: Path validation
    const path = require('path');

    const basePath = '/var/www/uploads';
    const userPath = path.normalize(filename);

    if (userPath.includes('..') || path.isAbsolute(userPath)) {
        throw new Error('Invalid path');
    }

    const fullPath = path.join(basePath, userPath);

    if (!fullPath.startsWith(basePath)) {
        throw new Error('Path traversal attempt');
    }

    return fs.readFileSync(fullPath, 'utf8');
}

module.exports = {
    executeCommand,
    executeWithShell,
    spawnWithShell,
    evalInjection,
    functionConstructorInjection,
    setTimeoutInjection,
    setIntervalInjection,
    fsInjection,
    pathTraversal,
    safeCommandExecution,
    safeEvalAlternative,
    safeFileRead
};