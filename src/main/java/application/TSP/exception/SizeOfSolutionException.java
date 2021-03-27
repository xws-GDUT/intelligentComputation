package application.TSP.exception;

/**
 * @Author xws
 * @email wansenxu@163.com
 */
public class SizeOfSolutionException extends RuntimeException{
    public SizeOfSolutionException() {
        super();
    }

    public SizeOfSolutionException(String message) {
        super(message);
    }
}
