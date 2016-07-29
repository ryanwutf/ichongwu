package co.ichongwu.vidser.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import co.ichongwu.vidser.entity.News;
import co.ichongwu.vidser.service.NewsService;


@Controller
public class NewsDetailController {

	@Autowired
	NewsService newsService;
	
	@RequestMapping("/news/detail")
	public String detail(
			@RequestParam Long id,
			Model model) {
		News news = newsService.detail(id);
		model.addAttribute("news", news);
		return "news-detail";
	}
}
