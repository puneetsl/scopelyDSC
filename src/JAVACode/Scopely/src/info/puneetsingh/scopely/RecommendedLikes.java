package info.puneetsingh.scopely;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class RecommendedLikes {
	static HashMap<Integer, SortedMap<Integer, String>> LikeList = new HashMap<Integer, SortedMap<Integer,String>>();
	static HashMap<String, Integer> UniqueLikes = new HashMap<String, Integer>();
	static HashMap<Integer,String> RevereseUniqueLikes = new HashMap<Integer,String>();
	public static void main(String[] args) throws IOException {
		loadLikePrior();
		loadUniqueLikes();
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			System.out.print("pslRecommend­Likes> ");
			boolean flag=true;
			String cmd = input.readLine();
			//			cmd = cmd.toLowerCase();
			//RevereseUniqueLikes.get(Integer.parseInt(LikeList.get(UniqueLikes.get(cmd)).get(LikeList.get(UniqueLikes.get(cmd)).lastKey())))
			String[] cmdArr = cmd.split(",");
			Set<String> uniqueRecoSet = new HashSet<String>();
			for (int i = 0; i < cmdArr.length; i++) {
				String like = cmdArr[i].trim();
				if(UniqueLikes.containsKey(like)&&LikeList.containsKey(UniqueLikes.get(like)))
				{
					String recommend = LikeList.get(UniqueLikes.get(like)).get(LikeList.get(UniqueLikes.get(like)).lastKey());
					String[] recoArr = recommend.split(",");
					for (int j = 0; j < recoArr.length; j++) {
						flag=false;
						uniqueRecoSet.add(RevereseUniqueLikes.get(Integer.parseInt(recoArr[j].trim())));
					}	
				}
			}
			if(flag)
			{
				System.out.println("Sorry! can not recommend anything for the input provided");
			}
			else
			{
				System.out.println(uniqueRecoSet.toString());
			}
			System.out.println();
		}
	}
	public static void loadUniqueLikes()
	{
		try{

			FileInputStream fstream = new FileInputStream("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/UniqueLikes.csv");
			DataInputStream in = new DataInputStream(fstream);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int i=1;
			while ((strLine = br.readLine()) != null)   {
				//				strLine = strLine.toLowerCase();
				UniqueLikes.put(strLine, i);
				RevereseUniqueLikes.put(i,strLine);
				i++;
			}
		}catch (Exception e){//Catch exception if any
			e.printStackTrace();
		}
	}

	public static void loadLikePrior()
	{
		try{

			FileInputStream fstream = new FileInputStream("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/LikePrior.csv");
			DataInputStream in = new DataInputStream(fstream);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null)   {
				if(strLine.equals(""))
					continue;
				String[] likeArr = strLine.split(",");
				if(likeArr.length<=2)
					continue;
				int Key = 0;
				for (int i = 0; i < likeArr.length; i++) {
					if(likeArr[i].equals(""))
						continue;
					if(i==0)
					{
						Key = Integer.parseInt(likeArr[i]);
					}
					if(i==1)
					{
						SortedMap<Integer, String> tempHash  = new TreeMap<Integer, String>();
						int valKey = Integer.parseInt(likeArr[i].split(":")[1]);
						String value = likeArr[i].split(":")[0];
						tempHash.put(valKey, value);
						LikeList.put(Key, tempHash);
					}
					if(i>1)
					{
						int valKey = Integer.parseInt(likeArr[i].split(":")[1]);
						String value = likeArr[i].split(":")[0];
						if(LikeList.get(Key).containsKey(valKey))
						{
							value = value+","+LikeList.get(Key).get(valKey);
							LikeList.get(Key).put(valKey, value);
						}
						else
						{
							LikeList.get(Key).put(valKey, value);
						}
					}
				}

			}

		}catch (Exception e){//Catch exception if any
			e.printStackTrace();
		}
	}
}
