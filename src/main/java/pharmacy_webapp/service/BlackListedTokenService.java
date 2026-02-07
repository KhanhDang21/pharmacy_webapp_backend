package pharmacy_webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy_webapp.model.BlackListedToken;
import pharmacy_webapp.repository.BlackListedTokenRepository;

import java.util.Date;

@Service
public class BlackListedTokenService {
    @Autowired
    private BlackListedTokenRepository blackListedTokenRepository;

    public void blackList(String token, Date expiryTime) {
        BlackListedToken blackListedToken = new BlackListedToken();
        blackListedToken.setToken(token);
        blackListedToken.setExpiryTime(expiryTime);
        blackListedTokenRepository.save(blackListedToken);
    }

    public boolean isBlackListed(String token) {
        return blackListedTokenRepository.findByToken(token).isPresent();
    }
}
