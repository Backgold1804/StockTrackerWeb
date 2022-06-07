package com.example.stocktracker.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.stocktracker.service.TrackerService;
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

@RestController
public class TrackerController {

	@Autowired
	private TrackerService service;
	
	@PostMapping(value = "/login", produces="application/json; charset=utf8")
	public ResponseEntity login(@RequestParam("id") String id, @RequestParam("password") String password, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("login connetion");
		
		UserVO vo = new UserVO();
		vo.setId(id);
		vo.setPassword(password);
		
		UserVO data = service.loginUser(vo);
		if (data == null) {
			map.put("datas", null);
			map.put("response_cd", "001");
			map.put("response_msg", "아이디/비밀번호가 틀립니다.");
		}else {
			map.put("datas", data);
			map.put("response_cd", "000");
			map.put("response_msg", "로그인되었습니다.");
		}
		return ResponseEntity.ok().body(map);		
	}
	
	@PostMapping(value = "/join", produces="application/json; charset=utf8")
	public ResponseEntity join(@RequestParam("id") String id, 
			@RequestParam("password") String password, 
			@RequestParam("phone") String phone,
			@RequestParam("findId") String findId,
			@RequestParam("nickname") String nickname,
			ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		UserVO vo = new UserVO();
		vo.setId(id);
		vo.setPassword(password);
		vo.setPhone(phone);
		vo.setFind_id(findId);
		vo.setNickname(nickname);

		if (service.idCheck(vo) == 1) {
			map.put("datas", null);
			map.put("response_cd", "002");
			map.put("response_msg", "이미 존재하는 아이디입니다.");
		} else if (service.phoneCheck(vo) == 1) {
			map.put("datas", null);
			map.put("response_cd", "003");
			map.put("response_msg", "이미 존재하는 전화번호입니다.");
		} else if (service.nicknameCheck(vo) == 1){
			map.put("datas", null);
			map.put("response_cd", "003");
			map.put("response_msg", "이미 존재하는 닉네임입니다.");
		} else {
			service.joinUser(vo);
			map.put("datas", vo);
			map.put("response_cd", "000");
			map.put("response_msg", "회원가입에 성공했습니다.");
		}
				
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/findId", produces="application/json; charset=utf8")
	public ResponseEntity findId(@RequestParam("phone") String phone, @RequestParam("findId") String findId, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		UserVO vo = new UserVO();
		vo.setPhone(phone);
		vo.setFind_id(findId);
		
		UserVO data = service.findId(vo);
		if (data == null) {
			map.put("datas", null);
			map.put("response_cd", "004");
			map.put("response_msg", "아이디 찾기에 실패했습니다.\n입력한 정보를 확인해주세요.");
		} else {
			data.setPassword(null);
			map.put("datas", data);
			map.put("response_cd", "000");
			map.put("response_msg", "아이디 찾기에 성공했습니다.");
		}
		
		return ResponseEntity.ok().body(map);		
	}
	
	@GetMapping(value = "/findPassword", produces="application/json; charset=utf8")
	public ResponseEntity findPassword(@RequestParam("id") String id, @RequestParam("phone") String phone, @RequestParam("findId") String findId, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		UserVO vo = new UserVO();
		vo.setId(id);
		vo.setPhone(phone);
		vo.setFind_id(findId);
		
		UserVO data = service.findPassword(vo);
		if (data == null) {
			map.put("datas", null);
			map.put("response_cd", "005");
			map.put("response_msg", "비밀번호 찾기에 실패했습니다.\n입력한 정보를 확인해주세요.");
		} else {
			map.put("datas", data);
			map.put("response_cd", "000");
			map.put("response_msg", "비밀번호 찾기에 성공했습니다.");
		}
		
		return ResponseEntity.ok().body(map);		
	}
	
	@GetMapping(value = "/selectStockList", produces="application/json; charset=utf8")
	public ResponseEntity selectStockList(@RequestParam("cust_uid") int cust_uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<MyStockListVO> list = new ArrayList<MyStockListVO>();
		list = service.selectStockList(cust_uid);
		
		for (int i = 0; i < list.size(); i++) {
			if ("0".equals(list.get(i).getHoldings())) {
				list.remove(i);
			}
		}
		
		if (list == null) {
			map.put("datas", null);
			map.put("response_cd", "006");
			map.put("response_msg", "정보 없음");
		} else {
			map.put("datas", list);
			map.put("response_cd", "000");
			map.put("response_msg", "정상");
		}
		
		System.out.println("Select Stock List");
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/selectTradingList", produces="application/json; charset=utf8")
	public ResponseEntity selectTradingList(@RequestParam("my_stock_uid") int my_stock_uid,  ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<TradingVO> list = new ArrayList<TradingVO>();
		list = service.selectTradingList(my_stock_uid);
		
		if (list == null) {
			map.put("datas", null);
			map.put("response_cd", "006");
			map.put("response_msg", "정보 없음");
		} else {
			map.put("datas", list);
			map.put("response_cd", "000");
			map.put("response_msg", "정상");
		}
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/getStockList", produces="application/json; charset=utf8")
	public ResponseEntity getStockList(ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<TickerVO> list = new ArrayList<TickerVO>();
		list = service.getStockList();
		
		if (list == null) {
			map.put("datas", null);
			map.put("response_cd", "006");
			map.put("response_msg", "정보 없음");
		} else {
			map.put("datas", list);
			map.put("response_cd", "000");
			map.put("response_msg", "정상");
		}
		
		return ResponseEntity.ok().body(map);
	}

	@PostMapping(value = "/putNewStock", produces="application/json; charset=utf8")
	public ResponseEntity putNewStock(@RequestParam("cust_uid") int cust_uid,
									@RequestParam("stock_name") String stock_name, 
									@RequestParam("price") int price, 
									@RequestParam("amount") int amount,
									@RequestParam("date") String date, 
									@RequestParam("time") String time, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		String datetime = date + " " + time;
		
		String ticker = service.getTicker(stock_name);
		
		MyStockVO vo = new MyStockVO();
		TradingInputVO tvo = new TradingInputVO();
		try {
			vo = service.myStockCheck(cust_uid, ticker);	
		} catch (Exception e) {
			e.printStackTrace();
			vo = null;
		}
		
		if (vo == null) {
			vo = new MyStockVO();
			vo.setCust_uid(cust_uid);
			vo.setTicker(ticker);
			vo.setBlended_price(price);
			vo.setBuy_amount(price * amount);
			vo.setHoldings(amount);
			vo.setInsert_date(datetime);
			vo.setUpdate_date(datetime);
			vo = service.putNewStock(vo);
			
			tvo.setMy_stock_uid(vo.getUid());
			tvo.setTicker(ticker);
			tvo.setTrading("B");
			tvo.setOrder_price(price);
			tvo.setOrder_amount(amount);
			tvo.setInsert_date(datetime);
			service.insertTrading(tvo);
			
						
			map.put("datas", vo);
			map.put("response_cd", "000");
			map.put("response_msg", "등록되었습니다.");
		} else {
			tvo.setMy_stock_uid(vo.getUid());
			tvo.setTicker(ticker);
			tvo.setTrading("B");
			tvo.setOrder_price(price);
			tvo.setOrder_amount(amount);
			tvo.setInsert_date(datetime);
			service.insertTrading(tvo);
			
			vo.setBuy_amount(amount * price);
			vo.setHoldings(amount);
			vo.setCust_uid(cust_uid);
			vo.setTicker(ticker);
			vo = service.updateBuyStock(vo);
			
			map.put("datas", vo);
			map.put("response_cd", "000");
			map.put("response_msg", "등록되었습니다.");
		}
		
		return ResponseEntity.ok().body(map);
	}
	
	@PostMapping(value = "/putTrading", produces="application/json; charset=utf8")
	public ResponseEntity putTrading(@RequestParam("cust_uid") int cust_uid,
			@RequestParam("stock_name") String stock_name, 
			@RequestParam("trading") String trading,
			@RequestParam("price") int price, 
			@RequestParam("amount") int amount,
			@RequestParam("date") String date, 
			@RequestParam("time") String time, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		String datetime = date + " " + time;
		
		String ticker = service.getTicker(stock_name);
		
		MyStockVO vo = new MyStockVO();
		TradingInputVO tvo = new TradingInputVO();
		
		vo = service.myStockCheck(cust_uid, ticker);
		
		tvo.setMy_stock_uid(vo.getUid());
		tvo.setTicker(ticker);
		tvo.setTrading(trading);
		tvo.setOrder_price(price);
		tvo.setOrder_amount(amount);
		tvo.setInsert_date(datetime);
		tvo = service.insertTrading(tvo);
		
		vo.setBuy_amount(amount * price);
		vo.setHoldings(amount);
		vo.setCust_uid(cust_uid);
		vo.setTicker(ticker);
		if ("B".equals(trading)) {
			vo = service.updateBuyStock(vo);
		} else {
			vo = service.updateSellStock(vo);
		}
		
		map.put("datas", tvo);
		map.put("response_cd", "000");
		map.put("response_msg", "등록되었습니다.");
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/batch1", produces="application/json; charset=utf8")
	public ResponseEntity batch1(ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("response_cd", "000");
        map.put("response_msg", "OK");
        System.out.println("start : batch");
		for(int i = 1; i < 6; i=i+2) {
			String ticker = service.getTickerConcat(i);
			
			CloseableHttpClient httpClient = null;
		      URIBuilder builder = null;
		      URI uri = null;
		      HttpGet httpget = null;
		      try {
		         httpClient = HttpClients.createDefault();
		         builder = new URIBuilder();
		         builder.setScheme("https").setHost("polling.finance.naver.com").setPath("/api/realtime")
		            .setParameter("query", "SERVICE_ITEM:"+ticker);
		         
		         uri = builder.build();
		         httpget = new HttpGet(uri);
		         CloseableHttpResponse httpResponse = httpClient.execute(httpget);
		   
		         String json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		           System.out.println("receiveBatch GET Response Status");
		           System.out.println(uri.getQuery());
		           System.out.println(httpResponse.getStatusLine().getStatusCode()+"");
		           System.out.println("==================================================================");
		           System.out.println("CALL NAVER : polling.finance.naver.com");
//		           System.out.println(json);
		           System.out.println("==================================================================");
		           
		           httpClient.close();
		           JSONParser json_parser = new JSONParser();
		           JSONObject jsons =(JSONObject) json_parser.parse(json);
		           Map jsons_data = new HashMap() ;
		           
		           jsons_data.put("result_cd", (String)jsons.get("resultCode"));
		           jsons_data.put("result_msg", (String)jsons.get("response_message"));
		           if(jsons.get("resultCode") != null && "success".equals(jsons.get("resultCode").toString())) {
			           JSONObject jsons_result =(JSONObject)jsons.get("result"); 
			           JSONArray jsons_areas = (JSONArray)jsons_result.get("areas");
			           JSONArray jsons_datas = (JSONArray)((JSONObject)jsons_areas.get(0)).get("datas");
			           
			           for(Object item : jsons_datas) {
			        	   JSONObject data = (JSONObject) item;
			        	   StockVO vo = new StockVO();
			        	   vo.setTicker(data.get("cd").toString());
			        	   vo.setClose_price(Integer.parseInt(data.get("nv").toString()));
			        	   vo.setOpen_price(Integer.parseInt(data.get("ov").toString()));
			        	   vo.setHigh_price(Integer.parseInt(data.get("hv").toString()));
			        	   vo.setLow_price(Integer.parseInt(data.get("lv").toString()));
			        	   if("CLOSE".equals(data.get("ms").toString()) || "PREOPEN".equals(data.get("ms").toString())) {
			        		   i=1000;
					           map.put("response_cd", "001");
					           map.put("response_msg", "CLOSE(업무시간아님)");
					           
					           System.out.println("batch : CLOSE(업무시간아님)");
			        		   break;
			        	   }
			        	   if(service.getTickerCount(vo.getTicker()) > 0)
			        		   service.updateTicker(vo);
			        	   else
			        		   service.insertTicker(vo);
			           }
		           }
		           map.put("datas", null);
		      } catch (Exception e) {
				e.printStackTrace();
				Map  jsons_data = new HashMap<String, String>();
				jsons_data.put("result_cd", "NCOM99");
				jsons_data.put("result_msg", e.getMessage());
				map.put("response_cd", "999");
				map.put("response_msg", "ERR");
				map.put("datas", null);
		      } finally {
				httpClient = null;
				builder = null;
				uri = null;
				httpget = null;
		      }
		}
		
		System.out.println("stop : batch");
		return ResponseEntity.ok().body(map);
	}
	
	
	@GetMapping(value = "/batch2", produces="application/json; charset=utf8")
	public ResponseEntity batch2(ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("response_cd", "000");
        map.put("response_msg", "OK");
        System.out.println("start : batch");
		for(int i = 0; i < 6; i=i+2) {
			String ticker = service.getTickerConcat(i);
			
			CloseableHttpClient httpClient = null;
		      URIBuilder builder = null;
		      URI uri = null;
		      HttpGet httpget = null;
		      try {
		         httpClient = HttpClients.createDefault();
		         builder = new URIBuilder();
		         builder.setScheme("https").setHost("polling.finance.naver.com").setPath("/api/realtime")
		            .setParameter("query", "SERVICE_ITEM:"+ticker);
		         
		         uri = builder.build();
		         httpget = new HttpGet(uri);
		         CloseableHttpResponse httpResponse = httpClient.execute(httpget);
		   
		         String json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		           System.out.println("receiveBatch GET Response Status");
		           System.out.println(uri.getQuery());
		           System.out.println(httpResponse.getStatusLine().getStatusCode()+"");
		           System.out.println("==================================================================");
		           System.out.println("CALL NAVER : polling.finance.naver.com");
//		           System.out.println(json);
		           System.out.println("==================================================================");
		           
		           httpClient.close();
		           JSONParser json_parser = new JSONParser();
		           JSONObject jsons =(JSONObject) json_parser.parse(json);
		           Map jsons_data = new HashMap() ;
		           
		           jsons_data.put("result_cd", (String)jsons.get("resultCode"));
		           jsons_data.put("result_msg", (String)jsons.get("response_message"));
		           if(jsons.get("resultCode") != null && "success".equals(jsons.get("resultCode").toString())) {
			           JSONObject jsons_result =(JSONObject)jsons.get("result"); 
			           JSONArray jsons_areas = (JSONArray)jsons_result.get("areas");
			           JSONArray jsons_datas = (JSONArray)((JSONObject)jsons_areas.get(0)).get("datas");
			           
			           for(Object item : jsons_datas) {
			        	   JSONObject data = (JSONObject) item;
			        	   StockVO vo = new StockVO();
			        	   vo.setTicker(data.get("cd").toString());
			        	   vo.setClose_price(Integer.parseInt(data.get("nv").toString()));
			        	   vo.setOpen_price(Integer.parseInt(data.get("ov").toString()));
			        	   vo.setHigh_price(Integer.parseInt(data.get("hv").toString()));
			        	   vo.setLow_price(Integer.parseInt(data.get("lv").toString()));
			        	   if("CLOSE".equals(data.get("ms").toString()) || "PREOPEN".equals(data.get("ms").toString())) {
			        		   i=1000;
					           map.put("response_cd", "001");
					           map.put("response_msg", "CLOSE(업무시간아님)");
					           
					           System.out.println("batch : CLOSE(업무시간아님)");
			        		   break;
			        	   }
			        	   if(service.getTickerCount(vo.getTicker()) > 0)
			        		   service.updateTicker(vo);
			        	   else
			        		   service.insertTicker(vo);
			           }
		           }
		           map.put("datas", null);
		      } catch (Exception e) {
				e.printStackTrace();
				Map  jsons_data = new HashMap<String, String>();
				jsons_data.put("result_cd", "NCOM99");
				jsons_data.put("result_msg", e.getMessage());
				map.put("response_cd", "999");
				map.put("response_msg", "ERR");
				map.put("datas", null);
		      } finally {
				httpClient = null;
				builder = null;
				uri = null;
				httpget = null;
		      }
		}
		
		System.out.println("stop : batch");
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/deleteUser", produces="application/json; charset=utf8")
	public ResponseEntity deleteUser(@RequestParam("uid") int uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		UserVO vo = new UserVO();
		vo.setUid(Integer.toString(uid));
				
		UserVO data = service.deleteUser(uid);
		
		map.put("response_cd", "000");
		map.put("response_msg", "탈퇴되었습니다.");
		map.put("datas", data);
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/setUser", produces="application/json; charset=utf8")
	public ResponseEntity setUser(@RequestParam("uid") int uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		UserVO vo = new UserVO();
		vo.setUid(Integer.toString(uid));
				
		UserVO data = service.setUser(uid);
		
		map.put("response_cd", "000");
		map.put("response_msg", "정상.");
		map.put("datas", data);
		
		return ResponseEntity.ok().body(map);
	}
	
	@PostMapping(value = "/updateUser", produces="application/json; charset=utf8")
	public ResponseEntity updateUser(@RequestParam("uid") int uid, 
									@RequestParam("password") String password,
									@RequestParam("phone") String phone, 
									@RequestParam("find_id") String find_id, 
									@RequestParam("nickname") String nickname, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		UserVO vo = new UserVO();
		vo.setUid(Integer.toString(uid));
		vo.setPassword(password);
		vo.setPhone(phone);
		vo.setFind_id(find_id);
		vo.setNickname(nickname);
				
		UserVO data = service.updateUser(vo);
		
		map.put("response_cd", "000");
		map.put("response_msg", "수정되었습니다.");
		map.put("datas", data);
		
		return ResponseEntity.ok().body(map);
	}
	
	@PostMapping(value = "/addFriend", produces="application/json; charset=utf8")
	public ResponseEntity addFriend(@RequestParam("uid") int uid, @RequestParam("nickname") String nickname, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		UserVO vo = service.findNickname(nickname);
		
		if (vo == null) {
			map.put("datas", null);
			map.put("response_cd", "008");
			map.put("response_msg", "존재하지 않는 닉네임 입니다.");
			return ResponseEntity.ok().body(map);
		} else if (Integer.parseInt(vo.getUid()) == uid) {
			map.put("datas", null);
			map.put("response_cd", "009");
			map.put("response_msg", "본인은 친구 추가할 수 없습니다.");
			return ResponseEntity.ok().body(map);
		}
		
		FriendVO friendVO = new FriendVO();
		
		try {
			friendVO = service.findFriend(uid, Integer.parseInt(vo.getUid()));
		} catch (Exception e) {
			
		}
		
		if (friendVO == null || friendVO.getUid() == 0) {
			service.insertFriend(uid, Integer.parseInt(vo.getUid()));
			map.put("datas", vo);
			map.put("response_cd", "000");
			map.put("response_msg", "등록되었습니다.");
		} else {
			map.put("datas", friendVO);
			map.put("response_cd", "007");
			map.put("response_msg", "이미 존재하는 친구입니다.");
		}
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/selectFriend", produces="application/json; charset=utf8")
	public ResponseEntity selectFriend(@RequestParam("uid") int uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<FriendVO> list = new ArrayList<FriendVO>();
		list = service.selectFriend(uid);
		
		if (list == null) {
			map.put("datas", null);
			map.put("response_cd", "006");
			map.put("response_msg", "정보 없음");
		} else {
			map.put("datas", list);
			map.put("response_cd", "000");
			map.put("response_msg", "정상");
		}
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/deleteFriend", produces="application/json; charset=utf8")
	public ResponseEntity deleteFriend(@RequestParam("uid") int uid, @RequestParam("friend_uid") int friend_uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		FriendVO friendVO = new FriendVO();
		friendVO.setUser_uid_front(uid);
		friendVO.setUser_uid_back(friend_uid);
		
		service.deleteFriend(friendVO);
		
		map.put("datas", null);
		map.put("response_cd", "000");
		map.put("response_msg", "처리되었습니다.");
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/deleteTrading", produces="application/json; charset=utf8")
	public ResponseEntity deleteTrading(@RequestParam("uid") int uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		TradingInputVO vo = service.deleteTrading(uid);
		
		map.put("datas", vo);
		map.put("response_cd", "000");
		map.put("response_msg", "삭제되었습니다.");
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/selectFriendStockList", produces="application/json; charset=utf8")
	public ResponseEntity selectFriendStockList(@RequestParam("uid") int uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<FriendStockListVO> stockList = service.selectFriendStockList(uid);
				
		map.put("datas", stockList);
		map.put("response_cd", "000");
		map.put("response_msg", "정상");
		System.out.println("selectFriendStockList");
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/selectChartList", produces="application/json; charset=utf8")
	public ResponseEntity selectChartList(@RequestParam("cust_uid") int cust_uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<ChartListVO> stockList = service.selectChartList(cust_uid);
				
		map.put("datas", stockList);
		map.put("response_cd", "000");
		map.put("response_msg", "정상");
		
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping(value = "/selectUpdateStock", produces="application/json; charset=utf8")
	public ResponseEntity selectUpdateStock(@RequestParam("my_stock_uid") int my_stock_uid, ModelMap model) throws Throwable {
		Map<String, Object> map = new HashMap<String, Object>();
		
		MyStockListVO vo = service.selectUpdateStock(my_stock_uid);
		
		if (vo != null) {
			map.put("datas", vo);
			map.put("response_cd", "000");
			map.put("response_msg", "정상");	
		} else {
			map.put("datas", null);
			map.put("response_cd", "006");
			map.put("response_msg", "정보없음");
		}
		
		return ResponseEntity.ok().body(map);
	}
}
