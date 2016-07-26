package co.ichongwu.vidser.crawler.video.letv;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PlayJsonH5 {
	
	public Playurl getPlayurl() {
		return playurl;
	}


	public void setPlayurl(Playurl playurl) {
		this.playurl = playurl;
	}


	public Playurl playurl;
	
	
	public static class Playurl{

		
		private String vid;
		private String title;
		private String duration;
		private String pic;
		private List<String> domain;
		private Map<String, List<String>> dispatch;
		
		public String getVid() {
			return vid;
		}
		public void setVid(String vid) {
			this.vid = vid;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String picUrl) {
			this.pic = picUrl;
		}
		public List<String> getDomain() {
			return domain;
		}
		public void setDomain(List<String> domain) {
			this.domain = domain;
		}
		public Map<String, List<String>> getDispatch() {
			return dispatch;
		}
		public void setDispatch(Map<String, List<String>> dispatch) {
			this.dispatch = dispatch;
		}
		
		
	}
}


