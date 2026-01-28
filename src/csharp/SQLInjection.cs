/**
 * SQL Injection vulnerabilities for C#.
 * Used for testing SAST tools.
 */

using System;
using System.Data;
using System.Data.SqlClient;

namespace VulnerableApp
{
    public class SQLInjection
    {
        // Hardcoded credentials
        private const string DB_USER = "admin";
        private const string DB_PASSWORD = "password123";
        private const string CONNECTION_STRING = "Server=localhost;Database=test;User Id=admin;Password=password123;";

        /**
         * Vulnerable SQL query with string concatenation
         */
        public DataTable GetUserDataVulnerable(string userId)
        {
            using (SqlConnection conn = new SqlConnection(CONNECTION_STRING))
            {
                conn.Open();

                // VULNERABLE: Direct string concatenation
                string query = "SELECT * FROM users WHERE id = '" + userId + "'";

                SqlCommand cmd = new SqlCommand(query, conn);
                SqlDataAdapter adapter = new SqlDataAdapter(cmd);
                DataTable dt = new DataTable();
                adapter.Fill(dt);

                return dt;
            }
        }

        /**
         * Multiple parameter injection
         */
        public DataTable SearchUsersVulnerable(string name, string email)
        {
            using (SqlConnection conn = new SqlConnection(CONNECTION_STRING))
            {
                conn.Open();

                // VULNERABLE: Multiple parameters concatenated
                string query = "SELECT * FROM users WHERE name = '" + name
                             + "' AND email = '" + email + "'";

                SqlCommand cmd = new SqlCommand(query, conn);
                SqlDataAdapter adapter = new SqlDataAdapter(cmd);
                DataTable dt = new DataTable();
                adapter.Fill(dt);

                return dt;
            }
        }

        /**
         * SQL injection in ORDER BY clause
         */
        public DataTable GetUsersSortedVulnerable(string sortColumn)
        {
            using (SqlConnection conn = new SqlConnection(CONNECTION_STRING))
            {
                conn.Open();

                // VULNERABLE: User input in ORDER BY
                string query = "SELECT * FROM users ORDER BY " + sortColumn;

                SqlCommand cmd = new SqlCommand(query, conn);
                SqlDataAdapter adapter = new SqlDataAdapter(cmd);
                DataTable dt = new DataTable();
                adapter.Fill(dt);

                return dt;
            }
        }

        /**
         * Command injection vulnerability
         */
        public void CommandInjectionVulnerable(string userInput)
        {
            // VULNERABLE: Command injection
            System.Diagnostics.Process.Start("ping", "-c 1 " + userInput);
        }

        /**
         * Path traversal vulnerability
         */
        public string PathTraversalVulnerable(string filename)
        {
            // VULNERABLE: Path traversal
            string path = @"C:\uploads\" + filename;

            return System.IO.File.ReadAllText(path);
        }

        /**
         * XSS vulnerability simulation
         */
        public string XssVulnerable(string userInput)
        {
            // VULNERABLE: No output encoding
            return "<div>" + userInput + "</div>";
        }

        /**
         * Insecure deserialization
         */
        public object DeserializeVulnerable(string serializedData)
        {
            System.Runtime.Serialization.Formatters.Binary.BinaryFormatter formatter =
                new System.Runtime.Serialization.Formatters.Binary.BinaryFormatter();

            // VULNERABLE: Deserializing untrusted data
            using (System.IO.MemoryStream ms = new System.IO.MemoryStream(Convert.FromBase64String(serializedData)))
            {
                return formatter.Deserialize(ms);
            }
        }

        /**
         * Hardcoded credentials
         */
        public bool CheckPasswordVulnerable(string input)
        {
            // VULNERABLE: Hardcoded password
            string password = "Admin@123";

            return input == password;
        }

        /**
         * Weak cryptographic algorithm
         */
        public string WeakCryptoVulnerable(string data)
        {
            // VULNERABLE: MD5 is cryptographically weak
            using (System.Security.Cryptography.MD5 md5 = System.Security.Cryptography.MD5.Create())
            {
                byte[] hash = md5.ComputeHash(System.Text.Encoding.UTF8.GetBytes(data));
                return BitConverter.ToString(hash).Replace("-", "");
            }
        }

        // Safe examples

        /**
         * Safe SQL query using parameterized statements
         */
        public DataTable GetUserDataSafe(string userId)
        {
            using (SqlConnection conn = new SqlConnection(CONNECTION_STRING))
            {
                conn.Open();

                // SAFE: Using parameterized query
                string query = "SELECT * FROM users WHERE id = @userId";

                SqlCommand cmd = new SqlCommand(query, conn);
                cmd.Parameters.AddWithValue("@userId", userId);

                SqlDataAdapter adapter = new SqlDataAdapter(cmd);
                DataTable dt = new DataTable();
                adapter.Fill(dt);

                return dt;
            }
        }

        /**
         * Safe command execution
         */
        public void SafeCommandExecution(string ipAddress)
        {
            // SAFE: Input validation
            if (System.Text.RegularExpressions.Regex.IsMatch(ipAddress, @"^[0-9.]+$"))
            {
                System.Diagnostics.Process.Start("ping", "-c 1 " + ipAddress);
            }
        }

        /**
         * Safe path handling
         */
        public string SafePathHandling(string filename)
        {
            // SAFE: Path validation
            string basePath = @"C:\uploads\";
            string fullPath = System.IO.Path.Combine(basePath, filename);
            fullPath = System.IO.Path.GetFullPath(fullPath);

            if (!fullPath.StartsWith(basePath))
            {
                throw new System.Security.SecurityException("Invalid path");
            }

            return System.IO.File.ReadAllText(fullPath);
        }

        /**
         * Safe XSS prevention
         */
        public string SafeXss(string userInput)
        {
            // SAFE: HTML encoding
            return "<div>" + System.Web.HttpUtility.HtmlEncode(userInput) + "</div>";
        }

        /**
         * Safe deserialization
         */
        public T SafeDeserialize<T>(string jsonData) where T : class
        {
            // SAFE: Using JSON serializer instead of BinaryFormatter
            return Newtonsoft.Json.JsonConvert.DeserializeObject<T>(jsonData);
        }
    }
}