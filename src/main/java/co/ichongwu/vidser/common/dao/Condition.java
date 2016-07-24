package co.ichongwu.vidser.common.dao;


public enum Condition {

    EQUALS {

        @Override
        public String suffix() {
            return "";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " = :{" + paramName + "}";
        }

    },
    NOT_EQUALS {

        @Override
        public String suffix() {
            return "#!=";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " != :{" + paramName + "}";
        }

    },
    GREATER_THAN {

        @Override
        public String suffix() {
            return "#>";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " > :{" + paramName + "}";
        }

    },
    LESS_THAN {

        @Override
        public String suffix() {
            return "#<";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " < :{" + paramName + "}";
        }

    },
    GREATER_THAN_OR_EQUALS {

        @Override
        public String suffix() {
            return "#>=";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " >= :{" + paramName + "}";
        }

    },
    LESS_THAN_OR_EQUALS {

        @Override
        public String suffix() {
            return "#<=";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " <= :{" + paramName + "}";
        }

    },
    IN {

        @Override
        public String suffix() {
            return "#in";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " in (:{" + paramName + "})";
        }

    },
    NOT_IN {

        @Override
        public String suffix() {
            return "#not in";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " not in (:{" + paramName + "})";
        }

    },
    LIKE {

        @Override
        public String suffix() {
            return "#like";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " like :{" + paramName + "}";
        }

    },
    IS_NULL {

        @Override
        public String suffix() {
            return "#is null";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " is null";
        }

    },
    IS_NOT_NULL {

        @Override
        public String suffix() {
            return "#is not null";
        }

        @Override
        public String expression(String paramName, String columnName) {
            return columnName + " is not null";
        }

    },
    ;

    public static String startsWith(String string){
        return string + "%";
    }
    
    public static String endsWith(String string){
        return "%" + string;
    }

    public static String contains(String string){
        return "%" + string + "%";
    }
    
    public abstract String suffix();

    public abstract String expression(String paramName, String columnName);

}
