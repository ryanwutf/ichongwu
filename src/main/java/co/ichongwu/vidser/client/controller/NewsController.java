package co.ichongwu.vidser.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ichongwu.vidser.common.vo.CommonResponse;
import co.ichongwu.vidser.common.vo.Page;
import co.ichongwu.vidser.config.Constants;
import co.ichongwu.vidser.entity.News;
import co.ichongwu.vidser.service.NewsService;

@RestController
@RequestMapping(method = RequestMethod.GET, produces = Constants.MIME.MINE_JSON)
public class NewsController {

	@Autowired
	NewsService newsService;
	
	@RequestMapping("/news/list")
	public CommonResponse list(
			@RequestParam(required=false) Integer pageSize,
			@RequestParam Integer pageNo) {
		Page<News> page = new Page<News>();
		page.setPageNo(pageNo);
		if(pageSize != null)  page.setPageSize(pageSize);
		return CommonResponse.success(newsService.list(page));
	}
	
}
