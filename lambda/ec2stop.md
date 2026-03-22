 **stop your EC2 instance using EventBridge + Lambda**

#  Step 1: Create IAM Role for Lambda

1. Go to **IAM → Roles → Create Role**
2. Select **AWS Service → Lambda**
3. Attach policy:

   *  `AmazonEC2FullAccess`

###  Minimal Policy (Recommended)

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["ec2:StopInstances"],
      "Resource": "*"
    }
  ]
}
```

4. Name: `lambda-ec2-stop-role`

---

#  Step 2: Create Lambda Function

1. Go to **Lambda → Create Function**
2. Choose:

   * Runtime: **Python 3.x**
   * Role: Select the role created above

### Add this code:

```python
import boto3

region = 'us-east-1'
instances = ['i-05294b6b5b9ad61e2']

ec2 = boto3.client('ec2', region_name=region)

def lambda_handler(event, context):
    ec2.stop_instances(InstanceIds=instances)
    print('stopped your instances: ' + str(instances))
```

3. Click **Deploy**

---

#  Step 3: Test Lambda 

1. Click **Test**
2. Create test event:test
3. Run → Check logs → Instance should stop 

---

#  Step 4: Create EventBridge Rule (Scheduler)

1. Go to **EventBridge → Rules → Create Rule**
2. Name: `stop-ec2-daily`
3. Rule type: **Schedule**

---

#  Step 5: Configure Schedule

You can choose:

### Option 1: Fixed time (Daily)

Example: Stop every day at 7 PM IST

Use **Cron Expression**:

```
cron(30 13 * * ? *)
```

Explanation:

* 13:30 UTC = 7:00 PM IST

---

#  Step 6: Add Target

1. Target type: **Lambda function**
2. Select your Lambda
3. Click **Next → Create Rule**

---

#  Step 7: Verify

* Wait for scheduled time 
* Check EC2 → Instance should stop automatically


