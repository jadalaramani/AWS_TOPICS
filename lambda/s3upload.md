#  Real-Time Example: **Auto Image Processing**

### Scenario

When a user uploads an image to **S3**, Lambda will **resize the image automatically**.

---

##  Architecture

User Upload → **S3 Bucket** → **Lambda Trigger** → **Process Image** → **Save to another S3 folder**

---

#  Step-by-Step Implementation

## Step 1: Create S3 Bucket

Create a bucket.

Example

```
ramani-image-upload
```

Inside bucket create folders:

```
uploads/
resized/
```

---

## Step 2: Create Lambda Function

Go to **AWS Console → Lambda → Create Function**

Settings:

* Function name: `image-resize`
* Runtime: `Python 3.10`
* Permissions: Create new role

---

## Step 3: Lambda Code Example (Python)

```python
import json
import boto3

s3 = boto3.client('s3')

def lambda_handler(event, context):
    
    bucket = event['Records'][0]['s3']['bucket']['name']
    key = event['Records'][0]['s3']['object']['key']
    
    print("File uploaded:", key)
    
    response = s3.copy_object(
        Bucket=bucket,
        CopySource={'Bucket': bucket, 'Key': key},
        Key="resized/" + key.split("/")[-1]
    )
    
    return {
        'statusCode': 200,
        'body': json.dumps('File processed successfully')
    }
```

---

## Step 4: Add S3 Trigger

In Lambda:

**Add Trigger → S3**

Configuration:

```
Bucket: ramani-image-upload
Event type: PUT
Prefix: uploads/
```

This means:

Whenever a file uploads to

```
uploads/
```

Lambda will trigger.

---

## Step 5: Test

Upload file:

```
uploads/test.jpg
```

Result:

Lambda runs and copies file to:

```
resized/test.jpg
```

---

#  End-to-End Flow

```
User Upload Image
        ↓
S3 Bucket (uploads/)
        ↓
S3 Event Trigger
        ↓
AWS Lambda Function
        ↓
Process File
        ↓
Save to resized/ folder
```

---

#  Pricing Concept

You pay based on:

* **Number of requests**
* **Execution time (milliseconds)**
* **Memory used**

Example:

```
1000 requests per month → Almost FREE
```

---

# ⭐ Another Real-Time Lambda Use Cases

| Use Case        | Example              |
| --------------- | -------------------- |
| File Processing | S3 image resize      |
| API Backend     | Lambda + API Gateway |
| Automation      | Start/Stop EC2       |
| Log Processing  | CloudWatch Logs      |
| Notifications   | Send email on events |
