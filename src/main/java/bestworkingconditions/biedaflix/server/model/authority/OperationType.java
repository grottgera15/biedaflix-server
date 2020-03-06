package bestworkingconditions.biedaflix.server.model.authority;

public enum OperationType {

    ADMINISTRATE_SOURCES("OP_ADMINISTRATE_SOURCES"),
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
