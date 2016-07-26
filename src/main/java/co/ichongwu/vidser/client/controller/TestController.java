package co.ichongwu.vidser.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ichongwu.vidser.common.vo.ErJson;
import co.ichongwu.vidser.config.Constants;
import co.ichongwu.vidser.dao.VideoDao;
import co.ichongwu.vidser.entity.Video;

@Controller
public class TestController {
	
	@Autowired
	VideoDao videoDao;
	
//	@RequestMapping("/test/index")
//	public Object Test() {
//		return ErJson.success(videoDao.get(1L));
//	}
	
	@RequestMapping("/test/video")
	public String video() {
		return "video";
	}
	
	@RequestMapping("/test/leaf")
	public String leaf() {
		return "leaf";
	}
}
