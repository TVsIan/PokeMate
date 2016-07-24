package dekk.pw.pokemate;

import POGOProtos.Inventory.Item.ItemIdOuterClass;
import POGOProtos.Networking.Envelopes.RequestEnvelopeOuterClass;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.GoogleLogin;
import com.pokegoapi.auth.PtcLogin;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import dekk.pw.pokemate.tasks.TaskController;
import okhttp3.OkHttpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by TimD on 7/21/2016.
 */
public class PokeMate {
    private static Properties properties = new Properties();
    private static Context context;
    private static TaskController taskControllor;

    public static void main(String[] args) throws IOException, LoginFailedException, RemoteServerException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        OkHttpClient http = builder.build();
        properties.load(new FileInputStream("config.properties"));
        String username = properties.getProperty("username");
        RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo auth;
        if (username.contains("@")) {
            auth = new GoogleLogin(http).login(username, properties.getProperty("password"));
            if (auth.hasToken()) {
                new PrintWriter("token.txt").println(auth.getToken().getContents());
            }
        } else {
            auth = new PtcLogin(http).login(username, properties.getProperty("password"));
        }
        System.out.println("Logged in as " + properties.getProperty("username"));
        PokemonGo go = new PokemonGo(auth, http);
        context = new Context(go, go.getPlayerProfile(),
                Double.parseDouble(properties.getProperty("speed")), false, auth, http);
        context.setPreferredBall(ItemIdOuterClass.ItemId.valueOf(properties.getProperty("preferred_ball", "ITEM_POKE_BALL")).getNumber());
        context.getLat().set(Double.parseDouble(properties.getProperty("latitude")));
        context.getLng().set(Double.parseDouble(properties.getProperty("longitude")));
        context.setGoogleApiKey(properties.getProperty("api-key"));
        context.setDupesToKeep(Integer.parseInt(properties.getProperty("dupes")));
        go.setLocation(context.getLat().get(), context.getLng().get(), 0);
        taskControllor = new TaskController(context);
        taskControllor.start();
    }

    public static Context getContext() {
        return context;
    }
}
