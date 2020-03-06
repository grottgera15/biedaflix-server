package bestworkingconditions.biedaflix.server.model.authority;

public enum OperationType {

    PUT("OP_PUT"),
    PATCH("OP_PATCH"),
    DELETE("OP_DELETE"),
    READ("OP_READ"),
    ADMINISTRATE_USERS("OP_ADMINISTRATE_USERS"),
    ADMINISTRATE_SERIES("OP_ADMINISTRATE_SERIES");

    private String operationName;

    OperationType(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationName() {
        return operationName;
    }
}
