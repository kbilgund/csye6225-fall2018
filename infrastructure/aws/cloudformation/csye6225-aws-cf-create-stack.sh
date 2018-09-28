
echo "stack name $1"

VpcName="${1}-csye6225-vpc"
IgName="${1}-csye6225-InternetGateway"
RouteTableName="${1}-csye6225-public-route-table"

aws cloudformation create-stack --stack-name ${1} --template-body file://csye6225-cf-networking.json --parameters ParameterKey=VPCName,ParameterValue=$VpcName ParameterKey=InternetGatewayName,ParameterValue=$IgName ParameterKey=RouteTableName,ParameterValue=$RouteTableName
echo "creating"
aws cloudformation wait stack-create-complete --stack-name ${1}
echo "created"
