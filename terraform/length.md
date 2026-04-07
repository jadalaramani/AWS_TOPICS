# 🚀 Terraform length() Function

## 📌 Overview

Calculates number of elements in a list.

## 🧱 Code Example

```hcl
variable "users" {
  default = ["ram", "sam", "john"]
}

output "total_users" {
  value = length(var.users)
}
```

## 🔁 Output

3
