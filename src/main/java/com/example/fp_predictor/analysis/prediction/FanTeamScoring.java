package com.example.fp_predictor.analysis.prediction;

/**
 * Скоринг сайта Fanteam.com - система начисления очков за удачные или неудачные действия.
 */
public class FanTeamScoring {

    /** Появление игрока на поле. */
    public final int appearance = 1;

    /** Отыгрыш игроком 60 минут. */
    public final int play60minutes = 1;

    /** Отыгрыш игроком полного матча. */
    public final int playFullTime = 1;

    /** Гол вратаря. */
    public final int goalkeeperGoal = 8;

    /** Гол защитника. */
    public final int defenderGoal = 6;

    /** Гол полузащитника. */
    public final int midfielderGoal = 5;

    /** Гол нападающего. */
    public final int forwardGoal = 4;

    /** Матч на ноль от защитника или вратаря. */
    public final int defenderCleanSheet = 4;

    /** Матч на ноль от полузащитника. */
    public final int midfielderCleanSheet = 1;

    /** Пропуск 2 голов (актуально для защитников и вратарей). */
    public final int miss2goals = -1;

    /** Фэнтези-ассист. */
    public final int assist = 3;

    /** Гол в свои ворота. */
    public final int ownGoal = -2;

    /** Незабитый пенальти. */
    public final int penaltyMissed = -2;

    /** Отбитый вратарем удар. */
    public final double save = 0.5;

    /** Отбитый вратарем пенальти. */
    public final int penaltySave = 5;

    /** Желтая карточка. */
    public final int yellowCard = -1;

    /** Красная карточка. */
    public final int redCard = -3;

    /** Штраф за нарушение, спровоцировавшее штрафной удар, с которого был забит гол. */
    public final int causeGoalFreeKick = -2;

    /** Штраф за нарушение, спровоцировавшее пенальти. */
    public final int causePenalty = -2;

    /** Положительный импакт (когда команда выиграла отрезок матча, в течение которого игрок находился на поле). */
    public final int positiveImpact = 1;

    /** Отрицательный импакт (когда команда проиграла отрезок матча, в течение которого игрок находился на поле). */
    public final int negativeImpact = -1;
}
