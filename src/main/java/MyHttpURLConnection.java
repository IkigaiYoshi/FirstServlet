
public class MyHttpURLConnection {


    public static void main(String[] args) throws Exception {
        String address = "http://192.168.0.101:8080/phonebook";

        HttpURLConnectionGet httpURLConnectionGet = new HttpURLConnectionGet(address);
        httpURLConnectionGet.start();
        HttpURLConnectionPost httpURLConnectionPost = new HttpURLConnectionPost(address);
        httpURLConnectionPost.start();
        Thread.sleep(60000000);
        httpURLConnectionGet.setStopConnect();
        httpURLConnectionPost.setStopConnect();
    }
}

