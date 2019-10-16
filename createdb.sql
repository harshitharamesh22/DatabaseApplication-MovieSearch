{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf600
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 CREATE TABLE Movies (\
  movieID varchar(100) PRIMARY KEY,\
  title varchar(500),\
  imdbMovieId varchar(100),\
  spanishTitle varchar(200),\
  imdbPictureUrl varchar(2500),\
  year INTEGER,\
  rotMovieId varchar(100),\
  rotAllCriticsRating NUMBER,\
  rotAllCriticsReviewsCount NUMBER,\
  rotAllCriticsFreshScore NUMBER,\
  rotAllCriticsRotScore NUMBER,\
  rotAllCriticsAvgScore NUMBER,\
  rotTopCriticsRating NUMBER,\
  rotTopCriticsReviewsCount NUMBER,\
  rotTopCriticsFreshScore NUMBER,\
  rotTopCriticsRotScore NUMBER,\
  rotTopCriticsScore NUMBER,\
  rotAudienceRating NUMBER,\
  rotAudienceRatingCount NUMBER,\
  rotAudienceAvgScore NUMBER,\
  rotPictureUrl varchar(2500)\
);\
\
CREATE TABLE Movie_genres (\
  movieID varchar(100),\
  genres varchar(100),\
  PRIMARY KEY (movieID, genres),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE\
);\
\
CREATE TABLE Movie_directors (\
  movieID varchar(100),\
  directorID varchar(100),\
  directorName varchar(100),\
  PRIMARY KEY (movieID, directorID),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE\
);\
\
CREATE TABLE Movie_actors (\
  movieID varchar(50),\
  actorID varchar(100),\
  actorName varchar(100),\
  ranking integer,\
  PRIMARY KEY (movieID, actorID),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE\
);\
\
CREATE TABLE Movie_countries (\
  movieID varchar(100) PRIMARY KEY,\
  country varchar(350),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE\
);\
\
CREATE TABLE Movie_locations (\
  movieID varchar(100),\
  location1_country varchar(350),\
  location2_state varchar(350),\
  location3_city varchar(350),\
  location4_street varchar(350),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE\
);\
--\
CREATE TABLE Tags (\
  tagID varchar(100) PRIMARY KEY,\
  tagText varchar(100)\
);\
\
CREATE TABLE Movie_tags (\
  movieID varchar(100),\
  tagID varchar(100) NOT NULL,\
  tagWeight NUMBER NOT NULL,\
  PRIMARY KEY (movieID, tagID, tagWeight),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE,\
  FOREIGN KEY (tagID) REFERENCES Tags (tagID) ON DELETE CASCADE\
);\
\
CREATE TABLE User_taggedmovies (\
  userID varchar(50),\
  movieID varchar(50),\
  tagID varchar(50),\
  date_day NUMBER(2,0),\
  date_month NUMBER(2,0),\
  date_year NUMBER(4,0),\
  date_hour NUMBER(2,0),\
  date_minute NUMBER(2,0),\
  date_second NUMBER(2,0),\
  PRIMARY KEY (userID, movieID, tagID),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE,\
  FOREIGN KEY (tagID) REFERENCES Tags (tagID) ON DELETE CASCADE\
);\
\
create table user_taggedmovies_timestamp(\
  userID varchar(100) NOT NULL,\
  movieID varchar(100) NOT NULL,\
  tagID varchar(100) NOT NULL,\
  timestamp char(50),\
  PRIMARY KEY (userID, movieID, tagID),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE,\
  FOREIGN KEY (tagID) REFERENCES Tags (tagID) ON DELETE CASCADE\
);\
\
CREATE TABLE User_ratedmovies (\
  userID varchar(100) ,\
  movieID varchar(100),\
  rating varchar(100),\
  date_day NUMBER(2,0),\
  date_month NUMBER(2,0),\
  date_year NUMBER(4,0),\
  date_hour NUMBER(2,0),\
  date_minute NUMBER(2,0),\
  date_second NUMBER(2,0),\
  PRIMARY KEY (userID, movieID),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE\
);\
\
CREATE TABLE user_ratedmovies_timestamp(\
  userID varchar(100) NOT NULL,\
  movieID varchar(100) NOT NULL,\
  rating NUMBER NOT NULL,\
  timestamp char(100),\
  PRIMARY KEY (userID, movieID),\
  FOREIGN KEY (movieID) REFERENCES Movies (movieID) ON DELETE CASCADE\
);\
\
commit;\
\
CREATE INDEX index_genres on Movie_genres(genres);\
CREATE INDEX index_country on Movie_countries(country);\
CREATE INDEX index_tagText on Tags(tagText);\
CREATE INDEX index_actorName on Movie_actors(actorName);\
CREATE INDEX index_directorName on Movie_directors(directorName);\
CREATE INDEX index_movie_location1 on Movie_locations(location1_country);}