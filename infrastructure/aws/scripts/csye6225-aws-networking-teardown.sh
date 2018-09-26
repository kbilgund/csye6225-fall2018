STACK_NAME=$1
vpc_name="{STACK_NAME}-csye6225-vpc"
internetgateway_name="{STACK_NAME}-csye6225-InternetGateway"
routetable_name="{STACK_NAME}-csye6225-public-route-table"
subnet1="{STACK_NAME}-csye6225-subnet1"
subnet2="{STACK_NAME}-csye6225-subnet2"
subnet3="{STACK_NAME}-csye6225-subnet3"



ig_id=$(aws ec2 describe-internet-gateway | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value==internetgateway_name)| .InternetGatewayId' -r)
vpc_id=$(aws ec2 describe-vpc | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value==vpc_name)| .VpcId' -r)
rt_id=$(aws ec2 describe-route-tables | jq '.RouteTables | .[] | select(.Tags[0] != null) | select(.Tags[0].Value==routetable_name)| .RouteTableId' -r)
# sub1=$(aws ec2 describe-subnets | jq '.Subnets[1].SubnetId' -r)
# sub2=$(aws ec2 describe-subnets | jq '.Subnets[3].SubnetId' -r)
# sub3=$(aws ec2 describe-subnets | jq '.Subnets[2].SubnetId' -r)
 # echo ${rt_id}

aws ec2 detach-internet-gateway --internet-gateway-id $ig_id --vpc-id $vpc_id
aws ec2 delete-internet-gateway --internet-gateway-id $ig_id
# aws ec2 delete-subnet --subnet-id $sub1
# aws ec2 delete-subnet --subnet-id $sub2
# aws ec2 delete-subnet --subnet-id $sub3
aws ec2 delete-route-table --route-table-id $rt_id
aws ec2 delete-vpc --vpc-id $vpc_id
