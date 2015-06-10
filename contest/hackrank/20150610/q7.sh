read length
for (( i=0; i<110; i++))
do
  dist[i]=0
done

read -a numbers

#echo "numerb is", $numbers
#numbers=($numbers)
#echo "numerb is", ${numbers[@]}

for i in ${numbers[@]}
do
	#echo $i
	dist[$i]=`expr ${dist[$i]} + 1`
done

for (( i=0 ; i<110 ; i++))
do
  if [ "`expr ${dist[i]} % 2`" -eq "1" ];
  then
  	#echo ${dist[i]},$i
    echo $i
  fi
done
