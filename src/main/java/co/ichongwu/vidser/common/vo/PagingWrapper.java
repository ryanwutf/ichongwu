package co.ichongwu.vidser.common.vo;

public class PagingWrapper {

    public static final String START = "start";

    public static final String LIMIT = "limit";

    private Object result;

    private Long count;

    private Integer start;

    private Integer limit;

    public PagingWrapper() {

    }

    public PagingWrapper(Object result, Long count, Integer start, Integer limit) {
        this.result = result;
        this.count = count;
        this.start = start;
        this.limit = limit;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

}
