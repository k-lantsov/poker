## Poker game console application

**Given:** _poker.txt_ text file consisted of with data about poker game hands for two players,
each row correspond separate round of a game

**Task:** compute how many rounds will first player win?

**Description:** by the task it's necessary to consider situations when two players had got the same poker combinations and determine who's win in result

**Additionally:** considered situations with draw rounds


### Used technologies: Java 17, Maven, JUnit5, AssertJ for assertions

**How does it work:**
1) reading of the text file line by line
2) formation of poker hands for both players
3) determination of combinations of the poker hands
4) comparison of the combinations
5) if the combinations are the same then additional checking who is winner of the round
6) if it's player #1 then increase counter of his rounds

**What was done well:**
1) considered situations with draw rounds
2) after refactoring I was able to avoid multiple checks in some classes - via applications of chain of responsibility and strategy design patterns