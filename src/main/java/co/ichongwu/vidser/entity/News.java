package co.ichongwu.vidser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import co.ichongwu.vidser.common.entity.AbstractEntity;

@Entity
public class News extends AbstractEntity{

	@Id
	private Long id;
	
	private String aid = "";
	private Integer source = 0;
	private String title = "";
	private String pic = "";
	private String content = "";
	private Integer status = 0;
	private Integer count = 0;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
