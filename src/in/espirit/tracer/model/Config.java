package in.espirit.tracer.model;

public class Config {
	
	private String key, value,id;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/*
	
	@Override
	public String toString() {
		return "id";
	}
	
	public Config(String id){
		this.key="status";
		this.value="test";		
	}
	
	public Config() {
		
	}
	*/
}
