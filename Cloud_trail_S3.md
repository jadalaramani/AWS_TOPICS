
##  What is AWS CloudTrail?

AWS CloudTrail is a service that enables governance, compliance, operational auditing, and risk auditing of your AWS account. It records all actions (called events) taken by users, roles, or services in AWS and delivers log files to Amazon S3.



##  Use Cases of CloudTrail

* Track who made changes in your account.
* Monitor API activity for auditing and troubleshooting.
* Detect unauthorized access.
* Integrate with CloudWatch for alerts.



##  Step-by-Step Hands-On Lab: CloudTrail with S3



###  Step 1: Login to AWS Console

* Open: [https://console.aws.amazon.com](https://console.aws.amazon.com)
* Make sure you're in the preferred AWS region (e.g., `us-east-1`).



### 📁 Step 2: Create an S3 Bucket

1. Go to the S3 service.
2. Click on Create bucket.
3. Provide a unique Bucket name, e.g., `cloudtrail-logs-ramani`.
4. Uncheck Block all public access (keep it checked for security, only uncheck for testing).
5. Click Create bucket.

✅ Note: S3 will store CloudTrail logs in a structured format like:

```
s3://cloudtrail-logs-ramani/AWSLogs/<account-id>/CloudTrail/<region>/<year>/<month>/<day>/...
```



### 🔐 Step 3: Configure Permissions 

Go to the S3 bucket → Permissions tab:

* You can attach a Bucket Policy to allow CloudTrail to write logs.

Example policy:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "AWSCloudTrailWrite",
      "Effect": "Allow",
      "Principal": {
        "Service": "cloudtrail.amazonaws.com"
      },
      "Action": "s3:PutObject",
      "Resource": "arn:aws:s3:::cloudtrail-logs-ramani/AWSLogs/*",
      "Condition": {
        "StringEquals": {
          "s3:x-amz-acl": "bucket-owner-full-control"
        }
      }
    }
  ]
}
```



###  Step 4: Create a CloudTrail Trail

1. Go to CloudTrail in the AWS Console.
2. Click on Create trail.
3. Trail name: `MyTrail`
4. Apply trail to all regions →  Yes
5. Storage location:

   * Choose Create a new S3 bucket or select the one you created.
6. Log file SSE encryption → Leave default (enabled)
7. Choose Create



###  Step 5: Generate Events

Now perform any AWS actions, e.g.:

* Launch an EC2 instance
* Create/delete an IAM user
* Modify a security group

Each of these will generate a CloudTrail event.


### 📂 Step 6: View Logs in S3

1. Go to the S3 bucket you used for CloudTrail.
2. Navigate to:

   ```
   AWSLogs/<your-account-id>/CloudTrail/<region>/...
   ```
3. Download the `.gz` log file.
4. Unzip and open the `.json` file to see the events.

Sample event:

```json
{
  "eventVersion": "1.05",
  "userIdentity": {
    "type": "IAMUser",
    "userName": "testuser"
  },
  "eventTime": "2025-06-19T09:10:23Z",
  "eventName": "RunInstances",
  "awsRegion": "us-east-1",
  "sourceIPAddress": "203.0.113.25",
  "userAgent": "aws-cli/2.7.15",
  "requestParameters": {...},
  "responseElements": {...}
}
```

## 🎯 Final Tips:

* CloudTrail is enabled by default, but it only stores 90 days of event history in the console. Persistent storage requires a trail with an S3 bucket.
* You can create multiple trails per account for different auditing purposes.
* Use Athena or CloudWatch Insights for advanced querying on CloudTrail logs.
