STACK_NAME=$1
vpc_name="{STACK_NAME}-csye6225-vpc"
internetgateway_name="{STACK_NAME}-csye6225-InternetGateway"
routetable_name="{STACK_NAME}-csye6225-public-route-table"
subnet1="{STACK_NAME}-csye6225-subnet1"
subnet2="{STACK_NAME}-csye6225-subnet2"
subnet3="{STACK_NAME}-csye6225-subnet3"

ig_id=$(aws ec2 describe-internet-gateways | jq '.InternetGateways | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="internetgateway_name")| .InternetGatewayId' -r)
vpc_id=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="routetable_name")| .VpcId' -r)
rt_id=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="routetable_name")| .RouteTableId' -r)
sub1=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="routetable_name")| .Associations | .[0] | .SubnetId' -r)
sub2=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="routetable_name")| .Associations | .[1] | .SubnetId' -r)
sub3=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value=="routetable_name")| .Associations | .[2] | .SubnetId' -r)
 
aws ec2 detach-internet-gateway --internet-gateway-id $ig_id --vpc-id $vpc_id
aws ec2 delete-internet-gateway --internet-gateway-id $ig_id
aws ec2 delete-subnet --subnet-id $sub1
aws ec2 delete-subnet --subnet-id $sub2
aws ec2 delete-subnet --subnet-id $sub3
aws ec2 delete-route-table --route-table-id $rt_id
aws ec2 delete-vpc --vpc-id $vpc_id
