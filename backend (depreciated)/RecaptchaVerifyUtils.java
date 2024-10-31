/*
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

public class RecaptchaVerifyUtils {

    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public static void verify(String gRecaptchaResponse) throws Exception {
        URL verifyUrl = new URL(SITE_VERIFY_URL);

        // Open Connection to URL
        HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

        // Add Request Header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Data will be sent to the server.
        String postParams = "secret=" + RecaptchaConstants.SECRET_KEY + "&response=" + gRecaptchaResponse;

        // Send Request
        conn.setDoOutput(true);

        // Get the output stream of Connection
        // Write data in this stream, which means to send data to Server.
        OutputStream outStream = conn.getOutputStream();
        outStream.write(postParams.getBytes());

        outStream.flush();
        outStream.close();

        // Get the InputStream from Connection to read data sent from the server.
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        // Use ObjectMapper for JSON deserialization
        ObjectMapper mapper = new ObjectMapper();
        // Disable indentation for a more compact response (optional)
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
        Map<String, Object> responseMap = mapper.readValue(inputStreamReader, Map.class);

        inputStreamReader.close();
//        System.out.println("response map");
//        System.out.println(responseMap.get("success"));
        if (responseMap.get("success").equals(true)) {
            // verification succeed
            System.out.println("recaptcha verified on the utils");
            return;
        }

        throw new Exception("recaptcha verification failed: response is " + responseMap);
    }

}*/
