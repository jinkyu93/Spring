[Back](../FeatIndex.md)

#Spring Security
* SSL
    * 인증서 생성
        * jdk bin 폴더의 keytool 사용
        * .\keytool -genkey -alias cert -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore cert.p12 -validity 36500
            * genkey : 키를 생성하겠음
            * alias : 키의 별칭
            * storetype : 저장 타입
        * sample information
            * Generating 2,048 bit RSA key pair and self-signed certificate (SHA256withRSA) with a validity of 36,500 days
            * for: CN=park, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown, Password=password
