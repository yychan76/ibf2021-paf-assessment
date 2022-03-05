package ibf.paf.portfolio.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public record UserProfile(
                int uId,
                String email,
                String displayName,
                String avatarUrl) {
        public static UserProfile populate(SqlRowSet rs) {
                return new UserProfile(
                                rs.getInt("u_id"),
                                rs.getString("email"),
                                rs.getString("display_name"),
                                rs.getString("avatar_url"));
        }
}
