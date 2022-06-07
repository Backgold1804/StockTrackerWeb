package com.example.stocktracker.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.stocktracker.vo.ChartListVO;
import com.example.stocktracker.vo.FriendStockListVO;
import com.example.stocktracker.vo.FriendVO;
import com.example.stocktracker.vo.MyStockListVO;
import com.example.stocktracker.vo.MyStockVO;
import com.example.stocktracker.vo.StockVO;
import com.example.stocktracker.vo.TickerVO;
import com.example.stocktracker.vo.TradingInputVO;
import com.example.stocktracker.vo.TradingVO;
import com.example.stocktracker.vo.UserVO;

@Repository
public class TrackerDAO {

	@Autowired
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "TrackerMapper";
	
	public UserVO loginUser(UserVO vo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".loginUser", vo);
	}
	
	public void joinUser(UserVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".insertUser", vo);
	}
	
	public int idCheck(UserVO vo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".idCheck", vo);
	}
	
	public int phoneCheck(UserVO vo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".phoneCheck", vo);
	}
	
	public int nicknameCheck(UserVO vo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".nicknameCheck", vo);
	}
	
	public UserVO findId(UserVO vo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".findId", vo);
	}
	
	public UserVO findPassword(UserVO vo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".findPassword", vo);
	}
	
	public List<MyStockListVO> selectStockList(int cust_uid) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectStockList", cust_uid);
	}
	
	public List<TradingVO> selectTradingList(int my_stock_uid) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectTradingList", my_stock_uid);
	}
	
	public List<TickerVO> getStockList() throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getStockList");
	}
	
	public String getTicker(String stock_name) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getTicker", stock_name);
	}
	
	public MyStockVO myStockCheck(int cust_uid, String ticker) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cust_uid", cust_uid);
		map.put("ticker", ticker);
		return sqlSession.selectOne(NAMESPACE + ".myStockCheck", map);
	}
	
	public void putNewStock(MyStockVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".putNewStock", vo);
	}
	
	public String getTickerConcat(int i) throws Exception {
		i = i * 500;
		return sqlSession.selectOne(NAMESPACE + ".getTickerConcat", i);
	}
	
	public int getTickerCount(String ticker) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getTickerCount", ticker);
	}
	
	public void insertTicker(StockVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".insertTicker", vo);
	}

	public void updateTicker(StockVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".updateTicker", vo);
	}
	
	public void insertTrading(TradingInputVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".insertTrading", vo);
	}
	
	public void updateBuyStock(MyStockVO vo) throws Exception {
		sqlSession.update(NAMESPACE + ".updateBuyTrading", vo);
	}
	
	public void updateSellStock(MyStockVO vo) throws Exception {
		sqlSession.update(NAMESPACE + ".updateSellTrading", vo);
	}
	
	public UserVO findUser(int uid) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".selectUser", uid);
	}
	
	public void deleteUser(int uid) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteUser", uid);
	}
	
	public void updateUser(UserVO vo) throws Exception {
		sqlSession.update(NAMESPACE + ".updateUser", vo);
	}
	
	public UserVO selectUser(UserVO vo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".selectUser", vo);
	}
	
	public UserVO findNickname(String nickname) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".findNickname", nickname);
	}
	
	public FriendVO findFriend(int uid, int friend_uid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("friend_uid", friend_uid);
		return sqlSession.selectOne(NAMESPACE + ".findFriend", map);
	}
	
	public void insertFriend(int uid, int friend_uid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("friend_uid", friend_uid);
		sqlSession.insert(NAMESPACE + ".insertFriend", map);
	}
	
	public List<FriendVO> selectFriend(int uid) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectFriend", uid);
	}
	
	public void deleteUserFriend(int uid) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteUserFriend", uid);
	}
	
	public void deleteUserMyStock(int uid) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteUserMyStock", uid);
	}
	
	public void deleteUserTrading(int uid) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteUserTrading", uid);
	}
	
	public void deleteFriend(FriendVO vo) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteFriend", vo);
	}
	
	public TradingInputVO selectTrading(int uid) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".selectTrading", uid);
	}
	
	public void deleteTrading(int uid) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteTrading", uid);
	}
	
	public void updateBuyMyStock(TradingInputVO vo) throws Exception {
		sqlSession.update(NAMESPACE + ".updateBuyMyStock", vo);
	}
	
	public void updateSellMyStock(TradingInputVO vo) throws Exception {
		sqlSession.update(NAMESPACE + ".updateSellMyStock", vo);
	}
	
	public List<FriendStockListVO> selectFriendStockList(int uid) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectFriendStockList", uid);
	}
	
	public List<ChartListVO> selectChartList(int cust_uid) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".selectChartList", cust_uid);
	}
	
	public MyStockListVO selectUpdateStock(int my_stock_uid) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".selectUpdateStock", my_stock_uid);
	}
	
	public TradingInputVO selectTradingOne(TradingInputVO vo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".selectTradingOne", vo);
	}
}
