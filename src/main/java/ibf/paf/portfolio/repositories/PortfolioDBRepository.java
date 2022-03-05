package ibf.paf.portfolio.repositories;

import static ibf.paf.portfolio.repositories.SQL.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf.paf.portfolio.models.CreateUserForm;
import ibf.paf.portfolio.models.UserProfile;

@Repository
public class PortfolioDBRepository {
    private static final Logger LOG = Logger.getLogger(PortfolioDBRepository.class.getName());

    @Autowired
    JdbcTemplate template;

    public List<UserProfile> getAllUsers() {
        final List<UserProfile> users = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(SQL_USER_SELECT_ALL);

        while (rs.next()) {
            UserProfile userProfile = UserProfile.populate(rs);
            users.add(userProfile);
        }
        return users;
    }

    // TODO return the portfolios and watchlists for the user too
    public Optional<UserProfile> getUserById(final int uId) {
        final SqlRowSet rs = template.queryForRowSet(SQL_USER_SELECT_BY_ID, uId);

        if (rs.next()) {
            return Optional.ofNullable(UserProfile.populate(rs));
        }
        return Optional.empty();
    }

    public int addUser(final CreateUserForm createUserForm) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_USER_INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, createUserForm.email());
            ps.setString(2, createUserForm.password());
            ps.setString(3, createUserForm.displayName());
            return ps;
        }, keyHolder);

        try {
            return keyHolder.getKey().intValue();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public boolean updateUser(final int uId, UserProfile userProfile) {
        int updated = template.update(SQL_USER_UPDATE_BY_ID,
                userProfile.email(),
                userProfile.displayName(),
                userProfile.avatarUrl(),
                uId);

        return updated > 0;
    }

    public boolean deleteUser(final int uId) {
        int deleted = template.update(SQL_USER_DELETE_BY_ID, uId);
        return deleted > 0;
    }
}
