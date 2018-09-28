# AWS Cloud Formation using Script
## Prerequisites:
### AWS-cli:
Installing the [AWS Command Line Interface](https://docs.aws.amazon.com/cli/latest/userguide/installing.html)<br>
### jq:
Installation the jq<br>
`sudo dnf install jq`

## csye6225-aws-networking-teardown.sh-
It is used to delete the whole infrastructure we created using the setup commmand. It takes an input for *STACK_NAME* which is compare with all the description of VPC, Internet Gateway and Route Table and delete them in a proper sequence.
