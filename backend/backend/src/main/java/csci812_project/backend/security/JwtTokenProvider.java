//package csci812_project.backend.security;
//
//import csci812_project.backend.entity.User;
//import csci812_project.backend.repository.UserRepository;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Value("${app.jwt-secret}")  // jwtSecret value setup in application.properties
//    private String jwtSecret;
//
//    @Value("${app.jwt-expiration-milliseconds}")   // expiration value setup in application.properties
//    private String jwtExpirationDate;
//
//    // Generate JWT token
//    public String generateToken(String username) {
//
//        // ✅ Retrieve Login entity from username
//        User user = userRepository.findByUserName(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//        // ✅ Generate JWT token
//        Date currentDate = new Date();
//        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);
//
//        return Jwts.builder()
//                .setSubject(username)  // ✅ Set subject as username
//                .claim("userId", user.getUserId())  // ✅ Attach userId claim
//                .claim("email", user.getEmail())  // ✅ Attach email claim
//                .setIssuedAt(currentDate)
//                .setExpiration(expirationDate)
//                .signWith(key(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//
//    private Key key(){
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//    }
//
//    // ✅ Extract username from JWT token
//    public String getUsername(String token) {
//        Claims claims = extractClaims(token);
//        return claims.getSubject();
//    }
//
//    private Claims extractClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    // ✅ Validate token and check expiration
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(key())
//                    .build()
//                    .parseClaimsJws(token);
//            return !isTokenExpired(token);
//        } catch (Exception e) {
//            return false;  // Invalid token
//        }
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractClaims(token).getExpiration().before(new Date());
//    }
//
//
////    public String getUsername(String token){
////        Claims claims = Jwts.parserBuilder()
////                .setSigningKey(key())
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////
////        String username = claims.getSubject();
////
////        return username;
////    }
//
//    // Validate JWT Token
////    public boolean validateToken(String token){
////        Jwts.parserBuilder()
////                .setSigningKey(key())
////                .build()
////                .parse(token);
////
////        return true;
////    }
//}
