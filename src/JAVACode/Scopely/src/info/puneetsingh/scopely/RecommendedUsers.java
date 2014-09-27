package info.puneetsingh.scopely;

import java.awt.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class RecommendedUsers {
	static HashMap<String, SortedMap<Integer, String>> UserList = new HashMap<String, SortedMap<Integer,String>>();
	static HashMap<Integer, SortedMap<Integer, String>> LikeList = new HashMap<Integer, SortedMap<Integer,String>>();
	static HashMap<String, Integer> UniqueLikes = new HashMap<String, Integer>();
	static HashMap<Integer,String> RevereseUniqueLikes = new HashMap<Integer,String>();
	public static void main(String[] args) throws IOException {
		loadUserPrior();
		loadLikePrior();
		loadUniqueLikes();
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			System.out.print("pslRecommend­Users> ");
			boolean flag=true;
			String cmd = input.readLine();
			if(!cmd.contains("="))
			{
				System.out.println("Not a good input");
				continue;
			}
			String str = cmd.split("=")[0];
			cmd = cmd.split("=")[1];
			String[] cmdArr = cmd.split(",");
			Set<String> uniqueRecoSet = new HashSet<String>();
			Set<String> similarRecoSet = new HashSet<String>();
			if(str.equals("likes"))
			{
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
					System.out.println("Hold on it would take some time..");
					System.out.println("Best Matches:" +findExactMatchUser(cmd));
					String cmdClose = uniqueRecoSet.toString();
					cmdClose = cmdClose.substring(1, cmdClose.length()-1);
					System.out.println("Finding 'kind of close' matches for: "+cmdClose);
					System.out.println("Similar Matches:" +findExactMatchUser(cmdClose));

				}
				System.out.println();
			}
			else if(str.equals("users"))
			{
				for (int i = 0; i < cmdArr.length; i++) {
					String user = cmdArr[i].trim();
					if(UserList.containsKey(user))
					{
						String recommend = UserList.get(user).get(UserList.get(user).lastKey());
						UserList.get(user).remove(UserList.get(user).lastKey());
						recommend = recommend+","+UserList.get(user).get(UserList.get(user).lastKey());
						UserList.get(user).remove(UserList.get(user).lastKey());
						String[] recoArr = recommend.split(",");
						for (int j = 0; j < recoArr.length; j++) {
							flag=false;
							uniqueRecoSet.add(recoArr[j]);
						}	
						recommend = UserList.get(user).get(UserList.get(user).lastKey());
						recoArr = recommend.split(",");
						for (int j = 0; j < recoArr.length; j++) {
							flag=false;
							similarRecoSet.add(recoArr[j]);
						}	
					}
				}
				if(flag)
				{
					System.out.println("Sorry! can not recommend anything for the input provided");
				}
				else
				{
					System.out.println("Best Matches: "+uniqueRecoSet.toString());
					System.out.println("Similar Matches: "+similarRecoSet.toString());
				}
				System.out.println();
			}
		}
	}
//	private static String findCloseMatchUser(Set<String> uniqueRecoSet) throws IOException {
//		FileInputStream fstream = new FileInputStream("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/InvertedIndex.csv");
//		DataInputStream in = new DataInputStream(fstream);
//		BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		String strLine;
//		ArrayList<String[]> userSet = new ArrayList<String[]>();
//		while ((strLine = br.readLine()) != null)   {
//			String first = strLine.split(",")[0];
//			for (int i = 0; i < uniqueRecoSet.size(); i++) {
//				if(first!=null&&!first.equals("")&&UniqueLikes.containsKey(uniqueRecoSet.toArray()[i]))
//					if(UniqueLikes.get(uniqueRecoSet.toArray()[i])==Integer.parseInt(first))
//					{
//						userSet.add(strLine.split(","));
//					}
//			}
//		}
//		java.util.List<String> intersectionList = Arrays.asList(userSet.get(0));
//		if(userSet.size()>0)
//		{
//			for (int i = 1; i < userSet.size(); i++) {
//				java.util.List<String> userList =  Arrays.asList(userSet.get(i));
//				intersectionList.retainAll(userList);
//			}
//			return getRandom(intersectionList);
//		}
//		return "Couldn't find anything!";
//	}
	private static String getRandom(java.util.List<String> intersectionList) {
		Random rand = new Random();
		int index=0;
		while(index==0||index>=intersectionList.size())
			index = rand.nextInt(10);
		return intersectionList.toArray()[index].toString();
	}
	private static String findExactMatchUser(String cmd) throws IOException {
		FileInputStream fstream = new FileInputStream("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/InvertedIndex.csv");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		String[] cmdArr= cmd.split(",");
		ArrayList<String[]> userSet = new ArrayList<String[]>();
		while ((strLine = br.readLine()) != null)   {
			String first = strLine.split(",")[0];
			for (int i = 0; i < cmdArr.length; i++) {
				if(first!=null&&!first.equals("")&&UniqueLikes.containsKey(cmdArr[i]))
					if(UniqueLikes.get(cmdArr[i])==Integer.parseInt(first))
					{
						userSet.add(strLine.split(","));
					}
			}
		}
		if(userSet.size()>0)
		{
			java.util.List<String> intersectionList = Arrays.asList(userSet.get(0));
			for (int i = 1; i < userSet.size(); i++) {
				java.util.List<String> userList =  Arrays.asList(userSet.get(i));
				intersectionList.retainAll(userList);
			}
			return getRandom(intersectionList);
		}
		return "Couldn't find anything!";
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
	public static void loadUserPrior()
	{
		try{
			FileInputStream fstream = new FileInputStream("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/UserPrior.csv");
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
				String Key = "";
				for (int i = 0; i < likeArr.length; i++) {
					if(likeArr[i].equals(""))
						continue;
					if(i==0)
					{
						Key = likeArr[i];
					}
					if(i==1)
					{
						SortedMap<Integer, String> tempHash  = new TreeMap<Integer, String>();
						int valKey = Integer.parseInt(likeArr[i].split(":")[1]);
						String value = likeArr[i].split(":")[0];
						tempHash.put(valKey, value);
						UserList.put(Key, tempHash);
					}
					if(i>1)
					{
						int valKey = Integer.parseInt(likeArr[i].split(":")[1]);
						String value = likeArr[i].split(":")[0];
						if(UserList.get(Key).containsKey(valKey))
						{
							value = value+","+UserList.get(Key).get(valKey);
							UserList.get(Key).put(valKey, value);
						}
						else
						{
							UserList.get(Key).put(valKey, value);
						}
					}
				}
			}
		}catch (Exception e){//Catch exception if any
			e.printStackTrace();
		}
	}


}
