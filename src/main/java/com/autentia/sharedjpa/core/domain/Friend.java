package com.autentia.sharedjpa.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    private long idFriend;
    private String name;

    public Friend(long idFriend) {
        this.idFriend = idFriend;
    }

    public long getIdFriend() {
        return idFriend;
    }


}
