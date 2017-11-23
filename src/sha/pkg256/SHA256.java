/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha.pkg256;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roman
 */

public class SHA256 {
    public String A;
    public String B;
    public String C;
    public String D;
    public String E;
    public String F;
    public String G;
    public String H;
    public String[] constants;
    public String[][] Mblock;
    public int blocks;
    
    public SHA256() {
        System.out.println(hash("abc"));
    }
    
    public String hash(String message) {
        prepareMessageSchedule(message);
        init();
        String intermediateHash = "";
        for(int j=0; j<blocks; j++) {
            String HIA = A;
            String HIB = B;
            String HIC = C;
            String HID = D;
            String HIE = E;
            String HIF = F;
            String HIG = G;
            String HIH = H;
            for(int i=0; i<64; i++) {
                String T1_1_1 = addTwoNumbersMod32(H, binaryToHex(sum1(E)));
                String T1_1_2 = addTwoNumbersMod32(binaryToHex(chString(E, F, G)), constants[i]);
                String T1_2 = Mblock[j][i];
                String T1_1 = addTwoNumbersMod32(T1_1_1, T1_1_2);
                //System.out.println("T1 = (H)"+H+" (W"+i+")"+Mblock[j][i]+" (Ch)"+binaryToHex(chString(E, F, G))+" (∑1)"+binaryToHex(sum1(E))+" (k"+i+")"+constants[i]+" = "+addTwoNumbersMod32(T1_1, T1_2));
                //System.out.println("T2 = (∑0)"+binaryToHex(sum0(A))+" (maj)"+binaryToHex(majString(A, B, C))+" = "+addTwoNumbersMod32(binaryToHex(sum0(A)), binaryToHex(majString(A, B, C))));
                String T1 = addTwoNumbersMod32(T1_1, T1_2);
                String T2 = addTwoNumbersMod32(binaryToHex(sum0(A)), binaryToHex(majString(A, B, C)));
                H = G;
                G = F;
                F = E;
                E = addTwoNumbersMod32(D, T1);
                D = C;
                C = B;
                B = A;
                A = addTwoNumbersMod32(T1, T2);
                String state = A+" "+B+" "+C+" "+D+" "+E+" "+F+" "+G+" "+H;
                System.out.println("t = "+i+" : "+state);
            }
            //Intermediate Hash Values
            A = addTwoNumbersMod32(A, HIA);
            B = addTwoNumbersMod32(B, HIB);
            C = addTwoNumbersMod32(C, HIC);
            D = addTwoNumbersMod32(D, HID);
            E = addTwoNumbersMod32(E, HIE);
            F = addTwoNumbersMod32(F, HIF);
            G = addTwoNumbersMod32(G, HIG);
            H = addTwoNumbersMod32(H, HIH);
            intermediateHash = A+B+C+D+E+F+G+H;
            System.out.println("IntMedH: "+A+" "+B+" "+C+" "+D+" "+E+" "+F+" "+G+" "+H);
        }
        System.out.println("");
        if(verify(intermediateHash, message)) {
            return A+B+C+D+E+F+G+H;
        } else {
            System.out.println("Du hast reingeschissen!!111!!einsElf");
            System.out.println("Calculated hash: "+intermediateHash);
            System.out.println("Should be:       "+integratedHash(message));
            return null;
        }
    }
    
    public void init() {
        A = "6a09e667";
        B = "bb67ae85";
        C = "3c6ef372";
        D = "a54ff53a";
        E = "510e527f";
        F = "9b05688c";
        G = "1f83d9ab";
        H = "5be0cd19";
        constants = new String[64];
        constants[0] = "428a2f98";
        constants[1] = "71374491";
        constants[2] = "b5c0fbcf";
        constants[3] = "e9b5dba5";
        constants[4] = "3956c25b";
        constants[5] = "59f111f1";
        constants[6] = "923f82a4";
        constants[7] = "ab1c5ed5";
        constants[8] = "d807aa98";
        constants[9] = "12835b01";
        constants[10] = "243185be";
        constants[11] = "550c7dc3";
        constants[12] = "72be5d74";
        constants[13] = "80deb1fe";
        constants[14] = "9bdc06a7";
        constants[15] = "c19bf174";
        constants[16] = "e49b69c1";
        constants[17] = "efbe4786";
        constants[18] = "0fc19dc6";
        constants[19] = "240ca1cc";
        constants[20] = "2de92c6f";
        constants[21] = "4a7484aa";
        constants[22] = "5cb0a9dc";
        constants[23] = "76f988da";
        constants[24] = "983e5152";
        constants[25] = "a831c66d";
        constants[26] = "b00327c8";
        constants[27] = "bf597fc7";
        constants[28] = "c6e00bf3";
        constants[29] = "d5a79147";
        constants[30] = "06ca6351";
        constants[31] = "14292967";
        constants[32] = "27b70a85";
        constants[33] = "2e1b2138";
        constants[34] = "4d2c6dfc";
        constants[35] = "53380d13";
        constants[36] = "650a7354";
        constants[37] = "766a0abb";
        constants[38] = "81c2c92e";
        constants[39] = "92722c85";
        constants[40] = "a2bfe8a1";
        constants[41] = "a81a664b";
        constants[42] = "c24b8b70";
        constants[43] = "c76c51a3";
        constants[44] = "d192e819";
        constants[45] = "d6990624";
        constants[46] = "f40e3585";
        constants[47] = "106aa070";
        constants[48] = "19a4c116";
        constants[49] = "1e376c08";
        constants[50] = "2748774c";
        constants[51] = "34b0bcb5";
        constants[52] = "391c0cb3";
        constants[53] = "4ed8aa4a";
        constants[54] = "5b9cca4f";
        constants[55] = "682e6ff3";
        constants[56] = "748f82ee";
        constants[57] = "78a5636f";
        constants[58] = "84c87814";
        constants[59] = "8cc70208";
        constants[60] = "90befffa";
        constants[61] = "a4506ceb";
        constants[62] = "bef9a3f7";
        constants[63] = "c67178f2";
    }
    
    public boolean verify(String x, String message) {
        return (integratedHash(message).equals(x));
    }
    
    public String hexToBinary(String pS) {
        if(pS != null) {
            String temp = "";
            for(int i = 0; i<pS.length(); i++) {
                char charat = pS.charAt(i);
                String charatStr = ""+charat;
                switch (charatStr) {
                    case "0": {
                        temp = temp+"0000";
                        break;
                    }
                    case "1": { 
                        temp = temp+"0001";
                        break;
                    }
                    case "2": {
                        temp = temp+"0010";
                        break;
                    }
                    case "3": {
                        temp = temp+"0011";
                        break;
                    }
                    case "4": {
                        temp = temp+"0100";
                        break;
                    }
                    case "5": {
                        temp = temp+"0101";
                        break;
                    }
                    case "6": {
                        temp = temp+"0110";
                        break;
                    }
                    case "7": {
                        temp = temp+"0111";
                        break;
                    }
                    case "8": {
                        temp = temp+"1000";
                        break;
                    }
                    case "9": {
                        temp = temp+"1001";
                        break;
                    }
                    case "a": {
                        temp = temp+"1010";
                        break;
                    }
                    case "b": {
                        temp = temp+"1011";
                        break;
                    }
                    case "c": {
                        temp = temp+"1100";
                        break;
                    }
                    case "d": {
                        temp = temp+"1101";
                        break;
                    }
                    case "e": {
                        temp = temp+"1110";
                        break;
                    }
                    case "f": {
                        temp = temp+"1111";
                        break;
                    }
                }
            }
            return temp;
        } else {
            return null;
        }
    }
    
    public String binaryToHex(String pB) {
        if(pB != null) {
            String temp = "";
            for(int i = 0; i<pB.length(); i += 4) {
                String charatStr = pB.substring(i, i+4);
                switch (charatStr) {
                    case "0000": {
                        temp = temp+"0";
                        break;
                    }
                    case "0001": { 
                        temp = temp+"1";
                        break;
                    }
                    case "0010": {
                        temp = temp+"2";
                        break;
                    }
                    case "0011": {
                        temp = temp+"3";
                        break;
                    }
                    case "0100": {
                        temp = temp+"4";
                        break;
                    }
                    case "0101": {
                        temp = temp+"5";
                        break;
                    }
                    case "0110": {
                        temp = temp+"6";
                        break;
                    }
                    case "0111": {
                        temp = temp+"7";
                        break;
                    }
                    case "1000": {
                        temp = temp+"8";
                        break;
                    }
                    case "1001": {
                        temp = temp+"9";
                        break;
                    }
                    case "1010": {
                        temp = temp+"a";
                        break;
                    }
                    case "1011": {
                        temp = temp+"b";
                        break;
                    }
                    case "1100": {
                        temp = temp+"c";
                        break;
                    }
                    case "1101": {
                        temp = temp+"d";
                        break;
                    }
                    case "1110": {
                        temp = temp+"e";
                        break;
                    }
                    case "1111": {
                        temp = temp+"f";
                        break;
                    }
                }
            }
            String output = temp;
            if(output.length() < 8) {
                int zeroPadding = 8-output.length();
                for(int i=0; i<zeroPadding; i++) {
                    output = "0"+output;
                }
            }
            return output;
        } else {
            return null;
        }
    }
    
    public String stringToBinary(String s) {
        if(s != null) {
            byte[] bytes = s.getBytes();
            StringBuilder binary = new StringBuilder();
            for (byte b : bytes) {
                int val = b;
                for (int i = 0; i < 8; i++) {
                    binary.append((val & 128) == 0 ? 0 : 1);
                    val <<= 1;
                }
            }
            String returnString = "" + binary;
            return returnString;
        } else {
            return null;
        }
    }
  
    public String stringToHex(String x) {
        if(x != null) {
            return binaryToHex(stringToBinary(x));
        } else {
            return null;
        }
    }
    
    public String hexToString(String hex){
        if(hex != null) {
            StringBuilder sb = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            for( int i=0; i<hex.length()-1; i+=2 ){
                String output = hex.substring(i, (i + 2));
                int decimal = Integer.parseInt(output, 16);
                sb.append((char)decimal);
                temp.append(decimal);
            }
            return sb.toString();
        } else {
            return null;
        }
    }
    
    public String binaryToString(String x) {
        if(x != null) {
            return hexToString(binaryToHex(x));
        } else {
            return null;
        }
    }
    
    public String maj(String a, String b, String c) {
        if(a != null && b != null && c != null) {
            int ai = Integer.parseInt(a);
            int bi = Integer.parseInt(b);
            int ci = Integer.parseInt(c);
            if((ai+bi+ci) > 1) {
                return "1";
            } else {
                return "0";
            }
        } else {
            return null;
        }
    }
    
    public String majString(String pA, String pB, String pC) {
        if(pA != null && pB != null && pC != null) {
            if(pA.length() == 8) pA = hexToBinary(pA);
            if(pB.length() == 8) pB = hexToBinary(pB);
            if(pC.length() == 8) pC = hexToBinary(pC);
            String temp = "";
            for(int i = 0; i<pA.length(); i++) {
                String ch1 = ""+pA.charAt(i);
                String ch2 = ""+pB.charAt(i);
                String ch3 = ""+pC.charAt(i);
                temp = temp+maj(ch1, ch2, ch3);
            }
            return temp;
        } else {
            return null;
        }
    }
    
    public String rotR(String pS, int i) {
        if(pS != null && i != 0) {
            String firstPart = pS.substring(pS.length()-i, pS.length());
            String secondPart = pS.substring(0, pS.length()-i);
            return (firstPart+secondPart);
        } else if(i == 0) {
            return pS;
        } else {
            return null;
        }
    }
    
    public String shr(String pS, int i) {
        if(pS != null && i != 0) {
            String secondPart = pS.substring(0, pS.length()-i);
            String firstPart = "";
            for(int x = 0; x<i; x++) {
                firstPart += "0";
            }
            return (firstPart+secondPart);
        } else if(i == 0) {
            return pS;
        } else {
            return null;
        }
    }
    
    public String chString(String pA, String pB, String pC) {
        if(pA != null && pB != null && pC != null) {
            if(pA.length() == 8) pA = hexToBinary(pA);
            if(pB.length() == 8) pB = hexToBinary(pB);
            if(pC.length() == 8) pC = hexToBinary(pC);
            String temp = "";
            for(int i = 0; i<pA.length(); i++) {
                char charat1 = pA.charAt(i);
                char charat2 = pB.charAt(i);
                char charat3 = pC.charAt(i);
                String ch1 = ""+charat1;
                String ch2 = ""+charat2;
                String ch3 = ""+charat3;
                temp = temp+ch(ch1, ch2, ch3);
            }
            return temp;
        } else {
            return null;
        }
    }
    
    public String ch(String e, String f, String g) {
        if(e != null && f != null && g != null) {
            int ai = Integer.parseInt(e);
            int bi = Integer.parseInt(f);
            int ci = Integer.parseInt(g);
            if(ai == 1) {
                return f;
            } else {
                return g;
            }
        } else {
            return null;
        }
    }
    
    public String xor(String a, String b, String c) {
        if(a != null && b != null && c != null) {
            int ai = Integer.parseInt(a);
            int bi = Integer.parseInt(b);
            int ci = Integer.parseInt(c);
            if((ai+bi+ci) == 1 || (ai+bi+ci) == 3) {
                return "1";
            } else {
                return "0";
            }
        } else {
            return null;
        }
    }
    
    public String xorString(String pA, String pB, String pC) {
        if(pA != null && pB != null && pC != null) {
            String temp = "";
            for(int i = 0; i<pA.length(); i++) {
                String ch1 = ""+pA.charAt(i);
                String ch2 = ""+pB.charAt(i);
                String ch3 = ""+pC.charAt(i);
                temp = temp+xor(ch1, ch2, ch3);
            }
            return temp;
        } else {
            return null;
        }
    }
    
    public String sum0(String x) {
        if(x.length() > 8) {
            if(x != null) {
                return xorString(rotR(x, 2), rotR(x, 13), rotR(x, 22));
            } else {
                return null;
            }
        } else {
            if(x != null) {
                x = hexToBinary(x);
                return sum0(x); 
            } else {
                return null;
            }
        }
    }
    
    public String sum1(String x) {
        if(x.length() > 8) {
            if(x != null) {
                return xorString(rotR(x, 6), rotR(x, 11), rotR(x, 25));
            } else {
                return null;
            }
        } else {
            if(x != null) {
                x = hexToBinary(x);
                return sum1(x); 
            } else {
                return null;
            }
        }
    }
    
    public String sigma0(String x) {
        if(x.length() > 8) {
            if(x != null) {
                return xorString(rotR(x, 7), rotR(x, 18), shr(x, 3));
            } else {
                return null;
            }
        } else {
            if(x != null) {
                x = hexToBinary(x);
                return sigma0(x); 
            } else {
                return null;
            }
        }
    }
    
    public String sigma1(String x) {
        if(x.length() > 8) {
            if(x != null) {
                return xorString(rotR(x, 17), rotR(x, 19), shr(x, 10));
            } else {
                return null;
            }
        } else {
            if(x != null) {
                x = hexToBinary(x);
                return sigma1(x); 
            } else {
                return null;
            }
        }
    }
    
    public String addTwoNumbersMod32(String a, String b) {
        BigInteger a1 = new BigInteger(a, 16);
        BigInteger a2 = new BigInteger(b, 16);
        BigInteger mod = new BigInteger("4294967296");
        BigInteger sum = new BigInteger("0");
        sum = sum.add(a1);
        sum = sum.add(a2);
        sum = sum.mod(mod);
        String output = Long.toHexString(sum.longValue());
        if(output.length() < 8) {
            int zeroPadding = 8-output.length();
            for(int i=0; i<zeroPadding; i++) {
                output = "0"+output;
            }
        }
        return output;
    }
    
    public void prepareMessageSchedule(String x) {
        if(x != null) {
            String binValue = stringToBinary(x);
            int l = binValue.length();
            blocks = 0;
            boolean determinedBlockCount = false;
            while(!determinedBlockCount) {
                if(!((l+64-512)<0)) {
                    blocks++;
                    l -= 512;
                } else {
                    blocks++;
                    determinedBlockCount = true;
                }
            }
            l = binValue.length();
            Mblock = new String[blocks][64];
            String message = "";
            for(int i=0; i<blocks; i++) {
                String messagePart = binValue.substring(0, l);
                String bitPart = "1";
                //Build zeroPadding
                String zeroPaddingPart = "";
                for(int k=0; k<(448*blocks-(messagePart.length()+1))+(blocks-1)*64; k++) {
                    zeroPaddingPart += "0";
                }
                //Build messageLength
                String messageLengthPart = "";
                for(int k=0; k<(64-(Integer.toString(l, 2).length())); k++) {
                    messageLengthPart += "0";
                }
                messageLengthPart += Integer.toString(l, 2);
                //Build block
                message = binaryToHex((messagePart+bitPart+zeroPaddingPart+messageLengthPart));
                String block = message.substring(i*128, (i+1)*128);
                //Build words
                for(int k=0; k<16; k++) {
                    Mblock[i][k] = block.substring(k*8, (k+1)*8);
                }
                for(int k=16; k<64; k++) {
                    String s1T2 = addTwoNumbersMod32(binaryToHex(sigma1(Mblock[i][k-2])), Mblock[i][k-7]);
                    String s0T16 = addTwoNumbersMod32(binaryToHex(sigma0(Mblock[i][k-15])), Mblock[i][k-16]);
                    Mblock[i][k] =  addTwoNumbersMod32(s1T2, s0T16);
                }
                System.out.print("M"+i+": ");
                for(int k=0; k<16; k++) {
                    System.out.print(Mblock[i][k]+" ");
                }
                System.out.println("");
            }
        } else {
            System.out.println("prepareMessageSchedule: Message == null");
        }
    }
    
    public String integratedHash(String x) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(x.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SHA256.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SHA256.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static void main(String[] args) {
         SHA256 test = new SHA256();
    }
}
