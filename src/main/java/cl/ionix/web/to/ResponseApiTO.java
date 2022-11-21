package cl.ionix.web.to;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ResponseApiTO<T> {

	private Integer responseCode;
	private String description;
	private Long elapsedTime;
	private T result;
	
	public void setHttpStatus(HttpStatus httpStatus) {
		this.description = httpStatus.name();
		this.responseCode = httpStatus.value();
	}
	
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseApiTO [responseCode=");
		builder.append(responseCode);
		builder.append(", description=");
		builder.append(description);
		builder.append(", elapsedTime=");
		builder.append(elapsedTime);
		builder.append(", result=");
		builder.append(result);
		builder.append("]");
		return builder.toString();
	}
}
