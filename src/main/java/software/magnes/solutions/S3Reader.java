package software.magnes.solutions;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class S3Reader {

    private S3Client s3Client;
    private String bucketName;

    public S3Reader(String accessKey, String secretKey, String sessionToken, String bucketName) {
        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsSessionCredentials.create(accessKey, secretKey, sessionToken)))
                .build();
        this.bucketName = bucketName;
    }

    public List<String[]> ler(String prefixo) throws IOException {
        List<String[]> dados = new ArrayList<>();

        for (S3Object obj : s3Client.listObjectsV2(
                ListObjectsV2Request.builder().bucket(bucketName).prefix(prefixo).build()).contents()) {
            if (obj.key().endsWith(".csv")) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(s3Client.getObject(
                                GetObjectRequest.builder().bucket(bucketName).key(obj.key()).build())));
                String linha;
                while ((linha = reader.readLine()) != null) {
                    dados.add(linha.split(","));
                }
                reader.close();
            }
        }
        return dados;
    }
}