package com.example.demofe.controller;

import java.io.IOException;
import java.util.List;

import com.example.demofe.model.AppUser;
import com.example.demofe.model.Board;
import com.example.demofe.model.ResultMsg;
import com.example.demofe.service.BoardService;
import com.example.demofe.util.PagerInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BoardFeController
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardFeController {
    private final BoardService boardService;

	@GetMapping("/list")
	public String boardList(PagerInfo pagerInfo, Model model) throws Exception {

        
		List<Board> boardList = boardService.boardList(pagerInfo);

		model.addAttribute("boardList", boardList);
		model.addAttribute("pagerInfo", pagerInfo);

		return "board/list";

	}

	@PostMapping("/ajaxList")
	public String boardAjaxList(PagerInfo pagerInfo, Model model) throws Exception {
		List<Board> boardList = boardService.boardList(pagerInfo);

		model.addAttribute("boardList", boardList);
		model.addAttribute("pagerInfo", pagerInfo);

		return "board/list-dataList";

	}

	@GetMapping("/view")
	public String view(@RequestParam int num, Model model) throws Exception {
		log.info("------view ====");
		String nlString = System.getProperty("line.separator").toString();
		Board board = boardService.selectBoard(num);
		model.addAttribute("board", board);
		model.addAttribute("nlString", nlString);

		return "board/view";
	}

	@GetMapping("/write")
	public String writeForm() {
		return "board/form";
	}

	 
	@PostMapping("/write/submit")
	public String writeSubmit(@ModelAttribute Board board) throws IOException {
		AppUser user = new AppUser();
		user.setId("user1");
		user.setMemberName("사용자1");
		ResultMsg resultMsg = boardService.insertBoard(board, user);
		if (StringUtils.equals(resultMsg.getSuccessYn(), "Y")) {
			return "redirect:/list";
        }
        
		return "redirect:/write";

	}

	@GetMapping("/modifyForm")
	public String modifyForm(@RequestParam int num, Model model) throws Exception {
		Board board = boardService.selectBoard(num);
		model.addAttribute("board", board);
		return "board/form";
	}

	@PostMapping("/modify/submit")
	public String modify(@ModelAttribute Board board) throws Exception{
		AppUser user = new AppUser();
		user.setId("user1");
		user.setMemberName("사용자1");

		ResultMsg resultMsg = boardService.updateBoard(board, user);

		if (StringUtils.equals(resultMsg.getSuccessYn(), "Y")) {
			return "redirect:/list";
		}

		return "redirect:/view?num=" + board.getNum();

	}

	@GetMapping("/delete")
	public String delete(@RequestParam int num) {
		AppUser user = new AppUser();
		user.setId("user1");
		user.setMemberName("사용자1");

		ResultMsg resultMsg = boardService.deleteBoard(num);

		return "redirect:/list";

	}

	@GetMapping("/rest/list")
	@ResponseBody
	public List<Board> rest(PagerInfo pagerInfo) throws Exception {
		return boardService.boardList(pagerInfo);

	}
	@GetMapping("/search")
	public String search(String search, PagerInfo pagerInfo, Model model) throws Exception {
		int pageSize = 10;
		
		List<Board> boardList =  boardService.searchBoardList(search);
		pagerInfo.init(pageSize, boardList.size());
		model.addAttribute("boardList", boardList);
		model.addAttribute("pagerInfo", pagerInfo);
		return "board/list";
	}
    
}