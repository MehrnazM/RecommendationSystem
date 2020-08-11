# Introduction
This project is a simple recommendation similar to Netflix when it suggests you a list of shows to watch or Amazon when it suggests you products to buy. 
This system presents two types of recommender to choose:

1. **General recommender**: It asks if you want to apply any filter on the results (genre, length, recent years, director(s)), and how many recommendations you want to
see. Also, it asks how many rating a movie should have to be a reliable result. For example if you decide that a movie should have at least 3 ratings, then 
the recommendations are among a list of movies that all of them have at least 3 ratings. After asking these above questions, the recommendation system show you top
rated movies (sorted from highest rating to lowest), based on your answers to the system's question.

2. **Exclusive recommender**: This recommender, besides all the same questions that are asked by the general recommender, asks you to rate some movies. You decide
how many movies you want to rate. These movies are top rated movies based on the filters you have set. After rating, the exclusive recommender suggests you top rated
movies based on the the list of the movies that raters with similar taste to you have rated. 



# Movie and Rater Databases
In data folder, there are 4 csv files. Two of these files are information about movies (one is a shorter version for testing purposes) and the other two are 
information about raters (one is a shorter version). <br/>*Each movie has an id and information about its genre, length, year of production, title, director, and a 
URL of a poster of that movie.<br/>*Each rater has an id, a list of movies they have rated (based on the movies ids), and the rating for each movie. <br/>In this 
project,there is a movie database and a rater database. These databases are initilized at the start of the program and any information that is needed from the data 
files is extracted from the related database. 

# Filters
The filters that the user should decide about are: genre, length of the movie (minimum length), year of production (for example if the user types 2000, all the 
suggestion will be after year 2000), and director(s) (all the suggestion movies will be directed by that director(s) or he/she will be one of the directors). 

# Calculation the average rating

**.General Recommendation:** This recommender simply generate a list of all movies that pass the filters and have the minimum number of ratings. Then it divides the 
sum of ratings for each movie by the number of ratings. Finally, it sorts the average list reversely.

**.Exclusive Recommendation:** This recommender asks the user to rate some movies (based on the filters that user chooses) and create a new rater with a new id in 
the rater database. Then it calculate the similarity scores of each rater comparing to the user. This is done by "dotProduct" method in ExclusiveAverage class. 
In this method, if a rater has shared movies with the user that they both rated, it it subtracts 5 from each rating and then add the multiplication of the user's 
new rating and the other rater's for each shared movie. Some scores are positive and some are negatives. The recommender only considers raters with positive 
similarity scores and creates a sorted list of similar raters.<br/>For exclusive recommendation, the program asks the user to choose a minimum number of ratings and
the number of similar raters that the user wants to see the recommendation from. The movies that pass the filters, should have at least the minimum number of 
ratings among the number of similar raters that the user has determined. For instance, if the minimum is 3, and the number of similar raters is 10, then each movie
should have at least 3 ratings from top 10 similar raters. If a movie has 3 ratings but only 2 of them are from top 10 raters, then this movie is not eligible. 
<br/>After generating a list of similar raters and list of movies that pass the filters and the minimumm number of ratings requirement, for each movie among the 
top number of raters of the list that the user decides on, the system calculates the average by dividing the sum of ratings (from top raters if they have rated that movie)
 by the number of ratings. At the end, the recommender sorts the average list reversely. 

