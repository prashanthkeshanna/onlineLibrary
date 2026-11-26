package in.co.prashanth.library.aero.service;

import java.util.UUID;

public class ReceiptAlreadyExistsException extends RuntimeException {
    public ReceiptAlreadyExistsException(UUID transactionId, Throwable cause) {
        super("A receipt already exists for transaction " + transactionId, cause);
    }
}
