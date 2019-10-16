{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf600
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 DROP INDEX index_country;\
DROP INDEX index_tagText;\
DROP INDEX index_actorName;\
DROP INDEX index_directorName;\
DROP INDEX index_movie_location1;\
\
DROP TABLE user_ratedmovies_timestamp;\
DROP TABLE User_ratedmovies;\
DROP TABLE user_taggedmovies_timestamp;\
\
DROP TABLE User_taggedmovies;\
\
DROP TABLE Movie_tags;\
\
DROP TABLE Tags; \
\
DROP TABLE Movie_locations;\
\
DROP TABLE Movie_countries;\
\
DROP TABLE Movie_actors;\
\
DROP TABLE Movie_directors;\
\
DROP TABLE Movie_genres;\
\
DROP TABLE Movies;}