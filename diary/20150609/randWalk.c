#include<stdio.h>
#include<time.h>
#include<stdlib.h>
int moveUp[2] = {0,-1};
int moveDown[2] = {0,1};
int moveLeft[2] = {-1,0};
int moveRight[2] = {1,0};
// {-1, 0} moveUp
// {1, 0} moveDown
// {0, -1} move Left
// {0, 1} move right
char graph[10][10];

void init(){
  for(int i=0;i<10;i++)
    for(int j=0;j<10;j++)
      graph[i][j] = '.';
}

int ifCanMove(int x, int y, int dir){
    int tmpX = x;
    int tmpY = y;
    if(dir == 0){
      tmpX += moveUp[0];
      tmpY += moveUp[1];
    } else if(dir == 1){
      tmpX += moveDown[0];
      tmpY += moveDown[1];
    } else if( dir == 2){
      tmpX += moveLeft[0];
      tmpY += moveLeft[1];
    } else if( dir == 3){
      tmpX += moveRight[0];
      tmpY += moveRight[1];
    }
    // we can not move out
    // Because the index of array starts from 0,
    // so the largest valid index is 9 instead of 10
    if(tmpX >= 0 && tmpX < 10 && tmpY >= 0 && tmpY < 10 && graph[tmpX][tmpY] == '.')
      return 1;
    else
      return 0;
}

int ifBlocked(int x, int y){
  // check if there are no ways to move
  int possibleDirections = 0;
  for(int i=0;i<4;i++){
    if(ifCanMove(x, y, i)){
      possibleDirections += 1;
    }
  }
  if(possibleDirections > 0)
    return 1;
  else
    return 0;
}

int main(){
  init();
  char currentLetter = 'A';
  int currentX = 0;
  int currentY = 0;
  graph[0][0] = currentLetter;
  currentLetter += 1;
  // initilize random number generator
  srand(time(NULL));
  while(ifBlocked(currentX, currentY) != 0){
    // generate random number
    int r = rand(); 
    // change random number to 0 ~ 4. Each number represents a direction
    int dir = r % 4;
    if(ifCanMove(currentX, currentY, dir)){
      if(dir == 0){
        currentX += moveUp[0];
        currentY += moveUp[1];
      } else if(dir == 1){
        currentX += moveDown[0];
        currentY += moveDown[1];
      } else if( dir == 2){
        currentX += moveLeft[0];
        currentY += moveLeft[1];
      } else if( dir == 3){
        currentX += moveRight[0];
        currentY += moveRight[1];
      }
      graph[currentX][currentY] = currentLetter;
      currentLetter += 1;
      if(currentLetter > 'Z')
        break;
    }
  }
  // ouput the graph
  for(int i=0;i<10;i++){
    for(int j=0;j<10;j++)
      printf("%c ", graph[i][j]);
    printf("\n");
  }
}
