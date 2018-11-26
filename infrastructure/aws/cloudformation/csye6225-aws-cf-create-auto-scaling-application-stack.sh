echo Stack-${1}
echo Network-${2}
echo Domain-${3}
VpcName="${2}-csye6225-vpc"
IgName="${2}-csye6225-InternetGateway"
Domain="csye6225-fall2018-${3}.csye6225.com"
DNS="csye6225-fall2018-${3}.me."

echo $VpcName
echo $RouteTableName
echo $Domain


CDArn=$(aws iam get-role --role-name CodeDeployServiceRole | jq .Role.Arn -r)
echo "CDArn" $CDArn
VpcId=$(aws ec2 describe-vpcs --query  'Vpcs[?Tags[?Key==`Name`]|[?Value==`'${VpcName}'`]].VpcId' --output text)
echo "VpcId" $VpcId
ELB3=$(aws ec2 describe-subnets --filters Name=tag:Name,Values=PublicSubnet | jq '.Subnets[0].SubnetId' -r)
echo "ELBSubnetId3" $ELB3
ELB1=$(aws ec2 describe-subnets --filters Name=tag:Name,Values=AZ1 | jq '.Subnets[0].SubnetId' -r)
echo "ELBSubnetId1" $ELB1
ELB2=$(aws ec2 describe-subnets --filters Name=tag:Name,Values=AZ2 | jq '.Subnets[0].SubnetId' -r)
echo "ELBSubnetId2" $ELB2
PSubnetId=$(aws ec2 describe-subnets --filters Name=tag:Name,Values=PrivateSubnet1 | jq '.Subnets[0].SubnetId' -r)
GSubnetId=$(aws ec2 describe-subnets --filters Name=tag:Name,Values=PrivateSubnet2 | jq '.Subnets[0].SubnetId' -r)
echo "GroupSubnetId" $PSubnetId $GSubnetId
CertificateArn=$(aws acm list-certificates | jq .CertificateSummaryList[0].CertificateArn -r)
aws cloudformation create-stack --stack-name ${1} --template-body file://csye6225-cf-auto-scaling-application.json  --parameters ParameterKey=VpcId,ParameterValue=$VpcId ParameterKey=ELBId1,ParameterValue=$ELB1 ParameterKey=ELBId2,ParameterValue=$ELB2 ParameterKey=ELBId3,ParameterValue=$ELB3 ParameterKey=GroupSubnetId1,ParameterValue=$PSubnetId ParameterKey=GroupSubnetId2,ParameterValue=$GSubnetId ParameterKey=DomainName,ParameterValue=$Domain ParameterKey=Domain,ParameterValue=$DNS ParameterKey=CertificateArn,ParameterValue=$CertificateArn ParameterKey=CDArn,ParameterValue=$CDArn
echo "creating"
aws cloudformation wait stack-create-complete --stack-name ${1}
echo "created"
