
#  EC2 Auto Start/Stop EventBridge + Lambda

##  Project Overview

This project automates the **starting and stopping of EC2 instances** using:

* AWS Lambda (Python)
* EventBridge (Scheduler)



#  Architecture

```
EventBridge (Cron Schedule)
        ↓
Lambda Function (Python - Boto3)
        ↓
EC2 Instance (Start / Stop)
```

---

#  Prerequisites

* AWS Account
* EC2 Instance (stopped/running)
* IAM permissions
* Basic knowledge of AWS services

---

#  Step 1: Create IAM Role for Lambda

1. Go to **IAM → Roles → Create Role**
2. Select **AWS Service → Lambda**
3. Attach policy:

### Minimal Policy:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ec2:StartInstances",
        "ec2:StopInstances",
        "ec2:DescribeInstances"
      ],
      "Resource": "*"
    }
  ]
}
```

or 

ec2fullaccess policy

4. Role Name: `lambda-ec2-scheduler-role`

---

#  Step 2: Create Lambda Function

1. Go to **Lambda → Create Function**
2. Runtime: **Python 3.x**
3. Attach the IAM role created above

###  Lambda Code

```python
import boto3

region = 'us-east-1'
instances = ['i-05294b6b5b9ad61e2']

ec2 = boto3.client('ec2', region_name=region)

def lambda_handler(event, context):
    action = event.get('action')

    if action == 'start':
        ec2.start_instances(InstanceIds=instances)
        print('Started instances: ' + str(instances))

    elif action == 'stop':
        ec2.stop_instances(InstanceIds=instances)
        print('Stopped instances: ' + str(instances))

    else:
        print('Invalid action. Use "start" or "stop".')
```

4. Click **Deploy**

---

#  Step 3: Test Lambda

### Test Event (Start)

```json
{
  "action": "start"
}
```

### Test Event (Stop)

```json
{
  "action": "stop"
}
```

 Verify in EC2 dashboard:

* stopped → running
* running → stopped

---

#  Step 4: Create EventBridge Rule (STOP)

1. Go to **EventBridge → Rules → Create Rule**
2. Name: `stop-ec2`
3. Rule Type: **Schedule**

### Cron (7 PM IST)

```
cron(30 13 * * ? *)
```

---

##  Target Configuration

* Target: Lambda Function
* Select your Lambda

### Input (VERY IMPORTANT)

Choose:
 **Constant (JSON text)**

```json
{
  "action": "stop"
}
```

---

#  Step 5: Create EventBridge Rule (START)

1. Create another rule: `start-ec2`

### Cron (9 AM IST)

```
cron(30 3 * * ? *)
```

### Input:

```json
{
  "action": "start"
}
```

---

#  Step 6: Verification

* Wait for scheduled time OR manually trigger rule
* Check Lambda logs in CloudWatch
* Verify EC2 state changes

---

#  Common Issues

| Issue            | Reason                   | Fix                           |
| ---------------- | ------------------------ | ----------------------------- |
| Invalid action   | No input passed          | Add JSON input in EventBridge |
| EC2 not starting | Wrong instance ID        | Verify ID                     |
| Permission error | IAM role missing access  | Add EC2 permissions           |
| Time mismatch    | Using IST instead of UTC | Convert to UTC                |

---

#  Real-Time Use Case

* Stop Dev/QA instances at night
* Start in morning automatically
* Save AWS cost (30–60%)

---resume project (strong impact)**
