/**
 * Web vulnerabilities for JavaScript (Node.js and browser).
 * Used for testing SAST tools.
 */

const express = require('express');
const app = express();

/**
 * Reflected XSS vulnerability (Node.js/Express)
 */
app.get('/xss', (req, res) => {
    const name = req.query.name || '';

    // VULNERABLE: Direct output of user input
    res.send(`<h1>Hello ${name}</h1>`);
});

/**
 * Stored XSS simulation
 */
function displayComment(comment) {
    // VULNERABLE: No output encoding
    return `<div class="comment">${comment}</div>`;
}

/**
 * DOM-based XSS
 */
function domXSS() {
    // Browser-side vulnerable code
    const userInput = window.location.hash.substring(1);

    // VULNERABLE: innerHTML with user input
    document.getElementById('output').innerHTML = userInput;
}

/**
 * Open Redirect vulnerability
 */
app.get('/redirect', (req, res) => {
    const url = req.query.url || '/';

    // VULNERABLE: No validation of redirect URL
    res.redirect(url);
});

/**
 * HTTP Response Splitting
 */
app.get('/response-splitting', (req, res) => {
    const userAgent = req.headers['user-agent'] || '';

    // VULNERABLE: User input in headers
    res.set('X-User-Agent', userAgent);
    res.send('OK');
});

/**
 * Insecure cookie handling
 */
app.get('/set-cookie', (req, res) => {
    const userId = req.query.userId || '';

    // VULNERABLE: Missing HttpOnly and Secure flags
    res.cookie('sessionId', userId, { maxAge: 3600000 });
    res.send('Cookie set');
});

/**
 * Server-Side Request Forgery (SSRF)
 */
app.get('/ssrf', async (req, res) => {
    const url = req.query.url || '';
    const https = require('https');

    // VULNERABLE: User controls URL
    https.get(url, (response) => {
        let data = '';
        response.on('data', (chunk) => {
            data += chunk;
        });
        response.on('end', () => {
            res.send(data);
        });
    }).on('error', (err) => {
        res.status(500).send('Error');
    });
});

/**
 * JSON Injection
 */
app.get('/json-injection', (req, res) => {
    const userData = req.query.data || '{}';

    // VULNERABLE: Direct JSON parsing without validation
    const data = JSON.parse(userData);

    // VULNERABLE: JSONP callback injection
    const callback = req.query.callback || 'callback';
    res.set('Content-Type', 'application/javascript');
    res.send(`${callback}(${JSON.stringify(data)})`);
});

/**
 * Prototype Pollution
 */
function mergeObjects(target, source) {
    // VULNERABLE: Prototype pollution
    for (const key in source) {
        if (source.hasOwnProperty(key)) {
            target[key] = source[key];
        }
    }
    return target;
}

/**
 * Regex Denial of Service (ReDoS)
 */
function vulnerableRegex(input) {
    // VULNERABLE: Exponential time regex
    const regex = /^([a-zA-Z0-9]+)*$/;
    return regex.test(input);
}

/**
 * Unsafe deserialization
 */
function unsafeDeserialization(serialized) {
    // VULNERABLE: Using eval for deserialization
    return eval(`(${serialized})`);
}

// Safe examples
/**
 * Safe XSS prevention
 */
app.get('/safe-xss', (req, res) => {
    const name = req.query.name || '';

    // SAFE: Using template engine with auto-escaping
    // Or manually escape
    const escapedName = escapeHtml(name);
    res.send(`<h1>Hello ${escapedName}</h1>`);
});

function escapeHtml(text) {
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, (m) => map[m]);
}

/**
 * Safe redirect with validation
 */
app.get('/safe-redirect', (req, res) => {
    const url = req.query.url || '/';

    // SAFE: Validate redirect URL
    const allowedDomains = ['example.com', 'mysite.com'];
    const parsedUrl = new URL(url, 'http://localhost');

    if (allowedDomains.includes(parsedUrl.hostname)) {
        res.redirect(url);
    } else {
        res.redirect('/');
    }
});

/**
 * Secure cookie handling
 */
app.get('/secure-cookie', (req, res) => {
    const userId = req.query.userId || '';

    // SAFE: HttpOnly and Secure flags
    res.cookie('sessionId', userId, {
        maxAge: 3600000,
        httpOnly: true,
        secure: process.env.NODE_ENV === 'production',
        sameSite: 'strict'
    });
    res.send('Secure cookie set');
});

/**
 * Safe JSON parsing
 */
function safeJsonParse(jsonString) {
    // SAFE: Using JSON.parse with try-catch
    try {
        return JSON.parse(jsonString);
    } catch (e) {
        throw new Error('Invalid JSON');
    }
}

module.exports = {
    app,
    displayComment,
    domXSS,
    mergeObjects,
    vulnerableRegex,
    unsafeDeserialization,
    escapeHtml,
    safeJsonParse
};

if (require.main === module) {
    app.listen(3000, () => {
        console.log('Server running on port 3000');
    });
}