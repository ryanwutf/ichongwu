package co.ichongwu.vidser.crawler.news.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import co.ichongwu.crawler.site56.FilePipeline;
import co.ichongwu.crawler.site56.Site56PageProcessor;
import co.ichongwu.crawler.utils.HttpUtil;

public class HomePageProcessor implements PageProcessor {
	
//	public static void main(String[] args) {
//		 Spider.create(new HomePageProcessor())
//		 .addUrl("http://123.57.136.98/")
//         .thread(1)
//         .run();
//	}
	
	private static final Logger LOG = LoggerFactory.getLogger(HomePageProcessor.class);
	
	public static final String LIST_URL = "http://123.57.136.98/index.php/Home/Index/getdata?c=19&page=$PAGE$&_=1469349296434";
	
	public static final String URL_PRIFIX = "http://123.57.136.98/";
	
	public static final String DETAIL_URL = "http://123.57.136.98/index.php/Home/Index/details.html?aid=$AID$&p=5&rp=null";
	
	public static final String DETAIL_XPATH = "//section[@class='main']";
	
	public static List<String> cellUrls = null;
	public static Map<String, NewsCell> cells = null;
	
	public HomePageProcessor() {
		super();
		List<NewsCell> cellList = getNewsCells();
		cellUrls = getCellUrls(cellList);
		cells = getCellMap(cellList);
	}
	
	private Site site = Site
			.me()
			.setDomain("123.57.136.98")
			.setSleepTime(1000)
			.setUserAgent(
					"Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
	
	
	public List<String> getCellUrls(List<NewsCell> newCells) {
		List<String> urls = new ArrayList<String>();
		for(NewsCell cell : newCells) {
			urls.add(DETAIL_URL.replace("$AID$", cell.getAid()));
		}
		return urls;
	}
	
	public Map<String, NewsCell> getCellMap(List<NewsCell> newCells) {
		Map<String, NewsCell> cells = new HashMap<String, NewsCell>();
		for(NewsCell cell : newCells) {
			cells.put(cell.getAid(), cell);
		}
		return cells;
	}
	
	@SuppressWarnings("unchecked")
	public List<NewsCell> getNewsCells() {
		List<NewsCell> results = new ArrayList<NewsCell>();
		List<String> urls = getListUrls();
		for(int i = 0; i < urls.size(); i++) {
			try {
				results = (List<NewsCell>) CollectionUtils.union(results, getNewsCell(urls.get(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}
	
	public List<NewsCell> getNewsCell(String url) throws IOException {
		String content = HttpUtil.get(url);
		LOG.info(content);
		if((Integer)JSON.parseObject(content).get("error") == 0) {
			HomeNews news = JSONObject.parseObject(content, HomeNews.class);
			
			news.getResult().forEach(newsCell -> {
				newsCell.setPic(getComplatePicUrl(newsCell.getPic()));
			});;
			return news.getResult();
		}
		return null;
	}

	private String getComplatePicUrl(String url) {
		return URL_PRIFIX + url;
	}
	
	public List<String> getListUrls() {
		List<String> urls = new ArrayList<String>();
		for(int i = 1; i <= 4; i++) {
			urls.add(LIST_URL.replace("$PAGE$", String.valueOf(i)));
		}
		return urls;
	}
	
	@Override
	public void process(Page page) {
		page.addTargetRequests(cellUrls);
		if(page.getUrl() != null && !StringUtils.isBlank(page.getUrl().toString())) {
			String detailHtml = page.getHtml().xpath(DETAIL_XPATH).get();
			
			if(detailHtml == null) {
				return;
			}
			
			String[] links = page.getHtml().xpath("//section/img/@src").all().stream().filter(link->link.startsWith("/uploads/")).toArray(String[]::new);
			
			String aid = page.getHtml().xpath("//span[@aid]/@aid").get();
			
			for(String link : links) {
				if(detailHtml.contains(link)) {
					detailHtml = detailHtml.replaceAll(link, getComplatePicUrl(link));
				}
			}
			
			page.putField(aid, detailHtml);
		}
		
		
	}

	@Override
	public Site getSite() {
		return site;
	}
	
	
	public static class HomeNews {
		private Integer error;
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
