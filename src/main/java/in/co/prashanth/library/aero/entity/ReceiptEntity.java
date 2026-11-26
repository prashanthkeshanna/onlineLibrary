package in.co.prashanth.library.aero.entity;

import in.co.prashanth.library.aero.dto.CreateReceiptRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import tools.jackson.databind.JsonNode;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "receipts", schema = "public")
public class ReceiptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private UUID transactionId;

    @Column(name = "order_id", nullable = false, length = 64)
    private String orderId;

    @Column(name = "order_number", nullable = false, length = 64)
    private String orderNumber;

    @Column(name = "customer_id", nullable = false, length = 64)
    private String customerId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "template_data", columnDefinition = "jsonb")
    private JsonNode templateData;

    @Column(name = "s3_key", length = 255)
    private String s3Key;

    @Column(name = "attachment_location", length = 512)
    private String attachmentLocation;

    @Column(name = "attempt_count", nullable = false)
    private int attemptCount;

    @Column(name = "last_error")
    private String lastError;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_step", nullable = false, length = 50)
    private CreateReceiptRequest.CurrentStep currentStep;

    @Enumerated(EnumType.STRING)
    @Column(name = "workflow_status", nullable = false, length = 20)
    private CreateReceiptRequest.WorkflowStatus workflowStatus;

    @Column(name = "next_attempt_at")
    private OffsetDateTime nextAttemptAt;

    @Column(name = "lease_expires_at")
    private OffsetDateTime leaseExpiresAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "transaction_data", columnDefinition = "jsonb")
    private JsonNode transactionData;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "order_data", columnDefinition = "jsonb")
    private JsonNode orderData;

    protected ReceiptEntity() {
    }

    public ReceiptEntity(
            UUID transactionId,
            String orderId,
            String orderNumber,
            String customerId,
            JsonNode templateData,
            String s3Key,
            String attachmentLocation,
            int attemptCount,
            String lastError,
            CreateReceiptRequest.CurrentStep currentStep,
            CreateReceiptRequest.WorkflowStatus workflowStatus,
            OffsetDateTime nextAttemptAt,
            OffsetDateTime leaseExpiresAt,
            JsonNode transactionData,
            JsonNode orderData
    ) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.templateData = templateData;
        this.s3Key = s3Key;
        this.attachmentLocation = attachmentLocation;
        this.attemptCount = attemptCount;
        this.lastError = lastError;
        this.currentStep = currentStep;
        this.workflowStatus = workflowStatus;
        this.nextAttemptAt = nextAttemptAt;
        this.leaseExpiresAt = leaseExpiresAt;
        this.transactionData = transactionData;
        this.orderData = orderData;
    }

    public Long getId() {
        return id;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public JsonNode getTemplateData() {
        return templateData;
    }

    public String getS3Key() {
        return s3Key;
    }

    public String getAttachmentLocation() {
        return attachmentLocation;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public String getLastError() {
        return lastError;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public CreateReceiptRequest.CurrentStep getCurrentStep() {
        return currentStep;
    }

    public CreateReceiptRequest.WorkflowStatus getWorkflowStatus() {
        return workflowStatus;
    }

    public OffsetDateTime getNextAttemptAt() {
        return nextAttemptAt;
    }

    public OffsetDateTime getLeaseExpiresAt() {
        return leaseExpiresAt;
    }

    public JsonNode getTransactionData() {
        return transactionData;
    }

    public JsonNode getOrderData() {
        return orderData;
    }
}
