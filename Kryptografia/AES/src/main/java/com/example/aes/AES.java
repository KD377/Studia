package com.example.aes;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class AES {

    private byte[][] key;
    private int columns, rounds;
    private static final int NUMBER_BYTES = 4;

    private final int[] sbox = { 0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F,
            0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76, 0xCA, 0x82,
            0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C,
            0xA4, 0x72, 0xC0, 0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC,
            0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15, 0x04, 0xC7, 0x23,
            0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27,
            0xB2, 0x75, 0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52,
            0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84, 0x53, 0xD1, 0x00, 0xED,
            0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58,
            0xCF, 0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9,
            0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8, 0x51, 0xA3, 0x40, 0x8F, 0x92,
            0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E,
            0x3D, 0x64, 0x5D, 0x19, 0x73, 0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A,
            0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB, 0xE0,
            0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62,
            0x91, 0x95, 0xE4, 0x79, 0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E,
            0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08, 0xBA, 0x78,
            0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B,
            0xBD, 0x8B, 0x8A, 0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E,
            0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E, 0xE1, 0xF8, 0x98,
            0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55,
            0x28, 0xDF, 0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41,
            0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16 };

    private int[] inv_sbox = { 0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5,
            0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB, 0x7C, 0xE3,
            0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4,
            0xDE, 0xE9, 0xCB, 0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D,
            0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E, 0x08, 0x2E, 0xA1,
            0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B,
            0xD1, 0x25, 0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4,
            0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92, 0x6C, 0x70, 0x48, 0x50,
            0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D,
            0x84, 0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4,
            0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06, 0xD0, 0x2C, 0x1E, 0x8F, 0xCA,
            0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
            0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF,
            0xCE, 0xF0, 0xB4, 0xE6, 0x73, 0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD,
            0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E, 0x47,
            0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E,
            0xAA, 0x18, 0xBE, 0x1B, 0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79,
            0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4, 0x1F, 0xDD,
            0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27,
            0x80, 0xEC, 0x5F, 0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D,
            0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF, 0xA0, 0xE0, 0x3B,
            0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53,
            0x99, 0x61, 0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1,
            0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D };

    //tablica Rcon kolejne potegi dwojki(Hexadecymalnie).
    private final int [] Rcon = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36};


    public AES(int columns, int rounds) {
       key =  new byte[NUMBER_BYTES *(rounds + 1)][4];
       this.columns = columns;
       this.rounds = rounds;
    }

    //genrowanie losowo pierwszych 16 bajtow glownego klucza
    public byte[] generateRandomMainKey() {
        SecureRandom random = new SecureRandom();
        byte[] byteArray = new byte[16];
        random.nextBytes(byteArray);
        return byteArray;
    }

    //wygenerowanie kolejnych 10 kluczy rundy za pomoca algorytmu
    public void generateKey(byte[] key){
        byte [] tmp = new byte[4];
        byte [] block = new byte[4];
        int x = 0;
        int y =0;
        while (x < columns)
        {
            this.key[0][x] = key[y];
            this.key[1][x] = key[y+1];
            this.key[2][x] = key[y+2];
            this.key[3][x] = key[y+3];
            x++;
            y+=4;
        }
        //pobranie ostatnich 4 bajtow poprzedniego klucza
        for (int round = 1; round <= 10; round++){
            tmp[0] = this.key[(round * 4) - 1][0];
            tmp[1] = this.key[(round * 4) - 1][1];
            tmp[2] = this.key[(round * 4) - 1][2];
            tmp[3] = this.key[(round * 4) - 1][3];
            tmp = shiftArrayLeft(tmp,1);
            for(int i = 0;i<4;i++){
                tmp[i] = subByte(tmp[i]);
            }
            tmp[0] ^=  (byte)Rcon[round-1];
            for(int j = 0;j < 4;j++){
                for (int i=0; i<4; i++){
                    block[i] = this.key[(round-1) * 4+j][i];
                }
                tmp = xorWords(tmp,block);
                for(int i = 0;i<4;i++){
                    this.key[round*4+j][i] = tmp[i];
                }
            }
        }
    }

    private byte subByte(byte number){
        number = (byte) (sbox[(number & 0xff)]);
        return number;
    }

    private byte invSubByte(byte number){
        number = (byte) (inv_sbox[(number & 0xff)]);
        return number;
    }



    //oepracja XOR dwoch tablic(uzywane do generowania kluczy)
    public byte[] xorWords(byte[] word1, byte[] word2) {
        if (word1.length == word2.length) {
            byte[] tmp = new byte[word1.length];
            for (int i = 0; i < word1.length; i++) {
                tmp[i] = (byte) (word1[i] ^ word2[i]);
            }
            return tmp;
        } else {
            return null;
        }
    }
    //przesuniecie wartosci w tablicy o step w lewo
    public byte[] shiftArrayLeft(byte[] array, int step) {
        for (int i = 0; i < step; i++) {
            int j;
            byte first;
            first = array[0];

            for (j = 1; j < array.length; j++) {
                array[j - 1] = array[j];
            }
            array[array.length - 1] = first;
        }
        return array;
    }
    //przesuniecie wartosci w tablicy o step w prawo
    public byte[] shiftArrayRight(byte[] array, int step) {
        for (int i = 0; i < step; i++) {
            int j;
            byte last;
            last = array[3];

            for (j = array.length-2 ; j >= 0; j--) {
                array[j+1] = array[j];
            }
            array[0] = last;
        }
        return array;
    }
    //operacja XOR bloku state i klucza dla danej rundy
    public byte[][] addRoundKey(byte[][] state, byte[][] roundKey, int round){
        byte[][] tmp = new byte[4][4];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                tmp[j][i] = (byte) (state[j][i] ^ roundKey[NUMBER_BYTES * round+i][j]);
            }
        }
        return tmp;
    }
    //zamiana bajtow na odpowiadajace w S-boxie
    public byte[][] subBytes(byte[][] state){
        byte[][] tmp = new byte[4][4];
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                tmp[x][y] = subByte(state[x][y]);
            }
        }
        return tmp;
    }
    public byte[][] invSubBytes(byte[][] state){
        byte[][] tmp = new byte[4][4];
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                tmp[x][y] = invSubByte(state[x][y]);
            }
        }
        return tmp;
    }
    //uzycie shiftArrayLeft dla tablicy dwuwymiarowej
    public byte[][] shiftRows(byte[][] state){
        byte[][] tmp = new byte[4][4];
        tmp[0] = state[0];
        for(int i = 1; i < 4; i++){
           tmp[i] = shiftArrayLeft(state[i], i);
        }

        return tmp;
    }
    //uzycie shiftArrayRight dla tablicy dwuwymiarowej
    public byte[][] invShiftRows(byte[][] state){
        byte[][] tmp = new byte[4][4];
        tmp[0] = state[0];
        for(int i = 1; i < 4; i++){
            tmp[i] = shiftArrayRight(state[i], i);
        }

        return tmp;
    }

    public  byte multiply(byte a, byte b)
    {
        byte aa = a, bb = b, r = 0, t;
        while (aa != 0)
        {
            if ((aa & 1) != 0)
                r = (byte) (r ^ bb);
            t = (byte) (bb & 0x20);
            bb = (byte) (bb << 1);
            if (t != 0)
                bb = (byte) (bb ^ 0x1b);
            aa = (byte) ((aa & 0xff) >> 1);
        }
        return r;
    }
    //przemnozenie bloku do zaszyfrowania przez ustalona macierz
    // 2 3 1 1
    // 1 2 3 1
    // 1 1 2 3
    // 3 1 1 2


    public byte[][] mixColumns(byte[][] state){
        int[] temp = new int[4];
        byte b02 = (byte)0x02, b03 = (byte)0x03;
        for (int i = 0; i < 4; i++) {
            temp[0] = multiply(b02, state[0][i]) ^ multiply(b03, state[1][i]) ^ state[2][i]  ^ state[3][i];
            temp[1] = state[0][i]  ^ multiply(b02, state[1][i]) ^ multiply(b03, state[2][i]) ^ state[3][i];
            temp[2] = state[0][i]  ^ state[1][i]  ^ multiply(b02, state[2][i]) ^ multiply(b03, state[3][i]);
            temp[3] = multiply(b03, state[0][i]) ^ state[1][i]  ^ state[2][i]  ^ multiply(b02, state[3][i]);
            for (int j = 0; j < 4; j++) {
                state[j][i] = (byte) (temp[j]);
            }
        }
        return state;
    }
    //przemnozenie bloku do zaszyfrowania przez ustalona macierz
    // e b d 9
    // 9 e b d
    // d 9 e b
    // b d 9 e

    public byte[][] invMixColumns(byte[][] state){
        int[] temp = new int[4];
        byte b02 = (byte)0x0e, b03 = (byte)0x0b, b04 = (byte)0x0d, b05 = (byte)0x09;
        for (int i = 0; i < 4; i++)
        {
            temp[0] = multiply(b02, state[0][i]) ^ multiply(b03, state[1][i]) ^ multiply(b04,state[2][i])  ^ multiply(b05,state[3][i]);
            temp[1] = multiply(b05, state[0][i]) ^ multiply(b02, state[1][i]) ^ multiply(b03,state[2][i])  ^ multiply(b04,state[3][i]);
            temp[2] = multiply(b04, state[0][i]) ^ multiply(b05, state[1][i]) ^ multiply(b02,state[2][i])  ^ multiply(b03,state[3][i]);
            temp[3] = multiply(b03, state[0][i]) ^ multiply(b04, state[1][i]) ^ multiply(b05,state[2][i])  ^ multiply(b02,state[3][i]);
            for (int j = 0; j < 4; j++)
                state[j][i] = (byte) (temp[j]);
        }
        return state;
    }


    //wykonanie wyszystkich krokow algorytmu w celu zaszyfrowania 16 bajtow
    public byte[] encrypt(byte[] text){
        byte[][] state = new byte[4][4];
        int j = 0;
        for(int i = 0; i < 4; i++){
            state[i][0] = text[j];
            state[i][1] = text[j+1];
            state[i][2] = text[j+2];
            state[i][3] = text[j+3];
            j+=4;
        }
        state = addRoundKey(state, key, 0);
        for(int i = 1; i < rounds; i++){
            state = subBytes(state);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, key, i);

        }
        state = subBytes(state);
        state = shiftRows(state);
        state = addRoundKey(state, key, 10);
        j=0;
        for(int i = 0; i < 4; i++){
            text[j] = state[i][0];
            text[j+1] = state[i][1];
            text[j+2] = state[i][2];
            text[j+3] = state[i][3];
            j+=4;
        }
        return text;
    }

    //wykonanie wyszystkich krokow algorytmu w celu deszyfrowania 16 bajtow
    public byte[] decrypt(byte[] text){
        byte[][] state = new byte[4][4];
        int j = 0;
        for(int i = 0; i < 4; i++){
            state[i][0] = text[j];
            state[i][1] = text[j+1];
            state[i][2] = text[j+2];
            state[i][3] = text[j+3];
            j+=4;
        }
        state = addRoundKey(state, key, 10);
        for(int i = 9; i > 0; i--){
            state = invSubBytes(state);
            state = invShiftRows(state);
            state = addRoundKey(state, key, i);
            state = invMixColumns(state);

        }
        state = invSubBytes(state);
        state = invShiftRows(state);
        state = addRoundKey(state, key, 0);
        j = 0;
        for(int i = 0; i < 4; i++){
            text[j] = state[i][0];
            text[j+1] = state[i][1];
            text[j+2] = state[i][2];
            text[j+3] = state[i][3];
            j+=4;
        }
        return text;
    }
    //zaszyfrowanie calego teksty, pliku lub zdjecia
    public byte[] encode(byte[] text, byte[] mainKey){
        int length;
        int x = text.length/16;
        if(x == 0){
            length = 16;
        }
        else if ((text.length % 16) != 0){
            length = (x + 1) * 16;
        }
        else{
            length = x * 16;
        }

        byte[] output = new byte[length];
        byte[] tmp = new byte[length];
        byte[] block = new byte[16];
        generateKey(mainKey);
        for(int i = 0; i < length; i++){
            if(i < text.length){
                tmp[i] = text[i];
            }
            else{
                tmp[i] = 0;
            }
        }
        for (int k = 0; k < tmp.length;)
        {
            for (int j=0;j<16;j++){
                block[j]=tmp[k++];
            }
            block = encrypt(block);
            System.arraycopy(block, 0, output,k - 16, block.length);

        }
        return output;
    }
    //deszyfrowanie calego teksty, pliku lub zdjecia
    public byte[] decode(byte[] encryptedText, byte[] mainKey){
        byte[] tmp = new byte[encryptedText.length];
        byte[] block = new byte[16];
        generateKey(mainKey);
        for(int i = 0; i < encryptedText.length;){
            for (int j = 0; j < 16; j++){
                block[j] = encryptedText[i];
                i+=1;
            }

            block = decrypt(block);
            System.arraycopy(block, 0, tmp,i - 16, block.length);
        }
        int x = 0;
        for (int i = 1; i < 17; i += 2)
        {
            if (tmp[tmp.length - i] == 0 && tmp[tmp.length - i-1] == 0 )
            {
                x += 2;
            }
            else
            {
                break;
            }
        }
        byte[] output = new byte[tmp.length-x];
        System.arraycopy(tmp, 0, output, 0, tmp.length - x);
        return output;
    }


}
