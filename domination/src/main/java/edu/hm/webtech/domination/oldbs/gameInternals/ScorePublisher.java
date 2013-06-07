package edu.hm.webtech.domination.oldbs.gameInternals;

/**
 * ScorePublisher for ScoreListener subscription.
 * Subscribed listeners will be informed f the score changes.
 *
 * @author Felix Schramm
 */
public interface ScorePublisher {

    /**
     * Subscribe to the publishing service, to get notified, when the score changes.
     * @param scoreListener  The ScoreListener to subscribe.
     */
    public void subscribeScoreChange(ScoreListener scoreListener);

}
