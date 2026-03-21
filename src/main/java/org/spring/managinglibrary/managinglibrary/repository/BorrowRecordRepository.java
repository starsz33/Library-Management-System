package org.spring.managinglibrary.managinglibrary.repository;

import org.spring.managinglibrary.managinglibrary.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord,Long> {
}
