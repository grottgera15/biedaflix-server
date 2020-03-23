package bestworkingconditions.biedaflix.server.identity.role;

public enum OperationType {

    ADMINISTRATE_SOURCES("OP_ADMINISTRATE_SOURCES"),
    ADMINISTRATE_USERS("OP_ADMINISTRATE_USERS"),
    ADMINISTRATE_SERIES("OP_ADMINISTRATE_SERIES");

    private String operationName;

    OperationType() {
    }

    OperationType(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
