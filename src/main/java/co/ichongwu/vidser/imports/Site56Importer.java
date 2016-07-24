package co.ichongwu.vidser.imports;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ichongwu.crawler.site56.Video56Content;
import co.ichongwu.crawler.site56.Video56MobileUtil;
import co.ichongwu.crawler.site56.Video56PCUtil;
import co.ichongwu.vidser.config.Constants;
import co.ichongwu.vidser.dao.VideoDao;
import co.ichongwu.vidser.entity.Video;

@Service
public class Site56Importer {
	@Autowired
	VideoDao videoDao;

	private static final String PREFIX = "搞笑动物视频";
	public static String DATA_FILE = "/Users/wuqiang/develop/workspace/mongoose/56.data";

	public List<String> readUrls(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(file)));
		String line = null;
		List<String> urls = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			urls.add(line);
		}
		return urls;
	}

	public boolean validate(Video56Content content) {
		if (content == null 
				|| StringUtils.isBlank(content.getmSubject())
				|| StringUtils.isBlank(Video56MobileUtil.chooseUrl(content))
				|| StringUtils.isBlank(content.getmCoverImg())) {
			return false;
		}
		return true;
	}

	public void import56Video() throws IOException {
		List<String> urls = readUrls(DATA_FILE);

		for (String url : urls) {
			Video56Content content = Video56MobileUtil.getVideoInfoObjByUrl(url);

			if (!validate(content)) {
				continue;
			}

			if (content.getmSubject().contains(PREFIX)) {
				Video video = new Video();
				video.setTitle(content.getmSubject().substring(PREFIX.length())
						.trim());
				video.setDescription(video.getTitle());
//				video.setUrl(chooseUrl(content));
				video.setThumb(content.getmCoverImg());
				video.setDuration(content.getmTotalime());
				video.setVid(String.valueOf(content.getmVid()));
				video.setCreateTime(new Date());
				video.setType(Constants.TYPE.VIDEO);
				video.setCreateUser(1L);
				video.setSource(Constants.VIDEO_SOURCE.SITE65);
				videoDao.insert(video);
			}
		}
	}
}
