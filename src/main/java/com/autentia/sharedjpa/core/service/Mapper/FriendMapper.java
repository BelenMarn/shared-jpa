package com.autentia.sharedjpa.core.service.Mapper;


import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.primaryAdapter.request.FriendRequest;
import com.autentia.sharedjpa.primaryAdapter.respond.FriendResponse;
import com.autentia.sharedjpa.secondaryAdapter.entity.FriendEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FriendMapper {
    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    Friend toDomainFromRequest(FriendRequest friendRequest);

    Friend toDomainFromEntity(FriendEntity friendEntity);

    List<Friend> toDomainListFromEntity(List<FriendEntity> friendEntity);

    FriendResponse toResponseFromDomain(Friend friend);

    List<FriendResponse> toResponseListFromDomain(List<Friend> friends);

    FriendEntity toEntityFromDomain(Friend friend);

}
