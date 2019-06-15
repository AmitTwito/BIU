package utility;

public class Selection<T> {

	private String key;
	private String message;
	private T returnVal;

	public Selection(String key, String message, T returnVal){
		this.key = key;
		this.message = message;
		this.returnVal = returnVal;
	}

	public String getKey() {
		return key;
	}

	public String getMessage() {
		return message;
	}

	public T getReturnVal() {
		return returnVal;
	}
}
