package com.pokemon.pokemon.security;

import com.pokemon.pokemon.entities.Jwt;
import com.pokemon.pokemon.entities.User;
import com.pokemon.pokemon.repository.JwtRepository;
import com.pokemon.pokemon.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
public class JwtService {

    public static final String BEARER = "bearer";
    private final String ENCRYPTION_KEY = "2465f9cfc7879457f57f42b74fcf225936548054d9609c40bb9664bb2700572a";
    private UserService userService;
    private JwtRepository jwtRepository;

    public JwtService(UserService userService, JwtRepository jwtRepository) {
        this.userService = userService;
        this.jwtRepository = jwtRepository;
    }

    public Jwt tokenByValue(String value) {
        return jwtRepository.findByValueAndDesactiveAndExpire(value, false, false).orElseThrow(() -> new RuntimeException("Token inconnu"));
    }

    public Map<String, String> generate(String email){
        User user = (User) userService.loadUserByUsername(email);
        disableToken(user);
        Map<String, String> jwtMap = this.generateJwt(user);

        Jwt jwt = new Jwt();
        jwt.setValue(jwtMap.get(BEARER));
        jwt.setExpire(false);
        jwt.setDesactive(false);
        jwt.setUser(user);

        System.out.println(jwt.getValue());

        jwtRepository.save(jwt);
        return jwtMap;
    }

    private void disableToken(User user){
        final List<Jwt> jwtList = jwtRepository.findByUserEmail(user.getEmail()).peek(
                jwt -> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                }
        ).collect(Collectors.toList());

        jwtRepository.saveAll(jwtList);
    }

    public String extractUsername(String token) {
        return getClaims(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getClaims(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaims(String token, Function<Claims, T> function){
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    // Récupère toutes les infos du token d'un coup
    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000; // 30min

        // Informations que l'on veut mettre dans le JWT
        final Map<String, Object> claims = Map.of(
                "prenom", user.getName(),
                "nom", user.getLastname(),
                "email", user.getEmail(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, user.getEmail()
        );

        final String bearer = Jwts.builder()
                .issuedAt(new Date(currentTime)) // Création du token
                .expiration(new Date(expirationTime)) // Date d'expiration du token
                .subject(user.getEmail()) // Personne pour laquel on génère le token
                .claims(claims) // Information du user stocké dans le token
                .signWith(getKey(), SignatureAlgorithm.HS256) // définir l'algorythme de signature
                .compact() // Transform en string
        ;

        return Map.of(BEARER, bearer);
    }

    // Site: randaomGenerate.io est conseillé pour générer une clé secrette
    // https://randomgenerate.io/encryption-key-generator
    private SecretKey getKey(){
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public void deconnexion() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = jwtRepository.findUserValidToken(
                user.getEmail()
        ).orElseThrow(() -> new RuntimeException("Token invalide"));

        jwt.setExpire(true);
        jwt.setDesactive(true);
        jwtRepository.save(jwt);
    }
}
