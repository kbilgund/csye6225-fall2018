echo "stack name $1"

aws cloudformation create-stack --stack-name ${1} --template-body file://csye6225-cf-cicd.json --capabilities CAPABILITY_NAMED_IAM
echo "creating"
aws cloudformation wait stack-create-complete --stack-name ${1}
echo "created"
