package map.locator.security;

import com.tomtom.traffic.legoland.common.jwt.JWTDecoder;
import com.tomtom.traffic.legoland.common.jwt.SimpleDecodedJWT;
import com.tomtom.traffic.legoland.common.user.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.tomtom.traffic.legoland.common.jwt.JWTUtils.getToken;

public class AuthenticationFilter
 extends OncePerRequestFilter {
    private final JWTDecoder jwtDecoder;

    public AuthenticationFilter(JWTDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        Optional.ofNullable(getToken(request))
                .map(jwtDecoder::decode)
                .ifPresent(this::authenticate);

        chain.doFilter(request, response);
    }

    private void authenticate(SimpleDecodedJWT decodedJWT) {
        var username = decodedJWT.getSubject();
        var authorities = getAuthorities(decodedJWT.getRole());
        var user = new User(username, "******", authorities);

        var authentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }

    private Collection<SimpleGrantedAuthority> getAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
