package in.co.prashanth.library.aero.repository;

import in.co.prashanth.library.aero.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, Long> {
    Optional<ReceiptEntity> findByTransactionId(UUID transactionId);
}
