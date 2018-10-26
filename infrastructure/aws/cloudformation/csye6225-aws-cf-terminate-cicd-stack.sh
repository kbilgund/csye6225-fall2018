echo "stack name $1"

aws s3 rm s3://code-deploy.csye6225-fall2018-${2} --recursive


aws cloudformation delete-stack --stack-name ${1}
echo "deleting"
aws cloudformation wait stack-delete-complete --stack-name ${1}

echo " $1 stack deleted"
