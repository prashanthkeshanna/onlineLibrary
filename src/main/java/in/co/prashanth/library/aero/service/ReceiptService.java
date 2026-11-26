package in.co.prashanth.library.aero.service;

import in.co.prashanth.library.aero.dto.CreateReceiptRequest;
import in.co.prashanth.library.aero.dto.ReceiptResponse;
import in.co.prashanth.library.aero.entity.ReceiptEntity;
import in.co.prashanth.library.aero.repository.ReceiptRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

@Service
public class ReceiptService {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptService.class);

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public ReceiptResponse createReceipt(CreateReceiptRequest request) {
        try {
            ReceiptEntity receipt = receiptRepository.saveAndFlush(new ReceiptEntity(
                    request.transactionId(),
                    request.orderId(),
                    request.orderNumber(),
                    request.customerId(),
                    request.templateData(),
                    request.s3Key(),
                    request.attachmentLocation(),
                    request.attemptCount() == null ? 0 : request.attemptCount(),
                    request.lastError(),
                    request.currentStep() == null
                            ? CreateReceiptRequest.CurrentStep.TRANSACTION_INGESTED
                            : request.currentStep(),
                    request.workflowStatus() == null
                            ? CreateReceiptRequest.WorkflowStatus.PENDING
                            : request.workflowStatus(),
                    request.nextAttemptAt(),
                    request.leaseExpiresAt(),
                    request.transactionData(),
                    request.orderData()
            ));
            logger.info("Created receipt with id={} and status={}",
                    receipt.getId(), receipt.getWorkflowStatus());
            return new ReceiptResponse(
                    receipt.getId(),
                    receipt.getTransactionId(),
                    receipt.getCurrentStep(),
                    receipt.getWorkflowStatus(),
                    receipt.getCreatedAt(),
                    receipt.getUpdatedAt()
            );
        } catch (DataIntegrityViolationException exception) {
            logger.warn("Receipt creation failed due to a database integrity constraint");
            throw new ReceiptAlreadyExistsException(request.transactionId(), exception);
        }
    }

    public Optional<ReceiptEntity> findById(long id) {
        Optional<ReceiptEntity> receipt = receiptRepository.findById(id);
        if (receipt.isPresent()) {
            logger.debug("Found receipt with id={}", id);
        } else {
            logger.info("Receipt with id={} was not found", id);
        }
        return receipt;
    }

    public Optional<ReceiptEntity> findByTransactionId(UUID transactionId) {
        Optional<ReceiptEntity> receipt = receiptRepository.findByTransactionId(transactionId);
        if (receipt.isPresent()) {
            logger.debug("Found receipt for transactionId={}", transactionId);
        } else {
            logger.info("Receipt for transactionId={} was not found", transactionId);
        }
        return receipt;
    }
}
