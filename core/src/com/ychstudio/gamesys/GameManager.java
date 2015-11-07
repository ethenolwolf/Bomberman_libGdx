package com.ychstudio.gamesys;

import com.artemis.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import java.util.LinkedList;
import java.util.Queue;

public class GameManager implements Disposable {

    private static final GameManager instance = new GameManager();

    private final AssetManager assetManager;

    public static final float PPM = 16.0f;

    public static final short NOTHING_BIT = 0;
    public static final short INDESTRUCTIIBLE_BIT = 1;
    public static final short BREAKABLE_BIT = 1 << 1;
    public static final short PLAYER_BIT = 1 << 2;
    public static final short BOMB_BIT = 1 << 3;
    public static final short EXPLOSION_BIT = 1 << 4;
    public static final short ENEMY_BIT = 1 << 5;
    public static final short POWERUP_BIT = 1 << 6;
    public static final short PORTAL_BIT = 1 << 7;

    public static boolean resetPlayerAbilities = false;

    public static int playerBombCapacity = 3;
    public static int playerBombLeft = 0;
    public static float playerBombRegeratingTime = 2.0f;
    public static float playerBombRegeratingTimeLeft = 0;
    public static int playerMaxSpeed = 0;
    public static int playerBombPower = 0;
    public static boolean playerKickBomb = false;
    public static boolean playerRemoteBomb = false;

    private Vector2 playerRespawnPosition;
    private Vector2 portalPosition;

    public static int playerLives = 3;

    public static int enemiesLeft;
    public static boolean levelCompleted;

    private Queue<Entity> remoteBombQueue;

    private String soundPath = "sounds/";
    private String musicPath = "music/";

    private GameManager() {
        // load resources
        assetManager = new AssetManager();

        assetManager.load("img/actors.pack", TextureAtlas.class);

        assetManager.load("sounds/Pickup.ogg", Sound.class);
        assetManager.load("sounds/PlaceBomb.ogg", Sound.class);
        assetManager.load("sounds/KickBomb.ogg", Sound.class);
        assetManager.load("sounds/Powerup.ogg", Sound.class);
        assetManager.load("sounds/Explosion.ogg", Sound.class);
        assetManager.load("sounds/Die.ogg", Sound.class);
        assetManager.load("sounds/EnemyDie.ogg", Sound.class);
        assetManager.load("sounds/PortalAppears.ogg", Sound.class);
        assetManager.load("sounds/Teleport.ogg", Sound.class);

        assetManager.load("music/SuperBomberman-Area1.ogg", Music.class);

        assetManager.finishLoading();

        playerRespawnPosition = new Vector2();
        portalPosition = new Vector2();

        remoteBombQueue = new LinkedList<Entity>();
    }

    public static GameManager getInstance() {
        return instance;
    }

    public static void resetPlayerAbilities() {
        playerBombCapacity = 3;
        playerMaxSpeed = 0;
        playerBombPower = 0;
        playerBombRegeratingTime = 2.0f;
        playerKickBomb = false;
        playerRemoteBomb = false;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void playSound(String soundName) {
        playSound(soundName, 1.0f, 1.0f, 0f);
    }

    public void playSound(String soundName, float volume, float pitch, float pan) {
        Sound sound = assetManager.get(soundPath + soundName, Sound.class);
        sound.play(volume, pitch, pan);
    }

    public void playMusic(String musicName) {
        Music music = assetManager.get(musicPath + musicName);
        if (!music.isPlaying()) {
            music.setLooping(true);
            music.play();
        }
    }

    public Queue<Entity> getRemoteBombDeque() {
        return remoteBombQueue;
    }

    public Vector2 getPlayerRespawnPosition() {
        return playerRespawnPosition;
    }

    public void setPlayerRespawnPosition(Vector2 position) {
        playerRespawnPosition.set(position);
    }

    public Vector2 getPortalPosition() {
        return portalPosition;
    }

    public void setPortalPosition(Vector2 position) {
        portalPosition.set(position);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

}
