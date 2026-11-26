package in.co.prashanth.library.aero.controller;

import in.co.prashanth.library.aero.dto.CreateReceiptRequest;
import in.co.prashanth.library.aero.dto.ReceiptResponse;
import in.co.prashanth.library.aero.entity.ReceiptEntity;
import in.co.prashanth.library.aero.service.ReceiptAlreadyExistsException;
import in.co.prashanth.library.aero.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReceiptById(@PathVariable long id) {
        return receiptService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Receipt not found with id " + id)));
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<?> getReceiptByTransactionId(@PathVariable UUID transactionId) {
        return receiptService.findByTransactionId(transactionId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Receipt not found for transactionId " + transactionId)));
    }

    @PostMapping
    public ResponseEntity<ReceiptResponse> createReceipt(@Valid @RequestBody CreateReceiptRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(receiptService.createReceipt(request));
    }

    @ExceptionHandler(ReceiptAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateReceipt(ReceiptAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", exception.getMessage()));
    }
}
