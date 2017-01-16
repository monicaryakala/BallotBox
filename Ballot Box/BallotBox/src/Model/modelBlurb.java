package Model;

public class modelBlurb {

	private String title;
	private String text;
	private String date;
	private String id;
	private String username;
	
	
	public modelBlurb(String id, String username, String title, String text, String date) {
		super();
		this.title = title;
		this.text = text;
		this.date = date;
		this.id = id;
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
