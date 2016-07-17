package co.ichongwu.vidser.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.ichongwu.vidser.common.vo.ErJson;
import co.ichongwu.vidser.config.Constants;
import co.ichongwu.vidser.dao.VideoDao;

@RestController
@RequestMapping(method = RequestMethod.GET, produces = Constants.MIME.MINE_JSON)
public class TestController {
	
	@Autowired
	VideoDao videoDao;
	
	@RequestMapping("/test")
	public Object Test() {
		return ErJson.success(videoDao.get(1L));
	}
}
