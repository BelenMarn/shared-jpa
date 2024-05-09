package com.autentia.sharedjpa.core.service.Mapper;

import com.autentia.sharedjpa.core.domain.Balance;
import com.autentia.sharedjpa.primaryAdapter.respond.BalanceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BalanceMapper {

    BalanceMapper INSTANCE = Mappers.getMapper(BalanceMapper.class);

    BalanceResponse toResponse(Balance balance);

    List<BalanceResponse> toResponseList(List<Balance> balances);
}
