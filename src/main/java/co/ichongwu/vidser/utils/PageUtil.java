package co.ichongwu.vidser.utils;

import co.ichongwu.vidser.config.Constants;


/**
 * Created by whf on 3/24/16.
 */
public class PageUtil {
    private PageUtil() {}

    /**
     * 计算数据库翻页偏移量
     * @param page
     * @param limit
     * @return
     */
    public static int getPageOffset(int page, int limit) {
        if (page < 1) {
            page = 1;
        }

        if (limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }

        return (page - 1) * limit;
    }

    /**
     * 计算总页数
     * @param totalCount 数据总条数
     * @param pageSize 每页的条数
     * @return
     */
    public static int getTotalPages(int totalCount, int pageSize) {
        if (pageSize <= 0) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        int mod = totalCount % pageSize;

        // 能整除
        if (0 == mod) {
            return totalCount / pageSize;
        }

        return totalCount / pageSize + 1;
    }
}
