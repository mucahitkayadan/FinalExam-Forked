package edu.miu.cse.vsms.exception;

public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String msg) {
        super(msg);
    }
}
