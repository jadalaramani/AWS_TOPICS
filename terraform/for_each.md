# 🚀 Terraform EC2 Deployment using for_each

## 📌 Overview

This Terraform project demonstrates how to use the **`for_each`** meta-argument to create multiple EC2 instances dynamically based on a list of environment names.

Instead of writing separate resource blocks, we use `for_each` to efficiently provision multiple instances with minimal code.

---

## 🧱 Project Structure

```
.
├── main.tf
├── variables.tf
└── README.md
```

---

## ⚙️ Configuration Details

### 🔹 Input Variable

```hcl
variable "instances" {
  default = ["dev", "qa", "prod"]
}
```

* Defines a list of environments.
* Each value will be used to create one EC2 instance.

---

### 🔹 EC2 Resource using for_each

```hcl
resource "aws_instance" "myec2" {
  for_each = toset(var.instances)

  ami           = "ami-0c3389a4fa5bddaad"
  instance_type = "t3.micro"

  tags = {
    Name = each.value
  }
}
```

---

## 🔁 How it Works

* `toset(var.instances)` converts the list into a set.
* `for_each` iterates over each value (`dev`, `qa`, `prod`).
* Terraform creates **3 EC2 instances**:

  * Instance 1 → Name: dev
  * Instance 2 → Name: qa
  * Instance 3 → Name: prod

---

## 🧪 Prerequisites

* Terraform installed
* AWS CLI configured (`aws configure`)
* IAM user with EC2 permissions

---

## ▶️ Usage

### 1️⃣ Initialize Terraform

```bash
terraform init
```

### 2️⃣ Validate Configuration

```bash
terraform validate
```

### 3️⃣ Preview Changes

```bash
terraform plan
```

### 4️⃣ Apply Configuration

```bash
terraform apply
```

Type `yes` when prompted.

---

## 🧹 Cleanup

To destroy all created resources:

```bash
terraform destroy
```

---

## 📊 Expected Output

After applying:

* 3 EC2 instances will be created
* Each instance will have a unique tag based on environment name

---

## 💡 Key Benefits of for_each

* Avoids duplicate code
* Easy to scale (just update variable list)
* Cleaner and more maintainable Terraform code

---

## 📌 Notes

* Ensure the AMI ID is valid for your AWS region
* You can modify instance types or add more tags as needed


