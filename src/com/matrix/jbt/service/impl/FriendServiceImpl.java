package com.matrix.jbt.service.impl;

import java.util.List;

import com.matrix.jbt.dao.impl.FriendDaoImpl;
import com.matrix.jbt.entity.Friend;
import com.matrix.jbt.service.FriendService;

public class FriendServiceImpl implements FriendService {
	private FriendDaoImpl friendDaoImpl = new FriendDaoImpl();

	@Override
	public int addFriend(Friend friend) {
		return friendDaoImpl.addFriend(friend);
	}

	@Override
	public boolean udpateFriend(int friendid) {
		return friendDaoImpl.udpateFriend(friendid);
	}

	@Override
	public boolean deleteFriend(int friendId) {
		return false;
	}

	@Override
	public Friend getFriendByFriendId(int friendId) {
		return null;
	}

	@Override
	public List<Friend> getFriendsByUserId(int userId) {
		return null;
	}

	@Override
	public boolean changeEmailStatus(int emailId, int status) {
		return false;
	}

	@Override
	public List<Friend> getFriendsByEmailStatus(int emailStatus) {
		return null;
	}

}
