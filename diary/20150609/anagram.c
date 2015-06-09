#include<stdio.h>
#include<string.h>

void countLetterDist(char* word, int* distArray){
  // This function counts the occurences of each letter in word

  // distArray[0] means how many 'a' in word
  // distArray[1] means how many 'b' in word
  // etc.

  // Initially, all values in distArray are 0. If there is an 'a' or 'A' in word,
  // we add 1 to distArray[0]. If there is a 'b' or 'B' in word, we add 1 to distArray[1].
  // If there is a 'c' or 'C' in word, we add 1 to distArray[2]. It's similar to other letters
  for(int i=0;i<strlen(word);i++){
    if(word[i] >= 'A' && word[i] <= 'Z')
      // if word[i] is 'A', word[i] - 'A' is 0
      // if word[i] is 'B', word[i] - 'A' is 1
      distArray[word[i] - 'A'] ++;
    else if(word[i] >= 'a' && word[i] <= 'z')
      // if word[i] is 'a', word[i] - 'a' is 0
      // if word[i] is 'b', word[i] - 'b' is 1
      distArray[word[i] - 'a'] ++;
  }
}

int main(){
  char word[10000];
  int letterDistsOne[26];
  int letterDistsTwo[26];
  memset(letterDistsOne, 0, sizeof(letterDistsOne));
  memset(letterDistsTwo, 0, sizeof(letterDistsTwo));
  printf("Enter first word: ");
  scanf("%s", word);
  countLetterDist(word, letterDistsOne);
  printf("Enter second word: ");
  scanf("%s", word);
  countLetterDist(word, letterDistsTwo);
  // 1 is true. 0 is false.
  int anagram = 1;
  for(int i=0; i<26; i++){
    if(letterDistsOne[i] != letterDistsTwo[i])
      anagram = 0;
  }
  if(anagram)
    printf("The words are anagrams.\n");
  else
    printf("The words are not anagrams.\n");
}
