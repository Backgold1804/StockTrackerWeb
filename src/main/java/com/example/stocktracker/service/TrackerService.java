package com.example.stocktracker.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.stocktracker.dao.TrackerDAO;
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

@Service
public class TrackerService {

	@Inject
	TrackerDAO trackerDAO;
	
	public UserVO loginUser(UserVO vo) throws Exception {
		return trackerDAO.loginUser(vo);
	}
	
	public void joinUser(UserVO vo) throws Exception {
		trackerDAO.joinUser(vo);
	}
	
	public int idCheck(UserVO vo) throws Exception {
		return trackerDAO.idCheck(vo);
	}
	
	public int phoneCheck(UserVO vo) throws Exception {
		return trackerDAO.phoneCheck(vo);
	}
	
	public int nicknameCheck(UserVO vo) throws Exception {
		return trackerDAO.nicknameCheck(vo);
	}
	
	public UserVO findId(UserVO vo) throws Exception {
		return trackerDAO.findId(vo);
	}
	
	public UserVO findPassword(UserVO vo) throws Exception {
		return trackerDAO.findPassword(vo);
	}
	
	public List<MyStockListVO> selectStockList(int cust_uid) throws Exception {
		return trackerDAO.selectStockList(cust_uid);
	}
	
	public List<TradingVO> selectTradingList(int my_stock_uid) throws Exception {
		return trackerDAO.selectTradingList(my_stock_uid);
	}
	
	public List<TickerVO> getStockList() throws Exception {
		return trackerDAO.getStockList();
	}
	
	public String getTicker(String stock_name) throws Exception {
		return trackerDAO.getTicker(stock_name);
	}
	
	public MyStockVO myStockCheck(int cust_uid, String ticker) throws Exception {
		return trackerDAO.myStockCheck(cust_uid, ticker);
	}
	
	public MyStockVO putNewStock(MyStockVO vo) throws Exception {
		trackerDAO.putNewStock(vo);
		return trackerDAO.myStockCheck(vo.getCust_uid(), vo.getTicker());
	}
	
	public String getTickerConcat(int i) throws Exception {
		return trackerDAO.getTickerConcat(i);
	}
	
	
	public int getTickerCount(String ticker) throws Exception {
		return trackerDAO.getTickerCount(ticker);
	}
	
	public void insertTicker(StockVO vo) throws Exception {
		trackerDAO.insertTicker(vo);
	}

	public void updateTicker(StockVO vo) throws Exception {
		trackerDAO.updateTicker(vo);
	}
	
	public TradingInputVO insertTrading(TradingInputVO vo) throws Exception {
		trackerDAO.insertTrading(vo);
		return trackerDAO.selectTradingOne(vo);
	}
	
	public MyStockVO updateBuyStock(MyStockVO vo) throws Exception {
		trackerDAO.updateBuyStock(vo);
		return trackerDAO.myStockCheck(vo.getCust_uid(), vo.getTicker());
	}
	
	public MyStockVO updateSellStock(MyStockVO vo) throws Exception {
		trackerDAO.updateSellStock(vo);
		return trackerDAO.myStockCheck(vo.getCust_uid(), vo.getTicker());
	}
		
	public UserVO deleteUser(int uid) throws Exception {
		UserVO vo = trackerDAO.findUser(uid);
		trackerDAO.deleteUser(uid);
		return vo;
	}
	
	public UserVO setUser(int uid) throws Exception {
		return trackerDAO.findUser(uid);
	}
	
	public UserVO updateUser(UserVO vo) throws Exception {
		trackerDAO.updateUser(vo);
		return trackerDAO.selectUser(vo);
	}
	
	public UserVO findNickname(String nickname) throws Exception {
		return trackerDAO.findNickname(nickname);
	}
	
	public FriendVO findFriend(int uid, int friend_uid) throws Exception {
		return trackerDAO.findFriend(uid, friend_uid);
	}
	
	public void insertFriend(int uid, int friend_uid) throws Exception {
		trackerDAO.insertFriend(uid, friend_uid);
	}
	
	public List<FriendVO> selectFriend(int uid) throws Exception {
		return trackerDAO.selectFriend(uid);
	}
	
	public void deleteUserFriend(int uid) throws Exception {
		trackerDAO.deleteUserFriend(uid);
	}
	
	public void deleteUserMyStock(int uid) throws Exception {
		trackerDAO.deleteUserMyStock(uid);
	}
	
	public void deleteUserTrading(int uid) throws Exception {
		trackerDAO.deleteUserTrading(uid);
	}
	
	public void deleteFriend(FriendVO vo) throws Exception {
		trackerDAO.deleteFriend(vo);
	}
	
	public TradingInputVO deleteTrading(int uid) throws Exception {
		TradingInputVO vo = trackerDAO.selectTrading(uid);
		vo.setOrder_sum(vo.getOrder_amount() * vo.getOrder_price());
		if ("B".equals(vo.getTrading())) {
			trackerDAO.updateBuyMyStock(vo);
		} else {
			trackerDAO.updateSellMyStock(vo);
		}		
		trackerDAO.deleteTrading(uid);
		return  vo;
	}
	
	public List<FriendStockListVO> selectFriendStockList(int uid) throws Exception {
		return trackerDAO.selectFriendStockList(uid);
	}
	
	public List<ChartListVO> selectChartList(int cust_uid) throws Exception {
		return trackerDAO.selectChartList(cust_uid);
	}
	
	public MyStockListVO selectUpdateStock(int my_stock_uid) throws Exception {
		return trackerDAO.selectUpdateStock(my_stock_uid);
	}
}
