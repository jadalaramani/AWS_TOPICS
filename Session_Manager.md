
##  What is Session Manager in AWS?

Session Manager is a capability of AWS Systems Manager that lets you securely connect to your Amazon EC2 instances and on-premises servers *without needing SSH, bastion hosts, or open inbound ports*.


##  Why Use Session Manager?

| Traditional Way       | Session Manager             |
| --------------------- | --------------------------- |
| SSH with key pairs    | No SSH needed               |
| Open port 22          | No open ports               |
| Bastion host required | No bastion host             |
| Hard to audit         | Centralized logging & audit |


##  Benefits of Session Manager

1.  Secure Access:

   * No need for SSH or inbound ports.
   * Works over HTTPS using AWS infrastructure.

2.  IAM-Controlled:

   * Access is managed with IAM policies (fine-grained control).

3.  Logging & Auditing:

   * Logs session activity to Amazon CloudWatch Logs or S3.

4.  No Key Management:

   * You don’t need to manage SSH key pairs.

5.  CLI, Console, or SDK Access:

   * You can start sessions from:

     * AWS Console
     * AWS CLI (`aws ssm start-session`)
     * SDKs


##  How Session Manager Works

1. Install SSM Agent:

   * EC2 instances need SSM Agent installed and running.
   * It comes pre-installed in Amazon Linux, Ubuntu, and Windows AMIs.

2. Attach IAM Role to Instance:

   * Example Policy: `AmazonSSMManagedInstanceCore`
   * This allows the instance to communicate with Systems Manager.

3. User Permissions:

   * The IAM user or role initiating the session needs permission to use Session Manager.
   * Example Policy: Allow `ssm:StartSession`, `ssm:TerminateSession`, etc.

4. Connectivity:

   * The instance must have internet access or access to SSM endpoints (via VPC endpoints if in private subnet).


##  Hands-On: Enable Session Manager

### 🔹 Step 1: EC2 Setup

* Launch an Amazon Linux 2 EC2 instance.

### 🔹 Step 2: Create or Use an IAM Role
You need an IAM role with the AmazonSSMManagedInstanceCore policy.

Go to IAM > Roles

Click Create role

Choose AWS service > EC2

Click Next

Attach policy: AmazonSSMManagedInstanceCore

Click Next > Name the role (e.g., SSMAccessRole) > Create role


### 🔹 Step 3: Verify SSM Agent is Running

* Amazon Linux 2 already has the agent.
* To check:

  ```bash
  sudo systemctl status amazon-ssm-agent
  ```

If it's not active, start it:

```bash
sudo systemctl start amazon-ssm-agent
```

### 🔹 Step 3:  Attach IAM Role to EC2 Instance

Go to EC2 Dashboard

Select your instance

Click Actions > Security > Modify IAM Role

Choose the IAM Role you just created (SSMAccessRole)

Save


### 🔹 Step 4: Start a Session

* Console:

1. Go to AWS Console
2. Navigate to Systems Manager > Session Manager
3. Click "Start session"
4. Select the EC2 instance you want to connect to (should be in “managed” state)
5. Click "Start session"
6. A terminal will open in the browser


##  Logging Session Output

You can configure AWS Systems Manager to log all session output to:

* Amazon S3 (for storage)
* CloudWatch Logs (for monitoring & auditing)

### Steps:

1. Go to Systems Manager → Session Manager → Preferences.
2. Enable session logging.
3. Choose log group (CloudWatch) or S3 bucket.

##  End a Session (Optional)

### From Console:

* Click "Terminate" in the browser session

##  Security Best Practices

* Use IAM policies to restrict access.
* Tag EC2 instances and restrict access using resource-level permissions.
* Enable CloudTrail for full audit logging.
* Use VPC endpoints for private network access.

##  Use Cases

* Admin access to EC2 without opening SSH.
* Troubleshooting Linux/Windows servers.
* Run commands across fleets securely.
* Easier compliance and auditing.


### Summary

SSM = Secure, Centralized, Scalable Server Management

It simplifies managing EC2 instances and systems across AWS without logging in and keeps everything auditable, secure, and automated.




