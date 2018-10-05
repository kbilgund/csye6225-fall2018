#!/bin/bash
echo "stack name $1"
VpcName="${1}-csye6225-vpc"
IgName="${1}-csye6225-InternetGateway"
RouteTableName="${1}-csye6225-public-route-table"
SubnetArray=()
NumberSubnets=2
AZArray=('us-east-2a' 'us-east-2b' 'us-east-2c');

{
	VpcId=$(aws ec2 create-vpc --cidr-block 10.0.0.0/16 | jq -r '.Vpc.VpcId') &&
	aws ec2 create-tags --resources $VpcId --tags Key=Name,Value=$VpcName

	for i in `seq 0 $NumberSubnets`; do
		SubnetArray+=($(aws ec2 create-subnet --availability-zone ${AZArray[$i]} --vpc-id $VpcId --cidr-block 10.0.${i}.0/24| jq -r '.Subnet.SubnetId'))
	done

	IgId=$(aws ec2 create-internet-gateway | jq -r '.InternetGateway.InternetGatewayId')
	aws ec2 create-tags --resources $IgId --tags Key=Name,Value=$IgName

	aws ec2 attach-internet-gateway --internet-gateway-id $IgId --vpc-id $VpcId

	RouteId=$(aws ec2 create-route-table --vpc-id $VpcId | jq -r '.RouteTable.RouteTableId')
	aws ec2 create-tags --resources $RouteId --tags Key=Name,Value=$RouteTableName

	for i in `seq 0 $NumberSubnets`; do
		aws ec2 associate-route-table --route-table-id $RouteId --subnet-id ${SubnetArray[$i]}
	done

	aws ec2 create-route --route-table-id $RouteId --destination-cidr-block 0.0.0.0/0 --gateway-id $IgId
	
} ||

{
		echo "Failed stack creation"
}
