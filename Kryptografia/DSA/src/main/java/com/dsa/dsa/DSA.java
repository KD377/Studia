package com.dsa.dsa;

import java.math.BigInteger;
import java.security.*;
import java.util.Random;

public class DSA {

    BigInteger p,q,g,h,x,y,k,r,s;
    MessageDigest messageDigest;
    int keyLen;
    SecureRandom random;

    public DSA(){
        random = new SecureRandom();
        //generateKey();
        try{
            messageDigest=MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {ex.printStackTrace();}
    }

    public void generateKey(){
        keyLen = 512 + random.nextInt(513);
        while (keyLen%64 != 0){
            keyLen++;
        }
        //System.out.print(keyLen);

        q = BigInteger.probablePrime(160,new Random());
        BigInteger help1;
        do{
            p=BigInteger.probablePrime(keyLen,new Random());
            help1 = p.subtract(BigInteger.ONE);
            p=p.subtract(help1.remainder(q));
        }while(!p.isProbablePrime(2));


        help1 = p.subtract(BigInteger.ONE);
        h = new BigInteger(keyLen-2,random);
        while(h.modPow(help1.divide(q),p).compareTo(BigInteger.ONE)!=1){
            h = new BigInteger(keyLen-2,random);
        }
        g=h.modPow(help1.divide(q),p);

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
    

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getG() {
        return g;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }
}
