echo "stack name $1"
VpcName="${1}-csye6225-vpc"
IgName="${1}-csye6225-InternetGateway"
RouteTableName="${1}-csye6225-public-route-table"

echo "VPC-" $VpcName
echo "IG-" $IgName
echo "RTB-" $RouteTableName

ig_id=$(aws ec2 describe-internet-gateways | jq '.InternetGateways | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="rohan-csye6225-InternetGateway")| .InternetGatewayId' -r)
echo "IG id" $ig_id
vpc_id=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="rohan-csye6225-public-route-table")| .VpcId' -r)
echo "VPC id" $vpc_id
rtb_id=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="rohan-csye6225-public-route-table")| .RouteTableId' -r)
echo "RTB id" $rtb_id
sub1=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="rohan-csye6225-public-route-table")| .Associations | .[0] | .SubnetId' -r)
sub2=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="rohan-csye6225-public-route-table")| .Associations | .[1] | .SubnetId' -r)
sub3=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="rohan-csye6225-public-route-table")| .Associations | .[2] | .SubnetId' -r)
echo "Subnet1" $sub1
echo "Subnet2" $sub2
echo "Subnet3" $sub3

aws ec2 detach-internet-gateway --internet-gateway-id $ig_id --vpc-id $vpc_id
echo "detach ig"
aws ec2 delete-internet-gateway --internet-gateway-id $ig_id
echo "delete ig"
aws ec2 delete-subnet --subnet-id $sub1
aws ec2 delete-subnet --subnet-id $sub2
aws ec2 delete-subnet --subnet-id $sub3
echo "delete subnets"
aws ec2 delete-route-table --route-table-id $rtb_id
echo "delete route table"
aws ec2 delete-vpc --vpc-id $vpc_id
echo "delete vpc"
