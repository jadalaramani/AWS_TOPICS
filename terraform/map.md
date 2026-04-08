# 🚀 Terraform Map Example

## 📌 Overview

Maps store key-value pairs.

## 🧱 Code Example

```hcl
variable "instance_ami" {
  default = {
    dev  = "ami-01b14b7ad41e17ba4"
    prod = "ami-0ec10929233384c7f"
  }
}

resource "aws_instance" "example" {
  ami           = var.instance_ami["dev"]
  instance_type = "t3.micro"
}

output "dev_ami" {
  value = var.instance_ami["dev"]
}
```

## 💡 Use Case

Used when values are mapped with keys.
