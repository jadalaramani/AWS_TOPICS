**AWS Transit Gateway Setup**

Connect multiple VPCs (e.g., VPC A, B, C) using AWS Transit Gateway (TGW) in a hub-and-spoke model to enable cross-VPC communication without full mesh VPC peering.


###  Prerequisites:

* Three VPCs in the same region (e.g., VPC A: 10.0.0.0/16, VPC B: 10.1.0.0/16, VPC C: 10.2.0.0/16)
* At least one public/private subnet per VPC
* EC2 instances launched in each VPC

###  Step-by-Step Implementation:

####  Step 1: Create the Transit Gateway

1. Open the **VPC Console**
2. In the sidebar, choose **Transit Gateways** > **Create Transit Gateway**
3. Enter:

   * Name tag: `My-TGW`
   * ASN: Default (64512) or custom
   * Enable DNS support: Yes
   * Default route table association/propagation: Yes
4. Click **Create Transit Gateway**



####  Step 2: Create Transit Gateway Attachments

Repeat for each VPC (A, B, C):

1. Go to **Transit Gateway Attachments** > **Create attachment**
2. Select Transit Gateway: `My-TGW`
3. Attachment type: VPC
4. Select VPC: (A / B / C)
5. Select at least 1 subnet from the VPC
6. Click **Create attachment**



####  Step 3: Accept Attachments (If auto-accept is OFF)

* Go to **Transit Gateway Attachments**
* For each pending attachment, click **Actions > Accept**



####  Step 4: Configure Transit Gateway Route Table

1. Go to **Transit Gateway Route Tables**
2. Use default or create a new one
3. For each attachment:

   * **Associate** with the route table
   * **Propagate** routes to allow route learning



####  Step 5: Update VPC Route Tables

**In VPC A route table:**

* Destination: `10.1.0.0/16` ➔ Target: Transit Gateway
* Destination: `10.2.0.0/16` ➔ Target: Transit Gateway

**In VPC B route table:**

* Destination: `10.0.0.0/16` ➔ Target: Transit Gateway
* Destination: `10.2.0.0/16` ➔ Target: Transit Gateway

**In VPC C route table:**

* Destination: `10.0.0.0/16` ➔ Target: Transit Gateway
* Destination: `10.1.0.0/16` ➔ Target: Transit Gateway



####  Step 6: Test Connectivity

* Use `ping`, `ssh`, or HTTP from EC2s across VPCs
* Ensure:

  * Security Groups allow traffic between VPC CIDRs
  * NACLs do not block inter-VPC traffic

