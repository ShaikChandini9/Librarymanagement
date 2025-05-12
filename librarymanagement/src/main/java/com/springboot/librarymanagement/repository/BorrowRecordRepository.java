package com.springboot.librarymanagement.repository;

import com.springboot.librarymanagement.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {


}
