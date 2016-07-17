package co.ichongwu.vidser.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sina.dina.common.dao.QueryLimit;

import co.ichongwu.vidser.common.vo.Page;
import co.ichongwu.vidser.dao.VideoDao;
import co.ichongwu.vidser.entity.Video;

@Service
public class VideoService {
	
	@Autowired
	VideoDao videoDao;
	
	public Page<Video> list(Page<Video> page) {
		QueryLimit queryLimit = new QueryLimit(page.offset(), page.limit());
		page.setResult(videoDao.list(null, queryLimit));
		return page;
	}

}
