package edu.cnm.deepdive.farkle.model.dao;

import edu.cnm.deepdive.farkle.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> findByAuthKey(String authKey);

  Optional<User> findByExternalKey(UUID externalKey);

//  List<Game> getAllByuserIdWhereWinnerIdIsUser
//  List<Game> getAllByuserIdWhereloserIdIsUser
  // TODO: 3/9/2025 add more spring data queries
//  @Query("SELECT u FROM User u ORDER BY u.")u
}
