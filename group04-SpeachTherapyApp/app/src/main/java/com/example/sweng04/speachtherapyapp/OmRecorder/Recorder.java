package com.example.sweng04.speachtherapyapp.OmRecorder;

public interface Recorder {

    void startRecording();

    void stopRecording();

    void pauseRecording();

    void resumeRecording();

    /**
     * Interface definition for a callback to be invoked when a silence is measured.
     */
    interface OnSilenceListener {

        /**
         * Called when a silence measured
         *
         * @param silenceTime The silence measured
         */
        void onSilence(long silenceTime);
    }

}
