package com.autentia.sharedjpa.secondaryAdapter.repository;


import com.autentia.sharedjpa.core.domain.Friend;
import com.autentia.sharedjpa.core.domain.repositoryDomain.ExpenseRepository;
import com.autentia.sharedjpa.core.domain.repositoryDomain.FriendRepository;
import com.autentia.sharedjpa.core.service.Mapper.FriendMapper;
import com.autentia.sharedjpa.secondaryAdapter.entity.ExpenseEntity;
import com.autentia.sharedjpa.secondaryAdapter.entity.FriendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaFriendRepositoryAdapter implements FriendRepository {

    private final JpaFriendRepository jpaFriendRepository;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public JpaFriendRepositoryAdapter(JpaFriendRepository jpaFriendRepository, ExpenseRepository expenseRepository) {
        this.jpaFriendRepository = jpaFriendRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Friend> findAll() {
        return jpaFriendRepository.findAll().stream()
                .map(FriendMapper.INSTANCE::toDomainFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Friend friend) {
        FriendEntity friendEntity = FriendMapper.INSTANCE.toEntityFromDomain(friend);
        jpaFriendRepository.save(friendEntity);
    }

    @Override
    public void update(Long id, String name) {
        Optional<FriendEntity> optionalFriend = jpaFriendRepository.findById(id);
        if (optionalFriend.isPresent()) {
            FriendEntity friendEntity = optionalFriend.get();
            friendEntity.setName(name);
            jpaFriendRepository.save(friendEntity);
        }
    }

    @Override
    public Friend findFriendById(long idFriend) {
        Optional<FriendEntity> optionalFriend = jpaFriendRepository.findById(idFriend);
        return optionalFriend.map(FriendMapper.INSTANCE::toDomainFromEntity).orElse(null);
    }

    @Override
    public int getNumberOfFriends() {
        return (int) jpaFriendRepository.count();
    }

    @Override
    public double getBalanceOfFriend(long id) {
        List<ExpenseEntity> expenses = expenseRepository.findExpensesOfFriend(id);
        return expenses.stream().mapToDouble(ExpenseEntity::getAmount).sum();
    }

    @Override
    public void deleteFriend(long idFriend) {
        jpaFriendRepository.deleteById(idFriend);
    }
}
