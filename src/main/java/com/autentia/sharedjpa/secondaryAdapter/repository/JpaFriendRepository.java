package com.autentia.sharedjpa.secondaryAdapter.repository;

import com.autentia.sharedjpa.secondaryAdapter.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFriendRepository  extends JpaRepository<FriendEntity, Long> {


}
