package com.dsa.dsa;

import java.math.BigInteger;
import java.security.*;

public class DSA {

    BigInteger p,q,g,h,x,y,k,r,s;
    MessageDigest messageDigest;
    int keyLen;
    SecureRandom random;

    public DSA(){
        random = new SecureRandom();
        generateKey();
        try{
            messageDigest=MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {ex.printStackTrace();}
    }

    public void generateKey(){
        keyLen = 512 + random.nextInt(513);
        while (keyLen%64 != 0){
            keyLen++;
        }
        q = BigInteger.probablePrime(160,random);
        BigInteger help1;
        do{
            p=BigInteger.probablePrime(keyLen,random);
            help1 = p.subtract(BigInteger.ONE);
        }while(!help1.mod(q).equals(BigInteger.ZERO));

        boolean isPrimitiveRootOfP = false;

        help1 = p.subtract(BigInteger.ONE);
        BigInteger exponent;

        while(!isPrimitiveRootOfP){
            h = new BigInteger(keyLen-2,random);
            exponent = help1.divide(q);
            g = h.modPow(exponent, p);
            isPrimitiveRootOfP = !g.modPow(q, p).equals(BigInteger.ONE);
        }

        x = new BigInteger(q.bitLength() - 1, random);
        while (x.compareTo(BigInteger.ZERO) == 0 || x.compareTo(q.subtract(BigInteger.ONE)) > 0) {
            x = new BigInteger(q.bitLength() - 1, random);
        }
        x = x.add(BigInteger.ONE);
        y=g.modPow(x,p);
    }

    public BigInteger[] sign(byte[] text){
        messageDigest.update(text);
        k = new BigInteger(q.bitLength()-1,random);
        r=g.modPow(k, p).mod(q);
        BigInteger kInv = k.modInverse(q);
        BigInteger hash = new BigInteger(1,messageDigest.digest());

        s = kInv.multiply(hash.add(x.multiply(r))).mod(q);
        return new BigInteger[] {r,s};
    }

    public boolean verifySignature(byte[] text, BigInteger[] signature){
        messageDigest.update(text);
        BigInteger hash = new BigInteger(1,messageDigest.digest());
        BigInteger w=signature[1].modInverse(q);
        BigInteger u1=hash.multiply(w).mod(q);
        BigInteger u2=signature[0].multiply(w).mod(q);
        BigInteger v=g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);
        return v.compareTo(signature[0]) == 0;
    }
}
