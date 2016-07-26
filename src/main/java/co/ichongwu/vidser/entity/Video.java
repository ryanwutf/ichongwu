package co.ichongwu.vidser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import co.ichongwu.vidser.common.entity.AbstractEntity;

@Entity
public class Video extends AbstractEntity{

	@Id
	private Long id;
	private String title = "";
	private String description = "";
	private Integer type = 0; //1: 视频
	private String url = "";
	private String thumb = "";
	private Long duration = 0L; //播放时长
	private Integer source = 0; //来源 1. 56, 2. letv
	private String vid = "";
	private Long count = 0L; //播放次数
	private Integer status = 0; //状态 0：正常 1：删除
	private String ext;
	@Transient
	private String displayUpdateDate = "";
	
	public String getDisplayUpdateDate() {
		return displayUpdateDate;
	}
	public void setDisplayUpdateDate(String displayUpdateDate) {
		this.displayUpdateDate = displayUpdateDate;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	
	
}
