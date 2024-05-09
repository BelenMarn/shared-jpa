package com.autentia.sharedjpa.secondaryAdapter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend")
public class FriendEntity {

    @Id
    private long idFriend;
    private String name;

}

