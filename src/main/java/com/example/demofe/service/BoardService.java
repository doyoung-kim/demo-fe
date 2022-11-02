package com.example.demofe.service;
import java.io.IOException;
import java.util.List;

import com.example.demofe.model.AppUser;
import com.example.demofe.model.Board;
import com.example.demofe.model.ResultMsg;
import com.example.demofe.util.PagerInfo;




/**
 * BoardService
 */
public interface BoardService {
    public Integer selectBoardTotalCount() throws Exception;
	public List<Board> boardList(PagerInfo pagerInfo) throws Exception;		
	public Board selectBoard(int num) throws Exception; 	
	
	public ResultMsg insertBoard(Board board,AppUser user)throws IOException; 
	public ResultMsg updateBoard(Board checkBoard, AppUser user)throws Exception ;	
	public ResultMsg  deleteBoard(int num);

	public List<Board> searchBoardList(String search) throws Exception;	
    
}