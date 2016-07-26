package co.ichongwu.vidser.crawler.news.home;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import co.ichongwu.crawler.utils.HttpUtil;

public class HomePageProcessor {
	
	private static final Logger LOG = LoggerFactory.getLogger(HomePageProcessor.class);
	
	public List<NewsCell> getNewsCells() {
		return null;
	}
	
	public List<NewsCell> getNewsCell(String url) throws IOException {
		String content = HttpUtil.get(url);
		LOG.info(content);
		if((Integer)JSON.parseObject(content).get("error") == 0) {
			HomeNews news = JSONObject.parseObject(content, HomeNews.class);
			return news.getResult();
		}
		return null;
	}
	
	
	public static class HomeNews {
		private Integer error;
//		private String page;
		private List<NewsCell> result;
		public Integer getError() {
			return error;
		}
		public void setError(Integer error) {
			this.error = error;
		}
		public List<NewsCell> getResult() {
			return result;
		}
		public void setResult(List<NewsCell> result) {
			this.result = result;
		}
		
		
	}
	
	public static class NewsCell {
		private String aid;
		private String title;
		private String pic;
		private String like;
		
		public String getAid() {
			return aid;
		}
		public void setAid(String aid) {
			this.aid = aid;
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
		public String getLike() {
			return like;
		}
		public void setLike(String like) {
			this.like = like;
		}
		
		
	}

}
