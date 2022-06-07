package com.example.stocktracker.vo;

public class FriendVO {
	int uid;
	int user_uid_front;
	int user_uid_back;
	String insert_date;
	String nickname;
	String friend_uid;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getUser_uid_front() {
		return user_uid_front;
	}
	public void setUser_uid_front(int user_uid_front) {
		this.user_uid_front = user_uid_front;
	}
	public int getUser_uid_back() {
		return user_uid_back;
	}
	public void setUser_uid_back(int user_uid_back) {
		this.user_uid_back = user_uid_back;
	}
	public String getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFriend_uid() {
		return friend_uid;
	}
	public void setFriend_uid(String friend_uid) {
		this.friend_uid = friend_uid;
	}
	
	
}
