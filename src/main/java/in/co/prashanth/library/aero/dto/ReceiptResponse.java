package in.co.prashanth.library.aero.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReceiptResponse(
        long id,
        UUID transactionId,
        CreateReceiptRequest.CurrentStep currentStep,
        CreateReceiptRequest.WorkflowStatus workflowStatus,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
