package dekk.pw.pokemate;

import POGOProtos.Networking.Envelopes.RequestEnvelopeOuterClass;
import com.google.common.util.concurrent.AtomicDouble;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.map.MapObjects;
import com.pokegoapi.api.player.PlayerProfile;
import okhttp3.OkHttpClient;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by TimD on 7/21/2016.
 */
public class Context {
    private OkHttpClient http;
    private PokemonGo api;
    private AtomicDouble lat = new AtomicDouble();
    private AtomicDouble lng = new AtomicDouble();
    private PlayerProfile profile;
    private double speed;
    private AtomicBoolean walking = new AtomicBoolean(false);
    private RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo authInfo;
    private int preferredBall;
    private String googleApiKey;
    private int dupesToKeep;

    public Context(PokemonGo go, PlayerProfile profile, double speed, boolean walking, RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo authInfo, OkHttpClient http) {
        this.api = go;
        this.profile = profile;
        this.speed = speed;
        this.walking.set(walking);
        this.authInfo = authInfo;
        this.http = http;
    }

    public void setGoogleApiKey(String googleApiKey) {
        this.googleApiKey = googleApiKey;
    }

    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public AtomicDouble getLat() {
        return lat;
    }

    public AtomicDouble getLng() {
        return lng;
    }

    public int getPreferredBall() {
        return preferredBall;
    }

    public void setDupesToKeep(int dupesToKeep) { this.dupesToKeep = dupesToKeep; }

    public int getDupesToKeep() { return dupesToKeep; }

    public void setPreferredBall(int preferredBall) {
        this.preferredBall = preferredBall;
    }

    public OkHttpClient getHttp() {
        return http;
    }

    public void setHttp(OkHttpClient http) {
        this.http = http;
    }

    public PokemonGo getApi() {
        return api;
    }

    public void setApi(PokemonGo api) {
        this.api = api;
    }

    public PlayerProfile getProfile() {
        return profile;
    }

    public void setProfile(PlayerProfile profile) {
        this.profile = profile;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isWalking() {
        return walking.get();
    }

    public AtomicBoolean getWalking() {
        return walking;
    }

    public RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(RequestEnvelopeOuterClass.RequestEnvelope.AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
}
