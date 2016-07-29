package co.ichongwu.vidser.crawler.news.home;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ichongwu.vidser.config.Constants;
import co.ichongwu.vidser.crawler.news.home.HomePageProcessor.NewsCell;
import co.ichongwu.vidser.dao.NewsDao;
import co.ichongwu.vidser.entity.News;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Service
public class HomeNewDBPipeline implements Pipeline{
	
	@Autowired
	NewsDao newsDao;

	@Override
	public void process(ResultItems resultItems, Task task) {
		resultItems.getAll().forEach((key,value) -> {
			News news = new News();
			NewsCell cell = HomePageProcessor.cells.get(key);
			news.setContent(value.toString());
			news.setPic(cell.getPic());
			news.setTitle(cell.getTitle());
			news.setSource(Constants.NEWS_SOURCE.HOME);
			news.setCreateTime(new Date());
			news.setAid(cell.getAid());
			newsDao.insert(news);
		});
		
	}

}
