echo "stack name $1"
Domain="code-deploy.csye6225-fall2018-${2}"
cdarn="arn:aws:s3:::code-deploy.csye6225-fall2018-${2}/*"
csarn="arn:aws:s3:::csye6225-fall2018-${2}.csye6225.com/*"

aws cloudformation create-stack --stack-name ${1} --template-body file://csye6225-cf-cicd.json --parameters ParameterKey=DomainName,ParameterValue=$Domain ParameterKey=CDARN,ParameterValue=$cdarn ParameterKey=CSARN,ParameterValue=$csarn --capabilities CAPABILITY_NAMED_IAM
echo "creating"
aws cloudformation wait stack-create-complete --stack-name ${1}
echo "created"
