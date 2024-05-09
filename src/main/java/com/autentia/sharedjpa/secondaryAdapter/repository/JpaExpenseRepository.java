package com.autentia.sharedjpa.secondaryAdapter.repository;

import com.autentia.sharedjpa.secondaryAdapter.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query(
            value = "select f.name from friend f, expense e where f.id_friend = e.id_friend and e.id_expense = :id",

            nativeQuery = true
    )
    String findExpenseFriendNameById(@Param("id") Long id);

}
