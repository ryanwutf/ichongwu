package co.ichongwu.vidser.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ichongwu.vidser.common.vo.CommonResponse;
import co.ichongwu.vidser.common.vo.Page;
import co.ichongwu.vidser.config.Constants;
import co.ichongwu.vidser.entity.Video;
import co.ichongwu.vidser.service.VideoService;


@RestController
@RequestMapping(method = RequestMethod.GET, produces = Constants.MIME.MINE_JSON)
public class VideoController {
	
	@Autowired
	VideoService videoService;

	@RequestMapping("/video/list")
	public CommonResponse list(
			@RequestParam(required=false) Integer pageSize,
			@RequestParam Integer pageNo) {
		Page<Video> page = new Page<Video>();
		page.setPageNo(pageNo);
		if(pageSize != null)  page.setPageSize(pageSize);
		return CommonResponse.success(videoService.list(page));
	}
	
	
	@RequestMapping("/video/url")
	public CommonResponse getUrl(
			@RequestParam String vid,
			@RequestParam Integer source) {
		return CommonResponse.success(videoService.getUrl(vid, source));
	}
	
	@RequestMapping("/video/addCount")
	public CommonResponse addCount(
			@RequestParam Long id) {
		videoService.addCount(id);
		return CommonResponse.getSuccessfulResponse();
	}
}
