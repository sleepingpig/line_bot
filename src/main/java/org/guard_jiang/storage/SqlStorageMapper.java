package org.guard_jiang.storage;

import org.apache.ibatis.annotations.Param;
import org.guard_jiang.BlockingRecord;
import org.guard_jiang.Credential;
import org.guard_jiang.Role;
import org.guard_jiang.UserRole;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by someone on 4/1/2017.
 */
public interface SqlStorageMapper {

    @Nonnull
    Set<String> getUserIds();

    @Nullable
    Credential getCredential(@Param("userId") @Nonnull String id);

    void setCredential(
            @Param("userId") @Nonnull String id,
            @Param("credential") @Nonnull Credential credential);

    void setGroupRole(
            @Param("groupId") @Nonnull String groupId,
            @Param("userId") @Nonnull String userId,
            @Param("role") @Nonnull Role role);

    List<UserRole> getGroupRoles(@Param("groupId") @Nonnull String groupId);

    void removeGroupRole(
            @Param("groupId") @Nonnull String groupId,
            @Param("userId") @Nonnull String userId);

    void setGroupMetadata(
            @Param("groupId") @Nonnull String groupId,
            @Param("metadata") @Nonnull GroupMetadata metadata
            );

    @Nullable
    GroupMetadata getGroupMetadata(@Param("groupId") @Nonnull String groupId);

    @Nonnull
    Collection<BlockingRecord> getGroupBlockingRecords(
            @Param("groupId") @Nonnull String groupId);

    void setGroupBlockingRecord(
            @Param("groupId") @Nonnull String groupId,
            @Param("record") @Nonnull BlockingRecord record);

    @Nonnull
    Set<String> getGroupMembersBackup(
            @Param("groupId") @Nonnull String groupId
    );

    @Nonnull
    void addGroupMembersBackup(
            @Param("groupId") @Nonnull String groupId,
            @Param("userId") @Nonnull String userId
    );

    @Nonnull
    void clearGroupMemberBackup(
            @Param("groupId") @Nonnull String groupId
    );
}
