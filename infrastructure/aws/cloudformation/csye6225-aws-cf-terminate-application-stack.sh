echo "stack name $1"
aws cloudformation delete-stack --stack-name ${1}
echo "deleting"
aws cloudformation wait stack-delete-complete --stack-name ${1}

echo " $1 stack deleted"
