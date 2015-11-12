package com.matrix.jbt.dao;

import java.util.List;

import com.matrix.jbt.entity.Friend;

public interface FriendDao {
	/**
	 * add a friend to database
	 * 
	 * @param friend
	 *            com.matrix.jbt.entity.Friend
	 * @return if success return true, else return false
	 */
	public int addFriend(Friend friend);

	/**
	 * update friend
	 * 
	 * @param friend
	 *            com.matrix.jbt.entity.Friend
	 * @return if success return true else return false
	 */
	public boolean udpateFriend(int friendid);

	/**
	 * delete a friend from database
	 * 
	 * @param friendId
	 *            Friend's id
	 * @return if success return true, else return false
	 */
	public boolean deleteFriend(int friendId);

	/**
	 * get friend by friend id
	 * 
	 * @param friendId
	 *            friend's id
	 * @return is success return a Friend object, else return null
	 */
	public Friend getFriendByFriendId(int friendId);

	/**
	 * get friends by userid
	 * 
	 * @param userId
	 * @return if success return a list of Friend, else return null
	 */
	public List<Friend> getFriendsByUserId(int userId);

	/**
	 * get friends by email status
	 * 
	 * @param emailStatus
	 * @return
	 */
	public List<Friend> getFriendsByEmailStatus(int emailStatus);
}
