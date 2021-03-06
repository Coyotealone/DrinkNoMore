/**************************************************************************
 * YoutubeActivity.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Coyote
 * Licence     : 
 * Last update : Feb 19, 2015
 **************************************************************************/

package com.coyote.drinknomore.view.youtube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.coyote.drinknomore.ChoicesActivity;
import com.coyote.drinknomore.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    /** * YouTube player view. */
    private YouTubePlayerView youTubeView;
    /** * Button to skip video. */
    private Button btnskipMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_youtube);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        /**
         * Initializing video player with developer key.
         */
        youTubeView.initialize(Config.DEVELOPER_KEY, this);

        btnskipMusic = (Button) findViewById(R.id.btn_skip_music);
        btnskipMusic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * init intent in new view
                 * @param YoutubeActivity.this Context
                 * @param ChoicesActivity.class Class
                 */
                Intent intent = new Intent(YoutubeActivity.this, ChoicesActivity.class);
                /**
                 * show new intent.
                 * @param Intent intent view ChoicesActivity
                 */
                startActivity(intent);
            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
            YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
            YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            /**
             * Use loadVideo() will auto play video.
             * Use cueVideo() method, if you don't want to play it automatically.
             * player.loadVideo(Config.YOUTUBE_VIDEO_CODE);
             */
            player.cueVideo(Config.YOUTUBE_VIDEO_CODE);
            /**
             * Hiding player controls.
             * player.setPlayerStyle(PlayerStyle.CHROMELESS);
             */
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            /**
             * Retry initialization if user performed a recovery action.
             */
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}
