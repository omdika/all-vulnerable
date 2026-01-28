# Terraform configuration with TFLint-detectable violations
# TFLint focuses on Terraform best practices, conventions, and potential errors

# VULNERABLE: terraform_deprecated_interpolation - Deprecated interpolation syntax
variable "environment" {
  default = "${var.env}"
}

# VULNERABLE: terraform_unused_declarations - Unused variables
variable "unused_variable" {
  default = "unused"
}

# VULNERABLE: terraform_comment_syntax - Invalid comment syntax
// Invalid comment style for Terraform
# Another invalid comment

# VULNERABLE: terraform_documented_variables - Missing variable descriptions
variable "instance_type" {
  # No description
}

# VULNERABLE: terraform_documented_outputs - Missing output descriptions
output "instance_id" {
  value = aws_instance.example.id
}

# VULNERABLE: terraform_documented_resources - Missing resource descriptions
resource "aws_instance" "example" {
  # No description in resource
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.micro"
}

# VULNERABLE: terraform_typed_variables - Variables without type constraints
variable "security_groups" {
  default = ["sg-12345678"]
}

# VULNERABLE: terraform_naming_convention - Naming convention violations
resource "aws_security_group" "BadlyNamed-SG" {  # Should use underscores
  name        = "BadlyNamed-SG"
  description = "Security group with bad naming"
}

# VULNERABLE: terraform_required_version - Missing required_version
# terraform {
#   required_version = ">= 0.12"
# }

# VULNERABLE: terraform_required_providers - Missing required_providers block
# provider "aws" {
#   version = "~> 3.0"
# }

# VULNERABLE: terraform_unused_required_providers - Unused providers
# provider "google" {
#   project = "my-project"
# }

# VULNERABLE: terraform_module_pinned_source - Module source not pinned
module "vpc" {
  source = "terraform-aws-modules/vpc/aws"  # Should pin to version
}

# VULNERABLE: terraform_module_version - Missing module version
module "ec2" {
  source  = "terraform-aws-modules/ec2-instance/aws"
  version = "~> 2.0"  # This is fine, but often missing
}

# VULNERABLE: terraform_standard_module_structure - Non-standard module structure
# Missing main.tf, variables.tf, outputs.tf

# VULNERABLE: aws_resource_missing_tags - AWS resources missing tags
resource "aws_s3_bucket" "example" {
  bucket = "my-bucket"
  # No tags
}

# VULNERABLE: aws_instance_previous_type - Using previous generation instance types
resource "aws_instance" "previous_gen" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = "m1.small"  # Previous generation
}

# VULNERABLE: aws_route_not_specified_target - Route without target
resource "aws_route" "example" {
  route_table_id = aws_route_table.example.id
  # No destination_cidr_block or other target
}

resource "aws_route_table" "example" {
  vpc_id = aws_vpc.example.id
}

resource "aws_vpc" "example" {
  cidr_block = "10.0.0.0/16"
}

# VULNERABLE: aws_s3_bucket_name - Invalid S3 bucket name
resource "aws_s3_bucket" "invalid_name" {
  bucket = "Invalid.Bucket.Name"  # Dots are problematic
}

# VULNERABLE: aws_db_instance_previous_type - Using previous generation DB instance
resource "aws_db_instance" "example" {
  allocated_storage    = 20
  storage_type        = "gp2"
  engine              = "mysql"
  engine_version      = "5.7"
  instance_class      = "db.t2.micro"  # Previous generation
  name                = "exampledb"
  username            = "admin"
  password            = "password123"  # Hardcoded, also a violation
}

# VULNERABLE: aws_elasticache_cluster_previous_type - Previous generation cache
resource "aws_elasticache_cluster" "example" {
  cluster_id           = "example-cache"
  engine              = "redis"
  node_type           = "cache.m1.small"  # Previous generation
  num_cache_nodes     = 1
  parameter_group_name = "default.redis3.2"
}

# VULNERABLE: azurerm_resource_missing_tags - Azure resources missing tags
# resource "azurerm_resource_group" "example" {
#   name     = "example-resources"
#   location = "West Europe"
#   # No tags
# }

# VULNERABLE: google_resource_missing_labels - GCP resources missing labels
# resource "google_compute_instance" "example" {
#   name         = "example-instance"
#   machine_type = "e2-micro"
#   zone         = "us-central1-a"
#   # No labels
# }

# VULNERABLE: terraform_deprecated_index_syntax - Deprecated index syntax
output "first_subnet" {
  value = aws_subnet.example.0.id  # Should be [0]
}

resource "aws_subnet" "example" {
  count = 2
  vpc_id     = aws_vpc.example.id
  cidr_block = "10.0.${count.index}.0/24"
}

# VULNERABLE: terraform_unnecessary_escape - Unnecessary escape sequences
variable "regex" {
  default = "\\d+"  # Unnecessary escape
}

# VULNERABLE: terraform_sensitive_variable_no_default - Sensitive variable with default
variable "db_password" {
  type      = string
  sensitive = true
  default   = "password123"  # Sensitive variable shouldn't have default
}

# VULNERABLE: terraform_module_provider_alias - Module with provider alias
provider "aws" {
  alias  = "us_east_1"
  region = "us-east-1"
}

module "module_with_alias" {
  source = "./modules/example"
  providers = {
    aws = aws.us_east_1  # Modules shouldn't use provider aliases
  }
}

# VULNERABLE: terraform_locals_order - Locals after resources
resource "aws_instance" "another" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.micro"
}

locals {
  instance_name = "example"  # Should be before resources
}

# Safe Terraform for comparison
/*
terraform {
  required_version = ">= 1.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
}

variable "instance_type" {
  type        = string
  description = "EC2 instance type"
  default     = "t3.micro"
}

resource "aws_instance" "example" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = var.instance_type

  tags = {
    Name        = "example-instance"
    Environment = "production"
    ManagedBy   = "terraform"
  }
}

output "instance_id" {
  value       = aws_instance.example.id
  description = "The ID of the EC2 instance"
}
*/