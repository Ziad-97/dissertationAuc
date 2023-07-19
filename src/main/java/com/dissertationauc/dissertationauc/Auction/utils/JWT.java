package com.dissertationauc.dissertationauc.Auction.utils;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.repositories.BidderRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JWT {
    public static final String secretKey = "jwtoken";
    private final BidderRepo bidderRepo;

    public JWT(BidderRepo bidderRepo) {
        this.bidderRepo = bidderRepo;
    }

    public String generateToken(String userName, boolean logout){
        Map<String, Object> claims = new HashMap<>();
        long expiryCurrentTimeMillis = System.currentTimeMillis();
        
        long issueCurrentTimeMillis = System.currentTimeMillis();
        

        Date expirationDate;
        Date issueDate = null;
        if (logout) {
            expirationDate = new Date(expiryCurrentTimeMillis);
            
        } else {
            issueDate = new Date(issueCurrentTimeMillis);
            expirationDate = new Date(expiryCurrentTimeMillis + 1800000);
        }

        return Jwts.builder()


                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }




    public boolean validateToken(String token) {
        String tokenUsername = extractUsername(token);
        Bidder bidder = bidderRepo.findByUserName(tokenUsername);
        if(bidder==null && isTokenExpired(token)){
            return false;
        }
        return true;
    }



    public static String extractUsername(String token){
        return extractClaims(token.substring(7)).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token.substring(7)).getExpiration().before(Date.from(Instant.now()));
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

}
