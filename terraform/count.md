# 🚀 Terraform count Example

## 📌 Overview

This project demonstrates how to use the `count` meta-argument to create multiple identical resources.

## 🧱 Code Example

```hcl
resource "aws_instance" "example" {
  count         = 3
  ami           = "ami-0c3389a4fa5bddaad"
  instance_type = "t2.micro"

  tags = {
    Name = "server-${count.index}"
  }
}
```

## 🔁 Output

* server-0
* server-1
* server-2

## 💡 Use Case

Use `count` when resources are identical.
