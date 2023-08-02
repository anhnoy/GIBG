package com.Auton.gibg.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
}
