package com.matrix.jbt.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.matrix.jbt.dao.FriendDao;
import com.matrix.jbt.entity.Friend;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class FriendDaoImpl implements FriendDao {
	private QueryRunner runner = new QueryRunner();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public synchronized int addFriend(Friend friend) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		int id = 0;
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn, ReadProperties.read("sql", "addFriend"),
					friend.getFirstName(), friend.getLastName(),
					friend.getPhone(), friend.getEmail(),
					friend.getOrganization(), friend.getDataAndTimeOfLead(),
					friend.getCampaign(), friend.getLeadSource(),
					friend.getStudyExtension(), friend.getAgreeForad(),
					friend.getInterest(), friend.getUserId(),
					friend.getEmailStatus());
			if (ret >= 0) {
				conn.commit();
				BigInteger friendId = runner.query(conn,
						ReadProperties.read("sql", "getLastInsertFriendId"),
						new ScalarHandler());
				// System.out.println("friendId-----------" + friendId);
				if (friendId != null) {
					id = new Integer(friendId.intValue());
				}
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public boolean udpateFriend(int friendid) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateFriend"), friendid);
			if (ret >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
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
	public List<Friend> getFriendsByEmailStatus(int emailStatus) {
		return null;
	}

}
