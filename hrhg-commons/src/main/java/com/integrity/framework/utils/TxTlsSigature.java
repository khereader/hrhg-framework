package com.integrity.framework.utils;

import com.integrity.framework.api.bean.TxCheckTLSSignatureResult;
import com.integrity.framework.api.bean.TxGenTLSSignatureResult;
import com.integrity.framework.api.bean.TxTLSSignature;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.Arrays;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * 腾讯云通讯签名生成校验工具<dr>
 *
 * @author 姚磊
 * @time 2017/12/22
 * @since 2.1.0
 */
public class TxTlsSigature {

    /**
     * 生成 tls 票据
     *
     * @param expire      有效期，单位是秒，推荐一个月
     * @param strAppid3rd 填写与 sdkAppid 一致字符串形式的值
     * @param skdAppid    应用的 appid
     * @param identifier  用户 id
     * @param accountType 创建应用后在配置页面上展示的 acctype
     * @param privStr     生成 tls 票据使用的私钥内容
     * @return 如果出错，TxGenTLSSignatureResult 中的 urlSig为空，errMsg 为出错信息，成功返回有效的票据
     * @throws IOException
     * @brief 生成 tls 票据
     */
    @Deprecated
    public static TxGenTLSSignatureResult GenTLSSignature(String expire,
                                                          String strAppid3rd, String skdAppid,
                                                          String identifier, String accountType,
                                                          String privStr) throws IOException {

        TxGenTLSSignatureResult result = new TxGenTLSSignatureResult();

        Security.addProvider(new BouncyCastleProvider());
        Reader reader = new CharArrayReader(privStr.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PEMParser parser = new PEMParser(reader);
        Object obj = parser.readObject();
        parser.close();
        PrivateKey privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);

        // 签名参数
        TxTLSSignature txTLSSignature = new TxTLSSignature();
        // 账号类型
        txTLSSignature.setAccountType(accountType);
        // 管理员账号
        txTLSSignature.setIdentifier(identifier);
        // 独立模式 SDKAppId相同
        txTLSSignature.setAppIdAtThird(strAppid3rd);
        // SDKAppId
        txTLSSignature.setSdkAppId(skdAppid);
        // 签名超时时间
        txTLSSignature.setExpireAfter(expire);
        // 签名生成时间戳
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        // 获取签名字符串
        String SerialString = getSignContent(strAppid3rd, accountType, identifier, skdAppid, time, expire);

        try {
            //Create Signature by SerialString
            Signature signature = Signature.getInstance(TxTlsConst.CODE_SHA256_WITH_ECDSA, TxTlsConst.CODE_BC);
            signature.initSign(privKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));
            byte[] signatureBytes = signature.sign();

            String sigTLS = Base64.encodeBase64String(signatureBytes);

            //Add TlsSig to jsonString
            txTLSSignature.setSig(sigTLS);
            txTLSSignature.setTime(time);
            String jsonString = JsonUtils.object2Json(txTLSSignature);
            //compression
            Deflater compresser = new Deflater();
            compresser.setInput(jsonString.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));

            compresser.finish();
            byte[] compressBytes = new byte[512];
            int compressBytesLength = compresser.deflate(compressBytes);
            compresser.end();

            String userSig = new String(TxBase64Url.base64EncodeUrl(Arrays.copyOfRange(compressBytes, 0, compressBytesLength)));

            result.setUrlSig(userSig);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrMessage(TxTlsConst.ERROR_GENERATE_USERSIG_FAILED);
        }

        return result;
    }

    /**
     * 校验 tls 票据
     *
     * @param urlSig      返回 tls 票据
     * @param strAppid3rd 填写与 sdkAppid 一致的字符串形式的值
     * @param skdAppid    应的 appid
     * @param identifier  用户 id
     * @param accountType 创建应用后在配置页面上展示的 acctype
     * @param publicKey   用于校验 tls 票据的公钥内容，但是需要先将公钥文件转换为 java 原生 api 使用的格式，下面是推荐的命令
     *                    openssl pkcs8 -topk8 -in ec_key.pem -outform PEM -out p8_priv.pem -nocrypt
     * @return 如果出错 CheckTLSSignatureResult 中的 verifyResult 为 false，错误信息在 errMsg，校验成功为 true
     * @throws DataFormatException
     * @brief 校验 tls 票据
     */
    @Deprecated
    public static TxCheckTLSSignatureResult CheckTLSSignature(String urlSig,
                                                              String strAppid3rd, String skdAppid,
                                                              String identifier, String accountType,
                                                              String publicKey) throws DataFormatException {
        TxCheckTLSSignatureResult result = new TxCheckTLSSignatureResult();
        Security.addProvider(new BouncyCastleProvider());

        //DeBaseUrl64 urlSig to json
        Base64 decoder = new Base64();

        //byte [] compressBytes = decoder.decode(urlSig.getBytes());
        byte[] compressBytes = TxBase64Url.base64DecodeUrl(urlSig.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));

        //Decompression
        Inflater decompression = new Inflater();
        decompression.setInput(compressBytes, 0, compressBytes.length);
        byte[] decompressBytes = new byte[1024];
        int decompressLength = decompression.inflate(decompressBytes);
        decompression.end();

        String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));

        try {
            TxTLSSignature txTLSSignature = JsonUtils.json2Object(jsonString, TxTLSSignature.class);
            String sigTLS = txTLSSignature.getSig();
            String sigTime = txTLSSignature.getTime();
            Long sigExpire = Long.valueOf(txTLSSignature.getExpireAfter());

            //debase64 TLS.Sig to get serailString
            byte[] signatureBytes = decoder.decode(sigTLS.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));

            //checkTime
            if (System.currentTimeMillis() / 1000 - Long.parseLong(sigTime) > sigExpire) {
                result.setErrMessage(TxTlsConst.ERROR_SIG_OUT_OF_DATE);
                return result;
            }

            //Get Serial String from json
            String SerialString = getSignContent(strAppid3rd, accountType, identifier, skdAppid, sigTime, txTLSSignature.getExpireAfter());

            Reader reader = new CharArrayReader(publicKey.toCharArray());
            PEMParser parser = new PEMParser(reader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            Object obj = parser.readObject();
            parser.close();
            PublicKey pubKeyStruct = converter.getPublicKey((SubjectPublicKeyInfo) obj);

            Signature signature = Signature.getInstance(TxTlsConst.CODE_SHA256_WITH_ECDSA, TxTlsConst.CODE_BC);
            signature.initVerify(pubKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));
            boolean bool = signature.verify(signatureBytes);
            result.setVerifyResult(bool);
        } catch (Exception e) {
            result.setErrMessage(TxTlsConst.ERROR_CHECKING_SIG_FAIL);
        }

        return result;
    }

    /**
     * 生成 tls 票据，精简参数列表，有效期默认为 180 天
     *
     * @param skdAppid   应用的 sdkappid
     * @param identifier 用户 id
     * @param privStr    私钥文件内容
     * @return
     * @throws IOException
     * @brief 生成 tls 票据，精简参数列表，有效期默认为 180 天
     */
    public static TxGenTLSSignatureResult GenTLSSignatureEx(
            String accountType,
            String strAppid3rd,
            String skdAppid,
            String identifier,
            String privStr) throws IOException {
        Long expireTime = TimeConst.DEFAULT_EXPIRE_DAY_NINETY * 2;
        return GenTLSSignatureEx(accountType, strAppid3rd, skdAppid, identifier, privStr, String.valueOf(expireTime));
    }

    /**
     * 生成 tls 票据，精简参数列表
     *
     * @param strAppid3rd 独立模式为 sdkappid
     * @param skdAppid    应用的 sdkappid
     * @param identifier  用户 id
     * @param privStr     私钥文件内容
     * @param expire      有效期，以秒为单位，推荐时长一个月
     * @return
     * @throws IOException
     * @brief 生成 tls 票据，精简参数列表
     */
    public static TxGenTLSSignatureResult GenTLSSignatureEx(
            String accountType,
            String strAppid3rd,
            String skdAppid,
            String identifier,
            String privStr,
            String expire) throws IOException {

        TxGenTLSSignatureResult result = new TxGenTLSSignatureResult();

        Security.addProvider(new BouncyCastleProvider());
        Reader reader = new CharArrayReader(privStr.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PEMParser parser = new PEMParser(reader);
        Object obj = parser.readObject();
        parser.close();
        PrivateKey privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);

        // 签名参数
        TxTLSSignature txTLSSignature = new TxTLSSignature();
        // 账号类型
        txTLSSignature.setAccountType(accountType);
        // 管理员账号
        txTLSSignature.setIdentifier(identifier);
        // 独立模式 SDKAppId相同
        txTLSSignature.setAppIdAtThird(strAppid3rd);
        // SDKAppId
        txTLSSignature.setSdkAppId(skdAppid);
        // 签名超时时间
        txTLSSignature.setExpireAfter(expire);
        // 签名生成时间戳
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        // 代签名内容
        String SerialString = getSignContent(strAppid3rd, accountType, identifier, skdAppid, time, expire);

        try {
            //Create Signature by SerialString
            Signature signature = Signature.getInstance(TxTlsConst.CODE_SHA256_WITH_ECDSA, TxTlsConst.CODE_BC);
            signature.initSign(privKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));
            byte[] signatureBytes = signature.sign();

            String sigTLS = Base64.encodeBase64String(signatureBytes);

            //Add TlsSig to jsonString
            txTLSSignature.setSig(sigTLS);
            txTLSSignature.setTime(time);
            String jsonString = JsonUtils.object2Json(txTLSSignature);

            //compression
            Deflater compresser = new Deflater();
            compresser.setInput(jsonString.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));

            compresser.finish();
            byte[] compressBytes = new byte[512];
            int compressBytesLength = compresser.deflate(compressBytes);
            compresser.end();
            String userSig = new String(TxBase64Url.base64EncodeUrl(Arrays.copyOfRange(compressBytes, 0, compressBytesLength)));

            result.setUrlSig(userSig);
        } catch (Exception e) {
            result.setErrMessage(TxTlsConst.ERROR_GENERATE_USERSIG_FAILED);
        }

        return result;
    }

    /**
     * @param strAppid3rd 独立模式为 sdkappid
     * @param accountType 账号类型 云通讯后台可查
     * @param urlSig      签名
     * @param sdkAppid    appId 云通讯后台可查
     * @param identifier  管理者账号
     * @param publicKey   云通讯 公钥
     * @return
     * @throws DataFormatException
     * @brief 生成 tls 票据，精简参数列表
     */
    public static TxCheckTLSSignatureResult CheckTLSSignatureEx(
            String accountType,
            String strAppid3rd,
            String urlSig,
            String sdkAppid,
            String identifier,
            String publicKey) throws DataFormatException {

        TxCheckTLSSignatureResult result = new TxCheckTLSSignatureResult();
        Security.addProvider(new BouncyCastleProvider());

        Base64 decoder = new Base64();

        byte[] compressBytes = TxBase64Url.base64DecodeUrl(urlSig.getBytes(Charset.forName("UTF-8")));

        //Decompression
        Inflater decompression = new Inflater();
        decompression.setInput(compressBytes, 0, compressBytes.length);
        byte[] decompressBytes = new byte[1024];
        int decompressLength = decompression.inflate(decompressBytes);
        decompression.end();

        try {
            String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));

            TxTLSSignature txTLSSignature = JsonUtils.json2Object(jsonString, TxTLSSignature.class);

            //Get TLS.Sig from json
            String sigTLS = txTLSSignature.getSig();
            byte[] signatureBytes = decoder.decode(sigTLS.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));

            String strSdkAppid = String.valueOf(txTLSSignature.getSdkAppId());
            String sigTime = String.valueOf(txTLSSignature.getTime());
            Long sigExpire = Long.valueOf(txTLSSignature.getExpireAfter());

            if (!strSdkAppid.equals(sdkAppid)) {
                result.setErrMessage(String.format(TxTlsConst.ERROR_TLS_SIG_NOT_EQUAL_SDK_APP_ID, strSdkAppid, sdkAppid));
                return result;
            }

            if (System.currentTimeMillis() / 1000 - Long.parseLong(sigTime) > sigExpire) {
                result.setErrMessage(TxTlsConst.ERROR_SIG_OUT_OF_DATE);
                return result;
            }

            //Get Serial String from json
            String SerialString = getSignContent(strAppid3rd, accountType, identifier, sdkAppid, sigTime, txTLSSignature.getExpireAfter());

            Reader reader = new CharArrayReader(publicKey.toCharArray());
            PEMParser parser = new PEMParser(reader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            Object obj = parser.readObject();
            parser.close();
            PublicKey pubKeyStruct = converter.getPublicKey((SubjectPublicKeyInfo) obj);

            Signature signature = Signature.getInstance(TxTlsConst.CODE_SHA256_WITH_ECDSA, TxTlsConst.CODE_BC);
            signature.initVerify(pubKeyStruct);
            signature.update(SerialString.getBytes(Charset.forName(TxTlsConst.CODE_UTF_8)));
            boolean bool = signature.verify(signatureBytes);
            result.setExpireTime(sigExpire.intValue());
            result.setInitTime(Integer.parseInt(sigTime));
            result.setVerifyResult(bool);
        } catch (Exception e) {
            e.printStackTrace();
            result.setErrMessage(TxTlsConst.ERROR_CHECKING_SIG_FAIL);
        }

        return result;
    }

    /**
     * 获取签名内容 严格按照此顺序
     *
     * @param strAppid3rd 独立模式为 sdkappid
     * @param accountType 账号类型 云通讯后台可查
     * @param identifier  管理者账号
     * @param skdAppid    appId 云通讯后台可查
     * @param sigTime     有效期 时间戳 10位
     * @param sigExpire   有效期，以秒为单位，推荐时长一个月
     * @return
     */
    private static String getSignContent(String strAppid3rd, String accountType, String identifier, String skdAppid, String sigTime, String sigExpire) {
        return "TLS.appid_at_3rd:" + strAppid3rd + "\n" +
                "TLS.account_type:" + accountType + "\n" +
                "TLS.identifier:" + identifier + "\n" +
                "TLS.sdk_appid:" + skdAppid + "\n" +
                "TLS.time:" + sigTime + "\n" +
                "TLS.expire_after:" + sigExpire + "\n";
    }

}
