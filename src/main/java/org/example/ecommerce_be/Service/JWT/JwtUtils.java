package org.example.ecommerce_be.Service.JWT;





import io.jsonwebtoken.*;

import io.jsonwebtoken.security.SignatureException;
import org.example.ecommerce_be.Security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    @Value("${intrust.app.jwtExpirationMs}")
    private long jwtExpirationMs;

    @Value("${intrust.app.jwtRefreshSecret}")
    private String jwtRefreshSecret;

    @Value("${intrust.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    public String generateToken(UserDetailsImpl userPrinciple) {
        return createAndSignJWT(userPrinciple);
    }

    private String createAndSignJWT(UserDetailsImpl userPrinciple) {



        Map<String, Object> claims = new HashMap<>();

        claims.put("id", userPrinciple.getId());
        claims.put("username", userPrinciple.getUsername());
        claims.put("email", userPrinciple.getEmail());
        claims.put("roles",userPrinciple.getAuthorities());

        RSAPrivateKey privateKey = null;
        try {
            privateKey = getPrivateKey("private.pem");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
//private RSAPrivateKey getPrivateKey(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//    String rootPath = System.getProperty("user.dir"); // lay thu muc hien tai cua java application
//    String filePath = rootPath +"/src/main/resources/cert/"+ fileName;
//    File file = new File(filePath);
//    FileInputStream fis = new FileInputStream(file); // đọc file
//    DataInputStream dis = new DataInputStream(fis); // đọc binay data từ file
//
//    byte[] keyBytes = new byte[(int) file.length()];
//    System.out.println(keyBytes.length);
//    dis.readFully(keyBytes);// đọc toàn bộ nội dung trong file và truyền vào keyBytes
//    // keyBytes giờ contain binary representation private key
//
//    KeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
//    KeyFactory keyFactory = KeyFactory.getInstance("RSA");//A KeyFactory is created for RSA keys.assigned KeyFactory algorithm
//    //The generatePrivate method of the KeyFactory is called with the PKCS8EncodedKeySpec to create an RSAPrivateKey.
//    // This RSAPrivateKey represents your private key.
//    RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
//    return  privateKey;
//
//
//}
    public static RSAPrivateKey getPrivateKey(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String rootPath = System.getProperty("user.dir");
        String filePath = rootPath + "/src/main/resources/cert/" + fileName;
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("-----BEGIN") || line.startsWith("-----END")) {
                continue;
            }
            sb.append(line);
        }
        br.close();

        byte[] keyBytes = Base64.getDecoder().decode(sb.toString());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtExpirationMs);
    }

    public Claims getClaimsFromJwtToken(String token) {
        RSAPrivateKey privateKey = null;
        try {
            privateKey =  getPrivateKey("private.pem");
            return Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).getBody();

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    public String getUserNameFromJwtToken(String token) {
        Claims claims = getClaimsFromJwtToken(token);
        if (claims != null && isTokenExpired(claims)) {
            return claims.getSubject();
        }
        return null;
    }

    public boolean validateJwtToken(String authToken) {
        RSAPrivateKey privateKey = null;
        try {
          privateKey =  getPrivateKey("private.pem");
            Jwts.parser().setSigningKey(privateKey).parseClaimsJws(authToken);
            return true;

        }
        catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
//    @Value("${jwt.app.jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${jwt.app.jwtExpirationMs}")
//    private int jwtExpirationMs;
//
//    public String generateJwtToken(UserDetailsImpl userDetails) {
//        return Jwts.builder()
//                .setSubject((userDetails.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(generateExpirationDate())
//                .signWith(SignatureAlgorithm.HS512, "truonggiang542002")
//                .compact();
//    }
//
//

}

