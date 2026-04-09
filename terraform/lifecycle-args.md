

# Lifecycle Arguments 

Lifecycle rules control how Terraform handles **create/update/delete**.

---

##  a) create_before_destroy

```hcl
resource "aws_instance" "example" {
  ami           = "ami-123456"
  instance_type = "t2.micro"

  lifecycle {
    create_before_destroy = true
  }
}
```

### 🔍 Use Case:

* When updating instance → new instance created first → old deleted
* Avoids downtime (important in production)

---

##  b) prevent_destroy

```hcl
resource "aws_db_instance" "prod_db" {
  identifier = "prod-db"
  engine     = "mysql"

  lifecycle {
    prevent_destroy = true
  }
}
```

### 🔍 Use Case:

* Protect critical resources (like DB)
* Terraform will throw error if someone tries to destroy

---

##  c) ignore_changes

```hcl
resource "aws_instance" "example" {
  ami           = "ami-123456"
  instance_type = "t2.micro"

  tags = {
    Name = "MyInstance"
  }

  lifecycle {
    ignore_changes = [tags]
  }
}
```

### 🔍 Use Case:

* Ignore manual changes (like tags updated outside Terraform)
* Prevent unnecessary recreation

---



👉 **"Why lifecycle is used?"**

You can answer:

> "Lifecycle arguments in Terraform are used to control resource behavior during updates, such as preventing accidental deletion, avoiding downtime using create_before_destroy, and ignoring external changes using ignore_changes."

---

##  Real-Time Scenario 

* Production DB → `prevent_destroy`
* Zero downtime deployment → `create_before_destroy`
* External tagging (by ops team) → `ignore_changes`

---

If you want, I can also give:
✅ Terraform **dynamic blocks example**
✅ Terraform **for_each vs count interview explanation**
✅ Full **README.md format (like your task requirement)**
