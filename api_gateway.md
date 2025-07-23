API Gateway is like a front door to your backend (like Lambda, EC2, or other services).

## Create Lambda Functions
 Create 1st Lambda: get-lambda
 
* Go to Lambda
* Click Create function
* Name: get-lambda
* Runtime: Python 3.12
* Set execution role: create new role
* Click Create function

Scroll to Function code and paste the below code:

#### Lambda: get-lambda ( GET USER end point)

```
import json

def lambda_handler(event, context):
    
    print(f'event: {event}');
    
    # this users object can be fetched from database  etc.
    users =  [
                {"id": 1, "name": "Ramani"}, 
                {"id": 2, "name": "Devops"}
             ]
        
    return {
        'statusCode': 200,
        'body': json.dumps(users)
    }
```
- Repeat for: POST-LAMBDA same as Get-lambda
 
#### Lambda: post-lambda ( POST USER end point)

```
import json

def lambda_handler(event, context):
    
    print(f'event: {event}')
    print(f'Posting user details')
    # save the information database
    
    return {
        'statusCode': 200,
        'body': json.dumps('User added')
    }
```
#### Create API Gateway REST API
Go to API Gateway

Choose Create API

Choose REST API → Click Build

Name it: user

Endpoint type: Regional → Create API

REST API base created.

#### Create Resources & Methods

➤ Resource: /user
Click Actions > Create Resource

Resource name: user

Resource path: /order → Click Create Resource

Method: GET

Select /user → Click Actions > Create Method → Choose GET

Integration type: Lambda Function

Enter Lambda function name: get-lambda

Click Save → Confirm permission prompt

Method: POST

Select /user → Click Actions > Create Method → Choose POST

Integration type: Lambda Function

Enter Lambda function name: post-lambda

Click Save → Confirm permission prompt

### Deploy the API
Click Actions > Deploy API

Choose [New Stage]

Name: dev → Click Deploy

You’ll get a base URL like:

```bash
https://abc123.execute-api.us-east-1.amazonaws.com/dev
```
