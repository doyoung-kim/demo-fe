function isExist(object) {
	if(object==null || object== undefined || trim(object).length==0 || object == "undefined") {
		return false;
	}
	return true;
}

function alertAjaxError() {
	alert("요청을 완료하지 못했습니다.");
}

function getAjax(requestUrl,params,callbackFunction,responseDataType) {
	if(!isExist(responseDataType)) {
		responseDataType = "html";
	}
	jQuery.ajax({
		url: requestUrl,
		type: 'get',
		data: params,
		dataType: responseDataType,
		success: function(result) {
			eval(callbackFunction)(result);
		},
		error: function() {
			eval(alertAjaxError)();
		}
	});
}

function postAjax(requestUrl,params,callbackFunction, responseDataType) {
	if(!isExist(responseDataType)) {
		responseDataType = "html";
	}
	
	jQuery.ajax({
		url: requestUrl,
		type: 'post',
		data: params,
		dataType: responseDataType,
		success: function(result) {
			eval(callbackFunction)(result);
		},
		error: function() {
			eval(alertAjaxError)();
		}
	});
}

function jsonAjax(requestUrl,params,callbackFunction) {
	jQuery.ajax({
		url: requestUrl,
		type: 'get',
		data: params,
		dataType: 'json',
		success: function(result) {
			eval(callbackFunction)(result);
		},
		error: function() {
			eval(alertAjaxError)();
		}
	});
}

function sendFormAjax(requestUrl, formId, callbackFunction, responseDataType) {
	var params = jQuery("#"+formId).serialize();
	
	if(!isExist(responseDataType)) {
		responseDataType = "html";
	}
	
	jQuery.ajax({
		url: requestUrl,
		type: 'post',
		data: params,
		dataType: responseDataType,
		success: function(result) {
			eval(callbackFunction)(result);
		},
		error: function() {
			eval(alertAjaxError)();
		}
	});
}

/**
 * <script src="/js/jquery.form.js"></script>
 * 한 화면에 하나만 사용할 것!
 */
var callback = "";
function bindAjaxFormWithFile(formId, callbackFunction, responseDataType) {
	if(!isExist(responseDataType)) {
		responseDataType = "html";
	}
	
	callback = callbackFunction;
	
	 var options = { 
        success:   call_callbackFunction,  // post-submit callback 
        dataType:  responseDataType        // 'xml', 'script', or 'json' (expected server response type) 
    }; 
 
	 jQuery('#'+formId).ajaxForm(options); 
}

function call_callbackFunction(data) {
	eval(callback)(data);
}

function trim(str) {
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

(function($){
	$.fn.serializeJSON=function() {
		var json = {};
		jQuery.map($(this).serializeArray(), function(n, i){
			json[n['name']] = n['value'];
		});
		return json;
	};
	
	$.fn.serializeObject = function() {
		var $ = jQuery;
		var o = {};
		var a = $(this).serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
})(jQuery);
