package com.zlb.memo.bean;

import java.io.Serializable;

/**
 * 
* @ClassName: User 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午3:45:04 
*
 */
public class User implements Serializable {
	private String userId;
	private String nickName;
	private String avatar;
	public User(String userId, String nickName, String avatar){
		this.userId = userId;
		this.nickName = nickName;
		this.avatar = avatar;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "id = " + userId
				+ "; nickName = " + nickName
				+ "; avatar = " + avatar;
	}
}
