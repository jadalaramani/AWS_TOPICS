# 🚀 Terraform List Example

## 📌 Overview

Demonstrates how to use lists in Terraform.

## 🧱 Code Example

```hcl
variable "regions" {
  default = ["us-east-1", "us-west-1", "ap-south-1"]
}

output "first_region" {
  value = var.regions[0]
}

output "second_region" {
  value = var.regions[1]
}

output "third_region" {
  value = var.regions[2]
}
```

## 💡 Use Case

Lists store ordered values and can be accessed using index.
