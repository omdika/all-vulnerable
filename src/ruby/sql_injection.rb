# SQL Injection vulnerabilities for Ruby.
# Used for testing SAST tools.

require 'sqlite3'
require 'mysql2'

# Hardcoded credentials
DB_USER = 'admin'
DB_PASSWORD = 'password123'
DB_NAME = 'test'

# Vulnerable SQL query with string concatenation
def get_user_data_vulnerable(user_id)
  client = Mysql2::Client.new(
    host: 'localhost',
    username: DB_USER,
    password: DB_PASSWORD,
    database: DB_NAME
  )

  # VULNERABLE: Direct string concatenation
  query = "SELECT * FROM users WHERE id = '#{user_id}'"

  result = client.query(query)

  users = []
  result.each do |row|
    users << row
  end

  users
end

# Multiple parameter injection
def search_users_vulnerable(name, email)
  db = SQLite3::Database.new 'test.db'

  # VULNERABLE: Multiple parameters concatenated
  query = "SELECT * FROM users WHERE name = '#{name}' AND email = '#{email}'"

  result = db.execute(query)

  result
end

# SQL injection in ORDER BY clause
def get_users_sorted_vulnerable(sort_column)
  client = Mysql2::Client.new(
    host: 'localhost',
    username: DB_USER,
    password: DB_PASSWORD,
    database: DB_NAME
  )

  # VULNERABLE: User input in ORDER BY
  query = "SELECT * FROM users ORDER BY #{sort_column}"

  result = client.query(query)

  users = []
  result.each do |row|
    users << row
  end

  users
end

# Command injection vulnerability
def command_injection_vulnerable(user_input)
  # VULNERABLE: Command injection
  command = "ping -c 1 #{user_input}"

  # VULNERABLE: Using backticks or system with user input
  output = `#{command}`

  output
end

# Path traversal vulnerability
def path_traversal_vulnerable(filename)
  # VULNERABLE: Path traversal
  path = "/var/www/uploads/#{filename}"

  content = File.read(path)

  content
end

# XSS vulnerability simulation
def xss_vulnerable(user_input)
  # VULNERABLE: No output encoding
  "<div>#{user_input}</div>"
end

# ERB template injection
def erb_injection_vulnerable(template)
  require 'erb'

  # VULNERABLE: User-controlled template
  erb = ERB.new(template)
  erb.result(binding)
end

# YAML.load vulnerability
def yaml_load_vulnerable(yaml_data)
  require 'yaml'

  # VULNERABLE: YAML.load with user input
  YAML.load(yaml_data)
end

# Marshal.load vulnerability
def marshal_load_vulnerable(data)
  # VULNERABLE: Marshal.load with user input
  Marshal.load(data)
end

# Hardcoded credentials
def check_password_vulnerable(input)
  # VULNERABLE: Hardcoded password
  password = "Admin@123"

  input == password
end

# Weak cryptographic algorithm
def weak_crypto_vulnerable(data)
  require 'digest'

  # VULNERABLE: MD5 is cryptographically weak
  Digest::MD5.hexdigest(data)
end

# Safe examples

# Safe SQL query using parameterized statements
def get_user_data_safe(user_id)
  client = Mysql2::Client.new(
    host: 'localhost',
    username: DB_USER,
    password: DB_PASSWORD,
    database: DB_NAME
  )

  # SAFE: Using parameterized query
  query = "SELECT * FROM users WHERE id = ?"

  result = client.prepare(query).execute(user_id)

  users = []
  result.each do |row|
    users << row
  end

  users
end

# Safe command execution
def safe_command_execution(ip_address)
  # SAFE: Input validation
  if ip_address =~ /^[0-9.]+$/
    # SAFE: Using system with array arguments
    system('ping', '-c', '1', ip_address)
  end
end

# Safe path handling
def safe_path_handling(filename)
  # SAFE: Path validation
  base_path = "/var/www/uploads/"
  user_path = File.expand_path(filename, base_path)

  if user_path.start_with?(File.expand_path(base_path))
    content = File.read(user_path)
    return content
  end

  raise SecurityError, "Invalid path"
end

# Safe XSS prevention
def safe_xss(user_input)
  require 'cgi'

  # SAFE: HTML escaping
  escaped = CGI.escapeHTML(user_input)
  "<div>#{escaped}</div>"
end

# Safe YAML loading
def safe_yaml_load(yaml_data)
  require 'yaml'

  # SAFE: Using safe_load
  YAML.safe_load(yaml_data, permitted_classes: [Symbol, Time])
end

# Example usage
if __FILE__ == $0
  users = get_user_data_vulnerable("1 OR '1'='1'")
  puts users.inspect
end