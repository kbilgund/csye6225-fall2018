echo Stack-${1}
echo Network-${2}
echo Domain-${3}
VpcName="${2}-csye6225-vpc"
IgName="${2}-csye6225-InternetGateway"
Domain="csye6225-fall2018-${3}.csye6225.com"

echo $VpcName
echo $RouteTableName
echo $Domain

VpcId=$(aws ec2 describe-vpcs --query  'Vpcs[?Tags[?Key==`Name`]|[?Value==`'${VpcName}'`]].VpcId' --output text)
echo "VpcId" $VpcId
ISubnetId=$(aws ec2 describe-subnets --filters Name=tag:Name,Values=PublicSubnet | jq '.Subnets[0].SubnetId' -r)
echo "InstanceSubnetId" $ISubnetId
PSubnetId=$(aws ec2 describe-subnets --filters Name=tag:Name,Values=PrivateSubnet1 | jq '.Subnets[0].SubnetId' -r)
GSubnetId=$(aws ec2 describe-subnets --filters Name=tag:Name,Values=PrivateSubnet2 | jq '.Subnets[0].SubnetId' -r)
echo "GroupSubnetId" $PSubnetId $GSubnetId
aws cloudformation create-stack --stack-name ${1} --template-body file://csye6225-cf-application.json  --parameters ParameterKey=VpcId,ParameterValue=$VpcId ParameterKey=InstanceSubnetId,ParameterValue=$ISubnetId ParameterKey=GroupSubnetId1,ParameterValue=$PSubnetId ParameterKey=GroupSubnetId2,ParameterValue=$GSubnetId ParameterKey=DomainName,ParameterValue=$Domain
echo "creating"
aws cloudformation wait stack-create-complete --stack-name ${1}
echo "created"
