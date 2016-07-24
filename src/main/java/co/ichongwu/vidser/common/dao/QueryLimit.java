package co.ichongwu.vidser.common.dao;


public class QueryLimit {

    private Number start = 0;

    private Number limit;

    public QueryLimit(Number limit) {
        this.limit = limit;
    }

    public QueryLimit(Number start, Number limit) {
        this.start = start;
        this.limit = limit;
    }

    public Number getStart() {
        return start;
    }

    public void setStart(Number start) {
        this.start = start;
    }

    public Number getLimit() {
        return limit;
    }

    public void setLimit(Number limit) {
        this.limit = limit;
    }

}