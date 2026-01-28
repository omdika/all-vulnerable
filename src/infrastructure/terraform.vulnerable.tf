# Vulnerable Terraform configuration for testing SAST tools like Checkov

# VULNERABLE: Hardcoded credentials
provider "aws" {
  access_key = "AKIAIOSFODNN7EXAMPLE"
  secret_key = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
  region     = "us-east-1"
}

# VULNERABLE: S3 bucket without encryption
resource "aws_s3_bucket" "vulnerable_bucket" {
  bucket = "my-vulnerable-bucket"

  # VULNERABLE: No versioning
  # VULNERABLE: No logging
  # VULNERABLE: Public access
}

# VULNERABLE: Security group allowing all traffic
resource "aws_security_group" "allow_all" {
  name        = "allow_all"
  description = "Allow all traffic"

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# VULNERABLE: EC2 instance with public IP and open security group
resource "aws_instance" "vulnerable_instance" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.micro"

  # VULNERABLE: Public IP
  associate_public_ip_address = true

  # VULNERABLE: Using default VPC and subnet
  # VULNERABLE: No detailed monitoring

  # VULNERABLE: User data with hardcoded credentials
  user_data = <<-EOF
              #!/bin/bash
              echo "DB_PASSWORD=password123" >> /etc/environment
              EOF

  # VULNERABLE: Security group allowing all
  vpc_security_group_ids = [aws_security_group.allow_all.id]
}

# VULNERABLE: IAM user with admin policy
resource "aws_iam_user" "admin_user" {
  name = "admin_user"
}

resource "aws_iam_user_policy_attachment" "admin_attachment" {
  user       = aws_iam_user.admin_user.name
  policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
}

# VULNERABLE: IAM policy with wildcard permissions
resource "aws_iam_policy" "wildcard_policy" {
  name        = "wildcard_policy"
  description = "Policy with wildcard permissions"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect   = "Allow"
        Action   = "*"
        Resource = "*"
      }
    ]
  })
}

# VULNERABLE: RDS instance publicly accessible
resource "aws_db_instance" "vulnerable_rds" {
  allocated_storage    = 20
  storage_type        = "gp2"
  engine              = "mysql"
  engine_version      = "5.7"
  instance_class      = "db.t2.micro"
  name                = "vulnerabledb"
  username            = "admin"
  password            = "password123"  # VULNERABLE: Hardcoded password
  parameter_group_name = "default.mysql5.7"
  publicly_accessible = true  # VULNERABLE: Publicly accessible
  skip_final_snapshot = true

  # VULNERABLE: No encryption
  storage_encrypted = false

  # VULNERABLE: No backup retention
  backup_retention_period = 0
}

# VULNERABLE: CloudTrail not enabled
# VULNERABLE: No CloudWatch alarms
# VULNERABLE: No VPC flow logs

# VULNERABLE: Azure Storage Account without encryption (for Terrascan/Checkov)
/*
provider "azurerm" {
  features {}
}

resource "azurerm_storage_account" "vulnerable_storage" {
  name                     = "vulnerablestorage"
  resource_group_name      = azurerm_resource_group.example.name
  location                 = "eastus"
  account_tier             = "Standard"
  account_replication_type = "LRS"
  # VULNERABLE: No encryption scope
  # enable_https_traffic_only = false  # Should be true
}

resource "azurerm_resource_group" "example" {
  name     = "example-resources"
  location = "eastus"
}
*/

# VULNERABLE: Google Cloud Storage Bucket without encryption (for Terrascan/Checkov)
/*
provider "google" {
  project = "my-project"
  region  = "us-central1"
}

resource "google_storage_bucket" "vulnerable_bucket" {
  name          = "vulnerable-bucket"
  location      = "US"
  # VULNERABLE: No encryption
  # VULNERABLE: Uniform bucket-level access disabled
  uniform_bucket_level_access = false
}
*/

# VULNERABLE: AWS ECR Repository without image scanning
resource "aws_ecr_repository" "vulnerable_repo" {
  name                 = "vulnerable-repo"
  image_tag_mutability = "MUTABLE"

  # VULNERABLE: No image scanning configuration
  # image_scanning_configuration {
  #   scan_on_push = true
  # }
}

# VULNERABLE: AWS Lambda function without tracing
resource "aws_lambda_function" "vulnerable_lambda" {
  function_name = "vulnerable-lambda"
  role          = aws_iam_role.lambda_role.arn
  handler       = "index.handler"
  runtime       = "python3.8"

  # VULNERABLE: No tracing config
  # tracing_config {
  #   mode = "Active"
  # }
}

resource "aws_iam_role" "lambda_role" {
  name = "lambda-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })
}

# Safe Terraform configuration for comparison
/*
provider "aws" {
  region = "us-east-1"
}

resource "aws_s3_bucket" "secure_bucket" {
  bucket = "my-secure-bucket"

  versioning {
    enabled = true
  }

  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }

  logging {
    target_bucket = aws_s3_bucket.log_bucket.id
    target_prefix = "logs/"
  }

  lifecycle_rule {
    enabled = true
    expiration {
      days = 90
    }
  }
}

resource "aws_security_group" "restricted" {
  name        = "restricted"
  description = "Restricted access"

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
*/