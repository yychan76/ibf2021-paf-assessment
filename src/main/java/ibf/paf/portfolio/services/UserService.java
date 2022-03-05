package ibf.paf.portfolio.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf.portfolio.models.CreateUserForm;
import ibf.paf.portfolio.models.UserProfile;
import ibf.paf.portfolio.repositories.PortfolioDBRepository;

@Service
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class.getName());

    @Autowired
    private PortfolioDBRepository portfolioRepo;

    public List<UserProfile> getAllUsers() {
        return portfolioRepo.getAllUsers();
    }

    public Optional<UserProfile> getUserById(final int uId) {
        return portfolioRepo.getUserById(uId);
    }

    public int addUser(final CreateUserForm createUserForm) throws SQLException {
        return portfolioRepo.addUser(createUserForm);
    }

    public boolean updateUser(final int uId, final UserProfile userProfile) {
        return portfolioRepo.updateUser(uId, userProfile);
    }

    public boolean deleteUser(final int uId) {
        return portfolioRepo.deleteUser(uId);
    }
}
