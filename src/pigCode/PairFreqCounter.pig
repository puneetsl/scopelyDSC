PairFile = load 'pairGen100.csv' as (like:chararray);
GroupPairs = Group PairFile by like;
CountFreq = foreach GroupPairs generate COUNT(PairFile) as cnt:long, group as like:chararray;
filterFreq = FILTER CountFreq BY cnt > 10;
orderFreq = order filterFreq by cnt DESC;
store orderFreq into 'PairHistogram100' using PigStorage(',');