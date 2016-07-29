package co.ichongwu.vidser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ichongwu.vidser.common.dao.QueryLimit;
import co.ichongwu.vidser.common.vo.Page;
import co.ichongwu.vidser.dao.NewsDao;
import co.ichongwu.vidser.entity.News;

@Service
public class NewsService {
	@Autowired
	NewsDao newsDao;
	
	public Page<News> list(Page<News> page) {
		QueryLimit queryLimit = new QueryLimit(page.offset(), page.limit());
		page.setData(newsDao.list(null, queryLimit));
		return page;
	}
	
	public News detail(Long id) {
		return newsDao.get(id);
	}
}
