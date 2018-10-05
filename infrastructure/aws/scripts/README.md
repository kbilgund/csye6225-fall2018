# AWS Cloud Formation using Script
## Prerequisites:
### AWS-cli:
Installing the [AWS Command Line Interface](https://docs.aws.amazon.com/cli/latest/userguide/installing.html)
### jq:
Installation the jq 
`sudo dnf install jq`

## csye6225-aws-networking-setup.sh-
This script setups the infrastructure in aws. It takes Stack Name as input and tags all the infrastructure to the tag name. it creates VPC, adds subnets to the vpc creates a routing table and add public route.

## csye6225-aws-networking-teardown.sh-
It is used to delete the whole infrastructure we created using the setup commmand. It takes an input for *STACK_NAME* which is compare with all the description of VPC, Internet Gateway and Route Table and delete them in a proper sequence.
