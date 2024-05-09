package com.autentia.sharedjpa.core.service.Mapper;

import com.autentia.sharedjpa.core.domain.Expense;
import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.secondaryAdapter.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(source = "idFriend", target = "friend")
    Expense toDomainFromEntity(ExpenseEntity expenseEntity);

    @Mapping(source = "friend", target = "idFriend")
    ExpenseEntity toEntityFromDomain(Expense expense);

    default Friend map(long idFriend) {
        return new Friend(idFriend);
    }

    default long map(Friend friend) {
        return friend.getIdFriend();
    }

}
