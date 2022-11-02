package com.example.demofe.service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demofe.model.AppUser;
import com.example.demofe.model.Board;
import com.example.demofe.model.ResultMsg;
import com.example.demofe.util.PagerInfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


/**
 * BoardServiceImpl
 */
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
	private static String SUCCESS = "Y";


	
	private  WebClient client;	
	private String apiUrl;

	public BoardServiceImpl(@Value("${api.board.url}") final String apiUrl ) {
		this.apiUrl = apiUrl;
		getWebClient();
	}
	public void getWebClient(){
		client =  WebClient
			.builder()
			.baseUrl(this.apiUrl)
			// .defaultCookie("쿠키키","쿠키값")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			//Memory 조정: 2M (default 256KB)
			.exchangeStrategies(ExchangeStrategies.builder()
            	.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2*1024*1024)) 
            	.build())
			.build();
	}


	@Override
	public Integer selectBoardTotalCount() {
		
		return client
				.get()
				.uri("/totalCount")
				.retrieve()			
				.bodyToMono(Integer.class).block();
	}

	@Override
	public List<Board> boardList(PagerInfo pagerInfo) {
		
		int count = selectBoardTotalCount();
		int pageSize = 5;
		pagerInfo.init(pageSize, count);		
		String page = String.valueOf(pagerInfo.getPage());

		return client
			.get()
			// .uri("?size={size}&page={page}", pageSize, page)
			.uri(uriBuiler -> uriBuiler.path("")
				.queryParam("size", pageSize)
				.queryParam("page", page)
				.build())
			.retrieve()			
			.bodyToFlux(Board.class).collectList().block();
	
	}
	@Override
	public List<Board> searchBoardList(String search) throws Exception {
		return client
			.get()
			.uri(uriBuiler -> uriBuiler.path("/search")
				.queryParam("search", search)			 
				.build())
			.retrieve()			
			.bodyToFlux(Board.class).collectList().block();
		
	}

	@Override
	public Board selectBoard(int num) {
		
		return client
				.get()
				.uri("/{num}", num)
				.retrieve()
				.bodyToMono(Board.class).block();
	}

	

	@Override
	public ResultMsg insertBoard(Board board, AppUser user) throws IOException {
		board.setWriteId(user.getId());
		board.setWriteName(user.getMemberName());
		board.setWriteDate(LocalDateTime.now());
		log.info("==service ===insertBoard");
		return client
				.post()
				.body(Mono.just(board), Board.class)
				// .body(fromFormData("name", "wonwoo"))
				//.body(fromFormData("name", "wonwoo").with("foo","bar").with("...","..."))
				//.bodyValue(new Sample("wonwoo"))
				.exchangeToMono(res ->  res.toEntity(ResultMsg.class))
				.map(entity -> {
					return getReturnResultMsg(entity);
				}).block();
	}

	@Override
	public ResultMsg updateBoard(Board board, AppUser user) {
	
		board.setModifyId(user.getId());
		board.setModifyName(user.getMemberName());
		board.setModifyDate(LocalDateTime.now());		

		log.info("==service ===updateBoard");
		return client
				.put()
				.body(Mono.just(board), Board.class)
				.exchangeToMono(res ->  res.toEntity(ResultMsg.class))				 
				.map(entity -> {
					return getReturnResultMsg(entity);
				}).block();
		
	}
	@Override
	public ResultMsg deleteBoard(int num) {
		return client
				.delete()
				.uri("/{num}", num)
				// .header("","")
				.exchangeToMono(res ->  res.toEntity(ResultMsg.class))
				.map(entity -> {
					return getReturnResultMsg(entity);
				}).block();
	}

	
	private ResultMsg getReturnResultMsg(ResponseEntity<ResultMsg> entity) {
		ResultMsg resultMsg = entity.getBody();					 
		if ((HttpStatus.OK).equals(entity.getStatusCode())) {
			resultMsg.setSuccessYn(SUCCESS);
			return resultMsg;
		}
		return resultMsg;

	}
	
	
}