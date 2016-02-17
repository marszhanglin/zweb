package z.pn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 推送消息  实体映射表
 * <br>hibernate会自动帮我们创建表    
 * @author Administrator
 *
 */
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//自动生成id
	private Long id;
    @Column(name = "apiKey", length = 64)
	private String apiKey;
    @Column(name = "username", nullable = false, length = 64)
	private String username;
    @Column(name = "title", nullable = false, length = 64 )
	private String title;
    @Column(name = "message", nullable = false, length = 1024)
	private String message;
    @Column(name = "uri",  length = 512)
	private String uri;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Notification(String apiKey, String username, String title,
			String message, String uri) {
		this.apiKey = apiKey;
		this.username = username;
		this.title = title;
		this.message = message;
		this.uri = uri;
	}
	
}
