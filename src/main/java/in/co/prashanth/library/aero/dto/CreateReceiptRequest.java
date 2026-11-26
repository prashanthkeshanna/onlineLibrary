package in.co.prashanth.library.aero.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.JsonNode;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateReceiptRequest(
        @NotNull UUID transactionId,
        @NotBlank @Size(max = 64) String orderId,
        @NotBlank @Size(max = 64) String orderNumber,
        @NotBlank @Size(max = 64) String customerId,
        JsonNode templateData,
        @Size(max = 255) String s3Key,
        @Size(max = 512) String attachmentLocation,
        @PositiveOrZero Integer attemptCount,
        String lastError,
        CurrentStep currentStep,
        WorkflowStatus workflowStatus,
        OffsetDateTime nextAttemptAt,
        OffsetDateTime leaseExpiresAt,
        JsonNode transactionData,
        JsonNode orderData
) {
    public enum CurrentStep {
        TRANSACTION_INGESTED,
        ORDER_ENRICHED,
        NOTIFICATION_TRANSFORMED,
        NOTIFICATION_SENT,
        NOTIFICATION_CONTENT_UPLOADED,
        COMPLETED
    }

    public enum WorkflowStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETE,
        FAILED
    }
}
