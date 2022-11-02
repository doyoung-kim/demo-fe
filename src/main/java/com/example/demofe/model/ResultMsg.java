package com.example.demofe.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ResultMsg
 */
@AllArgsConstructor
@Setter @Getter
@NoArgsConstructor
@Builder
@ToString
public class ResultMsg {
	private String successYn;
	private String statusCode;
	private String message;
	private String devMessage;
	
	
	
	public ResultMsg(String successYn) {
		this.successYn = successYn;
		this.statusCode = null;
		this.message = null;
		this.devMessage = null;
	}

}