package pharmacy_webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pharmacy_webapp.model.BlackListedToken;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BlackListedTokenRepository extends MongoRepository<BlackListedToken, String> {
    Optional<BlackListedToken> findByToken(String token);
    void deleteByExpiryTimeBefore(Date date);
}
