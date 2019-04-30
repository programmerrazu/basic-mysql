package org.razu.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
//import org.apache.commons.lang3.StringUtils;
import org.razu.entity.UserInfo;
import org.razu.utils.DateFormatProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenHelper {

    static final Logger LOG = LoggerFactory.getLogger(TokenHelper.class);

    @Value("${app.security.jwt.name}")
    private String APP_NAME;

    @Value("${app.security.jwt.secret}")
    public String SECRET;

    @Value("${app.security.jwt.expires_in}")
    private String EXPIRES_IN;

    @Value("${app.security.jwt.mobile_expires_in}")
    private String MOBILE_EXPIRES_IN;

    @Value("${app.security.jwt.header}")
    private String AUTH_HEADER;

    @Value("${app.security.jwt.cookie}")
    public String AUTH_TOKEN_COOKIE;

    @Value("${app.security.jwt.unknown.audience}")
    public String AUDIENCE_UNKNOWN;

    @Value("${app.security.jwt.web.audience}")
    public String AUDIENCE_WEB = "web";

    @Value("${app.security.jwt.mobile.audience}")
    static final String AUDIENCE_MOBILE = "mobile";

    @Value("${app.security.jwt.tablet.audience}")
    public String AUDIENCE_TABLET = "tablet";

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Long getUserIdFromToken(String token) {
        String userId = null;
        try {
            userId = getClaimFromToken(token, Claims::getId);

        } catch (Exception ex) {
            LOG.error("Unable extract your id from token::" + ex);
            userId = null;
        }
        return Long.parseLong(userId);
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

//    public String refreshToken(String token, Device device) {
//        String refreshedToken;
//        Date a = DateFormatProvider.now();
//        try {
//            final Claims claims = this.getAllClaimsFromToken(token);
//            claims.setIssuedAt(a);
//            refreshedToken = Jwts.builder()
//                    .setClaims(claims)
//                    .setExpiration(generateExpirationDate(device))
//                    .signWith(SIGNATURE_ALGORITHM, SECRET)
//                    .compact();
//        } catch (Exception e) {
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }
//    public String generateToken(String username, String userId, Device device) {
//        String audience = generateAudience(device);
//        return Jwts.builder()
//                .setIssuer(APP_NAME)
//                .setSubject(username)
//                .setId(userId)
//                .setAudience(audience)
//                .setIssuedAt(DateFormatProvider.now())
//                .setExpiration(generateExpirationDate(device))
//                .signWith(SIGNATURE_ALGORITHM, SECRET)
//                .compact();
//    }
    public String generateTokenForUserActivation(String username, String userId) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(username)
                .setId(userId)
                .setIssuedAt(DateFormatProvider.now())
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact();
    }

//    private String generateAudience(Device device) {
//        String audience = AUDIENCE_UNKNOWN;
//        if (device.isNormal()) {
//            audience = AUDIENCE_WEB;
//        } else if (device.isTablet()) {
//            audience = AUDIENCE_TABLET;
//        } else if (device.isMobile()) {
//            audience = AUDIENCE_MOBILE;
//        }
//        return audience;
//    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

//    private Date generateExpirationDate(Device device) {
//        int convertedTimeTokenExpireTime = Integer.parseInt(StringUtils.deleteWhitespace(EXPIRES_IN));
//        int convertedTimeMobileTokenExpireTime = Integer.parseInt(StringUtils.deleteWhitespace(MOBILE_EXPIRES_IN));
//        long expiresIn = device.isTablet() || device.isMobile() ? convertedTimeMobileTokenExpireTime : convertedTimeTokenExpireTime;
//        return new Date(DateFormatProvider.now().getTime() + expiresIn * 1000);
//    }
//    public int getExpiredIn(Device device) {
//        int convertedTimeTokenExpireTime = Integer.parseInt(StringUtils.deleteWhitespace(EXPIRES_IN));
//        int convertedTimeMobileTokenExpireTime = Integer.parseInt(StringUtils.deleteWhitespace(MOBILE_EXPIRES_IN));
//        return device.isMobile() || device.isTablet() ? convertedTimeMobileTokenExpireTime : convertedTimeTokenExpireTime;
//    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        boolean validateToken = true;
        UserInfo user = (UserInfo) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
//        validateToken = username != null
//                && username.equals(userDetails.getUsername())
//                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
        return validateToken;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        if (lastPasswordReset == null) {
            return false;
        }
        Date tokenCreatedtime = new Date(created.getTime());
        Date lastPasswordChangeTime = new Date(lastPasswordReset.getTime());
        boolean isvaild = (lastPasswordReset != null && tokenCreatedtime.before(lastPasswordChangeTime));
        return isvaild;
    }

    public String getToken(HttpServletRequest request) {
        /**
         * Getting the token from Cookie store
         */
        Cookie authCookie = getCookieValueByName(request, AUTH_TOKEN_COOKIE);
        if (authCookie != null) {
            return authCookie.getValue();
        }
        /**
         * Getting the token from Authentication header e.g Bearer your_token
         */
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * Find a specific HTTP cookie in a request.
     *
     * @param request The HTTP request object.
     * @param name The cookie name to look for.
     * @return The cookie, or <code>null</code> if not found.
     */
    public Cookie getCookieValueByName(HttpServletRequest request, String name) {
        if (request.getCookies() == null) {
            return null;
        }
        for (int i = 0; i < request.getCookies().length; i++) {
            if (request.getCookies()[i].getName().equals(name)) {
                return request.getCookies()[i];
            }
        }
        return null;
    }
}
