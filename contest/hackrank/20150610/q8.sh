#!/bin/bash
function outputTree {
	local indx=0
	for ((i=0;i<63;i++))
	do
	  for ((j=0;j<100;j++))
	  do
	    printf "%s" ${matrix[$indx]}
	    indx=$(( $indx + 1 ))
	  done
	  echo
	done
}

function doNothing {
	local indx=0
	for ((i=0;i<63;i++))
	do
	  for ((j=0;j<100;j++))
	  do
	    indx=$(( $indx + 1 ))
	  done
	done
}

function drawSubTree {
  local startX=$1
  local startY=$2
  local length=$3
  local currentDepth=$4
  local maxDepth=$5
  local indx=0

  if [ $currentDepth -eq $maxDepth ]; then
  	#echo "Equal return"
  	return
  fi

  currentDepth=$(( $currentDepth + 1 ))

  #echo "currentDepth is", $currentDepth, "length is", $length, "maxDepth", $maxDepth

  for((i=0;i<length;i++))
  do
  	startX=$(( $startX - 1 ))
  	indx=$(( $startX * 100 + $startY ))
  	matrix[$indx]='1'
  done
  
  local tmpX=$startX
  local tmpY=$startY

  for((i=0;i<length;i++))
  do
  	startX=$(( $startX - 1 ))
  	startY=$(( $startY - 1 ))
  	indx=$(( $startX * 100 + $startY ))
  	matrix[$indx]='1'
  done


  drawSubTree $startX $startY $(( $length / 2 )) $currentDepth $maxDepth
  
  #echo "length is", $length

  startX=$tmpX
  startY=$tmpY

  for((i=0;i<length;i++))
  do
  	startX=$(( $startX - 1 ))
  	startY=$(( $startY + 1 ))
  	indx=$(( $startX * 100 + $startY ))
  	matrix[$indx]='1'
  done

  drawSubTree $startX $startY $(( $length / 2 )) $currentDepth $maxDepth
  #outputTree
  
  doNothing

  return
}


indx=0

for ((i=0;i<100;i++))
do
  for ((j=0;j<63;j++))
  do
    matrix[$indx]='_'
    # indx=`expr $indx + 1`
    indx=$(( $indx + 1 ))
  done
done

read iteration

#iteration=5

#outputTree

startX=63
startY=49

for (( i=0; i < $iteration; i++ ))
do
	drawSubTree $startX $startY 16 0 $iteration
done

outputTree
