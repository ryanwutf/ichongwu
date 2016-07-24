package co.ichongwu.vidser.crawler.letv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.Gson;

import co.ichongwu.crawler.utils.HttpUtil;


/**
 * 乐视是ajax请求，可以直接请求url，然后解析json获取结果, 不需要爬虫
 * http://list.le.com/apin/chandata.json?c=30&d=2&md=&o=1&p=4&t=592026
 * 已知p是页码，从1开始
 * 宠物目前有374个视频，每页30个视频，共13页，所以最大的页码是30
 * @author wuqiang
 */
public class LetvPageProcessor {
	
//	public static void main(String[] args) throws IOException {
//		LetvPageProcessor processor = new LetvPageProcessor();
//		List<LetvVideoCell> cells = processor.getVideoCells();
//		System.out.println(cells.size());
//	}
	
	private final Logger log = LoggerFactory.getLogger(LetvPageProcessor.class);
	//#PAGE#替换
	public static String URL = "http://list.le.com/apin/chandata.json?c=30&d=2&md=&o=1&p=#PAGE#&t=592026";
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<LetvVideoCell> getVideoCells() throws IOException {
		List<LetvVideoCell> videoCells = new ArrayList<LetvVideoCell>();
		LetvVideoPage page = null;
		for(int i = 1; i < 14; i++) {
			String url = URL.replace("#PAGE#", String.valueOf(i));
			log.info("url " + i + " : " + url);
			String content = HttpUtil.get(url);
			log.debug(content);
			page = JSON.parseObject(content, LetvVideoPage.class);
			if(page != null && page.getVideoList() != null) {
				videoCells = (List<LetvVideoCell>) CollectionUtils.union(videoCells, page.getVideoList());
			}
		}
		return videoCells;
	}
	
	/**
	 * 
	 * @author wuqiang
	 *
	 */
	public static class LetvVideoPage {
		private List<LetvVideoCell> videoList;

		@JSONField(name="video_list") 
		public List<LetvVideoCell> getVideoList() {
			return videoList;
		}
		@JSONField(name="video_list") 
		public void setVideoList(List<LetvVideoCell> videoList) {
			this.videoList = videoList;
		}	
		
	}
	
	/**
	 * 
	 * @author wuqiang
	 *
	 */
	public static class LetvVideoCell {
		private String vid;
		private String videoPic;
		private String name;
		private String description = "";
		private String duration;
		private Map<String,String> images;
		
		public String getVid() {
			return vid;
		}
		public void setVid(String vid) {
			this.vid = vid;
		}
		public String getVideoPic() {
			return videoPic;
		}
		public void setVideoPic(String videoPic) {
			this.videoPic = videoPic;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public Map<String, String> getImages() {
			return images;
		}
		public void setImages(Map<String, String> images) {
			this.images = images;
		}
		
	}
}
