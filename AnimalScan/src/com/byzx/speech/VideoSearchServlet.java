package com.byzx.speech;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import com.baidu.aip.speech.AipSpeech;

@WebServlet("/videoAnalyze")
public class VideoSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//  填写网页上申请的appkey 如 $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
    private final String appKey = "tqDlru9AERM5USZGQTkle6Xu";

    // 填写网页上申请的APP SECRET 如 $secretKey="94dc99566550d87f8fa8ece112xxxxx"
    private final String secretKey = "Wp51Aa8Fl6TbgXNmdNtZAk8EpSw6428C";
 // 初始化一个FaceClient
    public static final String APP_ID = "15871052";
    public static final String API_KEY = "tqDlru9AERM5USZGQTkle6Xu";
    public static final String SECRET_KEY = "Wp51Aa8Fl6TbgXNmdNtZAk8EpSw6428C";
    AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
    
    // 需要识别的文件
    private final String filename = "D:/speech/unique.wav";

    // 文件格式
    private final String format = "wav";

    //  1537 表示识别普通话，使用输入法模型。1536表示识别普通话，使用搜索模型。 其它语种参见文档
    private final int dev_pid = 1537;

    private String cuid = "12345678JAVA";
 
 // 采样率固定值
    private final int rate = 16000;
	
    public boolean methodRaw = false; // 默认以json方式上传音频文件

    private final String url = "http://vop.baidu.com/server_api"; // 可以改为https

    public String run() throws Exception {
        TokenHolder holder = new TokenHolder(appKey, secretKey, TokenHolder.ASR_SCOPE);
        holder.resfresh();
        String token = holder.getToken();
        String result = null;
        result = runJsonPostMethod(token);
        System.out.println(result);
        return result;
    }

    public String runJsonPostMethod(String token) throws Exception {

        byte[] content = getFileContent(filename);
        String speech = base64Encode(content);

        JSONObject params = new JSONObject();
        params.put("dev_pid", dev_pid);
        params.put("format", format);
        params.put("rate", rate);
        params.put("token", token);
        params.put("cuid", cuid);
        params.put("channel", "1");
        params.put("len", content.length);
        params.put("speech", speech);

        // System.out.println(params.toString());
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result = ConnUtil.getResponseString(conn);
        return result;
    }

     public static void main(String[] args) throws Exception {
       new VideoSearchServlet().getSynthesis( new VideoSearchServlet().filename);
       
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			String res=getSynthesis(filename);
			//String res=run();
			System.out.println("res:"+getUtf8String(res));

			System.out.println("res:"+getChinese(getUtf8String(res)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getChinese(String paramValue) {
    	String regex = "([\u4e00-\u9fa5]+)";
    	String str = "";
    	Matcher matcher = Pattern.compile(regex).matcher(paramValue);
    	while (matcher.find()) {
    	str+= matcher.group(0);
    	}
    	return str;
    	}
	private static String getUtf8String(String s) throws UnsupportedEncodingException  
    {  
        StringBuffer sb = new StringBuffer();  
        sb.append(s);  
        String xmlString = "";  
        String xmlUtf8 = "";  
        xmlString = new String(sb.toString().getBytes("GBK"));  
        xmlUtf8 = URLEncoder.encode(xmlString , "GBK");  
          
        return URLDecoder.decode(xmlUtf8, "UTF-8");  
    }  
	
	private static byte[] getFileContent(String filename) throws Exception {
        File file = new File(filename);
        if (!file.canRead()) {
            System.err.println("文件不存在或者不可读: " + file.getAbsolutePath());
            throw new Exception("file cannot read: " + file.getAbsolutePath());
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return ConnUtil.getInputStreamContent(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static String base64Encode(byte[] content) {
        /**
         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
         String str = encoder.encodeToString(content);
         **/

        char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
        String str = new String(chars);

        return str;
    }
    
    /**
     * 语音识别
     * @param fileName
     * @return
     */
    public String getSynthesis(String filePathName){
  
    	 // 可选：设置网络连接参数
    	client.setConnectionTimeoutInMillis(2000); //建立连接的超时时间（单位：毫秒）
        client.setSocketTimeoutInMillis(60000); //通过打开的连接传输数据的超时时间（单位：毫秒）
        // 对本地语音文件进行识别G:\voice
//        String path = "F:\\testvoice\\"+fileName;
        JSONObject asrRes = client.asr(filePathName,format, rate, null);
        System.out.println("asrRes:"+asrRes.toString());
        String Rtext = asrRes.optString("result");
        System.out.println("Rtext:"+Rtext);
        String[] ss = null;
        if(Rtext.contains("，")){
        	ss=Rtext.split("，");
        }else if(Rtext.contains("？")){
        	ss=Rtext.split("？");
        }else if(Rtext.contains("！")){
        	ss=Rtext.split("！");
        }else{
        	return Rtext;
        }
        String b="";
        for (int i = 0; i < ss.length-1; i++) {
            String a ="";
        	if(i==0){
        		a = ss[i].substring(2) ;
      		//System.out.println("sssssssssss..."+ss[i].substring(2));
        	}else{
        		a = ss[i];
        	//	System.out.println("s.s.s.s..."+ss[i]);
        	}
        	b = b + a;
		}
        return b;
        
    }
}
