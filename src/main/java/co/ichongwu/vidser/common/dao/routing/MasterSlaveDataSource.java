package co.ichongwu.vidser.common.dao.routing;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MasterSlaveDataSource extends AbstractRoutingDataSource {

    public static final String MASTER = "master";

    public static final String SLAVE = "slave";

    private ThreadLocal<String> current = new ThreadLocal<String>();

    public void master() {
        current.set(MASTER);
    }

    public void slave() {
        current.set(SLAVE);
    }
    
    public void reset() {
        current.remove();
    }

    @Override
    protected String determineCurrentLookupKey() {
        return current.get();
    }
    
    @Override
    protected String resolveSpecifiedLookupKey(Object lookupKey) {
        return String.valueOf(lookupKey);
    }

}
