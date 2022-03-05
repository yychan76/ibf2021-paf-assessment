package ibf.paf.portfolio.models;

public record CreateUserForm(
        String email,
        String password,
        String displayName) {

}
