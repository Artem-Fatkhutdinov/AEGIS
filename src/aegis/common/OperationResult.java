package aegis.common;

public class OperationResult<T> {

    private T result;

    public OperationResult(T result){
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
