FlattedFile = load 'likesFlattenNoId.csv' as (like:chararray);
GroupLikes = Group FlattedFile by like;
CountFreq = foreach GroupLikes generate COUNT(FlattedFile) as cnt:long, group as like:chararray;
filterFreq = FILTER CountFreq BY cnt > 1300;
orderFreq = order filterFreq by cnt DESC;
store orderFreq into 'Histogram' using PigStorage(',');