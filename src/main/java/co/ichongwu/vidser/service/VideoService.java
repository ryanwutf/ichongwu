package co.ichongwu.vidser.service;

import java.io.IOException;

import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ichongwu.crawler.site56.Video56Content;
import co.ichongwu.crawler.site56.Video56MobileUtil;
import co.ichongwu.vidser.client.controller.VideoController;
import co.ichongwu.vidser.common.dao.QueryLimit;
import co.ichongwu.vidser.common.vo.Page;
import co.ichongwu.vidser.config.Constants;
import co.ichongwu.vidser.crawler.letv.LetvUtil;
import co.ichongwu.vidser.dao.VideoDao;
import co.ichongwu.vidser.entity.Video;

@Service
public class VideoService {

	@Autowired
	VideoDao videoDao;

	public Page<Video> list(Page<Video> page) {
		QueryLimit queryLimit = new QueryLimit(page.offset(), page.limit());
		page.setData(videoDao.list(null, queryLimit));
		return page;
	}

	public String getUrl(String vid, Integer source) {
		if (source == null) {
			return null;
		}

		if (Constants.VIDEO_SOURCE.SITE65.equals(source)) {
			return getSite56Url(vid);
		} else if (Constants.VIDEO_SOURCE.LETV.equals(source)) {
			return getLetvUrl(vid);
		} 
		
		return null;
	}

	protected String getSite56Url(String vid) {
		Video56Content content = Video56MobileUtil.getVideoInfoObjById(vid);
		return Video56MobileUtil.chooseUrl(content);
	}
	
	protected String getLetvUrl(String vid) {
		try {
			return LetvUtil.getPlayInfo(vid).getLocation();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
